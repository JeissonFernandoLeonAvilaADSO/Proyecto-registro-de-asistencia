package com.proyectoasistencia.prasis.services.UsersServices;

import com.proyectoasistencia.prasis.models.UsersModels.AprendizModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Date;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class AprendizService {

    private final JdbcTemplate jdbcTemplate;
    private final BCryptPasswordEncoder passwordEncoder;
    private static final Logger logger = LoggerFactory.getLogger(AprendizService.class);

    @Autowired
    public AprendizService(JdbcTemplate jdbcTemplate, BCryptPasswordEncoder passwordEncoder) {
        this.jdbcTemplate = jdbcTemplate;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Obtener un aprendiz por su documento.
     *
     * @param documento Documento del aprendiz a obtener.
     * @return Objeto AprendizModel correspondiente.
     */
    @Transactional(readOnly = true)
    public AprendizModel getAprendiz(String documento) {
        logger.info("Iniciando búsqueda de aprendiz con documento: {}", documento);

        try {
            // Paso 1: Obtener el IDPerfilUsuario que coincida con el documento y tenga el rol de aprendiz
            String sqlGetPerfilUsuarioIds = """
            SELECT pu.ID
            FROM perfilusuario pu
            INNER JOIN rol r ON pu.IDRol = r.ID
            WHERE pu.Documento = ? AND r.TipoRol = 'Aprendiz'
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

            Integer aprendizId = perfilUsuarioIds.get(0);
            logger.info("PerfilUsuarioId seleccionado: {}", aprendizId);

            // Paso 2: Obtener la información básica del aprendiz
            String sqlGetAprendizBasicInfo = """
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
            LIMIT 1
        """;

            AprendizModel aprendiz = jdbcTemplate.queryForObject(sqlGetAprendizBasicInfo, new Object[]{aprendizId}, (rs, rowNum) -> {
                return AprendizModel.builder()
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
                        .vinculaciones(new ArrayList<>()) // Inicializar la lista de vinculaciones
                        .build();
            });

            if (aprendiz == null) {
                logger.warn("No se pudo obtener la información básica del aprendiz con IDPerfilUsuario: {}", aprendizId);
                throw new RuntimeException("No se pudo obtener la información del aprendiz.");
            }

            logger.info("Información básica del aprendiz obtenida: {}", aprendiz);

            // Paso 3: Obtener las vinculaciones asociadas al aprendiz usando la consulta proporcionada
            String sqlGetVinculaciones = """
            SELECT
                cf.NombreClase AS ClaseFormacion,
                f.NumeroFicha AS Ficha,
                pf.ProgramaFormacion AS ProgramaFormacion,
                jf.JornadasFormacion AS JornadaFormacion,
                nf.NivelFormacion AS NivelFormacion,
                s.CentroFormacion AS Sede,
                a.Area AS Area,
                CONCAT(u_instructor.Nombres, ' ', u_instructor.Apellidos) AS NombreInstructor
            FROM
                aprendiz_claseinstructorficha acf
                    INNER JOIN claseformacion_instructor_ficha cif ON acf.IDClaseInstructorFIcha = cif.ID
                    INNER JOIN claseformacion cf ON cif.IDClaseFormacion = cf.ID
                    INNER JOIN fichas f ON cif.IDFicha = f.ID
                    INNER JOIN programaformacion pf ON f.IDProgramaFormacion = pf.ID
                    INNER JOIN jornadaformacion jf ON cf.IDJornadaFormacion = jf.ID
                    INNER JOIN nivelformacion nf ON pf.IDNivelFormacion = nf.ID
                    INNER JOIN sede s ON pf.IDSede = s.ID
                    INNER JOIN areas a ON pf.IDArea = a.ID
                    INNER JOIN perfilusuario u_instructor ON cif.IDPerfilUsuario = u_instructor.ID
            WHERE
                acf.IDPerfilUsuario = ?
        """;

            jdbcTemplate.query(sqlGetVinculaciones, new Object[]{aprendizId}, (rs) -> {
                Map<String, Object> vinculacion = new HashMap<>();
                vinculacion.put("ClaseFormacion", rs.getString("ClaseFormacion"));
                vinculacion.put("Ficha", rs.getInt("Ficha"));
                vinculacion.put("ProgramaFormacion", rs.getString("ProgramaFormacion"));
                vinculacion.put("JornadaFormacion", rs.getString("JornadaFormacion"));
                vinculacion.put("NivelFormacion", rs.getString("NivelFormacion"));
                vinculacion.put("Sede", rs.getString("Sede"));
                vinculacion.put("Area", rs.getString("Area"));
                vinculacion.put("NombreInstructor", rs.getString("NombreInstructor"));
                aprendiz.getVinculaciones().add(vinculacion);
            });

            logger.info("Información completa del aprendiz obtenida exitosamente: {}", aprendiz);
            return aprendiz;

        } catch (IncorrectResultSizeDataAccessException e) {
            logger.error("Error de tamaño incorrecto en los resultados: ", e);
            throw new RuntimeException("Error en la búsqueda del aprendiz.");
        } catch (Exception e) {
            logger.error("Error al obtener el aprendiz: ", e);
            throw new RuntimeException("Error al obtener el aprendiz.");
        }
    }

    /**
     * Crear un nuevo aprendiz.
     *
     * @param aprendiz Objeto AprendizModel a crear.
     * @param fichas Lista de números de fichas a las que se asociará el aprendiz.
     * @return El objeto AprendizModel creado con sus vinculaciones.
     */
    @Transactional(rollbackFor = Exception.class)
    public AprendizModel createAprendiz(AprendizModel aprendiz, List<Integer> fichas) {
        logger.info("Iniciando creación de aprendiz con documento: {}", aprendiz.getDocumento());

        try {
            // Paso 1: Verificar si el usuario ya existe
            String sqlCheckUsuario = "SELECT COUNT(*) FROM usuario WHERE Usuario = ?";
            Integer countUsuario = jdbcTemplate.queryForObject(sqlCheckUsuario, new Object[]{aprendiz.getUser()}, Integer.class);
            if (countUsuario != null && countUsuario > 0) {
                logger.warn("El nombre de usuario ya existe: {}", aprendiz.getUser());
                throw new RuntimeException("El nombre de usuario ya existe: " + aprendiz.getUser());
            }

            // Paso 2: Verificar si el documento ya está registrado
            String sqlCheckDocumento = "SELECT COUNT(*) FROM perfilusuario WHERE Documento = ?";
            Integer countDocumento = jdbcTemplate.queryForObject(sqlCheckDocumento, new Object[]{aprendiz.getDocumento()}, Integer.class);
            if (countDocumento != null && countDocumento > 0) {
                logger.warn("El documento ya está registrado: {}", aprendiz.getDocumento());
                throw new RuntimeException("El documento ya está registrado: " + aprendiz.getDocumento());
            }

            // Paso 3: Insertar en la tabla `usuario` y obtener el ID
            String sqlInsertUsuario = "INSERT INTO usuario (Usuario, Contraseña) VALUES (?, ?)";
            KeyHolder keyHolderUsuario = new GeneratedKeyHolder();

            int rowsAffectedUsuario = jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(sqlInsertUsuario, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, aprendiz.getUser());
                ps.setString(2, passwordEncoder.encode(aprendiz.getPassword()));
                return ps;
            }, keyHolderUsuario);

            if (rowsAffectedUsuario == 0) {
                logger.error("La inserción en la tabla usuario no afectó a ninguna fila.");
                throw new RuntimeException("La inserción en la tabla usuario no afectó a ninguna fila.");
            }

            Number generatedUserId = keyHolderUsuario.getKey();
            if (generatedUserId == null) {
                logger.error("No se generó una clave primaria para el usuario.");
                throw new RuntimeException("No se generó una clave primaria para el usuario.");
            }

            int usuarioId = generatedUserId.intValue();
            logger.debug("Usuario insertado con ID: {}", usuarioId);

            // Paso 4: Obtener el ID de tipo de documento
            String sqlGetTipoDocumento = "SELECT ID FROM tipodocumento WHERE TipoDocumento = ?";
            Integer idTipoDocumento = jdbcTemplate.queryForObject(sqlGetTipoDocumento, new Object[]{aprendiz.getTipoDocumento()}, Integer.class);
            if (idTipoDocumento == null) {
                logger.error("Tipo de documento inválido: {}", aprendiz.getTipoDocumento());
                throw new RuntimeException("Tipo de documento inválido: " + aprendiz.getTipoDocumento());
            }

            // Paso 5: Obtener el ID de género
            String sqlGetGenero = "SELECT ID FROM genero WHERE TiposGeneros = ?";
            Integer idGenero = jdbcTemplate.queryForObject(sqlGetGenero, new Object[]{aprendiz.getGenero()}, Integer.class);
            if (idGenero == null) {
                logger.error("Género inválido: {}", aprendiz.getGenero());
                throw new RuntimeException("Género inválido: " + aprendiz.getGenero());
            }

            // Paso 6: Extraer el nombre del barrio de la residencia
            String residencia = aprendiz.getResidencia();
            String[] residenciaParts = residencia.split(" - ");
            if (residenciaParts.length != 3) {
                logger.error("La residencia debe tener el formato 'Departamento - Municipio - Barrio'.");
                throw new RuntimeException("La residencia debe tener el formato 'Departamento - Municipio - Barrio'.");
            }
            String nombreBarrio = residenciaParts[2].trim();

            // Paso 7: Obtener el ID del barrio
            String sqlGetBarrio = "SELECT ID FROM barrios WHERE nombre_barrio = ?";
            Integer idBarrio = jdbcTemplate.queryForObject(sqlGetBarrio, new Object[]{nombreBarrio}, Integer.class);
            if (idBarrio == null) {
                logger.error("Barrio inválido: {}", nombreBarrio);
                throw new RuntimeException("Barrio inválido: " + nombreBarrio);
            }

            // Paso 8: Insertar en la tabla `perfilusuario` y asignar el rol de aprendiz
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
                IDBarrio,
                Estado
            ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, (SELECT ID FROM rol WHERE TipoRol = 'Aprendiz'), ?, 'Habilitado')
        """;

            KeyHolder keyHolderPerfilUsuario = new GeneratedKeyHolder();

            int rowsAffectedPerfilUsuario = jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(sqlInsertPerfilUsuario, Statement.RETURN_GENERATED_KEYS);
                ps.setInt(1, usuarioId);
                ps.setString(2, aprendiz.getDocumento());
                ps.setInt(3, idTipoDocumento);
                ps.setString(4, aprendiz.getNombres());
                ps.setString(5, aprendiz.getApellidos());
                ps.setDate(6, new java.sql.Date(aprendiz.getFechaNacimiento().getTime()));
                ps.setString(7, aprendiz.getTelefono());
                ps.setString(8, aprendiz.getCorreo());
                ps.setInt(9, idGenero);
                ps.setInt(10, idBarrio);
                return ps;
            }, keyHolderPerfilUsuario);

            if (rowsAffectedPerfilUsuario == 0) {
                logger.error("La inserción en la tabla perfilusuario no afectó a ninguna fila.");
                throw new RuntimeException("La inserción en la tabla perfilusuario no afectó a ninguna fila.");
            }

            Number generatedPerfilUsuarioId = keyHolderPerfilUsuario.getKey();
            if (generatedPerfilUsuarioId == null) {
                logger.error("No se generó una clave primaria para perfilusuario.");
                throw new RuntimeException("No se generó una clave primaria para perfilusuario.");
            }

            int perfilUsuarioId = generatedPerfilUsuarioId.intValue();
            logger.debug("PerfilUsuario insertado con ID: {}", perfilUsuarioId);

            // Paso 9: Asociar el aprendiz con las vinculaciones de las fichas proporcionadas
            if (fichas == null || fichas.isEmpty()) {
                logger.error("Debe proporcionar al menos una ficha para asociar al aprendiz.");
                throw new RuntimeException("Debe proporcionar al menos una ficha para asociar al aprendiz.");
            }

            for (Integer fichaNumero : fichas) {
                logger.debug("Procesando ficha: {}", fichaNumero);

                // Obtener el ID de la ficha
                String sqlGetFicha = "SELECT ID FROM fichas WHERE NumeroFicha = ?";
                Integer idFicha = jdbcTemplate.queryForObject(sqlGetFicha, new Object[]{fichaNumero}, Integer.class);
                if (idFicha == null) {
                    logger.error("Ficha inválida: {}", fichaNumero);
                    throw new RuntimeException("Ficha inválida: " + fichaNumero);
                }

                // Obtener todos los IDs de claseformacion_instructor_ficha para la ficha
                String sqlGetClaseInstructorFichaIds = "SELECT ID FROM claseformacion_instructor_ficha WHERE IDFicha = ?";
                List<Integer> claseInstructorFichaIds = jdbcTemplate.queryForList(sqlGetClaseInstructorFichaIds, new Object[]{idFicha}, Integer.class);
                logger.debug("claseInstructorFichaIds obtenidos para ficha {}: {}", fichaNumero, claseInstructorFichaIds);

                if (claseInstructorFichaIds.isEmpty()) {
                    logger.warn("No se encontraron asociaciones en 'claseformacion_instructor_ficha' para la ficha: {}", fichaNumero);
                    throw new RuntimeException("No se encontraron asociaciones en 'claseformacion_instructor_ficha' para la ficha: " + fichaNumero);
                }

                // Insertar en la tabla `aprendiz_claseinstructorficha` todas las asociaciones
                String sqlInsertAprendizClaseInstructorFicha = "INSERT INTO aprendiz_claseinstructorficha (IDPerfilUsuario, IDClaseInstructorFIcha) VALUES (?, ?)";

                for (Integer claseInstructorFichaId : claseInstructorFichaIds) {
                    jdbcTemplate.update(sqlInsertAprendizClaseInstructorFicha, perfilUsuarioId, claseInstructorFichaId);
                    logger.debug("Relación aprendiz-claseinstructorficha insertada: PerfilUsuarioID={}, ClaseInstructorFichaID={}", perfilUsuarioId, claseInstructorFichaId);
                }
            }

            // Paso 10: Obtener las vinculaciones asociadas al aprendiz para retornarlas en el modelo
            String sqlGetVinculaciones = """
                                    SELECT
                                        cf.NombreClase AS ClaseFormacion,
                                        f.NumeroFicha AS Ficha,
                                        pf.ProgramaFormacion AS ProgramaFormacion,
                                        jf.JornadasFormacion AS JornadaFormacion,
                                        nf.NivelFormacion AS NivelFormacion,
                                        s.CentroFormacion AS Sede,
                                        a.Area AS Area,
                                        CONCAT(u_instructor.Nombres, ' ', u_instructor.Apellidos) AS NombreInstructor
                                    FROM
                                        aprendiz_claseinstructorficha acf
                                            INNER JOIN claseformacion_instructor_ficha cif ON acf.IDClaseInstructorFIcha = cif.ID
                                            INNER JOIN claseformacion cf ON cif.IDClaseFormacion = cf.ID
                                            INNER JOIN fichas f ON cif.IDFicha = f.ID
                                            INNER JOIN programaformacion pf ON f.IDProgramaFormacion = pf.ID
                                            INNER JOIN jornadaformacion jf ON cf.IDJornadaFormacion = jf.ID
                                            INNER JOIN nivelformacion nf ON pf.IDNivelFormacion = nf.ID
                                            INNER JOIN sede s ON pf.IDSede = s.ID
                                            INNER JOIN areas a ON pf.IDArea = a.ID
                                            INNER JOIN perfilusuario u_instructor ON cif.IDPerfilUsuario = u_instructor.ID
                                    WHERE
                                        acf.IDPerfilUsuario = ?""";

            jdbcTemplate.query(sqlGetVinculaciones, new Object[]{perfilUsuarioId}, (rs) -> {
                Map<String, Object> vinculacion = new HashMap<>();
                vinculacion.put("ClaseFormacion", rs.getString("ClaseFormacion"));
                vinculacion.put("Ficha", rs.getInt("Ficha"));
                vinculacion.put("ProgramaFormacion", rs.getString("ProgramaFormacion"));
                vinculacion.put("JornadaFormacion", rs.getString("JornadaFormacion"));
                vinculacion.put("NivelFormacion", rs.getString("NivelFormacion"));
                vinculacion.put("Sede", rs.getString("Sede"));
                vinculacion.put("Area", rs.getString("Area"));
                vinculacion.put("NombreInstructor", rs.getString("NombreInstructor"));
                logger.info("Vinculacion: {}", vinculacion);
                aprendiz.getVinculaciones().add(vinculacion);
            });

            logger.info("Aprendiz creado exitosamente con documento: {}", aprendiz.getDocumento());
            return aprendiz;

        } catch (DataAccessException e) {
            logger.error("Error al acceder a la base de datos: {}", e.getMessage());
            throw new RuntimeException("Error al acceder a la base de datos: " + e.getMessage(), e);
        } catch (Exception e) {
            logger.error("Error al crear el aprendiz: {}", e.getMessage());
            throw new RuntimeException("Error al crear el aprendiz: " + e.getMessage(), e);
        }
    }

    /**
     * Actualizar un aprendiz existente.
     *
     * @param documento Documento actual del aprendiz a actualizar.
     * @param aprendiz  Objeto AprendizModel con los datos actualizados.
     * @return El objeto AprendizModel actualizado con sus vinculaciones.
     */
    @Transactional(rollbackFor = Exception.class)
    public AprendizModel updateAprendiz(String documento, AprendizModel aprendiz) {
        logger.info("Iniciando actualización de aprendiz con documento: {}", documento);

        try {
            // Paso 1: Obtener el ID del perfil de usuario basado en el documento y rol 'Aprendiz'
            String sqlGetPerfilUsuarioId = "SELECT ID FROM perfilusuario WHERE Documento = ? AND IDRol = (SELECT ID FROM rol WHERE TipoRol = 'Aprendiz')";
            Integer perfilUsuarioId = jdbcTemplate.queryForObject(sqlGetPerfilUsuarioId, new Object[]{documento}, Integer.class);

            if (perfilUsuarioId == null) {
                logger.warn("No se encontró el perfil de usuario con documento: {}", documento);
                throw new RuntimeException("No se encontró el perfil de usuario con documento: " + documento);
            }

            // Paso 2: Obtener el IDUsuario asociado al perfil
            String sqlGetUsuarioId = "SELECT IDUsuario FROM perfilusuario WHERE ID = ?";
            Integer usuarioId = jdbcTemplate.queryForObject(sqlGetUsuarioId, new Object[]{perfilUsuarioId}, Integer.class);

            if (usuarioId == null) {
                logger.warn("No se encontró el usuario asociado al perfil de usuario con ID: {}", perfilUsuarioId);
                throw new RuntimeException("No se encontró el usuario asociado al perfil de usuario con ID: " + perfilUsuarioId);
            }

            // Paso 3: Obtener los IDs necesarios para actualizar el perfil de usuario
            // Obtener el ID del tipo de documento
            String sqlGetTipoDocumento = "SELECT ID FROM tipodocumento WHERE TipoDocumento = ?";
            Integer idTipoDocumento = jdbcTemplate.queryForObject(sqlGetTipoDocumento, new Object[]{aprendiz.getTipoDocumento()}, Integer.class);

            if (idTipoDocumento == null) {
                logger.warn("Tipo de documento inválido: {}", aprendiz.getTipoDocumento());
                throw new RuntimeException("Tipo de documento inválido: " + aprendiz.getTipoDocumento());
            }

            // Obtener el ID del género
            String sqlGetGenero = "SELECT ID FROM genero WHERE TiposGeneros = ?";
            Integer idGenero = jdbcTemplate.queryForObject(sqlGetGenero, new Object[]{aprendiz.getGenero()}, Integer.class);

            if (idGenero == null) {
                logger.warn("Género inválido: {}", aprendiz.getGenero());
                throw new RuntimeException("Género inválido: " + aprendiz.getGenero());
            }

            // Extraer el nombre del barrio de la residencia y obtener su ID
            String[] residenciaParts = aprendiz.getResidencia().split(" - ");
            if (residenciaParts.length != 3) {
                logger.error("La residencia debe tener el formato 'Departamento - Municipio - Barrio'.");
                throw new RuntimeException("La residencia debe tener el formato 'Departamento - Municipio - Barrio'.");
            }
            String nombreBarrio = residenciaParts[2].trim();

            String sqlGetBarrio = "SELECT ID FROM barrios WHERE nombre_barrio = ?";
            Integer idBarrio = jdbcTemplate.queryForObject(sqlGetBarrio, new Object[]{nombreBarrio}, Integer.class);

            if (idBarrio == null) {
                logger.warn("Barrio inválido: {}", nombreBarrio);
                throw new RuntimeException("Barrio inválido: " + nombreBarrio);
            }

            // Paso 4: Actualizar la información básica del aprendiz
            String sqlUpdateUsuario = "UPDATE usuario SET Usuario = ?, Contraseña = ? WHERE ID = ?";
            jdbcTemplate.update(sqlUpdateUsuario, aprendiz.getUser(), passwordEncoder.encode(aprendiz.getPassword()), usuarioId);

            String sqlUpdatePerfilUsuario = """
        UPDATE perfilusuario
        SET Documento = ?, IDTipoDocumento = ?, Nombres = ?, Apellidos = ?, FecNacimiento = ?, Telefono = ?, Correo = ?, IDGenero = ?, IDBarrio = ?, Estado = ?
        WHERE ID = ?
        """;
            jdbcTemplate.update(sqlUpdatePerfilUsuario,
                    aprendiz.getDocumento(),
                    idTipoDocumento,
                    aprendiz.getNombres(),
                    aprendiz.getApellidos(),
                    new java.sql.Date(aprendiz.getFechaNacimiento().getTime()),
                    aprendiz.getTelefono(),
                    aprendiz.getCorreo(),
                    idGenero,
                    idBarrio,
                    "Habilitado",
                    perfilUsuarioId);

            // Paso 5: Gestionar las asociaciones en 'aprendiz_claseinstructorficha'
            List<Map<String, Object>> nuevasVinculaciones = aprendiz.getVinculaciones();

            // Obtener todas las vinculaciones actuales en la base de datos
            String sqlGetCurrentVinculaciones = """
        SELECT acf.IDClaseInstructorFicha, cf.NombreClase, f.NumeroFicha, jf.JornadasFormacion
        FROM aprendiz_claseinstructorficha acf
        INNER JOIN claseformacion_instructor_ficha cif ON acf.IDClaseInstructorFIcha = cif.ID
        INNER JOIN claseformacion cf ON cif.IDClaseFormacion = cf.ID
        INNER JOIN fichas f ON cif.IDFicha = f.ID
        INNER JOIN jornadaformacion jf ON cf.IDJornadaFormacion = jf.ID
        WHERE acf.IDPerfilUsuario = ?
        """;
            List<Map<String, Object>> vinculacionesActuales = jdbcTemplate.query(sqlGetCurrentVinculaciones, new Object[]{perfilUsuarioId}, (rs, rowNum) -> {
                Map<String, Object> vinculacionActual = new HashMap<>();
                vinculacionActual.put("IDClaseInstructorFicha", rs.getInt("IDClaseInstructorFicha"));
                vinculacionActual.put("ClaseFormacion", rs.getString("NombreClase"));
                vinculacionActual.put("Ficha", rs.getInt("NumeroFicha"));
                vinculacionActual.put("JornadaFormacion", rs.getString("JornadasFormacion"));
                return vinculacionActual;
            });

            // Paso 6: Comparar y realizar los cambios necesarios en las asociaciones
            Set<Integer> asociacionesParaMantener = new HashSet<>();

            for (Map<String, Object> nuevaVinculacion : nuevasVinculaciones) {
                String claseFormacion = (String) nuevaVinculacion.get("ClaseFormacion");
                Integer fichaNumero = (Integer) nuevaVinculacion.get("Ficha");
                String jornadaFormacion = (String) nuevaVinculacion.get("JornadaFormacion");

                // Buscar en la base de datos los IDs necesarios
                String sqlGetFichaId = "SELECT ID FROM fichas WHERE NumeroFicha = ?";
                Integer fichaId = jdbcTemplate.queryForObject(sqlGetFichaId, new Object[]{fichaNumero}, Integer.class);

                String sqlGetClaseFormacionId = """
            SELECT cf.ID
            FROM claseformacion cf
            INNER JOIN jornadaformacion jf ON cf.IDJornadaFormacion = jf.ID
            WHERE cf.NombreClase = ? AND jf.JornadasFormacion = ?
            """;
                Integer claseFormacionId = jdbcTemplate.queryForObject(sqlGetClaseFormacionId, new Object[]{claseFormacion, jornadaFormacion}, Integer.class);

                // Obtener el ID de claseformacion_instructor_ficha
                String sqlGetClaseInstructorFichaId = """
            SELECT ID FROM claseformacion_instructor_ficha WHERE IDFicha = ? AND IDClaseFormacion = ?
            """;
                Integer claseInstructorFichaId = jdbcTemplate.queryForObject(sqlGetClaseInstructorFichaId, new Object[]{fichaId, claseFormacionId}, Integer.class);

                boolean encontrada = false;

                // Verificar si la nueva vinculación ya existe en las vinculaciones actuales
                for (Map<String, Object> vinculacionActual : vinculacionesActuales) {
                    Integer idActual = (Integer) vinculacionActual.get("IDClaseInstructorFicha");
                    if (idActual.equals(claseInstructorFichaId)) {
                        // Si la vinculación ya existe, la mantenemos
                        asociacionesParaMantener.add(idActual);
                        encontrada = true;
                        break;
                    }
                }

                // Si no se encontró en las relaciones actuales, insertamos una nueva
                if (!encontrada) {
                    String sqlInsertAprendizClaseInstructorFicha = "INSERT INTO aprendiz_claseinstructorficha (IDPerfilUsuario, IDClaseInstructorFIcha) VALUES (?, ?)";
                    jdbcTemplate.update(sqlInsertAprendizClaseInstructorFicha, perfilUsuarioId, claseInstructorFichaId);
                    logger.debug("Relación aprendiz-claseinstructorficha insertada: PerfilUsuarioID={}, ClaseInstructorFichaID={}", perfilUsuarioId, claseInstructorFichaId);
                }
            }

            // Paso 7: Eliminar relaciones que no se encuentran en las nuevas vinculaciones
            for (Map<String, Object> vinculacionActual : vinculacionesActuales) {
                Integer idActual = (Integer) vinculacionActual.get("IDClaseInstructorFicha");
                if (!asociacionesParaMantener.contains(idActual)) {
                    String sqlDeleteAprendizClaseInstructorFicha = "DELETE FROM aprendiz_claseinstructorficha WHERE IDPerfilUsuario = ? AND IDClaseInstructorFIcha = ?";
                    jdbcTemplate.update(sqlDeleteAprendizClaseInstructorFicha, perfilUsuarioId, idActual);
                    logger.debug("Relación aprendiz-claseinstructorficha eliminada: PerfilUsuarioID={}, ClaseInstructorFichaID={}", perfilUsuarioId, idActual);
                }
            }

            // Paso 8: Obtener las vinculaciones actualizadas
            String sqlGetVinculaciones = """
        SELECT
            cf.NombreClase AS ClaseFormacion,
            f.NumeroFicha AS Ficha,
            pf.ProgramaFormacion AS ProgramaFormacion,
            jf.JornadasFormacion AS JornadaFormacion,
            nf.NivelFormacion AS NivelFormacion,
            s.CentroFormacion AS Sede,
            a.Area AS Area,
            CONCAT(u_instructor.Nombres, ' ', u_instructor.Apellidos) AS NombreInstructor
        FROM
            aprendiz_claseinstructorficha acf
            INNER JOIN claseformacion_instructor_ficha cif ON acf.IDClaseInstructorFIcha = cif.ID
            INNER JOIN claseformacion cf ON cif.IDClaseFormacion = cf.ID
            INNER JOIN fichas f ON cif.IDFicha = f.ID
            INNER JOIN programaformacion pf ON f.IDProgramaFormacion = pf.ID
            INNER JOIN jornadaformacion jf ON cf.IDJornadaFormacion = jf.ID
            INNER JOIN nivelformacion nf ON pf.IDNivelFormacion = nf.ID
            INNER JOIN sede s ON pf.IDSede = s.ID
            INNER JOIN areas a ON pf.IDArea = a.ID
            INNER JOIN perfilusuario u_instructor ON cif.IDPerfilUsuario = u_instructor.ID
        WHERE
            acf.IDPerfilUsuario = ?
        """;

            List<Map<String, Object>> updatedVinculaciones = new ArrayList<>();

            jdbcTemplate.query(sqlGetVinculaciones, new Object[]{perfilUsuarioId}, (rs) -> {
                Map<String, Object> vinculacion = new HashMap<>();
                vinculacion.put("ClaseFormacion", rs.getString("ClaseFormacion"));
                vinculacion.put("Ficha", rs.getInt("Ficha"));
                vinculacion.put("ProgramaFormacion", rs.getString("ProgramaFormacion"));
                vinculacion.put("JornadaFormacion", rs.getString("JornadaFormacion"));
                vinculacion.put("NivelFormacion", rs.getString("NivelFormacion"));
                vinculacion.put("Sede", rs.getString("Sede"));
                vinculacion.put("Area", rs.getString("Area"));
                vinculacion.put("NombreInstructor", rs.getString("NombreInstructor"));
                updatedVinculaciones.add(vinculacion);
            });

            // Paso 9: Construir y retornar el objeto AprendizModel actualizado
            AprendizModel updatedAprendiz = AprendizModel.builder()
                    .user(aprendiz.getUser())
                    .password(aprendiz.getPassword())
                    .documento(aprendiz.getDocumento())
                    .tipoDocumento(aprendiz.getTipoDocumento())
                    .nombres(aprendiz.getNombres())
                    .apellidos(aprendiz.getApellidos())
                    .fechaNacimiento(aprendiz.getFechaNacimiento())
                    .telefono(aprendiz.getTelefono())
                    .correo(aprendiz.getCorreo())
                    .genero(aprendiz.getGenero())
                    .residencia(aprendiz.getResidencia())
                    .estado(aprendiz.getEstado())
                    .vinculaciones(updatedVinculaciones)
                    .build();

            logger.info("Aprendiz actualizado exitosamente con documento: {}", aprendiz.getDocumento());
            return updatedAprendiz;

        } catch (DataAccessException e) {
            logger.error("Error al acceder a la base de datos: {}", e.getMessage());
            throw new RuntimeException("Error al acceder a la base de datos: " + e.getMessage(), e);
        } catch (Exception e) {
            logger.error("Error al actualizar el aprendiz: {}", e.getMessage());
            throw new RuntimeException("Error al actualizar el aprendiz: " + e.getMessage(), e);
        }
    }

    /**
     * Eliminar un aprendiz por documento.
     *
     * @param documento Documento del aprendiz a eliminar.
     * @return true si la eliminación fue exitosa, false en caso contrario.
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteAprendiz(String documento) {
        logger.info("Iniciando eliminación de aprendiz con documento: {}", documento);

        try {
            // Paso 1: Obtener el IDPerfilUsuario y IDUsuario
            String sqlGetIds = """
                SELECT pu.ID AS IDPerfilUsuario, u.ID AS IDUsuario
                FROM perfilusuario pu
                INNER JOIN usuario u ON pu.IDUsuario = u.ID
                INNER JOIN rol r ON pu.IDRol = r.ID
                WHERE pu.Documento = ? AND r.TipoRol = 'Aprendiz'
            """;

            Map<String, Object> idsMap = jdbcTemplate.queryForMap(sqlGetIds, new Object[]{documento});

            Integer perfilUsuarioId = (Integer) idsMap.get("IDPerfilUsuario");
            Integer usuarioId = (Integer) idsMap.get("IDUsuario");

            logger.debug("IDPerfilUsuario: {}, IDUsuario: {}", perfilUsuarioId, usuarioId);

            // Paso 2: Eliminar de la tabla `aprendiz_claseinstructorficha`
            String sqlDeleteRelations = "DELETE FROM aprendiz_claseinstructorficha WHERE IDPerfilUsuario = ?";
            int rowsDeletedRelations = jdbcTemplate.update(sqlDeleteRelations, perfilUsuarioId);
            logger.debug("Relaciones eliminadas en aprendiz_claseinstructorficha: {}", rowsDeletedRelations);

            // Paso 3: Eliminar de la tabla `perfilusuario`
            String sqlDeletePerfilUsuario = "DELETE FROM perfilusuario WHERE ID = ?";
            int rowsDeletedPerfilUsuario = jdbcTemplate.update(sqlDeletePerfilUsuario, perfilUsuarioId);
            if (rowsDeletedPerfilUsuario == 0) {
                logger.error("No se pudo eliminar el perfil de usuario con ID: {}", perfilUsuarioId);
                throw new RuntimeException("No se pudo eliminar el perfil de usuario con ID: " + perfilUsuarioId);
            }
            logger.debug("Perfil de usuario eliminado con ID: {}", perfilUsuarioId);

            // Paso 4: Eliminar de la tabla `usuario`
            String sqlDeleteUsuario = "DELETE FROM usuario WHERE ID = ?";
            int rowsDeletedUsuario = jdbcTemplate.update(sqlDeleteUsuario, usuarioId);
            if (rowsDeletedUsuario == 0) {
                logger.error("No se pudo eliminar el usuario con ID: {}", usuarioId);
                throw new RuntimeException("No se pudo eliminar el usuario con ID: " + usuarioId);
            }
            logger.debug("Usuario eliminado con ID: {}", usuarioId);

            logger.info("Aprendiz eliminado exitosamente con documento: {}", documento);
            return true;

        } catch (EmptyResultDataAccessException e) {
            logger.warn("No se encontró ningún aprendiz con el documento: {}", documento);
            throw new RuntimeException("No se encontró ningún aprendiz con el documento: " + documento, e);
        } catch (DataAccessException e) {
            logger.error("Error al acceder a la base de datos: {}", e.getMessage());
            throw new RuntimeException("Error al acceder a la base de datos: " + e.getMessage(), e);
        } catch (Exception e) {
            logger.error("Error al eliminar el aprendiz: {}", e.getMessage());
            throw new RuntimeException("Error al eliminar el aprendiz: " + e.getMessage(), e);
        }
    }

    /**
     * Obtener el ID del Aprendiz por su documento.
     *
     * @param documento Documento del aprendiz.
     * @return ID del Aprendiz, o null si no se encuentra.
     */
    public Integer getAprendizIdByDocumento(String documento) {
        try {
            String sqlGetAprendizId = """
                SELECT pu.ID 
                FROM perfilusuario pu
                INNER JOIN rol r ON pu.IDRol = r.ID
                WHERE pu.Documento = ? AND r.TipoRol = 'Aprendiz'
            """;

            return jdbcTemplate.queryForObject(sqlGetAprendizId, new Object[]{documento}, Integer.class);
        } catch (EmptyResultDataAccessException e) {
            logger.warn("No se encontró el Aprendiz con documento: {}", documento);
            return null;
        } catch (Exception e) {
            logger.error("Error al obtener el ID del Aprendiz: {}", e.getMessage());
            throw new RuntimeException("Error al obtener el ID del Aprendiz: " + e.getMessage(), e);
        }
    }

    /**
     * Obtener todos los aprendices asociados a una ficha específica.
     *
     * @param ficha Número de ficha.
     * @return Lista de AprendizModel asociados a la ficha.
     */
    @Transactional(readOnly = true)
    public List<AprendizModel> getAllAprendicesFicha(Integer ficha) {
        logger.info("Iniciando búsqueda de aprendices para la ficha: {}", ficha);

        try {
            // Paso 1: Obtener los documentos de los aprendices asociados a la ficha
            String sqlGetDocumentos = """
            SELECT DISTINCT pu.Documento
            FROM perfilusuario pu
            INNER JOIN rol r ON pu.IDRol = r.ID
            INNER JOIN aprendiz_claseinstructorficha acf ON pu.ID = acf.IDPerfilUsuario
            INNER JOIN claseformacion_instructor_ficha cif ON acf.IDClaseInstructorFIcha = cif.ID
            INNER JOIN fichas f ON cif.IDFicha = f.ID
            WHERE f.NumeroFicha = ? AND r.TipoRol = 'Aprendiz'
        """;

            // Obtener lista de documentos de los aprendices asociados a la ficha
            List<String> documentosAprendices = jdbcTemplate.queryForList(sqlGetDocumentos, new Object[]{ficha}, String.class);
            logger.debug("Documentos de aprendices encontrados para la ficha {}: {}", ficha, documentosAprendices);

            if (documentosAprendices.isEmpty()) {
                logger.warn("No se encontraron aprendices asociados a la ficha: {}", ficha);
                return new ArrayList<>();
            }

            // Paso 2: Usar el método getAprendiz para obtener la información completa de cada aprendiz
            List<AprendizModel> aprendices = new ArrayList<>();
            for (String documento : documentosAprendices) {
                try {
                    AprendizModel aprendiz = getAprendiz(documento); // Reutilizar el método getAprendiz
                    aprendices.add(aprendiz);
                } catch (Exception e) {
                    logger.error("Error al obtener la información del aprendiz con documento: {}", documento, e);
                }
            }

            logger.info("Información de todos los aprendices obtenida exitosamente para la ficha: {}", ficha);
            return aprendices;

        } catch (DataAccessException e) {
            logger.error("Error al acceder a la base de datos: {}", e.getMessage());
            throw new RuntimeException("Error al acceder a la base de datos: " + e.getMessage(), e);
        } catch (Exception e) {
            logger.error("Error al obtener los aprendices: {}", e.getMessage());
            throw new RuntimeException("Error al obtener los aprendices: " + e.getMessage(), e);
        }
    }


    /**
     * Habilitar un aprendiz dado su documento.
     *
     * @param documento Documento del aprendiz a habilitar.
     * @return true si la operación fue exitosa, false en caso contrario.
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean habilitarAprendiz(String documento) {
        logger.info("Iniciando habilitación de aprendiz con documento: {}", documento);

        try {
            // Verificar si el PerfilUsuario está referenciado en la tabla Aprendiz
            String verificarSql = "SELECT COUNT(*) FROM perfilusuario pu INNER JOIN rol r ON pu.IDRol = r.ID WHERE pu.Documento = ? AND r.TipoRol = 'Aprendiz'";
            Integer count = jdbcTemplate.queryForObject(verificarSql, new Object[]{documento}, Integer.class);

            if (count != null && count > 0) {
                // Actualizar el estado en perfilusuario
                String actualizarSql = "UPDATE perfilusuario SET Estado = 'Habilitado' WHERE Documento = ?";
                int rowsAffected = jdbcTemplate.update(actualizarSql, documento);
                if (rowsAffected > 0) {
                    logger.info("Aprendiz habilitado exitosamente con documento: {}", documento);
                    return true;
                }
            }

            logger.warn("No existe un Aprendiz asociado a este documento: {}", documento);
            return false;

        } catch (DataAccessException e) {
            logger.error("Error al acceder a la base de datos: {}", e.getMessage());
            throw new RuntimeException("Error al acceder a la base de datos: " + e.getMessage(), e);
        }
    }

    /**
     * Inhabilitar un aprendiz dado su documento.
     *
     * @param documento Documento del aprendiz a inhabilitar.
     * @return true si la operación fue exitosa, false en caso contrario.
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean inhabilitarAprendiz(String documento) {
        logger.info("Iniciando inhabilitación de aprendiz con documento: {}", documento);

        try {
            // Verificar si el PerfilUsuario está referenciado en la tabla Aprendiz
            String verificarSql = "SELECT COUNT(*) FROM perfilusuario pu INNER JOIN rol r ON pu.IDRol = r.ID WHERE pu.Documento = ? AND r.TipoRol = 'Aprendiz'";
            Integer count = jdbcTemplate.queryForObject(verificarSql, new Object[]{documento}, Integer.class);

            if (count != null && count > 0) {
                // Actualizar el estado en perfilusuario
                String actualizarSql = "UPDATE perfilusuario SET Estado = 'Deshabilitado' WHERE Documento = ?";
                int rowsAffected = jdbcTemplate.update(actualizarSql, documento);
                if (rowsAffected > 0) {
                    logger.info("Aprendiz inhabilitado exitosamente con documento: {}", documento);
                    return true;
                }
            }

            logger.warn("No existe un Aprendiz asociado a este documento: {}", documento);
            return false;

        } catch (DataAccessException e) {
            logger.error("Error al acceder a la base de datos: {}", e.getMessage());
            throw new RuntimeException("Error al acceder a la base de datos: " + e.getMessage(), e);
        }
    }

    /**
     * Obtener las vinculaciones de aprendices por ficha.
     *
     * @param ficha Número de ficha.
     * @return Lista de vinculaciones de aprendiz.
     */
    public List<Map<String, Object>> obtenerVinculacionesPorFicha(Integer ficha) {
        String sql = """
            SELECT f.NumeroFicha, a.Area, s.CentroFormacion AS Sede,
                   cf.NombreClase AS ClaseFormacion, jf.JornadasFormacion AS JornadaFormacion,
                   CONCAT(i.Nombres, ' ', i.Apellidos) AS NombreInstructor,
                   nf.NivelFormacion, pf.ProgramaFormacion, cif.ID, i.Documento AS DocumentoInstructor
            FROM claseformacion_instructor_ficha cif
                     INNER JOIN claseformacion cf ON cif.IDClaseFormacion = cf.ID
                     INNER JOIN fichas f ON cif.IDFicha = f.ID
                     INNER JOIN programaformacion pf ON f.IDProgramaFormacion = pf.ID
                     INNER JOIN jornadaformacion jf ON cf.IDJornadaFormacion = jf.ID
                     INNER JOIN nivelformacion nf ON pf.IDNivelFormacion = nf.ID
                     INNER JOIN sede s ON pf.IDSede = s.ID
                     INNER JOIN areas a ON pf.IDArea = a.ID
                     INNER JOIN perfilusuario i ON cif.IDPerfilUsuario = i.ID
            WHERE f.NumeroFicha = ?
        """;

        return jdbcTemplate.queryForList(sql, ficha);
    }
}

