package com.proyectoasistencia.prasis.services.UsersServices;

import com.proyectoasistencia.prasis.models.UsersModels.AprendizModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Collections;
import java.util.List;
import java.util.Map;


@Service
public class AprendizService {

    private final JdbcTemplate jdbcTemplate;
    private final BCryptPasswordEncoder passwordEncoder;


    @Autowired
    public AprendizService(JdbcTemplate jdbcTemplate, BCryptPasswordEncoder passwordEncoder) {
        this.jdbcTemplate = jdbcTemplate;
        this.passwordEncoder = passwordEncoder;

    }

    // Obtener un aprendiz por documento
    public AprendizModel getAprendiz(String documento) {
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
                       fc.NumeroFicha AS NumeroFicha,
                       pform.ProgramaFormacion AS ProgramaFormacion,
                       jf.JornadasFormacion AS JornadasFormacion,
                       nf.NivelFormacion AS NivelFormacion,
                       sd.CentroFormacion AS CentroFormacion,
                       areas.Area AS Area
                FROM aprendiz ap
                    INNER JOIN perfilusuario pf ON ap.IDPerfilUsuario = pf.ID
                    INNER JOIN usuario us ON pf.IDUsuario = us.ID
                    INNER JOIN tipodocumento td ON pf.IDTipoDocumento = td.ID
                    INNER JOIN genero ge ON pf.IDGenero = ge.ID
                    INNER JOIN barrios ON pf.IDBarrio = barrios.id_barrio
                    INNER JOIN municipios mun ON barrios.id_municipio = mun.id_municipio
                    INNER JOIN departamentos dept ON mun.id_departamento = dept.id_departamento
                    INNER JOIN fichas fc ON ap.IDFicha = fc.ID
                    INNER JOIN programaformacion pform ON fc.IDProgramaFormacion = pform.ID
                    INNER JOIN jornadaformacion jf ON pform.IDJornadaFormacion = jf.ID
                    INNER JOIN nivelformacion nf ON pform.IDNivelFormacion = nf.ID
                    INNER JOIN sede sd ON pform.IDSede = sd.ID
                    INNER JOIN areas ON pform.IDArea = areas.ID
                WHERE pf.Documento = ?
                """;

        try {
            return jdbcTemplate.queryForObject(consulta, new Object[]{documento}, (rs, rowNum) ->
                    AprendizModel.builder()
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
                            .ficha(rs.getInt("NumeroFicha"))
                            .programaFormacion(rs.getString("ProgramaFormacion"))
                            .jornadaFormacion(rs.getString("JornadasFormacion"))
                            .nivelFormacion(rs.getString("NivelFormacion"))
                            .sede(rs.getString("CentroFormacion"))
                            .area(rs.getString("Area"))
                            .build());

        } catch (Exception e) {
            e.printStackTrace();
            return null; // Maneja la excepción adecuadamente
        }
    }

    // Crear un nuevo aprendiz
    @Transactional(rollbackFor = Exception.class)
    public AprendizModel createAprendiz(AprendizModel aprendiz) {
        // Validación de datos de entrada
        System.out.println("Iniciando método createAprendiz");

        if (aprendiz == null) {
            throw new IllegalArgumentException("El objeto aprendiz no debe ser nulo.");
        }

        // Imprimir todos los campos del objeto aprendiz para verificar que no sean nulos
        System.out.println("Datos del aprendiz recibidos:");
        System.out.println("Usuario: " + aprendiz.getUser());
        System.out.println("Contraseña: " + aprendiz.getPassword());
        System.out.println("Documento: " + aprendiz.getDocumento());
        System.out.println("TipoDocumento: " + aprendiz.getTipoDocumento());
        System.out.println("Nombres: " + aprendiz.getNombres());
        System.out.println("Apellidos: " + aprendiz.getApellidos());
        System.out.println("FechaNacimiento: " + aprendiz.getFechaNacimiento());
        System.out.println("Teléfono: " + aprendiz.getTelefono());
        System.out.println("Correo: " + aprendiz.getCorreo());
        System.out.println("Género: " + aprendiz.getGenero());
        System.out.println("Residencia: " + aprendiz.getResidencia());
        System.out.println("Ficha: " + aprendiz.getFicha());
        // Añadir más campos si es necesario

        // Validar que los datos requeridos no sean nulos o vacíos
        if (aprendiz == null) {
            throw new IllegalArgumentException("El objeto aprendiz no debe ser nulo.");
        }
        if (aprendiz.getUser() == null || aprendiz.getUser().isEmpty()) {
            throw new IllegalArgumentException("El nombre de usuario es requerido.");
        }
        if (aprendiz.getPassword() == null || aprendiz.getPassword().isEmpty()) {
            throw new IllegalArgumentException("La contraseña es requerida.");
        }
        if (aprendiz.getDocumento() == null || aprendiz.getDocumento().isEmpty()) {
            throw new IllegalArgumentException("El documento es requerido.");
        }
        if (aprendiz.getTipoDocumento() == null || aprendiz.getTipoDocumento().isEmpty()) {
            throw new IllegalArgumentException("El tipo de documento es requerido.");
        }
        if (aprendiz.getNombres() == null || aprendiz.getNombres().isEmpty()) {
            throw new IllegalArgumentException("El nombre es requerido.");
        }
        if (aprendiz.getApellidos() == null || aprendiz.getApellidos().isEmpty()) {
            throw new IllegalArgumentException("El apellido es requerido.");
        }
        if (aprendiz.getFechaNacimiento() == null) {
            throw new IllegalArgumentException("La fecha de nacimiento es requerida.");
        }
        if (aprendiz.getTelefono() == null || aprendiz.getTelefono().isEmpty()) {
            throw new IllegalArgumentException("El teléfono es requerido.");
        }
        if (aprendiz.getCorreo() == null || aprendiz.getCorreo().isEmpty()) {
            throw new IllegalArgumentException("El correo es requerido.");
        }
        if (aprendiz.getGenero() == null || aprendiz.getGenero().isEmpty()) {
            throw new IllegalArgumentException("El género es requerido.");
        }
        if (aprendiz.getResidencia() == null || aprendiz.getResidencia().isEmpty()) {
            throw new IllegalArgumentException("La residencia es requerida.");
        }
        if (aprendiz.getFicha() == null) {
            throw new IllegalArgumentException("La ficha es requerida.");
        }

        try {
            // Verificar si el usuario o documento ya existen
            System.out.println("Verificando si el usuario o documento ya existen en la base de datos.");

            Integer countUsuario = jdbcTemplate.queryForObject(
                    "SELECT COUNT(*) FROM usuario WHERE Usuario = ?",
                    new Object[]{aprendiz.getUser()},
                    Integer.class
            );
            System.out.println("Cantidad de usuarios con el mismo nombre: " + countUsuario);
            if (countUsuario != null && countUsuario > 0) {
                throw new IllegalArgumentException("El nombre de usuario ya existe: " + aprendiz.getUser());
            }

            Integer countDocumento = jdbcTemplate.queryForObject(
                    "SELECT COUNT(*) FROM perfilusuario WHERE Documento = ?",
                    new Object[]{aprendiz.getDocumento()},
                    Integer.class
            );
            System.out.println("Cantidad de documentos iguales: " + countDocumento);
            if (countDocumento != null && countDocumento > 0) {
                throw new IllegalArgumentException("El documento ya está registrado: " + aprendiz.getDocumento());
            }

            // Paso 1: Insertar en la tabla `usuario` y obtener el ID
            System.out.println("Insertando en la tabla usuario.");
            String sqlInsertUsuario = "INSERT INTO usuario (Usuario, Contraseña) VALUES (?, ?)";
            KeyHolder keyHolderUsuario = new GeneratedKeyHolder();

            int rowsAffectedUsuario = jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(sqlInsertUsuario, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, aprendiz.getUser());
                ps.setString(2, passwordEncoder.encode(aprendiz.getPassword()));
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
                    new Object[]{aprendiz.getTipoDocumento()},
                    Integer.class
            );
            System.out.println("ID TipoDocumento: " + idTipoDocumento);
            if (idTipoDocumento == null) {
                throw new IllegalArgumentException("Tipo de documento inválido: " + aprendiz.getTipoDocumento());
            }

            Integer idGenero = jdbcTemplate.queryForObject(
                    "SELECT ID FROM genero WHERE TiposGeneros = ?",
                    new Object[]{aprendiz.getGenero()},
                    Integer.class
            );
            System.out.println("ID Género: " + idGenero);
            if (idGenero == null) {
                throw new IllegalArgumentException("Género inválido: " + aprendiz.getGenero());
            }

            String residencia = aprendiz.getResidencia();
            String[] residenciaParts = residencia.split(" - ");
            if (residenciaParts.length < 3) {
                throw new IllegalArgumentException("La residencia debe tener el formato 'Departamento - Municipio - Barrio'");
            }
            String nombreBarrio = residenciaParts[2].trim();
            System.out.println("Nombre del barrio: " + nombreBarrio);

            Integer idBarrio = jdbcTemplate.queryForObject(
                    "SELECT id_barrio FROM barrios WHERE nombre_barrio = ?",
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
                ps.setString(2, aprendiz.getDocumento());
                ps.setInt(3, idTipoDocumento);
                ps.setString(4, aprendiz.getNombres());
                ps.setString(5, aprendiz.getApellidos());
                ps.setDate(6, new java.sql.Date(aprendiz.getFechaNacimiento().getTime()));
                ps.setString(7, aprendiz.getTelefono());
                ps.setString(8, aprendiz.getCorreo());
                ps.setInt(9, idGenero);
                ps.setInt(10, 4); // Ajustar según el ID del rol 'aprendiz' en tu base de datos
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

            // Paso 4: Obtener ID de la ficha
            System.out.println("Obteniendo ID de la ficha.");
            Integer idFicha = jdbcTemplate.queryForObject(
                    "SELECT ID FROM fichas WHERE NumeroFicha = ?",
                    new Object[]{aprendiz.getFicha()},
                    Integer.class
            );
            System.out.println("ID Ficha: " + idFicha);
            if (idFicha == null) {
                throw new IllegalArgumentException("Ficha inválida: " + aprendiz.getFicha());
            }

            // Paso 5: Insertar en la tabla `aprendiz`
            System.out.println("Insertando en la tabla aprendiz.");
            String sqlInsertAprendiz = "INSERT INTO aprendiz (IDPerfilUsuario, IDFicha) VALUES (?, ?)";
            int rowsAffectedAprendiz = jdbcTemplate.update(sqlInsertAprendiz, perfilUsuarioId, idFicha);
            System.out.println("Filas afectadas en la inserción de aprendiz: " + rowsAffectedAprendiz);

            if (rowsAffectedAprendiz == 0) {
                throw new RuntimeException("La inserción en la tabla aprendiz no afectó a ninguna fila.");
            }


            System.out.println("Proceso completado exitosamente.");
            return aprendiz;

        } catch (DataAccessException e) {
            e.printStackTrace();
            throw new RuntimeException("Error al acceder a la base de datos: " + e.getMessage(), e);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error al crear el aprendiz: " + e.getMessage(), e);
        }
    }



    // Actualizar un aprendiz existente
    @Transactional(rollbackFor = Exception.class)
    public AprendizModel updateAprendiz(String documentoActual, AprendizModel aprendiz) {
        // Validación de datos de entrada
        System.out.println("Iniciando método updateAprendiz");

        if (aprendiz == null) {
            throw new IllegalArgumentException("El objeto aprendiz no debe ser nulo.");
        }

        // Imprimir todos los campos del objeto aprendiz para verificar que no sean nulos
        System.out.println("Datos del aprendiz recibidos:");
        System.out.println("Usuario: " + aprendiz.getUser());
        System.out.println("Contraseña: " + aprendiz.getPassword());
        System.out.println("Documento: " + aprendiz.getDocumento());
        System.out.println("TipoDocumento: " + aprendiz.getTipoDocumento());
        System.out.println("Nombres: " + aprendiz.getNombres());
        System.out.println("Apellidos: " + aprendiz.getApellidos());
        System.out.println("FechaNacimiento: " + aprendiz.getFechaNacimiento());
        System.out.println("Teléfono: " + aprendiz.getTelefono());
        System.out.println("Correo: " + aprendiz.getCorreo());
        System.out.println("Género: " + aprendiz.getGenero());
        System.out.println("Residencia: " + aprendiz.getResidencia());
        System.out.println("Ficha: " + aprendiz.getFicha());

        // Validar que los datos requeridos no sean nulos o vacíos
        if (aprendiz.getUser() == null || aprendiz.getUser().isEmpty()) {
            throw new IllegalArgumentException("El nombre de usuario es requerido.");
        }
        if (aprendiz.getPassword() == null || aprendiz.getPassword().isEmpty()) {
            throw new IllegalArgumentException("La contraseña es requerida.");
        }
        if (aprendiz.getDocumento() == null || aprendiz.getDocumento().isEmpty()) {
            throw new IllegalArgumentException("El documento es requerido.");
        }
        if (aprendiz.getTipoDocumento() == null || aprendiz.getTipoDocumento().isEmpty()) {
            throw new IllegalArgumentException("El tipo de documento es requerido.");
        }
        if (aprendiz.getNombres() == null || aprendiz.getNombres().isEmpty()) {
            throw new IllegalArgumentException("El nombre es requerido.");
        }
        if (aprendiz.getApellidos() == null || aprendiz.getApellidos().isEmpty()) {
            throw new IllegalArgumentException("El apellido es requerido.");
        }
        if (aprendiz.getFechaNacimiento() == null) {
            throw new IllegalArgumentException("La fecha de nacimiento es requerida.");
        }
        if (aprendiz.getTelefono() == null || aprendiz.getTelefono().isEmpty()) {
            throw new IllegalArgumentException("El teléfono es requerido.");
        }
        if (aprendiz.getCorreo() == null || aprendiz.getCorreo().isEmpty()) {
            throw new IllegalArgumentException("El correo es requerido.");
        }
        if (aprendiz.getGenero() == null || aprendiz.getGenero().isEmpty()) {
            throw new IllegalArgumentException("El género es requerido.");
        }
        if (aprendiz.getResidencia() == null || aprendiz.getResidencia().isEmpty()) {
            throw new IllegalArgumentException("La residencia es requerida.");
        }
        if (aprendiz.getFicha() == null) {
            throw new IllegalArgumentException("La ficha es requerida.");
        }

        try {
            // Verificar si el aprendiz existe
            System.out.println("Verificando si el aprendiz existe en la base de datos.");

            Integer countAprendiz = jdbcTemplate.queryForObject(
                    "SELECT COUNT(*) FROM perfilusuario pu INNER JOIN rol r ON pu.IDRol = r.ID WHERE pu.Documento = ? AND r.TipoRol = 'aprendiz'",
                    new Object[]{documentoActual},
                    Integer.class
            );
            System.out.println("Cantidad de aprendices con el documento actual: " + countAprendiz);
            if (countAprendiz == null || countAprendiz == 0) {
                throw new IllegalArgumentException("No se encontró un aprendiz con el documento: " + documentoActual);
            }

            // Verificar si el nuevo nombre de usuario o documento ya existen en otro usuario
            System.out.println("Verificando si el nuevo nombre de usuario o documento ya existen en otro usuario.");

            Integer countUsuario = jdbcTemplate.queryForObject(
                    "SELECT COUNT(*) FROM usuario u INNER JOIN perfilusuario pu ON u.ID = pu.IDUsuario WHERE u.Usuario = ? AND pu.Documento <> ?",
                    new Object[]{aprendiz.getUser(), documentoActual},
                    Integer.class
            );
            System.out.println("Cantidad de usuarios con el mismo nombre de usuario: " + countUsuario);
            if (countUsuario != null && countUsuario > 0) {
                throw new IllegalArgumentException("El nombre de usuario ya existe: " + aprendiz.getUser());
            }

            Integer countDocumento = jdbcTemplate.queryForObject(
                    "SELECT COUNT(*) FROM perfilusuario WHERE Documento = ? AND Documento <> ?",
                    new Object[]{aprendiz.getDocumento(), documentoActual},
                    Integer.class
            );
            System.out.println("Cantidad de documentos iguales: " + countDocumento);
            if (countDocumento != null && countDocumento > 0) {
                throw new IllegalArgumentException("El documento ya está registrado: " + aprendiz.getDocumento());
            }

            // Obtener IDs relacionados
            System.out.println("Obteniendo IDs relacionados.");

            // Obtener IDPerfilUsuario y IDUsuario
            String sqlGetIDs = """
            SELECT pu.ID AS IDPerfilUsuario, u.ID AS IDUsuario
            FROM perfilusuario pu
            INNER JOIN usuario u ON pu.IDUsuario = u.ID
            WHERE pu.Documento = ?
        """;
            Map<String, Object> idsMap = jdbcTemplate.queryForMap(sqlGetIDs, documentoActual);

            int perfilUsuarioId = (int) idsMap.get("IDPerfilUsuario");
            int usuarioId = (int) idsMap.get("IDUsuario");

            System.out.println("IDPerfilUsuario: " + perfilUsuarioId);
            System.out.println("IDUsuario: " + usuarioId);

            Integer idTipoDocumento = jdbcTemplate.queryForObject(
                    "SELECT ID FROM tipodocumento WHERE TipoDocumento = ?",
                    new Object[]{aprendiz.getTipoDocumento()},
                    Integer.class
            );
            System.out.println("ID TipoDocumento: " + idTipoDocumento);
            if (idTipoDocumento == null) {
                throw new IllegalArgumentException("Tipo de documento inválido: " + aprendiz.getTipoDocumento());
            }

            Integer idGenero = jdbcTemplate.queryForObject(
                    "SELECT ID FROM genero WHERE TiposGeneros = ?",
                    new Object[]{aprendiz.getGenero()},
                    Integer.class
            );
            System.out.println("ID Género: " + idGenero);
            if (idGenero == null) {
                throw new IllegalArgumentException("Género inválido: " + aprendiz.getGenero());
            }

            String residencia = aprendiz.getResidencia();
            String[] residenciaParts = residencia.split(" - ");
            if (residenciaParts.length < 3) {
                throw new IllegalArgumentException("La residencia debe tener el formato 'Departamento - Municipio - Barrio'");
            }
            String nombreBarrio = residenciaParts[2].trim();
            System.out.println("Nombre del barrio: " + nombreBarrio);

            Integer idBarrio = jdbcTemplate.queryForObject(
                    "SELECT id_barrio FROM barrios WHERE nombre_barrio = ?",
                    new Object[]{nombreBarrio},
                    Integer.class
            );
            System.out.println("ID Barrio: " + idBarrio);
            if (idBarrio == null) {
                throw new IllegalArgumentException("Barrio inválido: " + nombreBarrio);
            }

            // Obtener ID de la ficha
            System.out.println("Obteniendo ID de la ficha.");
            Integer idFicha = jdbcTemplate.queryForObject(
                    "SELECT ID FROM fichas WHERE NumeroFicha = ?",
                    new Object[]{aprendiz.getFicha()},
                    Integer.class
            );
            System.out.println("ID Ficha: " + idFicha);
            if (idFicha == null) {
                throw new IllegalArgumentException("Ficha inválida: " + aprendiz.getFicha());
            }

            // Paso 1: Actualizar en la tabla `usuario`
            System.out.println("Actualizando en la tabla usuario.");
            String sqlUpdateUsuario = "UPDATE usuario SET Usuario = ?, Contraseña = ? WHERE ID = ?";
            int rowsAffectedUsuario = jdbcTemplate.update(sqlUpdateUsuario,
                    aprendiz.getUser(),
                    passwordEncoder.encode(aprendiz.getPassword()),
                    usuarioId);
            System.out.println("Filas afectadas en la actualización de usuario: " + rowsAffectedUsuario);

            if (rowsAffectedUsuario == 0) {
                throw new RuntimeException("La actualización en la tabla usuario no afectó a ninguna fila.");
            }

            // Paso 2: Actualizar en la tabla `perfilusuario`
            System.out.println("Actualizando en la tabla perfilusuario.");
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
            int rowsAffectedPerfilUsuario = jdbcTemplate.update(sqlUpdatePerfilUsuario,
                    aprendiz.getDocumento(),
                    idTipoDocumento,
                    aprendiz.getNombres(),
                    aprendiz.getApellidos(),
                    new java.sql.Date(aprendiz.getFechaNacimiento().getTime()),
                    aprendiz.getTelefono(),
                    aprendiz.getCorreo(),
                    idGenero,
                    idBarrio,
                    perfilUsuarioId);
            System.out.println("Filas afectadas en la actualización de perfilusuario: " + rowsAffectedPerfilUsuario);

            if (rowsAffectedPerfilUsuario == 0) {
                throw new RuntimeException("La actualización en la tabla perfilusuario no afectó a ninguna fila.");
            }

            // Paso 3: Actualizar en la tabla `aprendiz`
            System.out.println("Actualizando en la tabla aprendiz.");
            String sqlUpdateAprendiz = "UPDATE aprendiz SET IDFicha = ? WHERE IDPerfilUsuario = ?";
            int rowsAffectedAprendiz = jdbcTemplate.update(sqlUpdateAprendiz, idFicha, perfilUsuarioId);
            System.out.println("Filas afectadas en la actualización de aprendiz: " + rowsAffectedAprendiz);

            if (rowsAffectedAprendiz == 0) {
                throw new RuntimeException("La actualización en la tabla aprendiz no afectó a ninguna fila.");
            }

            System.out.println("Proceso de actualización completado exitosamente.");
            return aprendiz;

        } catch (DataAccessException e) {
            e.printStackTrace();
            throw new RuntimeException("Error al acceder a la base de datos: " + e.getMessage(), e);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error al actualizar el aprendiz: " + e.getMessage(), e);
        }
    }


    // Eliminar un aprendiz por documento
    public boolean deleteAprendiz(String documento) {
        String sql = """
                DELETE FROM aprendiz 
                WHERE IDPerfilUsuario = (SELECT ID FROM perfilusuario WHERE Documento = ?)
                """;
        int deleted = jdbcTemplate.update(sql, documento);

        if (deleted > 0) {
            String deletePerfilUsuario = """
                    DELETE FROM perfilusuario 
                    WHERE Documento = ?
                    """;
            jdbcTemplate.update(deletePerfilUsuario, documento);
            return true;
        } else {
            return false;
        }
    }


    public List<AprendizModel> getAllAprendicesFicha(Integer ficha) {
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
                   fc.NumeroFicha AS NumeroFicha,
                   pform.ProgramaFormacion AS ProgramaFormacion,
                   jf.JornadasFormacion AS JornadasFormacion,
                   nf.NivelFormacion AS NivelFormacion,
                   sd.CentroFormacion AS CentroFormacion,
                   areas.Area AS Area
            FROM aprendiz ap
                INNER JOIN perfilusuario pf ON ap.IDPerfilUsuario = pf.ID
                INNER JOIN usuario us ON pf.IDUsuario = us.ID
                INNER JOIN tipodocumento td ON pf.IDTipoDocumento = td.ID
                INNER JOIN genero ge ON pf.IDGenero = ge.ID
                INNER JOIN barrios ON pf.IDBarrio = barrios.id_barrio
                INNER JOIN municipios mun ON barrios.id_municipio = mun.id_municipio
                INNER JOIN departamentos dept ON mun.id_departamento = dept.id_departamento
                INNER JOIN fichas fc ON ap.IDFicha = fc.ID
                INNER JOIN programaformacion pform ON fc.IDProgramaFormacion = pform.ID
                INNER JOIN jornadaformacion jf ON pform.IDJornadaFormacion = jf.ID
                INNER JOIN nivelformacion nf ON pform.IDNivelFormacion = nf.ID
                INNER JOIN sede sd ON pform.IDSede = sd.ID
                INNER JOIN areas ON pform.IDArea = areas.ID
            WHERE fc.NumeroFicha = ?
            """;

        try {
            return jdbcTemplate.query(consulta, new Object[]{ficha}, (rs, rowNum) ->
                    AprendizModel.builder()
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
                            .ficha(rs.getInt("NumeroFicha"))
                            .programaFormacion(rs.getString("ProgramaFormacion"))
                            .jornadaFormacion(rs.getString("JornadasFormacion"))
                            .nivelFormacion(rs.getString("NivelFormacion"))
                            .sede(rs.getString("CentroFormacion"))
                            .area(rs.getString("Area"))
                            .build());

        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList(); // Devuelve una lista vacía en lugar de null en caso de error
        }
    }
}