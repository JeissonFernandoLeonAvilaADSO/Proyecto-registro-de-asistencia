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
            // Paso 1: Obtener todos los IDPerfilUsuario que coincidan
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

            Integer perfilUsuarioId = perfilUsuarioIds.get(0);
            logger.info("PerfilUsuarioId seleccionado: {}", perfilUsuarioId);

            // Paso 2: Obtener la información básica del instructor con ClaseFormacion como String
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
                COALESCE(
                    (SELECT cf.NombreClase
                     FROM claseformacion cf
                     WHERE cf.IDInstructor = i.ID
                     LIMIT 1),
                    'Clase no asignada'
                ) AS ClaseFormacion,
                pu.Estado AS Estado
            FROM
                perfilusuario pu
                INNER JOIN usuario u ON pu.IDUsuario = u.ID
                INNER JOIN tipodocumento td ON pu.IDTipoDocumento = td.ID
                INNER JOIN genero g ON pu.IDGenero = g.ID
                INNER JOIN barrios b ON pu.IDBarrio = b.ID
                INNER JOIN municipios m ON b.id_municipio = m.ID
                INNER JOIN departamentos d ON m.id_departamento = d.ID
                LEFT JOIN instructor i ON pu.ID = i.IDPerfilUsuario
            WHERE
                pu.ID = ?
            LIMIT 1
        """;

            InstructorModel instructor = jdbcTemplate.queryForObject(sqlGetInstructorBasicInfo, new Object[]{perfilUsuarioId}, (rs, rowNum) -> {
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
                        .ClaseFormacion(rs.getString("ClaseFormacion"))
                        // Inicializar las listas vacías
                        .fichas(new ArrayList<>())
                        .programasFormacion(new ArrayList<>())
                        .jornadasFormacion(new ArrayList<>())
                        .nivelesFormacion(new ArrayList<>())
                        .sedes(new ArrayList<>())
                        .areas(new ArrayList<>())
                        .build();
            });

            if (instructor == null) {
                logger.warn("No se pudo obtener la información básica del instructor con IDPerfilUsuario: {}", perfilUsuarioId);
                throw new RuntimeException("No se pudo obtener la información del instructor.");
            }

            logger.info("Información básica del instructor obtenida: {}", instructor);

            // Paso 3: Obtener el IDInstructor
            String sqlGetInstructorId = "SELECT ID FROM instructor WHERE IDPerfilUsuario = ?";
            Integer instructorId = jdbcTemplate.queryForObject(sqlGetInstructorId, new Object[]{perfilUsuarioId}, Integer.class);

            if (instructorId == null) {
                logger.warn("No se encontró el instructor con IDPerfilUsuario: {}", perfilUsuarioId);
                throw new RuntimeException("No se encontró el instructor con IDPerfilUsuario: " + perfilUsuarioId);
            }

            logger.info("InstructorId encontrado: {}", instructorId);

            // Paso 4: Obtener las fichas asociadas al instructor
            String sqlGetFichas = """
            SELECT f.NumeroFicha
            FROM instructor_ficha inf
            INNER JOIN fichas f ON inf.IDFicha = f.ID
            WHERE inf.IDInstructor = ?
        """;

            List<Integer> fichas = jdbcTemplate.query(sqlGetFichas, new Object[]{instructorId}, (rs, rowNum) -> rs.getInt("NumeroFicha"));
            instructor.getFichas().addAll(fichas);
            logger.debug("Fichas asociadas al instructor {}: {}", instructorId, fichas);

            if (fichas.isEmpty()) {
                logger.info("No hay fichas asociadas al instructor con ID: {}", instructorId);
                return instructor;
            }

            // Paso 5: Obtener detalles adicionales basados en las fichas
            MapSqlParameterSource params = new MapSqlParameterSource();
            params.addValue("fichas", fichas);

            NamedParameterJdbcTemplate namedJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate.getDataSource());

            // Obtener programas de formación
            String sqlGetProgramasFormacion = """
            SELECT DISTINCT pf.ProgramaFormacion
            FROM fichas f
            INNER JOIN programaformacion pf ON f.IDProgramaFormacion = pf.ID
            WHERE f.NumeroFicha IN (:fichas)
        """;

            List<String> programasFormacion = namedJdbcTemplate.query(sqlGetProgramasFormacion, params, (rs, rowNum) -> rs.getString("ProgramaFormacion"));
            instructor.getProgramasFormacion().addAll(programasFormacion);
            logger.debug("Programas de formación: {}", programasFormacion);

            // Obtener jornadas de formación (Ajuste realizado aquí)
            String sqlGetJornadasFormacion = """
            SELECT DISTINCT jf.JornadasFormacion
            FROM fichas f
            INNER JOIN jornadaformacion jf ON f.IDJornadaFormacion = jf.ID
            WHERE f.NumeroFicha IN (:fichas)
        """;

            List<String> jornadasFormacion = namedJdbcTemplate.query(sqlGetJornadasFormacion, params, (rs, rowNum) -> rs.getString("JornadasFormacion"));
            instructor.getJornadasFormacion().addAll(jornadasFormacion);
            logger.debug("Jornadas de formación: {}", jornadasFormacion);

            // Obtener niveles de formación
            String sqlGetNivelesFormacion = """
            SELECT DISTINCT nf.NivelFormacion
            FROM fichas f
            INNER JOIN programaformacion pf ON f.IDProgramaFormacion = pf.ID
            INNER JOIN nivelformacion nf ON pf.IDNivelFormacion = nf.ID
            WHERE f.NumeroFicha IN (:fichas)
        """;

            List<String> nivelesFormacion = namedJdbcTemplate.query(sqlGetNivelesFormacion, params, (rs, rowNum) -> rs.getString("NivelFormacion"));
            instructor.getNivelesFormacion().addAll(nivelesFormacion);
            logger.debug("Niveles de formación: {}", nivelesFormacion);

            // Obtener sedes
            String sqlGetSedes = """
            SELECT DISTINCT s.CentroFormacion
            FROM fichas f
            INNER JOIN programaformacion pf ON f.IDProgramaFormacion = pf.ID
            INNER JOIN sede s ON pf.IDSede = s.ID
            WHERE f.NumeroFicha IN (:fichas)
        """;

            List<String> sedes = namedJdbcTemplate.query(sqlGetSedes, params, (rs, rowNum) -> rs.getString("CentroFormacion"));
            instructor.getSedes().addAll(sedes);
            logger.debug("Sedes: {}", sedes);

            // Obtener áreas
            String sqlGetAreas = """
            SELECT DISTINCT a.Area
            FROM fichas f
            INNER JOIN programaformacion pf ON f.IDProgramaFormacion = pf.ID
            INNER JOIN areas a ON pf.IDArea = a.ID
            WHERE f.NumeroFicha IN (:fichas)
        """;

            List<String> areas = namedJdbcTemplate.query(sqlGetAreas, params, (rs, rowNum) -> rs.getString("Area"));
            instructor.getAreas().addAll(areas);
            logger.debug("Áreas: {}", areas);

            logger.info("Instructor obtenido exitosamente: {}", instructor);
            return instructor;

        } catch (IncorrectResultSizeDataAccessException e) {
            logger.error("Se encontraron múltiples perfiles de usuario para el documento: {}. Error: {}", documento, e.getMessage(), e);
            throw new RuntimeException("Se encontraron múltiples perfiles de usuario para el documento: " + documento, e);
        } catch (Exception e) {
            logger.error("Error al obtener el instructor con documento {}: {}", documento, e.getMessage(), e);
            throw new RuntimeException("Error al obtener el instructor: " + e.getMessage(), e);
        }
    }

    // Crear un nuevo instructor
    @Transactional(rollbackFor = Exception.class)
    public InstructorModel createInstructor(InstructorModel instructor) {
        // Validación de datos de entrada
        System.out.println("Iniciando método createInstructor");

        if (instructor == null) {
            throw new IllegalArgumentException("El objeto instructor no debe ser nulo.");
        }

        // Imprimir todos los campos del objeto instructor para verificar que no sean nulos
        System.out.println("Datos del instructor recibidos:");
        System.out.println("Usuario: " + instructor.getUser());
        System.out.println("Contraseña: " + instructor.getPassword());
        System.out.println("Documento: " + instructor.getDocumento());
        System.out.println("TipoDocumento: " + instructor.getTipoDocumento());
        System.out.println("Nombres: " + instructor.getNombres());
        System.out.println("Apellidos: " + instructor.getApellidos());
        System.out.println("FechaNacimiento: " + instructor.getFechaNacimiento());
        System.out.println("Teléfono: " + instructor.getTelefono());
        System.out.println("Correo: " + instructor.getCorreo());
        System.out.println("Género: " + instructor.getGenero());
        System.out.println("Residencia: " + instructor.getResidencia());
        System.out.println("ClaseFormacion" + instructor.getClaseFormacion());
        System.out.println("Fichas: " + instructor.getFichas());
        // Añadir más campos si es necesario

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
        if (instructor.getFichas() == null || instructor.getFichas().isEmpty()) {
            throw new IllegalArgumentException("Las fichas son requeridas.");
        }

        try {
            // Verificar si el usuario o documento ya existen
            System.out.println("Verificando si el usuario o documento ya existen en la base de datos.");

            Integer countUsuario = jdbcTemplate.queryForObject(
                    "SELECT COUNT(*) FROM usuario WHERE Usuario = ?",
                    new Object[]{instructor.getUser()},
                    Integer.class
            );
            System.out.println("Cantidad de usuarios con el mismo nombre: " + countUsuario);
            if (countUsuario != null && countUsuario > 0) {
                throw new IllegalArgumentException("El nombre de usuario ya existe: " + instructor.getUser());
            }

            Integer countDocumento = jdbcTemplate.queryForObject(
                    "SELECT COUNT(*) FROM perfilusuario WHERE Documento = ?",
                    new Object[]{instructor.getDocumento()},
                    Integer.class
            );
            System.out.println("Cantidad de documentos iguales: " + countDocumento);
            if (countDocumento != null && countDocumento > 0) {
                throw new IllegalArgumentException("El documento ya está registrado: " + instructor.getDocumento());
            }

            // Paso 1: Insertar en la tabla `usuario` y obtener el ID
            System.out.println("Insertando en la tabla usuario.");
            String sqlInsertUsuario = "INSERT INTO usuario (Usuario, Contraseña) VALUES (?, ?)";
            KeyHolder keyHolderUsuario = new GeneratedKeyHolder();

            int rowsAffectedUsuario = jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(sqlInsertUsuario, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, instructor.getUser());
                ps.setString(2, passwordEncoder.encode(instructor.getPassword()));
                return ps;
            }, keyHolderUsuario);

            System.out.println("Filas afectadas en la inserción de usuario: " + rowsAffectedUsuario);

            if (rowsAffectedUsuario == 0) {
                throw new RuntimeException("La inserción en la tabla usuario no afectó a ninguna fila.");
            }

            Number generatedUserId = keyHolderUsuario.getKey();
            if (generatedUserId == null) {
                throw new RuntimeException("No se generó una clave primaria para el usuario.");
            }

            int usuarioId = generatedUserId.intValue();
            System.out.println("ID de usuario generado: " + usuarioId);

            // Paso 2: Obtener IDs relacionados
            System.out.println("Obteniendo IDs relacionados.");
            Integer idTipoDocumento = jdbcTemplate.queryForObject(
                    "SELECT ID FROM tipodocumento WHERE TipoDocumento = ?",
                    new Object[]{instructor.getTipoDocumento()},
                    Integer.class
            );
            System.out.println("ID TipoDocumento: " + idTipoDocumento);
            if (idTipoDocumento == null) {
                throw new IllegalArgumentException("Tipo de documento inválido: " + instructor.getTipoDocumento());
            }

            Integer idGenero = jdbcTemplate.queryForObject(
                    "SELECT ID FROM genero WHERE TiposGeneros = ?",
                    new Object[]{instructor.getGenero()},
                    Integer.class
            );
            System.out.println("ID Género: " + idGenero);
            if (idGenero == null) {
                throw new IllegalArgumentException("Género inválido: " + instructor.getGenero());
            }

            String residencia = instructor.getResidencia();
            String[] residenciaParts = residencia.split(" - ");
            if (residenciaParts.length < 3) {
                throw new IllegalArgumentException("La residencia debe tener el formato 'Departamento - Municipio - Barrio'");
            }
            String nombreBarrio = residenciaParts[2].trim();
            System.out.println("Nombre del barrio: " + nombreBarrio);

            Integer idBarrio = jdbcTemplate.queryForObject(
                    "SELECT ID FROM barrios WHERE nombre_barrio = ?",
                    new Object[]{nombreBarrio},
                    Integer.class
            );
            System.out.println("ID Barrio: " + idBarrio);
            if (idBarrio == null) {
                throw new IllegalArgumentException("Barrio inválido: " + nombreBarrio);
            }

            // Paso 3: Insertar en la tabla `perfilusuario` y obtener el ID
            System.out.println("Insertando en la tabla perfilusuario.");
            String sqlInsertPerfilUsuario = """
                INSERT INTO perfilusuario (
                    IDUsuario,
                    Documento,
                    IDTipoDocumento,
                    Nombres,
                    Apellidos,
                    FecNacimiento,
                    Telefono,
                    Correo,
                    IDGenero,
                    IDRol,
                    IDBarrio)
                VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
            """;
            KeyHolder keyHolderPerfilUsuario = new GeneratedKeyHolder();

            int rowsAffectedPerfilUsuario = jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(sqlInsertPerfilUsuario, Statement.RETURN_GENERATED_KEYS);
                ps.setInt(1, usuarioId);
                ps.setString(2, instructor.getDocumento());
                ps.setInt(3, idTipoDocumento);
                ps.setString(4, instructor.getNombres());
                ps.setString(5, instructor.getApellidos());
                ps.setDate(6, new java.sql.Date(instructor.getFechaNacimiento().getTime()));
                ps.setString(7, instructor.getTelefono());
                ps.setString(8, instructor.getCorreo());
                ps.setInt(9, idGenero);
                ps.setInt(10, 1);
                ps.setInt(11, idBarrio);
                return ps;
            }, keyHolderPerfilUsuario);

            System.out.println("Filas afectadas en la inserción de perfilusuario: " + rowsAffectedPerfilUsuario);

            if (rowsAffectedPerfilUsuario == 0) {
                throw new RuntimeException("La inserción en la tabla perfilusuario no afectó a ninguna fila.");
            }

            Number generatedPerfilUsuarioId = keyHolderPerfilUsuario.getKey();
            if (generatedPerfilUsuarioId == null) {
                throw new RuntimeException("No se generó una clave primaria para perfilusuario.");
            }

            int perfilUsuarioId = generatedPerfilUsuarioId.intValue();
            System.out.println("ID de perfilUsuario generado: " + perfilUsuarioId);

            // Paso 4: Insertar en la tabla `instructor` y obtener el ID
            System.out.println("Insertando en la tabla instructor.");
            String sqlInsertInstructor = "INSERT INTO instructor (IDPerfilUsuario) VALUES (?)";
            KeyHolder keyHolderInstructor = new GeneratedKeyHolder();

            int rowsAffectedInstructor = jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(sqlInsertInstructor, Statement.RETURN_GENERATED_KEYS);
                ps.setInt(1, perfilUsuarioId);
                return ps;
            }, keyHolderInstructor);

            if (rowsAffectedInstructor == 0) {
                throw new RuntimeException("La inserción en la tabla instructor no afectó a ninguna fila.");
            }

            Number generatedInstructorId = keyHolderInstructor.getKey();
            if (generatedInstructorId == null) {
                throw new RuntimeException("No se generó una clave primaria para instructor.");
            }

            int instructorId = generatedInstructorId.intValue();
            System.out.println("ID de instructor generado: " + instructorId);

            // **Nuevo Paso 5: Asociar Fichas al Instructor**
            System.out.println("Asociando fichas al instructor.");
            List<Integer> fichas = instructor.getFichas(); // Asegúrate de que este método retorna una lista de números de ficha

            for (Integer numeroFicha : fichas) {
                System.out.println("Procesando número de ficha: " + numeroFicha);

                // Buscar el ID de la ficha basado en NumeroFicha
                Integer fichaId = jdbcTemplate.queryForObject(
                        "SELECT ID FROM fichas WHERE NumeroFicha = ?",
                        new Object[]{numeroFicha},
                        Integer.class
                );

                if (fichaId == null) {
                    throw new IllegalArgumentException("La ficha con NúmeroFicha " + numeroFicha + " no existe.");
                }

                System.out.println("ID de la ficha encontrada: " + fichaId);

                // Insertar la asociación en la tabla `instructor_ficha`
                String sqlInsertInstructorFicha = "INSERT INTO instructor_ficha (IDFicha, IDInstructor) VALUES (?, ?)";

                int rowsAffectedInstructorFicha = jdbcTemplate.update(
                        sqlInsertInstructorFicha,
                        fichaId,
                        instructorId
                );

                if (rowsAffectedInstructorFicha == 0) {
                    throw new RuntimeException("No se pudo asociar la ficha número " + numeroFicha + " con el instructor.");
                }

                System.out.println("Ficha número " + numeroFicha + " asociada correctamente al instructor.");
            }

            // Finalización exitosa
            System.out.println("Proceso completado exitosamente.");
            return instructor;

        } catch (DataAccessException e) {
            e.printStackTrace();
            throw new RuntimeException("Error al acceder a la base de datos: " + e.getMessage(), e);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error al crear el instructor: " + e.getMessage(), e);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public InstructorModel updateInstructor(String documento, InstructorModel instructor) {
        // Validación de datos de entrada
        System.out.println("Iniciando método updateInstructor");

        if (instructor == null) {
            throw new IllegalArgumentException("El objeto instructor no debe ser nulo.");
        }

        // Imprimir todos los campos del objeto instructor para verificar que no sean nulos
        System.out.println("Datos del instructor recibidos:");
        System.out.println("Usuario: " + instructor.getUser());
        System.out.println("Contraseña: " + instructor.getPassword());
        System.out.println("Documento: " + instructor.getDocumento());
        System.out.println("TipoDocumento: " + instructor.getTipoDocumento());
        System.out.println("Nombres: " + instructor.getNombres());
        System.out.println("Apellidos: " + instructor.getApellidos());
        System.out.println("FechaNacimiento: " + instructor.getFechaNacimiento());
        System.out.println("Teléfono: " + instructor.getTelefono());
        System.out.println("Correo: " + instructor.getCorreo());
        System.out.println("Género: " + instructor.getGenero());
        System.out.println("Residencia: " + instructor.getResidencia());
        System.out.println("Fichas: " + instructor.getFichas());
        // Añadir más campos si es necesario

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
        // La residencia puede ser opcional si así lo deseas
        if (instructor.getFichas() == null || instructor.getFichas().isEmpty()) {
            throw new IllegalArgumentException("Las fichas son requeridas.");
        }

        try {
            // Obtener el ID del perfil de usuario
            String sqlGetPerfilUsuarioId = "SELECT ID FROM perfilusuario WHERE Documento = ?";
            System.out.println("Obteniendo ID de perfil de usuario con documento: " + documento);

            Integer perfilUsuarioId = jdbcTemplate.queryForObject(sqlGetPerfilUsuarioId, new Object[]{documento}, Integer.class);
            System.out.println("ID de perfil de usuario obtenido: " + perfilUsuarioId);

            if (perfilUsuarioId == null) {
                throw new RuntimeException("No se encontró el perfil de usuario con documento: " + documento);
            }

            // Obtener el ID del usuario para actualizar los campos de usuario y contraseña
            String sqlGetUsuarioId = "SELECT IDUsuario FROM perfilusuario WHERE Documento = ?";
            System.out.println("Obteniendo ID del usuario con documento: " + documento);

            Integer usuarioId = jdbcTemplate.queryForObject(sqlGetUsuarioId, new Object[]{documento}, Integer.class);
            System.out.println("ID de usuario obtenido: " + usuarioId);

            if (usuarioId == null) {
                throw new RuntimeException("No se encontró el usuario asociado al documento: " + documento);
            }

            // Verificar si el nuevo nombre de usuario ya existe para otro usuario
            System.out.println("Verificando si el nuevo nombre de usuario ya existe para otro usuario.");
            Integer countUsuario = jdbcTemplate.queryForObject(
                    "SELECT COUNT(*) FROM usuario WHERE Usuario = ? AND ID != ?",
                    new Object[]{instructor.getUser(), usuarioId},
                    Integer.class
            );
            System.out.println("Cantidad de usuarios con el mismo nombre: " + countUsuario);
            if (countUsuario != null && countUsuario > 0) {
                throw new IllegalArgumentException("El nombre de usuario ya existe: " + instructor.getUser());
            }

            // Verificar si el nuevo documento ya existe para otro perfil de usuario
            System.out.println("Verificando si el nuevo documento ya existe para otro perfil de usuario.");
            Integer countDocumento = jdbcTemplate.queryForObject(
                    "SELECT COUNT(*) FROM perfilusuario WHERE Documento = ? AND ID != ?",
                    new Object[]{instructor.getDocumento(), perfilUsuarioId},
                    Integer.class
            );
            System.out.println("Cantidad de documentos iguales: " + countDocumento);
            if (countDocumento != null && countDocumento > 0) {
                throw new IllegalArgumentException("El documento ya está registrado: " + instructor.getDocumento());
            }

            // Actualizar en la tabla `usuario`
            String sqlUpdateUsuario = """
        UPDATE usuario
        SET Usuario = ?, Contraseña = ?
        WHERE ID = ?
        """;

            System.out.println("Actualizando usuario y contraseña para el usuario con ID: " + usuarioId);
            int updatedUsuario = jdbcTemplate.update(sqlUpdateUsuario,
                    instructor.getUser(),
                    passwordEncoder.encode(instructor.getPassword()),
                    usuarioId);
            System.out.println("Filas actualizadas en usuario: " + updatedUsuario);

            if (updatedUsuario == 0) {
                throw new RuntimeException("La actualización no afectó a ninguna fila en usuario.");
            }

            // Obtener IDs relacionados
            System.out.println("Obteniendo IDs relacionados.");
            Integer idTipoDocumento = jdbcTemplate.queryForObject(
                    "SELECT ID FROM tipodocumento WHERE TipoDocumento = ?",
                    new Object[]{instructor.getTipoDocumento()},
                    Integer.class
            );
            System.out.println("ID TipoDocumento: " + idTipoDocumento);
            if (idTipoDocumento == null) {
                throw new IllegalArgumentException("Tipo de documento inválido: " + instructor.getTipoDocumento());
            }

            Integer idGenero = jdbcTemplate.queryForObject(
                    "SELECT ID FROM genero WHERE TiposGeneros = ?",
                    new Object[]{instructor.getGenero()},
                    Integer.class
            );
            System.out.println("ID Género: " + idGenero);
            if (idGenero == null) {
                throw new IllegalArgumentException("Género inválido: " + instructor.getGenero());
            }

            // Manejo de la residencia
            Integer idBarrio = null;
            if (instructor.getResidencia() != null && !instructor.getResidencia().isEmpty()) {
                String residencia = instructor.getResidencia();
                String[] residenciaParts = residencia.split(" - ");
                if (residenciaParts.length >= 3) {
                    String nombreBarrio = residenciaParts[2].trim();
                    System.out.println("Nombre del barrio: " + nombreBarrio);

                    idBarrio = jdbcTemplate.queryForObject(
                            "SELECT ID FROM barrios WHERE nombre_barrio = ?",
                            new Object[]{nombreBarrio},
                            Integer.class
                    );
                    System.out.println("ID Barrio: " + idBarrio);
                    if (idBarrio == null) {
                        throw new IllegalArgumentException("Barrio inválido: " + nombreBarrio);
                    }
                } else {
                    System.out.println("La residencia no tiene el formato esperado, se omitirá el IDBarrio.");
                }
            } else {
                System.out.println("No se proporcionó residencia, se omitirá el IDBarrio.");
            }

            // Actualizar en la tabla `perfilusuario`
            String sqlUpdatePerfilUsuario = """
        UPDATE perfilusuario
        SET Documento = ?,
            IDTipoDocumento = ?,
            Nombres = ?,
            Apellidos = ?,
            FecNacimiento = ?,
            Telefono = ?,
            Correo = ?,
            IDGenero = ?,
            IDBarrio = ?
        WHERE ID = ?
        """;

            System.out.println("Actualizando perfil de usuario con ID: " + perfilUsuarioId);
            int updatedPerfilUsuario = jdbcTemplate.update(sqlUpdatePerfilUsuario,
                    instructor.getDocumento(),
                    idTipoDocumento,
                    instructor.getNombres(),
                    instructor.getApellidos(),
                    new java.sql.Date(instructor.getFechaNacimiento().getTime()),
                    instructor.getTelefono(),
                    instructor.getCorreo(),
                    idGenero,
                    idBarrio, // Puede ser null si no se proporcionó o no se encontró
                    perfilUsuarioId);
            System.out.println("Filas actualizadas en perfilusuario: " + updatedPerfilUsuario);

            if (updatedPerfilUsuario == 0) {
                throw new RuntimeException("La actualización no afectó a ninguna fila en perfilusuario.");
            }

            // Obtener el ID del instructor
            String sqlGetInstructorId = "SELECT ID FROM instructor WHERE IDPerfilUsuario = ?";
            System.out.println("Obteniendo ID del instructor con IDPerfilUsuario: " + perfilUsuarioId);

            Integer instructorId = jdbcTemplate.queryForObject(sqlGetInstructorId, new Object[]{perfilUsuarioId}, Integer.class);
            System.out.println("ID del instructor obtenido: " + instructorId);

            if (instructorId == null) {
                throw new RuntimeException("No se encontró el instructor con IDPerfilUsuario: " + perfilUsuarioId);
            }

            // Actualizar asociaciones en `instructor_ficha`
            // Primero eliminamos las asociaciones existentes
            String sqlDeleteInstructorFicha = "DELETE FROM instructor_ficha WHERE IDInstructor = ?";
            System.out.println("Eliminando registros antiguos en instructor_ficha para IDInstructor: " + instructorId);

            jdbcTemplate.update(sqlDeleteInstructorFicha, instructorId);

            // Ahora insertamos las nuevas asociaciones
            String sqlInsertInstructorFicha = "INSERT INTO instructor_ficha (IDInstructor, IDFicha) VALUES (?, ?)";
            for (Integer fichaNumero : instructor.getFichas()) {
                System.out.println("Procesando ficha número: " + fichaNumero);

                Integer idFicha = jdbcTemplate.queryForObject(
                        "SELECT ID FROM fichas WHERE NumeroFicha = ?",
                        new Object[]{fichaNumero},
                        Integer.class
                );
                if (idFicha == null) {
                    throw new IllegalArgumentException("Ficha inválida: " + fichaNumero);
                }
                System.out.println("Insertando ficha con ID: " + idFicha + " para el instructor con ID: " + instructorId);

                int rowsInserted = jdbcTemplate.update(sqlInsertInstructorFicha, instructorId, idFicha);
                if (rowsInserted == 0) {
                    throw new RuntimeException("No se pudo insertar la asociación en instructor_ficha para ficha ID: " + idFicha);
                }
            }

            System.out.println("Actualización del instructor completada con éxito.");
            return instructor;

        } catch (DataAccessException e) {
            e.printStackTrace();
            System.err.println("Error al acceder a la base de datos: " + e.getMessage());
            throw new RuntimeException("Error al acceder a la base de datos: " + e.getMessage(), e);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error al actualizar el instructor: " + e.getMessage());
            throw new RuntimeException("Error al actualizar el instructor: " + e.getMessage(), e);
        }
    }


    // Eliminar un instructor por documento
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteInstructor(String documento) {
        Integer perfilUsuarioId = null;
        Integer instructorId = null;
        Integer usuarioId = null;

        try {
            // 1. Obtener todos los IDs necesarios antes de eliminar
            // Obtener el ID del perfil de usuario
            String sqlGetPerfilUsuarioId = "SELECT ID FROM perfilusuario WHERE Documento = ?";
            System.out.println("Obteniendo ID del perfil de usuario para el documento: " + documento);
            perfilUsuarioId = jdbcTemplate.queryForObject(sqlGetPerfilUsuarioId, new Object[]{documento}, Integer.class);
            System.out.println("ID del perfil de usuario obtenido: " + perfilUsuarioId);

            // Obtener el ID del instructor
            String sqlGetInstructorId = "SELECT ID FROM instructor WHERE IDPerfilUsuario = ?";
            System.out.println("Obteniendo ID del instructor para el perfil de usuario con ID: " + perfilUsuarioId);
            instructorId = jdbcTemplate.queryForObject(sqlGetInstructorId, new Object[]{perfilUsuarioId}, Integer.class);
            System.out.println("ID del instructor obtenido: " + instructorId);

            // Obtener el IDUsuario desde perfilusuario
            String sqlGetUsuarioId = "SELECT IDUsuario FROM perfilusuario WHERE ID = ?";
            System.out.println("Obteniendo ID de usuario para el perfil de usuario con ID: " + perfilUsuarioId);
            usuarioId = jdbcTemplate.queryForObject(sqlGetUsuarioId, new Object[]{perfilUsuarioId}, Integer.class);
            System.out.println("ID de usuario obtenido: " + usuarioId);

            // 2. Una vez obtenidos todos los IDs, proceder con las eliminaciones

            // Eliminar de instructor_ficha
            String sqlDeleteInstructorFicha = "DELETE FROM instructor_ficha WHERE IDInstructor = ?";
            System.out.println("Eliminando registros de instructor_ficha para el instructor con ID: " + instructorId);
            jdbcTemplate.update(sqlDeleteInstructorFicha, instructorId);

            // Eliminar de instructor
            String sqlDeleteInstructor = "DELETE FROM instructor WHERE ID = ?";
            System.out.println("Eliminando el registro de instructor con ID: " + instructorId);
            int deletedInstructor = jdbcTemplate.update(sqlDeleteInstructor, instructorId);

            if (deletedInstructor > 0) {
                // Eliminar de perfilusuario
                String sqlDeletePerfilUsuario = "DELETE FROM perfilusuario WHERE ID = ?";
                System.out.println("Eliminando el registro de perfil de usuario con ID: " + perfilUsuarioId);
                jdbcTemplate.update(sqlDeletePerfilUsuario, perfilUsuarioId);

                // Eliminar de usuario
                String sqlDeleteUsuario = "DELETE FROM usuario WHERE ID = ?";
                System.out.println("Eliminando el registro de usuario con ID: " + usuarioId);
                int rowsAffected = jdbcTemplate.update(sqlDeleteUsuario, usuarioId);

                if (rowsAffected > 0) {
                    System.out.println("Usuario eliminado exitosamente con ID: " + usuarioId);
                } else {
                    System.out.println("No se eliminó ningún registro de usuario con ID: " + usuarioId);
                    throw new RuntimeException("Error: No se pudo eliminar el usuario con ID: " + usuarioId);
                }

                System.out.println("Eliminación del instructor y los registros asociados completada.");
                return true;
            } else {
                System.out.println("No se pudo eliminar el instructor.");
                throw new RuntimeException("Error al eliminar el instructor"); // Lanzar excepción para que se realice el rollback
            }
        } catch (EmptyResultDataAccessException e) {
            System.out.println("Error: No se encontró alguno de los registros necesarios para el documento: " + documento);
            throw new RuntimeException("Error: No se encontró alguno de los registros necesarios para el documento: " + documento, e);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error inesperado al eliminar el instructor: " + e.getMessage());
            throw new RuntimeException("Error inesperado al eliminar el instructor: " + e.getMessage(), e);
        }
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

