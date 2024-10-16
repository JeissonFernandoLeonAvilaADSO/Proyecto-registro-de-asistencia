package com.proyectoasistencia.prasis.services.UsersServices;

import com.proyectoasistencia.prasis.models.UsersModels.InstructorModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.sql.SQLOutput;
import java.sql.Statement;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class InstructorService {

    private final JdbcTemplate jdbcTemplate;
    private final BCryptPasswordEncoder passwordEncoder;
    private static final Logger logger = LoggerFactory.getLogger(InstructorService.class);


    @Autowired
    public InstructorService(JdbcTemplate jdbcTemplate, BCryptPasswordEncoder passwordEncoder) {
        this.jdbcTemplate = jdbcTemplate;
        this.passwordEncoder = passwordEncoder;

    }

    @Transactional(readOnly = true)
    public InstructorModel getInstructor(String documento) {
        logger.info("Iniciando búsqueda de instructor con documento: {}", documento);

        try {
            // Paso 1: Obtener todos los IDPerfilUsuario que coincidan con el documento y que tengan el rol de instructor
            String sqlGetPerfilUsuarioIds = """
        SELECT pu.ID
        FROM perfilusuario pu
        INNER JOIN rol r ON pu.IDRol = r.ID
        WHERE pu.Documento = ? AND r.TipoRol = 'Instructor'
        """;

            List<Integer> perfilUsuarioIds = jdbcTemplate.queryForList(sqlGetPerfilUsuarioIds, new Object[]{documento}, Integer.class);
            logger.debug("PerfilUsuarioIds encontrados para documento {}: {}", documento, perfilUsuarioIds);

            if (perfilUsuarioIds.isEmpty()) {
                logger.warn("No se encontró ningún perfil de usuario con documento: {}", documento);
                throw new RuntimeException("No se encontró el perfil de usuario con documento: " + documento);
            }

            if (perfilUsuarioIds.size() > 1) {
                logger.error("Se encontraron múltiples perfiles de usuario para el documento: {}. IDs: {}", documento, perfilUsuarioIds);
                throw new RuntimeException("Se encontraron múltiples perfiles de usuario para el documento: " + documento);
            }

            Integer instructorId = perfilUsuarioIds.get(0);
            logger.info("PerfilUsuarioId seleccionado: {}", instructorId);

            // Paso 2: Obtener la información básica del instructor
            String sqlGetInstructorBasicInfo = """
                                            SELECT
                                                u.Usuario AS Usuario,
                                                u.Contraseña AS Contraseña,
                                                pu.Documento AS Documento,
                                                td.TipoDocumento AS TipoDocumento,
                                                pu.Nombres AS Nombres,
                                                pu.Apellidos AS Apellidos,
                                                pu.FecNacimiento AS FecNacimiento,
                                                pu.Telefono AS Telefono,
                                                pu.Correo AS Correo,
                                                g.TiposGeneros AS Genero,
                                                CONCAT(d.nombre_departamento, ' - ', m.nombre_municipio, ' - ', b.nombre_barrio) AS Residencia,
                                                pu.Estado AS Estado
                                            FROM
                                                perfilusuario pu
                                                    INNER JOIN usuario u ON pu.IDUsuario = u.ID
                                                    INNER JOIN tipodocumento td ON pu.IDTipoDocumento = td.ID
                                                    INNER JOIN genero g ON pu.IDGenero = g.ID
                                                    INNER JOIN barrios b ON pu.IDBarrio = b.ID
                                                    INNER JOIN municipios m ON b.id_municipio = m.ID
                                                    INNER JOIN departamentos d ON m.id_departamento = d.ID
                                            WHERE
                                                pu.ID = ?
                                            LIMIT 1""";

            InstructorModel instructor = jdbcTemplate.queryForObject(sqlGetInstructorBasicInfo, new Object[]{instructorId}, (rs, rowNum) -> {
                return InstructorModel.builder()
                        .user(rs.getString("Usuario"))
                        .password(rs.getString("Contraseña"))
                        .documento(rs.getString("Documento"))
                        .tipoDocumento(rs.getString("TipoDocumento"))
                        .nombres(rs.getString("Nombres"))
                        .apellidos(rs.getString("Apellidos"))
                        .fechaNacimiento(rs.getDate("FecNacimiento"))
                        .telefono(rs.getString("Telefono"))
                        .correo(rs.getString("Correo"))
                        .genero(rs.getString("Genero"))
                        .residencia(rs.getString("Residencia"))
                        .estado(rs.getString("Estado"))
                        .clasesFormacion(new ArrayList<>())
                        .fichas(new ArrayList<>())
                        .programasFormacion(new ArrayList<>())
                        .jornadasFormacion(new ArrayList<>())
                        .nivelesFormacion(new ArrayList<>())
                        .sedes(new ArrayList<>())
                        .areas(new ArrayList<>())
                        .build();
            });

            if (instructor == null) {
                logger.warn("No se pudo obtener la información básica del instructor con IDPerfilUsuario: {}", instructorId);
                throw new RuntimeException("No se pudo obtener la información del instructor.");
            }

            logger.info("Información básica del instructor obtenida: {}", instructor);

            // Paso 3: Obtener las clases, fichas y programas asociados al instructor desde la tabla claseformacion_instructor_ficha
            String sqlGetClassFichaInfo = """
                SELECT cf.NombreClase AS ClaseFormacion, f.NumeroFicha AS Ficha, pf.ProgramaFormacion AS ProgramaFormacion,
                       jf.JornadasFormacion AS JornadaFormacion, nf.NivelFormacion AS NivelFormacion, s.CentroFormacion AS Sede, a.Area AS Area
                FROM claseformacion_instructor_ficha cif
                         INNER JOIN claseformacion cf ON cif.IDClaseFormacion = cf.ID
                         INNER JOIN fichas f ON cif.IDFicha = f.ID
                         INNER JOIN programaformacion pf ON f.IDProgramaFormacion = pf.ID
                         INNER JOIN jornadaformacion jf ON f.IDJornadaFormacion = jf.ID
                         INNER JOIN nivelformacion nf ON pf.IDNivelFormacion = nf.ID
                         INNER JOIN sede s ON pf.IDSede = s.ID
                         INNER JOIN areas a ON pf.IDArea = a.ID
                WHERE cif.IDPerfilUsuario = ?""";

            jdbcTemplate.query(sqlGetClassFichaInfo, new Object[]{instructorId}, (rs) -> {
                // Agregar clases de formación
                instructor.getClasesFormacion().add(rs.getString("ClaseFormacion"));

                // Agregar fichas
                instructor.getFichas().add(rs.getInt("Ficha"));

                // Agregar programas de formación
                instructor.getProgramasFormacion().add(rs.getString("ProgramaFormacion"));

                // Agregar jornadas de formación
                instructor.getJornadasFormacion().add(rs.getString("JornadaFormacion"));

                // Agregar niveles de formación
                instructor.getNivelesFormacion().add(rs.getString("NivelFormacion"));

                // Agregar sedes
                instructor.getSedes().add(rs.getString("Sede"));

                // Agregar áreas
                instructor.getAreas().add(rs.getString("Area"));
            });

            logger.info("Información completa del instructor obtenida exitosamente: {}", instructor);
            return instructor;

        } catch (IncorrectResultSizeDataAccessException e) {
            logger.error("Error de tamaño incorrecto en los resultados: ", e);
            throw new RuntimeException("Error en la búsqueda del instructor.");
        } catch (Exception e) {
            logger.error("Error al obtener el instructor: ", e);
            throw new RuntimeException("Error al obtener el instructor.");
        }
    }



    @Transactional(rollbackFor = Exception.class)
    public InstructorModel createInstructor(InstructorModel instructor) {
        // Validación de datos de entrada
        if (instructor == null) {
            throw new IllegalArgumentException("El objeto instructor no debe ser nulo.");
        }

        // Validar que los datos requeridos no sean nulos o vacíos
        if (instructor.getUser() == null || instructor.getUser().isEmpty()) {
            throw new IllegalArgumentException("El nombre de usuario es requerido.");
        }
        if (instructor.getPassword() == null || instructor.getPassword().isEmpty()) {
            throw new IllegalArgumentException("La contraseña es requerida.");
        }
        if (instructor.getDocumento() == null || instructor.getDocumento().isEmpty()) {
            throw new IllegalArgumentException("El documento es requerido.");
        }
        if (instructor.getTipoDocumento() == null || instructor.getTipoDocumento().isEmpty()) {
            throw new IllegalArgumentException("El tipo de documento es requerido.");
        }
        if (instructor.getNombres() == null || instructor.getNombres().isEmpty()) {
            throw new IllegalArgumentException("El nombre es requerido.");
        }
        if (instructor.getApellidos() == null || instructor.getApellidos().isEmpty()) {
            throw new IllegalArgumentException("El apellido es requerido.");
        }
        if (instructor.getFechaNacimiento() == null) {
            throw new IllegalArgumentException("La fecha de nacimiento es requerida.");
        }
        if (instructor.getTelefono() == null || instructor.getTelefono().isEmpty()) {
            throw new IllegalArgumentException("El teléfono es requerido.");
        }
        if (instructor.getCorreo() == null || instructor.getCorreo().isEmpty()) {
            throw new IllegalArgumentException("El correo es requerido.");
        }
        if (instructor.getGenero() == null || instructor.getGenero().isEmpty()) {
            throw new IllegalArgumentException("El género es requerido.");
        }
        if (instructor.getResidencia() == null || instructor.getResidencia().isEmpty()) {
            throw new IllegalArgumentException("La residencia es requerida.");
        }

        try {
            // Verificar si el usuario ya existe
            Integer countUsuario = jdbcTemplate.queryForObject(
                    "SELECT COUNT(*) FROM usuario WHERE Usuario = ?",
                    new Object[]{instructor.getUser()},
                    Integer.class
            );
            if (countUsuario != null && countUsuario > 0) {
                throw new IllegalArgumentException("El nombre de usuario ya existe: " + instructor.getUser());
            }

            // Verificar si el documento ya está registrado
            Integer countDocumento = jdbcTemplate.queryForObject(
                    "SELECT COUNT(*) FROM perfilusuario WHERE Documento = ?",
                    new Object[]{instructor.getDocumento()},
                    Integer.class
            );
            if (countDocumento != null && countDocumento > 0) {
                throw new IllegalArgumentException("El documento ya está registrado: " + instructor.getDocumento());
            }

            // Paso 1: Insertar en la tabla `usuario` y obtener el ID
            String sqlInsertUsuario = "INSERT INTO usuario (Usuario, Contraseña) VALUES (?, ?)";
            KeyHolder keyHolderUsuario = new GeneratedKeyHolder();

            int rowsAffectedUsuario = jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(sqlInsertUsuario, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, instructor.getUser());
                ps.setString(2, passwordEncoder.encode(instructor.getPassword()));
                return ps;
            }, keyHolderUsuario);

            if (rowsAffectedUsuario == 0) {
                throw new RuntimeException("La inserción en la tabla usuario no afectó a ninguna fila.");
            }

            Number generatedUserId = keyHolderUsuario.getKey();
            if (generatedUserId == null) {
                throw new RuntimeException("No se generó una clave primaria para el usuario.");
            }

            int usuarioId = generatedUserId.intValue();

            // Paso 2: Obtener el ID de tipo de documento
            Integer idTipoDocumento = jdbcTemplate.queryForObject(
                    "SELECT ID FROM tipodocumento WHERE TipoDocumento = ?",
                    new Object[]{instructor.getTipoDocumento()},
                    Integer.class
            );
            if (idTipoDocumento == null) {
                throw new IllegalArgumentException("Tipo de documento inválido: " + instructor.getTipoDocumento());
            }

            // Paso 3: Obtener el ID de género
            Integer idGenero = jdbcTemplate.queryForObject(
                    "SELECT ID FROM genero WHERE TiposGeneros = ?",
                    new Object[]{instructor.getGenero()},
                    Integer.class
            );
            if (idGenero == null) {
                throw new IllegalArgumentException("Género inválido: " + instructor.getGenero());
            }

            // Paso 4: Extraer el nombre del barrio de la residencia
            String residencia = instructor.getResidencia();
            String[] residenciaParts = residencia.split(" - ");
            if (residenciaParts.length != 3) {
                throw new IllegalArgumentException("La residencia debe tener el formato 'municipio - departamento - barrio'.");
            }
            String nombreBarrio = residenciaParts[2].trim();

            // Paso 5: Obtener el ID del barrio
            Integer idBarrio = jdbcTemplate.queryForObject(
                    "SELECT ID FROM barrios WHERE nombre_barrio = ?",
                    new Object[]{nombreBarrio},
                    Integer.class
            );
            if (idBarrio == null) {
                throw new IllegalArgumentException("Barrio inválido: " + nombreBarrio);
            }

            // Paso 6: Insertar en la tabla `perfilusuario` y asignar el rol de instructor (IDRol = 1)
            String sqlInsertPerfilUsuario = """
                INSERT INTO perfilusuario (
                    IDUsuario, Documento, IDTipoDocumento, Nombres, Apellidos, FecNacimiento, 
                    Telefono, Correo, IDGenero, IDRol, IDBarrio
                ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, 1, ?)
            """;
            jdbcTemplate.update(sqlInsertPerfilUsuario,
                    usuarioId,
                    instructor.getDocumento(),
                    idTipoDocumento,
                    instructor.getNombres(),
                    instructor.getApellidos(),
                    new java.sql.Date(instructor.getFechaNacimiento().getTime()),
                    instructor.getTelefono(),
                    instructor.getCorreo(),
                    idGenero,
                    idBarrio
            );

            logger.info("Instructor creado exitosamente con usuario ID: {}", usuarioId);
            return instructor;

        } catch (DataAccessException e) {
            logger.error("Error al acceder a la base de datos: {}", e.getMessage());
            throw new RuntimeException("Error al acceder a la base de datos: " + e.getMessage(), e);
        } catch (Exception e) {
            logger.error("Error al crear el instructor: {}", e.getMessage());
            throw new RuntimeException("Error al crear el instructor: " + e.getMessage(), e);
        }
    }


    @Transactional(rollbackFor = Exception.class)
    public InstructorModel updateInstructor(String documento, InstructorModel instructor) {
        // Validación de datos de entrada
        if (instructor == null) {
            throw new IllegalArgumentException("El objeto instructor no debe ser nulo.");
        }

        // Validar que los datos requeridos no sean nulos o vacíos
        if (instructor.getUser() == null || instructor.getUser().isEmpty()) {
            throw new IllegalArgumentException("El nombre de usuario es requerido.");
        }
        if (instructor.getPassword() == null || instructor.getPassword().isEmpty()) {
            throw new IllegalArgumentException("La contraseña es requerida.");
        }
        if (instructor.getDocumento() == null || instructor.getDocumento().isEmpty()) {
            throw new IllegalArgumentException("El documento es requerido.");
        }
        if (instructor.getTipoDocumento() == null || instructor.getTipoDocumento().isEmpty()) {
            throw new IllegalArgumentException("El tipo de documento es requerido.");
        }
        if (instructor.getNombres() == null || instructor.getNombres().isEmpty()) {
            throw new IllegalArgumentException("El nombre es requerido.");
        }
        if (instructor.getApellidos() == null || instructor.getApellidos().isEmpty()) {
            throw new IllegalArgumentException("El apellido es requerido.");
        }
        if (instructor.getFechaNacimiento() == null) {
            throw new IllegalArgumentException("La fecha de nacimiento es requerida.");
        }
        if (instructor.getTelefono() == null || instructor.getTelefono().isEmpty()) {
            throw new IllegalArgumentException("El teléfono es requerido.");
        }
        if (instructor.getCorreo() == null || instructor.getCorreo().isEmpty()) {
            throw new IllegalArgumentException("El correo es requerido.");
        }
        if (instructor.getGenero() == null || instructor.getGenero().isEmpty()) {
            throw new IllegalArgumentException("El género es requerido.");
        }
        if (instructor.getResidencia() == null || instructor.getResidencia().isEmpty()) {
            throw new IllegalArgumentException("La residencia es requerida.");
        }

        try {
            // Paso 1: Obtener el ID del perfil de usuario basado en el documento
            String sqlGetPerfilUsuarioId = "SELECT ID FROM perfilusuario WHERE Documento = ?";
            Integer perfilUsuarioId = jdbcTemplate.queryForObject(sqlGetPerfilUsuarioId, new Object[]{documento}, Integer.class);

            if (perfilUsuarioId == null) {
                throw new RuntimeException("No se encontró el perfil de usuario con documento: " + documento);
            }

            // Paso 2: Obtener el ID del usuario asociado al perfil
            String sqlGetUsuarioId = "SELECT IDUsuario FROM perfilusuario WHERE Documento = ?";
            Integer usuarioId = jdbcTemplate.queryForObject(sqlGetUsuarioId, new Object[]{documento}, Integer.class);

            if (usuarioId == null) {
                throw new RuntimeException("No se encontró el usuario asociado al documento: " + documento);
            }

            // Paso 3: Verificar si el nuevo nombre de usuario ya existe para otro usuario
            Integer countUsuario = jdbcTemplate.queryForObject(
                    "SELECT COUNT(*) FROM usuario WHERE Usuario = ? AND ID != ?",
                    new Object[]{instructor.getUser(), usuarioId},
                    Integer.class
            );
            if (countUsuario != null && countUsuario > 0) {
                throw new IllegalArgumentException("El nombre de usuario ya existe: " + instructor.getUser());
            }

            // Paso 4: Verificar si el nuevo documento ya existe para otro perfil de usuario
            Integer countDocumento = jdbcTemplate.queryForObject(
                    "SELECT COUNT(*) FROM perfilusuario WHERE Documento = ? AND ID != ?",
                    new Object[]{instructor.getDocumento(), perfilUsuarioId},
                    Integer.class
            );
            if (countDocumento != null && countDocumento > 0) {
                throw new IllegalArgumentException("El documento ya está registrado: " + instructor.getDocumento());
            }

            // Paso 5: Obtener el ID de tipo de documento
            Integer idTipoDocumento = jdbcTemplate.queryForObject(
                    "SELECT ID FROM tipodocumento WHERE TipoDocumento = ?",
                    new Object[]{instructor.getTipoDocumento()},
                    Integer.class
            );
            if (idTipoDocumento == null) {
                throw new IllegalArgumentException("Tipo de documento inválido: " + instructor.getTipoDocumento());
            }

            // Paso 6: Obtener el ID de género
            Integer idGenero = jdbcTemplate.queryForObject(
                    "SELECT ID FROM genero WHERE TiposGeneros = ?",
                    new Object[]{instructor.getGenero()},
                    Integer.class
            );
            if (idGenero == null) {
                throw new IllegalArgumentException("Género inválido: " + instructor.getGenero());
            }

            // Paso 7: Extraer el nombre del barrio de la residencia
            String residencia = instructor.getResidencia();
            String[] residenciaParts = residencia.split(" - ");
            if (residenciaParts.length != 3) {
                throw new IllegalArgumentException("La residencia debe tener el formato 'municipio - departamento - barrio'.");
            }
            String nombreBarrio = residenciaParts[2].trim();

            // Paso 8: Obtener el ID del barrio
            Integer idBarrio = jdbcTemplate.queryForObject(
                    "SELECT ID FROM barrios WHERE nombre_barrio = ?",
                    new Object[]{nombreBarrio},
                    Integer.class
            );
            if (idBarrio == null) {
                throw new IllegalArgumentException("Barrio inválido: " + nombreBarrio);
            }

            // Paso 9: Actualizar la tabla `usuario`
            String sqlUpdateUsuario = """
            UPDATE usuario
            SET Usuario = ?, Contraseña = ?
            WHERE ID = ?
        """;
            int rowsAffectedUsuario = jdbcTemplate.update(sqlUpdateUsuario,
                    instructor.getUser(),
                    passwordEncoder.encode(instructor.getPassword()),
                    usuarioId
            );
            if (rowsAffectedUsuario == 0) {
                throw new RuntimeException("La actualización en la tabla usuario no afectó a ninguna fila.");
            }

            // Paso 10: Actualizar la tabla `perfilusuario`
            String sqlUpdatePerfilUsuario = """
            UPDATE perfilusuario
            SET Documento = ?, IDTipoDocumento = ?, Nombres = ?, Apellidos = ?, FecNacimiento = ?, 
                Telefono = ?, Correo = ?, IDGenero = ?, IDBarrio = ?
            WHERE ID = ?
        """;
            int rowsAffectedPerfilUsuario = jdbcTemplate.update(sqlUpdatePerfilUsuario,
                    instructor.getDocumento(),
                    idTipoDocumento,
                    instructor.getNombres(),
                    instructor.getApellidos(),
                    new java.sql.Date(instructor.getFechaNacimiento().getTime()),
                    instructor.getTelefono(),
                    instructor.getCorreo(),
                    idGenero,
                    idBarrio,
                    perfilUsuarioId
            );
            if (rowsAffectedPerfilUsuario == 0) {
                throw new RuntimeException("La actualización en la tabla perfilusuario no afectó a ninguna fila.");
            }

            logger.info("Instructor actualizado exitosamente con perfil ID: {}", perfilUsuarioId);
            return instructor;

        } catch (DataAccessException e) {
            logger.error("Error al acceder a la base de datos: {}", e.getMessage());
            throw new RuntimeException("Error al acceder a la base de datos: " + e.getMessage(), e);
        } catch (Exception e) {
            logger.error("Error al actualizar el instructor: {}", e.getMessage());
            throw new RuntimeException("Error al actualizar el instructor: " + e.getMessage(), e);
        }
    }



    // Eliminar un instructor por documento
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteInstructor(String documento) {
        Integer perfilUsuarioId = null;
        Integer usuarioId = null;

        try {
            // 1. Obtener todos los IDs necesarios antes de eliminar
            // Obtener el ID del perfil de usuario
            String sqlGetPerfilUsuarioId = "SELECT ID FROM perfilusuario WHERE Documento = ?";
            System.out.println("Obteniendo ID del perfil de usuario para el documento: " + documento);
            perfilUsuarioId = jdbcTemplate.queryForObject(sqlGetPerfilUsuarioId, new Object[]{documento}, Integer.class);
            System.out.println("ID del perfil de usuario obtenido: " + perfilUsuarioId);

            // Obtener el IDUsuario desde perfilusuario
            String sqlGetUsuarioId = "SELECT IDUsuario FROM perfilusuario WHERE ID = ?";
            System.out.println("Obteniendo ID de usuario para el perfil de usuario con ID: " + perfilUsuarioId);
            usuarioId = jdbcTemplate.queryForObject(sqlGetUsuarioId, new Object[]{perfilUsuarioId}, Integer.class);
            System.out.println("ID de usuario obtenido: " + usuarioId);


        } catch (EmptyResultDataAccessException e) {
            System.out.println("Error: No se encontró alguno de los registros necesarios para el documento: " + documento);
            throw new RuntimeException("Error: No se encontró alguno de los registros necesarios para el documento: " + documento, e);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error inesperado al eliminar el instructor: " + e.getMessage());
            throw new RuntimeException("Error inesperado al eliminar el instructor: " + e.getMessage(), e);
        }
        return false;
    }

    // Obtener todos los instructores
    public List<InstructorModel> getAllInstructors() {
        String consulta = """
            SELECT us.Usuario AS Usuario,
                   us.Contraseña AS Contraseña,
                   pf.Documento AS Documento,
                   td.TipoDocumento AS TipoDocumento,
                   pf.Nombres AS Nombres,
                   pf.Apellidos AS Apellidos,
                   pf.FecNacimiento AS FecNacimiento,
                   pf.Telefono AS Telefono,
                   pf.Correo AS Correo,
                   ge.TiposGeneros AS TiposGeneros,
                   CONCAT(dept.nombre_departamento, ' - ', mun.nombre_municipio, ' - ', barrios.nombre_barrio) AS Residencia,
                   GROUP_CONCAT(DISTINCT fc.NumeroFicha) AS NumeroFichas,
                   GROUP_CONCAT(DISTINCT pform.ProgramaFormacion) AS ProgramasFormacion,
                   GROUP_CONCAT(DISTINCT jf.JornadasFormacion) AS JornadasFormacion,
                   GROUP_CONCAT(DISTINCT nf.NivelFormacion) AS NivelesFormacion,
                   GROUP_CONCAT(DISTINCT sd.CentroFormacion) AS CentrosFormacion,
                   GROUP_CONCAT(DISTINCT areas.Area) AS Areas
            FROM instructor i
                INNER JOIN perfilusuario pf ON i.IDPerfilUsuario = pf.ID
                INNER JOIN usuario us ON pf.IDUsuario = us.ID
                INNER JOIN tipodocumento td ON pf.IDTipoDocumento = td.ID
                INNER JOIN genero ge ON pf.IDGenero = ge.ID
                INNER JOIN barrios ON pf.IDBarrio = barrios.ID
                INNER JOIN municipios mun ON barrios.id_municipio = mun.ID
                INNER JOIN departamentos dept ON mun.id_departamento = dept.ID
                LEFT JOIN instructor_ficha inf ON i.ID = inf.IDInstructor
                LEFT JOIN fichas fc ON inf.IDFicha = fc.ID
                LEFT JOIN programaformacion pform ON fc.IDProgramaFormacion = pform.ID
                LEFT JOIN jornadaformacion jf ON pform.IDJornadaFormacion = jf.ID
                LEFT JOIN nivelformacion nf ON pform.IDNivelFormacion = nf.ID
                LEFT JOIN sede sd ON pform.IDSede = sd.ID
                LEFT JOIN areas ON pform.IDArea = areas.ID
            GROUP BY pf.Documento
            """;

        try {
            return jdbcTemplate.query(consulta, (rs, rowNum) -> {
                // Convertir las cadenas concatenadas en listas
                List<Integer> fichas = Arrays.stream(Optional.ofNullable(rs.getString("NumeroFichas")).orElse("").split(","))
                        .filter(s -> !s.isEmpty())
                        .map(Integer::parseInt)
                        .collect(Collectors.toList());

                List<String> programasFormacion = Arrays.asList(Optional.ofNullable(rs.getString("ProgramasFormacion")).orElse("").split(","));
                List<String> jornadasFormacion = Arrays.asList(Optional.ofNullable(rs.getString("JornadasFormacion")).orElse("").split(","));
                List<String> nivelesFormacion = Arrays.asList(Optional.ofNullable(rs.getString("NivelesFormacion")).orElse("").split(","));
                List<String> centrosFormacion = Arrays.asList(Optional.ofNullable(rs.getString("CentrosFormacion")).orElse("").split(","));
                List<String> areas = Arrays.asList(Optional.ofNullable(rs.getString("Areas")).orElse("").split(","));

                return InstructorModel.builder()
                        .user(rs.getString("Usuario"))
                        .password(rs.getString("Contraseña"))
                        .documento(rs.getString("Documento"))
                        .tipoDocumento(rs.getString("TipoDocumento"))
                        .nombres(rs.getString("Nombres"))
                        .apellidos(rs.getString("Apellidos"))
                        .fechaNacimiento(rs.getDate("FecNacimiento"))
                        .telefono(rs.getString("Telefono"))
                        .correo(rs.getString("Correo"))
                        .genero(rs.getString("TiposGeneros"))
                        .residencia(rs.getString("Residencia"))
                        .fichas(fichas)
                        .programasFormacion(programasFormacion)
                        .jornadasFormacion(jornadasFormacion)
                        .nivelesFormacion(nivelesFormacion)
                        .sedes(centrosFormacion)
                        .areas(areas)
                        .build();
            });

        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList(); // Devuelve una lista vacía en caso de error
        }
    }
    public Integer getInstructorIdByDocumento(String documento) {
        try {
            // Consulta SQL para obtener el ID del instructor usando el documento en perfilusuario
            String sqlGetInstructorIdByDocumento = """
            SELECT i.ID 
            FROM instructor i
            INNER JOIN perfilusuario pu ON i.IDPerfilUsuario = pu.ID
            WHERE pu.Documento = ?
        """;

            // Ejecutar la consulta y retornar el resultado
            return jdbcTemplate.queryForObject(sqlGetInstructorIdByDocumento, new Object[]{documento}, Integer.class);
        } catch (EmptyResultDataAccessException e) {
            // Manejar el caso donde no se encuentra el instructor
            System.err.println("No se encontró el instructor con documento: " + documento);
            return null;
        } catch (Exception e) {
            // Capturar y manejar cualquier otro error
            e.printStackTrace();
            throw new RuntimeException("Error al obtener el ID del instructor: " + e.getMessage(), e);
        }
    }


    /**
     * Habilita un Instructor dado su documento.
     *
     * @param documento Documento del Instructor a habilitar.
     * @return true si la operación fue exitosa, false en caso contrario.
     */
    public boolean habilitarInstructor(String documento) {
        // Verificar si el PerfilUsuario está referenciado en la tabla Instructor
        String verificarSql = "SELECT COUNT(*) FROM Instructor WHERE IDPerfilUsuario = (SELECT id FROM perfilusuario WHERE Documento = ?)";
        Integer count = jdbcTemplate.queryForObject(verificarSql, new Object[]{documento}, Integer.class);

        if (count != null && count > 0) {
            // Actualizar el estado en perfilusuario
            String actualizarSql = "UPDATE perfilusuario SET Estado = 'Habilitado' WHERE Documento = ?";
            int rowsAffected = jdbcTemplate.update(actualizarSql, documento);
            return rowsAffected > 0;
        } else {
            // No existe un Instructor asociado a este documento
            return false;
        }
    }

    /**
     * Inhabilita un Instructor dado su documento.
     *
     * @param documento Documento del Instructor a inhabilitar.
     * @return true si la operación fue exitosa, false en caso contrario.
     */
    public boolean inhabilitarInstructor(String documento) {
        // Verificar si el PerfilUsuario está referenciado en la tabla Instructor
        String verificarSql = "SELECT COUNT(*) FROM Instructor WHERE IDPerfilUsuario = (SELECT id FROM perfilusuario WHERE Documento = ?)";
        Integer count = jdbcTemplate.queryForObject(verificarSql, new Object[]{documento}, Integer.class);

        if (count != null && count > 0) {
            // Actualizar el estado en perfilusuario
            String actualizarSql = "UPDATE perfilusuario SET Estado = 'Deshabilitado' WHERE Documento = ?";
            int rowsAffected = jdbcTemplate.update(actualizarSql, documento);
            return rowsAffected > 0;
        } else {
            // No existe un Instructor asociado a este documento
            return false;
        }
    }

}

