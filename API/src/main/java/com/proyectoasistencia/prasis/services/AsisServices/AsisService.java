package com.proyectoasistencia.prasis.services.AsisServices;


import com.proyectoasistencia.prasis.models.UsersModels.AprendizModel;
import com.proyectoasistencia.prasis.services.UsersServices.AprendizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class AsisService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private AprendizService aprendizService;

    @Transactional(rollbackFor = Exception.class)
    public Map<String, Object> upAsis(Map<String, Object> asistenciaData) {
        // Obtener los datos de la asistencia
        int ficha = (int) Optional.ofNullable(asistenciaData.get("Ficha"))
                .orElseThrow(() -> new IllegalArgumentException("Ficha es requerida"));

        String claseFormacion = Optional.ofNullable(asistenciaData.get("ClaseFormacion"))
                .map(Object::toString)
                .orElseThrow(() -> new IllegalArgumentException("Clase de formación es requerida"));

        // Obtener la información de la clase e instructor
        Map<String, Object> claseEInstructor = obtenerClaseEInstructorPorFichaYClase(ficha, claseFormacion);

        if (claseEInstructor == null) {
            throw new IllegalArgumentException("No se encontró la clase o el instructor para la ficha y clase proporcionados.");
        }

        // Procesar la lista de aprendices
        List<Map<String, Object>> listaAprendices = (List<Map<String, Object>>) asistenciaData.get("ListaAprendices");

        if (listaAprendices == null) {
            throw new IllegalArgumentException("Lista de aprendices es requerida.");
        }

        // Crear una lista final que incluirá tanto los aprendices presentes como los ausentes
        List<Map<String, Object>> listaCompleta = new ArrayList<>();

        // Obtener los aprendices asociados a la ficha desde la base de datos
        List<AprendizModel> aprendicesTotales = obtenerAprendicesPorFicha(ficha);

        // Iterar sobre todos los aprendices de la ficha y agregar al resultado final
        for (AprendizModel aprendiz : aprendicesTotales) {
            // Verificar si el aprendiz está en la lista de asistencia proporcionada
            Map<String, Object> aprendizEncontrado = listaAprendices.stream()
                    .filter(a -> a.get("Documento").toString().equals(aprendiz.getDocumento()))
                    .findFirst()
                    .orElse(null);

            // Si está presente en la lista, agregar los datos completos y las horas de inasistencia reportadas
            if (aprendizEncontrado != null) {
                Map<String, Object> aprendizCompleto = construirAprendizConHoras(aprendiz, (int) aprendizEncontrado.get("HorasInasistencia"));
                listaCompleta.add(aprendizCompleto);
            } else {
                // Si no está en la lista, agregarlo con 5 horas de inasistencia
                Map<String, Object> aprendizAusente = construirAprendizConHoras(aprendiz, 5);
                listaCompleta.add(aprendizAusente);
            }
        }

        // Completar el JSON final con la información del instructor, clase, y lista de aprendices
        Map<String, Object> resultadoFinal = new HashMap<>();
        resultadoFinal.put("TipoAsistencia", asistenciaData.get("TipoAsistencia"));
        resultadoFinal.put("ProgramaFormacion", claseEInstructor.get("ProgramaFormacion"));
        resultadoFinal.put("Ficha", ficha);
        resultadoFinal.put("NivelFormacion", claseEInstructor.get("NivelFormacion"));
        resultadoFinal.put("Sede", claseEInstructor.get("Sede"));
        resultadoFinal.put("Instructor", claseEInstructor.get("Instructor"));
        resultadoFinal.put("ClaseFormacion", claseEInstructor.get("NombreClase"));
        resultadoFinal.put("Ambiente", asistenciaData.get("Ambiente"));
        resultadoFinal.put("Fecha", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));  // Fecha actual
        resultadoFinal.put("ListaAprendices", listaCompleta);

        return resultadoFinal;
    }

    // Método para obtener la clase y el instructor por ficha y clase de formación
    public Map<String, Object> obtenerClaseEInstructorPorFichaYClase(int ficha, String claseFormacion) {
        String sql = """
                    SELECT cf.NombreClase, CONCAT(pu.Nombres, ' ', pu.Apellidos) AS Instructor,
                           pform.ProgramaFormacion, nf.NivelFormacion, sd.CentroFormacion AS Sede
                    FROM claseformacion_fichas cff
                             INNER JOIN claseformacion cf ON cff.IDClaseFormacion = cf.ID
                             INNER JOIN instructor ins ON cf.IDInstructor = ins.ID
                             INNER JOIN perfilusuario pu ON ins.IDPerfilUsuario = pu.ID
                             INNER JOIN fichas f ON cff.IDFicha = f.ID
                             INNER JOIN programaformacion pform ON f.IDProgramaFormacion = pform.ID
                             INNER JOIN nivelformacion nf ON pform.IDNivelFormacion = nf.ID
                             INNER JOIN sede sd ON pform.IDSede = sd.ID
                    WHERE f.NumeroFicha = ? AND cf.NombreClase = ?""";

        try {
            return jdbcTemplate.queryForObject(sql, new Object[]{ficha, claseFormacion}, (rs, rowNum) -> {
                Map<String, Object> resultado = new HashMap<>();
                resultado.put("NombreClase", rs.getString("NombreClase"));
                resultado.put("Instructor", rs.getString("Instructor"));
                resultado.put("ProgramaFormacion", rs.getString("ProgramaFormacion"));
                resultado.put("NivelFormacion", rs.getString("NivelFormacion"));
                resultado.put("Sede", rs.getString("Sede"));
                return resultado;
            });
        } catch (EmptyResultDataAccessException e) {
            System.out.println("No se encontró clase o instructor para la ficha: " + ficha + " y la clase: " + claseFormacion);
            return null;
        }
    }

    // Método para obtener todos los aprendices asociados a una ficha
    public List<AprendizModel> obtenerAprendicesPorFicha(int ficha) {
        System.out.println(ficha);
        String sql = """
                SELECT us.Usuario AS Usuario,
                       us.Contraseña AS Contraseña,
                       pf.Documento AS Documento,
                       td.TipoDocumento AS TipoDocumento,
                       pf.Nombres AS Nombres,
                       pf.Apellidos AS Apellidos,
                       pf.FecNacimiento AS FecNacimiento,
                       pf.Telefono AS Telefono,
                       pf.Correo AS Correo,
                       ge.TiposGeneros AS Genero,
                       CONCAT(dept.nombre_departamento, ' - ', mun.nombre_municipio, ' - ', barrios.nombre_barrio) AS Residencia,
                       fc.NumeroFicha AS NumeroFicha,
                       pform.ProgramaFormacion AS ProgramaFormacion,
                       jf.JornadasFormacion AS JornadaFormacion,
                       nf.NivelFormacion AS NivelFormacion,
                       sd.CentroFormacion AS Sede,
                       areas.Area AS Area
                FROM aprendiz ap
                    INNER JOIN perfilusuario pf ON ap.IDPerfilUsuario = pf.ID
                    INNER JOIN usuario us ON pf.IDUsuario = us.ID
                    INNER JOIN tipodocumento td ON pf.IDTipoDocumento = td.ID
                    INNER JOIN genero ge ON pf.IDGenero = ge.ID
                    INNER JOIN barrios ON pf.IDBarrio = barrios.ID
                    INNER JOIN municipios mun ON barrios.id_municipio = mun.ID
                    INNER JOIN departamentos dept ON mun.id_departamento = dept.ID
                    INNER JOIN fichas fc ON ap.IDFicha = fc.ID
                    INNER JOIN programaformacion pform ON fc.IDProgramaFormacion = pform.ID
                    INNER JOIN jornadaformacion jf ON pform.IDJornadaFormacion = jf.ID
                    INNER JOIN nivelformacion nf ON pform.IDNivelFormacion = nf.ID
                    INNER JOIN sede sd ON pform.IDSede = sd.ID
                    INNER JOIN areas ON pform.IDArea = areas.ID
                WHERE fc.NumeroFicha = ?
                """;

        return jdbcTemplate.query(sql, new Object[]{ficha}, (rs, rowNum) -> AprendizModel.builder()
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
                .ficha(rs.getInt("NumeroFicha"))
                .programaFormacion(rs.getString("ProgramaFormacion"))
                .jornadaFormacion(rs.getString("JornadaFormacion"))
                .nivelFormacion(rs.getString("NivelFormacion"))
                .sede(rs.getString("Sede"))
                .area(rs.getString("Area"))
                .build());
    }

    // Método auxiliar para construir un aprendiz con las horas de inasistencia
    private Map<String, Object> construirAprendizConHoras(AprendizModel aprendiz, int horasInasistencia) {
        Map<String, Object> aprendizMap = new HashMap<>();
        aprendizMap.put("Documento", aprendiz.getDocumento());
        aprendizMap.put("Nombre", aprendiz.getNombres() + " " + aprendiz.getApellidos());
        aprendizMap.put("Telefono", aprendiz.getTelefono());
        aprendizMap.put("Correo", aprendiz.getCorreo());
        aprendizMap.put("Genero", aprendiz.getGenero());
        aprendizMap.put("Residencia", aprendiz.getResidencia());
        aprendizMap.put("HorasInasistencia", horasInasistencia);
        return aprendizMap;
    }

    @Transactional(rollbackFor = Exception.class)
    public void registrarAsistenciaYActividades(Map<String, Object> datosAsistencia, byte[] archivoExcel) throws SQLException {

        System.out.println("Iniciando el método registrarAsistenciaYActividades con datosAsistencia: " + datosAsistencia);

        // 1. Subir el archivo Excel a la tabla `asistencia`
        String sqlInsertAsistencia = "INSERT INTO asistencia (AsistenciaExcel) VALUES (?)";
        KeyHolder asistenciaKeyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sqlInsertAsistencia, Statement.RETURN_GENERATED_KEYS);
            ps.setBytes(1, archivoExcel);  // archivoExcel es el BLOB del archivo
            return ps;
        }, asistenciaKeyHolder);

        int idAsistencia = asistenciaKeyHolder.getKey().intValue();  // ID del registro en `asistencia`
        System.out.println("Se ha insertado el archivo Excel en la tabla asistencia con ID: " + idAsistencia);

        // 2. Verificar y obtener valores de `datosAsistencia`
        String claseFormacion = Optional.ofNullable(datosAsistencia.get("ClaseFormacion"))
                .map(Object::toString)
                .orElseThrow(() -> {
                    System.out.println("ClaseFormacion es nulo en los datos de asistencia: " + datosAsistencia);
                    return new IllegalArgumentException("ClaseFormacion no puede ser nulo");
                });

        String ambiente = Optional.ofNullable(datosAsistencia.get("Ambiente"))
                .map(Object::toString)
                .orElseThrow(() -> {
                    System.out.println("Ambiente es nulo en los datos de asistencia: " + datosAsistencia);
                    return new IllegalArgumentException("Ambiente no puede ser nulo");
                });

        Integer ficha = Optional.ofNullable((Integer) datosAsistencia.get("Ficha"))
                .orElseThrow(() -> {
                    System.out.println("Ficha es nulo en los datos de asistencia: " + datosAsistencia);
                    return new IllegalArgumentException("Ficha no puede ser nulo");
                });

        String tipoAsistencia = Optional.ofNullable(datosAsistencia.get("TipoAsistencia"))
                .map(Object::toString)
                .orElseThrow(() -> {
                    System.out.println("TipoAsistencia es nulo en los datos de asistencia: " + datosAsistencia);
                    return new IllegalArgumentException("TipoAsistencia no puede ser nulo");
                });

        String instructor = Optional.ofNullable(datosAsistencia.get("Instructor"))
                .map(Object::toString)
                .orElseThrow(() -> {
                    System.out.println("Instructor es nulo en los datos de asistencia: " + datosAsistencia);
                    return new IllegalArgumentException("Instructor no puede ser nulo");
                });

        System.out.println("ClaseFormacion: " + claseFormacion + ", Ambiente: " + ambiente + ", Ficha: " + ficha + ", TipoAsistencia: " + tipoAsistencia + ", Instructor: " + instructor);

        // 3. Insertar en la tabla `registroasistencias`
        String sqlInsertRegistroAsistencia = """
            INSERT INTO registroasistencias (IDClaseFormacion, IDAmbiente, IDFicha, Fecha, IDArchivo, IDTipoActividad)
            VALUES (?, ?, ?, CURRENT_TIMESTAMP, ?, ?)
        """;

        int idClaseFormacion = obtenerIdClaseFormacion(claseFormacion);
        System.out.println("ID de la ClaseFormacion obtenido: " + idClaseFormacion);

        int idAmbiente = obtenerIdAmbiente(ambiente);
        System.out.println("ID de Ambiente obtenido: " + idAmbiente);

        int idTipoActividad = obtenerIdTipoActividad(tipoAsistencia);
        System.out.println("ID de TipoActividad obtenido: " + idTipoActividad);

        int idFicha = obtenerIdFicha(ficha);
        System.out.println("ID de Ficha obtenido: " + idFicha);

        KeyHolder registroAsistenciaKeyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sqlInsertRegistroAsistencia, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, idClaseFormacion);
            ps.setInt(2, idAmbiente);
            ps.setInt(3, idFicha);
            ps.setInt(4, idAsistencia);
            ps.setInt(5, idTipoActividad);
            return ps;
        }, registroAsistenciaKeyHolder);

        int idRegistroAsistencia = registroAsistenciaKeyHolder.getKey().intValue();  // ID del registro en `registroasistencias`
        System.out.println("Se ha insertado en registroasistencias con ID: " + idRegistroAsistencia);

        // 4. Insertar los registros en la tabla `registroactividad`
        List<Map<String, Object>> listaAprendices = (List<Map<String, Object>>) datosAsistencia.get("ListaAprendices");

        if (listaAprendices == null || listaAprendices.isEmpty()) {
            System.out.println("ListaAprendices está vacía o es nula: " + datosAsistencia);
            throw new IllegalArgumentException("La lista de aprendices no puede estar vacía.");
        }

        String sqlInsertRegistroActividad = """
            INSERT INTO registroactividad (IDRegistroAsistencia, HorasInasistencia, IDAprendiz, IDSoporte)
            VALUES (?, ?, ?, NULL)
        """;

        for (Map<String, Object> aprendiz : listaAprendices) {
            String documentoAprendiz = Optional.ofNullable(aprendiz.get("Documento"))
                    .map(Object::toString)
                    .orElseThrow(() -> {
                        System.out.println("Documento es nulo para uno de los aprendices: " + aprendiz);
                        return new IllegalArgumentException("Documento no puede ser nulo");
                    });

            int horasInasistencia = Optional.ofNullable((Integer) aprendiz.get("HorasInasistencia"))
                    .orElse(0);  // Asume 0 si no se proporciona.

            int idAprendiz = obtenerIdAprendizPorDocumento(documentoAprendiz);
            System.out.println("ID del aprendiz obtenido: " + idAprendiz);

            // Insertar cada registro en `registroactividad`
            jdbcTemplate.update(sqlInsertRegistroActividad, idRegistroAsistencia, horasInasistencia, idAprendiz);
        }
    }

    // Métodos auxiliares para obtener los IDs de otras tablas

    private int obtenerIdClaseFormacion(String claseFormacion) throws SQLException {
        String sql = "SELECT ID FROM claseformacion WHERE NombreClase = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{claseFormacion}, Integer.class);
    }

    private int obtenerIdAmbiente(String ambiente) throws SQLException {
        String sql = "SELECT ID FROM ambientes WHERE Ambiente = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{ambiente}, Integer.class);
    }

    private int obtenerIdTipoActividad(String tipoActividad) throws SQLException {
        String sql = "SELECT ID FROM actividad WHERE TipoActividad = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{tipoActividad}, Integer.class);
    }

    private int obtenerIdAprendizPorDocumento(String documento) throws SQLException {
        String sql = """
                    SELECT az.ID FROM aprendiz az
                              INNER JOIN perfilusuario pu ON az.IDPerfilUsuario = pu.ID
                              WHERE pu.Documento = ?""";
        return jdbcTemplate.queryForObject(sql, new Object[]{documento}, Integer.class);
    }

    private int obtenerIdFicha(int numeroFicha) {
        String sql = "SELECT ID FROM fichas WHERE NumeroFicha = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new Object[]{numeroFicha}, Integer.class);
        } catch (EmptyResultDataAccessException e) {
            System.out.println("No se encontró la ficha: " + numeroFicha);
            throw new IllegalArgumentException("Ficha no encontrada: " + numeroFicha);
        }
    }

    // Método para obtener todas las asistencias con joins correspondientes e incluir el archivo BLOB
    public List<Map<String, Object>> listarAsistencias() {
        String sql = """
            SELECT 
                ra.Fecha AS FechaRegistro,
                cf.NombreClase AS ClaseFormacion,
                a.Ambiente AS Ambiente,
                f.NumeroFicha AS Ficha,
                CONCAT(pu.Nombres, ' ', pu.Apellidos) AS Instructor,
                ta.TipoActividad AS TipoAsistencia,
                ass.AsistenciaExcel AS ArchivoExcel
            FROM registroasistencias ra
            INNER JOIN claseformacion cf ON ra.IDClaseFormacion = cf.ID
            INNER JOIN ambientes a ON ra.IDAmbiente = a.ID
            INNER JOIN fichas f ON ra.IDFicha = f.ID
            INNER JOIN instructor i ON cf.IDInstructor = i.ID
            INNER JOIN perfilusuario pu ON i.IDPerfilUsuario = pu.ID
            INNER JOIN actividad ta ON ra.IDTipoActividad = ta.ID
            INNER JOIN asistencia ass ON ra.IDArchivo = ass.ID
        """;

        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Map<String, Object> asistencia = new HashMap<>();
            asistencia.put("FechaRegistro", rs.getTimestamp("FechaRegistro"));
            asistencia.put("ClaseFormacion", rs.getString("ClaseFormacion"));
            asistencia.put("Ambiente", rs.getString("Ambiente"));
            asistencia.put("Ficha", rs.getInt("Ficha"));
            asistencia.put("Instructor", rs.getString("Instructor"));
            asistencia.put("TipoAsistencia", rs.getString("TipoAsistencia"));
            asistencia.put("ArchivoExcel", rs.getBytes("ArchivoExcel"));  // Obtiene el archivo en formato BLOB
            return asistencia;
        });
    }

    public List<Map<String, Object>> listarAsistenciasPorInstructor(String documentoInstructor) throws SQLException {
        String sql = """
            SELECT 
                ra.Fecha AS FechaRegistro,
                cf.NombreClase AS ClaseFormacion,
                a.Ambiente AS Ambiente,
                f.NumeroFicha AS Ficha,
                CONCAT(pu.Nombres, ' ', pu.Apellidos) AS Instructor,
                ta.TipoActividad AS TipoAsistencia,
                ass.AsistenciaExcel AS ArchivoExcel
            FROM registroasistencias ra
            INNER JOIN claseformacion cf ON ra.IDClaseFormacion = cf.ID
            INNER JOIN ambientes a ON ra.IDAmbiente = a.ID
            INNER JOIN fichas f ON ra.IDFicha = f.ID
            INNER JOIN instructor i ON cf.IDInstructor = i.ID
            INNER JOIN perfilusuario pu ON i.IDPerfilUsuario = pu.ID
            INNER JOIN actividad ta ON ra.IDTipoActividad = ta.ID
            INNER JOIN asistencia ass ON ra.IDArchivo = ass.ID
            WHERE pu.Documento = ?
        """;

        return jdbcTemplate.query(sql, new Object[]{documentoInstructor}, (rs, rowNum) -> {
            Map<String, Object> asistencia = new HashMap<>();
            asistencia.put("FechaRegistro", rs.getTimestamp("FechaRegistro"));
            asistencia.put("ClaseFormacion", rs.getString("ClaseFormacion"));
            asistencia.put("Ambiente", rs.getString("Ambiente"));
            asistencia.put("Ficha", rs.getInt("Ficha"));
            asistencia.put("Instructor", rs.getString("Instructor"));
            asistencia.put("TipoAsistencia", rs.getString("TipoAsistencia"));
            asistencia.put("ArchivoExcel", rs.getBytes("ArchivoExcel"));  // Obtiene el archivo en formato BLOB
            return asistencia;
        });
    }

    public List<Map<String, Object>> FiltroListarAsistencias(String documentoInstructor, String ambiente, String programaFormacion, Integer ficha) {
        StringBuilder sql = new StringBuilder("""
            SELECT CONCAT(pu.Nombres, ' ', pu.Apellidos) AS Instructor,
                   cf.NombreClase,
                   amb.Ambiente,
                   fc.NumeroFicha,
                   ra.Fecha,
                   ac.AsistenciaExcel,
                   av.TipoActividad
            FROM registroasistencias ra
                     INNER JOIN asistencia ac ON ra.IDArchivo = ac.ID
                     INNER JOIN claseformacion cf ON ra.IDClaseFormacion = cf.ID
                     INNER JOIN instructor i ON cf.IDInstructor = i.ID
                     INNER JOIN perfilusuario pu ON i.IDPerfilUsuario = pu.ID
                     INNER JOIN ambientes amb ON ra.IDAmbiente = amb.ID
                     INNER JOIN fichas fc ON ra.IDFicha = fc.ID
                     INNER JOIN programaformacion pf ON fc.IDProgramaFormacion = pf.ID
                     INNER JOIN actividad av ON ra.IDTipoActividad = av.ID
            WHERE pu.Documento = ?""");

        List<Object> params = new ArrayList<>();
        params.add(documentoInstructor);

        if (ambiente != null && !ambiente.isEmpty()) {
            sql.append(" AND amb.Ambiente = ?");
            params.add(ambiente);
        }

        if (programaFormacion != null && !programaFormacion.isEmpty()) {
            sql.append(" AND pf.ProgramaFormacion = ?");
            params.add(programaFormacion);
        }

        if (ficha != null) {
            sql.append(" AND fc.NumeroFicha = ?");
            params.add(ficha);
        }

        return jdbcTemplate.query(sql.toString(), params.toArray(), (rs, rowNum) -> {
            Map<String, Object> asistencia = new HashMap<>();

            asistencia.put("ClaseFormacion", rs.getString("NombreClase"));
            asistencia.put("Ambiente", rs.getString("Ambiente"));
            asistencia.put("Ficha", rs.getInt("NumeroFicha"));
            asistencia.put("Instructor", rs.getString("Instructor"));
            asistencia.put("Fecha", rs.getTimestamp("Fecha"));
            asistencia.put("TipoAsistencia", rs.getString("TipoActividad"));
            asistencia.put("ArchivoExcel", rs.getBytes("AsistenciaExcel"));
            return asistencia;
        });
    }


}




