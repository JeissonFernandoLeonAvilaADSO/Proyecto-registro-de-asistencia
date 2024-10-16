package com.proyectoasistencia.prasis.services.AsisServices;


import com.proyectoasistencia.prasis.models.UsersModels.AprendizModel;
import com.proyectoasistencia.prasis.services.DataTablesServices.ActividadDataService;
import com.proyectoasistencia.prasis.services.DataTablesServices.AmbientesDataService;
import com.proyectoasistencia.prasis.services.DataTablesServices.AprendicesDataService;
import com.proyectoasistencia.prasis.services.UsersServices.AprendizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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
    private AprendicesDataService aprendicesDataService;

    @Autowired
    private AmbientesDataService ambientesDataService;

    @Autowired
    private ActividadDataService actividadDataService;


    /**
     * Procesa la asistencia y prepara los datos para su registro y generación de Excel.
     *
     * @param asistenciaData Datos de la asistencia recibidos desde el controlador.
     * @return Mapa con los datos procesados de la asistencia.
     */
    @Transactional(rollbackFor = Exception.class)
    public Map<String, Object> upAsis(Map<String, Object> asistenciaData) {
        // Obtener los datos de la asistencia
        int ficha = (int) Optional.ofNullable(asistenciaData.get("Ficha"))
                .orElseThrow(() -> new IllegalArgumentException("Ficha es requerida"));

        String claseFormacion = Optional.ofNullable(asistenciaData.get("ClaseFormacion"))
                .map(Object::toString)
                .orElseThrow(() -> new IllegalArgumentException("Clase de formación es requerida"));

        String instructorDocumento = Optional.ofNullable(asistenciaData.get("InstructorDocumento"))
                .map(Object::toString)
                .orElseThrow(() -> new IllegalArgumentException("Documento del instructor es requerido"));

        // Obtener la información de la clase, instructor y ficha desde la tabla ternaria
        Map<String, Object> claseEInstructorFicha = obtenerClaseEInstructorFichaPorFichaClaseYInstructor(ficha, claseFormacion, instructorDocumento);

        if (claseEInstructorFicha == null) {
            throw new IllegalArgumentException("No se encontró la asignación de clase, instructor y ficha proporcionados.");
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
        resultadoFinal.put("ProgramaFormacion", claseEInstructorFicha.get("ProgramaFormacion"));
        resultadoFinal.put("Ficha", ficha);
        resultadoFinal.put("NivelFormacion", claseEInstructorFicha.get("NivelFormacion"));
        resultadoFinal.put("Sede", claseEInstructorFicha.get("Sede"));
        resultadoFinal.put("InstructorDocumento", instructorDocumento);
        resultadoFinal.put("Instructor", claseEInstructorFicha.get("Instructor"));
        resultadoFinal.put("ClaseFormacion", claseEInstructorFicha.get("NombreClase"));
        resultadoFinal.put("Ambiente", asistenciaData.get("Ambiente"));
        resultadoFinal.put("Fecha", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));  // Fecha actual
        resultadoFinal.put("ListaAprendices", listaCompleta);

        return resultadoFinal;
    }

    /**
     * Método para obtener la asignación de clase, instructor y ficha desde la tabla ternaria.
     *
     * @param ficha               Número de ficha.
     * @param claseFormacion      Nombre de la clase de formación.
     * @param instructorDocumento Documento del instructor.
     * @return Mapa con información de la asignación o null si no se encuentra.
     */
    public Map<String, Object> obtenerClaseEInstructorFichaPorFichaClaseYInstructor(int ficha, String claseFormacion, String instructorDocumento) {
        String sql = """
            SELECT 
                cif.ID AS IDClaseInstructorFicha,
                cf.NombreClase,
                CONCAT(pu_instructor.Nombres, ' ', pu_instructor.Apellidos) AS Instructor,
                pform.ProgramaFormacion, 
                nf.NivelFormacion, 
                sd.CentroFormacion AS Sede
            FROM claseformacion_instructor_ficha cif
            INNER JOIN claseformacion cf ON cif.IDClaseFormacion = cf.ID
            INNER JOIN perfilusuario pu_instructor ON cif.IDPerfilUsuario = pu_instructor.ID
            INNER JOIN fichas f ON cif.IDFicha = f.ID
            INNER JOIN programaformacion pform ON f.IDProgramaFormacion = pform.ID
            INNER JOIN nivelformacion nf ON pform.IDNivelFormacion = nf.ID
            INNER JOIN sede sd ON pform.IDSede = sd.ID
            WHERE f.NumeroFicha = ? AND cf.NombreClase = ? AND pu_instructor.Documento = ?
            """;

        try {
            return jdbcTemplate.queryForObject(sql, new Object[]{ficha, claseFormacion, instructorDocumento}, (rs, rowNum) -> {
                Map<String, Object> resultado = new HashMap<>();
                resultado.put("IDClaseInstructorFicha", rs.getInt("IDClaseInstructorFicha"));
                resultado.put("NombreClase", rs.getString("NombreClase"));
                resultado.put("Instructor", rs.getString("Instructor"));
                resultado.put("ProgramaFormacion", rs.getString("ProgramaFormacion"));
                resultado.put("NivelFormacion", rs.getString("NivelFormacion"));
                resultado.put("Sede", rs.getString("Sede"));
                return resultado;
            });
        } catch (EmptyResultDataAccessException e) {
            System.out.println("No se encontró la asignación para ficha: " + ficha + ", clase: " + claseFormacion + ", instructor: " + instructorDocumento);
            return null;
        }
    }

    public List<AprendizModel> obtenerAprendicesPorFicha(int ficha) {
        System.out.println("Obteniendo aprendices para la ficha: " + ficha);
        String sql = """
            SELECT DISTINCT us.Usuario AS Usuario,
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
            FROM aprendiz_claseinstructorficha acif
                 INNER JOIN perfilusuario pf ON acif.IDPerfilUsuario = pf.ID
                 INNER JOIN usuario us ON pf.IDUsuario = us.ID
                 INNER JOIN tipodocumento td ON pf.IDTipoDocumento = td.ID
                 INNER JOIN genero ge ON pf.IDGenero = ge.ID
                 INNER JOIN barrios ON pf.IDBarrio = barrios.ID
                 INNER JOIN municipios mun ON barrios.id_municipio = mun.ID
                 INNER JOIN departamentos dept ON mun.id_departamento = dept.ID
                 INNER JOIN claseformacion_instructor_ficha cif ON acif.IDClaseInstructorFicha = cif.ID
                 INNER JOIN fichas fc ON cif.IDFicha = fc.ID
                 INNER JOIN programaformacion pform ON fc.IDProgramaFormacion = pform.ID
                 INNER JOIN jornadaformacion jf ON fc.IDJornadaFormacion = jf.ID
                 INNER JOIN nivelformacion nf ON pform.IDNivelFormacion = nf.ID
                 INNER JOIN sede sd ON pform.IDSede = sd.ID
                 INNER JOIN areas ON pform.IDArea = areas.ID
            WHERE fc.NumeroFicha = ?
            """;

        try {
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
                    .build());
        } catch (EmptyResultDataAccessException e) {
            System.out.println("No se encontraron aprendices para la ficha: " + ficha);
            return Collections.emptyList();
        }
    }

    /**
     * Método auxiliar para construir un aprendiz con las horas de inasistencia.
     *
     * @param aprendiz          Objeto AprendizModel.
     * @param horasInasistencia Horas de inasistencia.
     * @return Mapa con los datos del aprendiz y sus horas de inasistencia.
     */
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

    /**
     * Método para registrar la asistencia y las actividades asociadas en la base de datos.
     *
     * @param datosAsistencia Datos de la asistencia procesada.
     * @param archivoExcel    Archivo Excel generado en bytes.
     * @throws SQLException Si ocurre un error en las operaciones de base de datos.
     */
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

        String instructorDocumento = Optional.ofNullable(datosAsistencia.get("InstructorDocumento"))
                .map(Object::toString)
                .orElseThrow(() -> {
                    System.out.println("InstructorDocumento es nulo en los datos de asistencia: " + datosAsistencia);
                    return new IllegalArgumentException("InstructorDocumento no puede ser nulo");
                });

        System.out.println("ClaseFormacion: " + claseFormacion + ", Ambiente: " + ambiente + ", Ficha: " + ficha + ", TipoAsistencia: " + tipoAsistencia + ", InstructorDocumento: " + instructorDocumento);

        // 3. Insertar en la tabla `registroasistencias`
        String sqlInsertRegistroAsistencia = """
                INSERT INTO registroasistencias (IDClaseInstructorFicha, IDAmbiente, Fecha, IDArchivo, IDTipoActividad)
                VALUES (?, ?, CURRENT_TIMESTAMP, ?, ?)
            """;

        // Obtener la relación entre Clase, Instructor y Ficha
        Map<String, Object> claseInstructorFicha = obtenerClaseEInstructorFichaPorFichaClaseYInstructor(ficha, claseFormacion, instructorDocumento);
        if (claseInstructorFicha == null) {
            throw new IllegalArgumentException("No se encontró la asignación de clase, instructor y ficha proporcionados.");
        }

        int idClaseInstructorFicha = (int) claseInstructorFicha.get("IDClaseInstructorFicha");
        System.out.println("IDClaseInstructorFicha obtenido: " + idClaseInstructorFicha);


        Integer idAmbiente = ambientesDataService.obtenerIdAmbientePorNombre(ambiente);
        System.out.println("ID de Ambiente obtenido: " + idAmbiente);


        Integer idTipoActividad = actividadDataService.obtenerIdActividadPorNombre(tipoAsistencia);
        System.out.println("ID de TipoActividad obtenido: " + idTipoActividad);

        // Insertar en la tabla registroasistencias
        KeyHolder registroAsistenciaKeyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sqlInsertRegistroAsistencia, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, idClaseInstructorFicha);
            ps.setInt(2, idAmbiente);
            ps.setInt(3, idAsistencia);
            ps.setInt(4, idTipoActividad);
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
                INSERT INTO registroactividad (IDRegistroAsistencia, HorasInasistencia, IDPerfilUsuario, IDSoporte)
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


            int idAprendiz = aprendicesDataService.obtenerIdAprendizPorDocumento(documentoAprendiz);
            System.out.println("ID del aprendiz obtenido: " + idAprendiz);

            // Insertar cada registro en `registroactividad`
            jdbcTemplate.update(sqlInsertRegistroActividad, idRegistroAsistencia, horasInasistencia, idAprendiz);
        }
    }

    /**
     * Método para obtener todas las asistencias con joins correspondientes e incluir el archivo BLOB.
     *
     * @return Lista de asistencias.
     */
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
                    INNER JOIN claseformacion_instructor_ficha cif ON ra.IDClaseInstructorFicha = cif.ID
                    INNER JOIN claseformacion cf ON cif.IDClaseFormacion = cf.ID
                    INNER JOIN ambientes a ON ra.IDAmbiente = a.ID
                    INNER JOIN fichas f ON ra.IDFicha = f.ID
                    INNER JOIN perfilusuario pu ON cif.IDPerfilUsuario = pu.ID
                    INNER JOIN actividad ta ON ra.IDTipoActividad = ta.ID
                    INNER JOIN asistencia ass ON ra.IDArchivo = ass.ID
                """;

        try {
            List<Map<String, Object>> asistencias = jdbcTemplate.query(sql, (rs, rowNum) -> {
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

            return asistencias;
        } catch (Exception e) {
            System.out.println("Error al listar todas las asistencias: " + e.getMessage());
            throw new RuntimeException("Error al listar todas las asistencias.", e);
        }
    }

    /**
     * Método para listar las asistencias de un instructor específico por su documento.
     *
     * @param documentoInstructor Documento del instructor.
     * @return Lista de asistencias asociadas al instructor.
     * @throws SQLException Si ocurre un error en la consulta.
     */
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
                INNER JOIN claseformacion_instructor_ficha cif ON ra.IDClaseInstructorFicha = cif.ID
                INNER JOIN claseformacion cf ON cif.IDClaseFormacion = cf.ID
                INNER JOIN ambientes a ON ra.IDAmbiente = a.ID
                INNER JOIN fichas f ON cif.IDFicha = f.ID
                INNER JOIN perfilusuario pu ON cif.IDPerfilUsuario = pu.ID
                INNER JOIN actividad ta ON ra.IDTipoActividad = ta.ID
                INNER JOIN asistencia ass ON ra.IDArchivo = ass.ID
                WHERE pu.Documento = ?
            """;

        try {
            List<Map<String, Object>> asistencias = jdbcTemplate.query(sql, new Object[]{documentoInstructor}, (rs, rowNum) -> {
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

            // En vez de lanzar una excepción, devolvemos una lista vacía
            return asistencias.isEmpty() ? Collections.emptyList() : asistencias;

        } catch (EmptyResultDataAccessException e) {
            System.out.println("No se encontraron asistencias para el instructor con documento: " + documentoInstructor);
            return Collections.emptyList();
        } catch (Exception e) {
            System.out.println("Error al listar asistencias por instructor: " + e.getMessage());
            throw new RuntimeException("Error al listar asistencias por instructor.", e);
        }
    }


    /**
     * Método para filtrar las asistencias basándose en varios parámetros.
     *
     * @param documentoInstructor Documento del instructor.
     * @param ambiente            Ambiente de la asistencia (opcional).
     * @param programaFormacion   Programa de formación (opcional).
     * @param ficha               Número de ficha (opcional).
     * @return Lista de asistencias que cumplen con los criterios de filtrado.
     */
    public List<Map<String, Object>> FiltroListarAsistencias(String documentoInstructor, String ambiente, String programaFormacion, Integer ficha) {
        StringBuilder sql = new StringBuilder("""
        SELECT 
            DISTINCT CONCAT(pu.Nombres, ' ', pu.Apellidos) AS Instructor,
            cf.NombreClase AS ClaseFormacion,
            amb.Ambiente AS Ambiente,
            f.NumeroFicha AS Ficha,
            ra.Fecha AS FechaRegistro,
            ass.AsistenciaExcel AS AsistenciaExcel,
            ta.TipoActividad AS TipoAsistencia
        FROM registroasistencias ra
        INNER JOIN claseformacion_instructor_ficha cif ON ra.IDClaseInstructorFicha = cif.ID
        INNER JOIN claseformacion cf ON cif.IDClaseFormacion = cf.ID
        INNER JOIN ambientes amb ON ra.IDAmbiente = amb.ID
        INNER JOIN fichas f ON cif.IDFicha = f.ID
        INNER JOIN perfilusuario pu ON cif.IDPerfilUsuario = pu.ID
        INNER JOIN programaformacion pf ON f.IDProgramaFormacion = pf.ID
        INNER JOIN actividad ta ON ra.IDTipoActividad = ta.ID
        INNER JOIN asistencia ass ON ra.IDArchivo = ass.ID
        WHERE pu.Documento = ?
    """);

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
            sql.append(" AND f.NumeroFicha = ?");
            params.add(ficha);
        }

        try {
            return jdbcTemplate.query(sql.toString(), params.toArray(), (rs, rowNum) -> {
                Map<String, Object> asistencia = new HashMap<>();
                asistencia.put("Instructor", rs.getString("Instructor"));
                asistencia.put("ClaseFormacion", rs.getString("ClaseFormacion"));
                asistencia.put("Ambiente", rs.getString("Ambiente"));
                asistencia.put("Ficha", rs.getInt("Ficha"));
                asistencia.put("FechaRegistro", rs.getTimestamp("FechaRegistro"));
                asistencia.put("TipoAsistencia", rs.getString("TipoAsistencia"));
                asistencia.put("AsistenciaExcel", rs.getBytes("AsistenciaExcel"));
                return asistencia;
            });

        } catch (Exception e) {
            System.out.println("Error al filtrar asistencias: " + e.getMessage());
            throw new RuntimeException("Error al filtrar asistencias.", e);
        }
    }

    /**
     * Método para listar asistencias agrupadas por clase de formación para un aprendiz específico.
     * Incluye el instructor a cargo de cada clase.
     *
     * @param documento Documento del aprendiz como String.
     * @return Lista de asistencias agrupadas por clase.
     */
    public List<Map<String, Object>> listarAsistenciasAgrupadasPorClase(String documento) {
        String sql = """
                        SELECT
                            cf.NombreClase AS ClaseFormacion,
                            CONCAT(pu_instructor.Nombres, ' ', pu_instructor.Apellidos) AS Instructor,
                            COUNT(DISTINCT ra.ID) AS TotalAsistencias,
                            COALESCE(SUM(raa.HorasInasistencia), 0) AS TotalHorasInasistencia,
                            SUM(CASE WHEN raa.HorasInasistencia > 0 THEN 1 ELSE 0 END) AS AsistenciasConInasistencias
                        FROM registroasistencias ra
                                 INNER JOIN claseformacion_instructor_ficha cif ON ra.IDClaseInstructorFicha = cif.ID
                                 INNER JOIN claseformacion cf ON cif.IDClaseFormacion = cf.ID
                                 INNER JOIN perfilusuario pu_instructor ON cif.IDPerfilUsuario = pu_instructor.ID
                                 INNER JOIN registroactividad raa ON ra.ID = raa.IDRegistroAsistencia
                                 INNER JOIN perfilusuario pu_aprendiz ON raa.IDPerfilUsuario = pu_aprendiz.ID
                        WHERE pu_aprendiz.Documento = ?
                        GROUP BY cf.NombreClase, pu_instructor.Nombres, pu_instructor.Apellidos
                        ORDER BY TotalHorasInasistencia DESC""";

        System.out.println("Ejecutando consulta SQL para documento: " + documento);

        try {
            List<Map<String, Object>> resultados = jdbcTemplate.query(sql, new Object[]{documento}, (rs, rowNum) -> {
                Map<String, Object> asistenciaAgrupada = new HashMap<>();
                asistenciaAgrupada.put("ClaseFormacion", rs.getString("ClaseFormacion"));
                asistenciaAgrupada.put("Instructor", rs.getString("Instructor"));
                asistenciaAgrupada.put("TotalAsistencias", rs.getInt("TotalAsistencias"));
                asistenciaAgrupada.put("TotalHorasInasistencia", rs.getInt("TotalHorasInasistencia"));
                asistenciaAgrupada.put("AsistenciasConInasistencias", rs.getInt("AsistenciasConInasistencias"));
                return asistenciaAgrupada;
            });

            if (resultados.isEmpty()) {
                throw new IllegalArgumentException("No se encontraron asistencias agrupadas por clase para el documento proporcionado.");
            }

            System.out.println("Número de registros obtenidos: " + resultados.size());
            return resultados;
        } catch (EmptyResultDataAccessException e) {
            System.out.println("No se encontraron asistencias agrupadas por clase para el documento: " + documento);
            throw new IllegalArgumentException("No se encontraron asistencias agrupadas por clase para el documento proporcionado.");
        } catch (Exception e) {
            System.out.println("Error al listar asistencias agrupadas por clase: " + e.getMessage());
            throw new RuntimeException("Error al listar asistencias agrupadas por clase.", e);
        }
    }


    /**
     * Método para listar todas las asistencias de un aprendiz específico dado su documento.
     *
     * @param documento Documento del aprendiz como String.
     * @return Lista de asistencias con detalles y archivo Excel en Base64.
     */
    public List<Map<String, Object>> listarAsistenciasPorAprendiz(String documento) {
        String sql = """
                SELECT DISTINCT
                    ra.Fecha AS FechaRegistro,
                    cf.NombreClase AS ClaseFormacion,
                    amb.Ambiente AS Ambiente,
                    f.NumeroFicha AS Ficha,
                    CONCAT(pu_instructor.Nombres, ' ', pu_instructor.Apellidos) AS Instructor,
                    ta.TipoActividad AS TipoAsistencia,
                    COALESCE(raa.HorasInasistencia, 0) AS HorasInasistencia,
                    ass.AsistenciaExcel AS ArchivoExcel
                FROM registroasistencias ra
                         INNER JOIN claseformacion_instructor_ficha cif ON ra.IDClaseInstructorFicha = cif.ID
                         INNER JOIN claseformacion cf ON cif.IDClaseFormacion = cf.ID
                         INNER JOIN perfilusuario pu_instructor ON cif.IDPerfilUsuario = pu_instructor.ID
                         INNER JOIN fichas f ON cif.IDFicha = f.ID
                         INNER JOIN ambientes amb ON ra.IDAmbiente = amb.ID
                         INNER JOIN actividad ta ON ra.IDTipoActividad = ta.ID
                         INNER JOIN registroactividad raa ON ra.ID = raa.IDRegistroAsistencia
                         INNER JOIN perfilusuario pu_aprendiz ON raa.IDPerfilUsuario = pu_aprendiz.ID
                         INNER JOIN asistencia ass ON ra.IDArchivo = ass.ID
                WHERE pu_aprendiz.Documento = ?
                ORDER BY ra.Fecha DESC""";

        System.out.println("Ejecutando consulta SQL para listar asistencias por aprendiz: " + documento);

        try {
            List<Map<String, Object>> resultados = jdbcTemplate.query(sql, new Object[]{documento}, (rs, rowNum) -> {
                Map<String, Object> asistencia = new HashMap<>();
                asistencia.put("FechaRegistro", rs.getTimestamp("FechaRegistro"));
                asistencia.put("ClaseFormacion", rs.getString("ClaseFormacion"));
                asistencia.put("Ambiente", rs.getString("Ambiente"));
                asistencia.put("Ficha", rs.getInt("Ficha"));
                asistencia.put("Instructor", rs.getString("Instructor"));
                asistencia.put("TipoAsistencia", rs.getString("TipoAsistencia"));
                asistencia.put("HorasInasistencia", rs.getInt("HorasInasistencia"));
                asistencia.put("ArchivoExcel", rs.getBytes("ArchivoExcel"));  // Retorna el archivo en formato BLOB o null
                return asistencia;
            });

            if (resultados.isEmpty()) {
                throw new IllegalArgumentException("No se encontraron asistencias para el aprendiz con documento: " + documento);
            }

            System.out.println("Número de registros obtenidos: " + resultados.size());
            return resultados;
        } catch (EmptyResultDataAccessException e) {
            System.out.println("No se encontraron asistencias para el aprendiz con documento: " + documento);
            throw new IllegalArgumentException("No se encontraron asistencias para el aprendiz con documento: " + documento);
        } catch (Exception e) {
            System.out.println("Error al listar asistencias por aprendiz: " + e.getMessage());
            throw new RuntimeException("Error al listar asistencias por aprendiz.", e);
        }
    }



    /**
     * Método para listar detalles de inasistencias por clase de formación para un aprendiz específico.
     * Incluye el soporte en formato Base64 si existe.
     *
     * @param documento Documento del aprendiz como String.
     * @return Lista de detalles de inasistencias por clase de formación.
     */
    public List<Map<String, Object>> listarDetallesInasistenciasPorClase(String documento) {
        String sql = """
                SELECT
                    ra.ID,
                    ra.Fecha AS FechaRegistro,
                    cf.NombreClase AS ClaseFormacion,
                    CONCAT(pu_instructor.Nombres, ' ', pu_instructor.Apellidos) AS Instructor,
                    ta.TipoActividad AS TipoAsistencia,
                    SUM(raa.HorasInasistencia) AS TotalHorasInasistencia,
                    COUNT(raa.ID) AS NumeroDeInasistencias,
                    s.SoportePDF AS Soporte
                FROM registroasistencias ra
                         INNER JOIN claseformacion_instructor_ficha cif ON ra.IDClaseInstructorFicha = cif.ID
                         INNER JOIN claseformacion cf ON cif.IDClaseFormacion = cf.ID
                         INNER JOIN perfilusuario pu_instructor ON cif.IDPerfilUsuario = pu_instructor.ID
                         INNER JOIN actividad ta ON ra.IDTipoActividad = ta.ID
                         INNER JOIN registroactividad raa ON ra.ID = raa.IDRegistroAsistencia
                         LEFT JOIN soporte s ON raa.IDSoporte = s.ID
                         INNER JOIN perfilusuario pu_aprendiz ON raa.IDPerfilUsuario = pu_aprendiz.ID
                WHERE pu_aprendiz.Documento = ?
                GROUP BY ra.ID, ra.Fecha, cf.NombreClase, pu_instructor.Nombres, pu_instructor.Apellidos, ta.TipoActividad, s.SoportePDF
                ORDER BY ra.Fecha DESC""";

        System.out.println("Ejecutando consulta SQL para listar detalles de inasistencias por clase: " + documento);

        try {
            List<Map<String, Object>> resultados = jdbcTemplate.query(sql, new Object[]{documento}, (rs, rowNum) -> {
                Map<String, Object> detalleInasistencia = new HashMap<>();
                detalleInasistencia.put("ID", rs.getInt("ID"));
                detalleInasistencia.put("FechaRegistro", rs.getTimestamp("FechaRegistro"));
                detalleInasistencia.put("ClaseFormacion", rs.getString("ClaseFormacion"));
                detalleInasistencia.put("Instructor", rs.getString("Instructor"));
                detalleInasistencia.put("TipoAsistencia", rs.getString("TipoAsistencia"));
                detalleInasistencia.put("TotalHorasInasistencia", rs.getInt("TotalHorasInasistencia"));
                detalleInasistencia.put("NumeroDeInasistencias", rs.getInt("NumeroDeInasistencias"));
                detalleInasistencia.put("Soporte", rs.getBytes("Soporte"));  // Retorna el soporte en formato BLOB o null
                return detalleInasistencia;
            });

            if (resultados.isEmpty()) {
                throw new IllegalArgumentException("No se encontraron detalles de inasistencias para el aprendiz con documento: " + documento);
            }

            System.out.println("Número de registros obtenidos: " + resultados.size());
            return resultados;
        } catch (EmptyResultDataAccessException e) {
            System.out.println("No se encontraron detalles de inasistencias para el aprendiz con documento: " + documento);
            throw new IllegalArgumentException("No se encontraron detalles de inasistencias para el aprendiz con documento: " + documento);
        } catch (Exception e) {
            System.out.println("Error al listar detalles de inasistencias por clase: " + e.getMessage());
            throw new RuntimeException("Error al listar detalles de inasistencias por clase.", e);
        }
    }


    /**
     * Método para obtener las inasistencias de un aprendiz en una semana (7 días) a partir de una fecha de inicio.
     *
     * @param documento   Documento del aprendiz.
     * @param fechaInicio Fecha de inicio de la semana, en formato 'YYYY-MM-DD'.
     * @return Lista de inasistencias en la semana especificada.
     */
    public List<Map<String, Object>> obtenerInasistenciasPorSemana(String documento, String fechaInicio) {
        // Definir la consulta principal, calculando la fecha de fin directamente
        String sql = """
                SELECT
                    ra.Fecha AS Fecha,
                    cf.NombreClase AS NombreClase,
                    CONCAT(pu_instructor.Nombres, ' ', pu_instructor.Apellidos) AS Instructor,
                    CONCAT(pu_aprendiz.Nombres, ' ', pu_aprendiz.Apellidos) AS Aprendiz,
                    raa.HorasInasistencia AS HorasInasistencia
                FROM registroactividad raa
                INNER JOIN registroasistencias ra ON raa.IDRegistroAsistencia = ra.ID
                INNER JOIN claseformacion_instructor_ficha cif ON ra.IDClaseInstructorFicha = cif.ID
                INNER JOIN claseformacion cf ON cif.IDClaseFormacion = cf.ID
                INNER JOIN perfilusuario pu_instructor ON cif.IDPerfilUsuario = pu_instructor.ID
                INNER JOIN perfilusuario pu_aprendiz ON raa.IDPerfilUsuario = pu_aprendiz.ID
                WHERE pu_aprendiz.Documento = ?
                AND DATE(ra.Fecha) BETWEEN ? AND DATE_ADD(?, INTERVAL 6 DAY)
            """;

        try {
            // Ejecutar la consulta y mapear los resultados
            List<Map<String, Object>> inasistencias = jdbcTemplate.query(sql, new Object[]{documento, fechaInicio, fechaInicio}, (rs, rowNum) -> {
                Map<String, Object> inasistencia = new HashMap<>();
                inasistencia.put("Fecha", rs.getTimestamp("Fecha"));
                inasistencia.put("NombreClase", rs.getString("NombreClase"));
                inasistencia.put("Instructor", rs.getString("Instructor"));
                inasistencia.put("Aprendiz", rs.getString("Aprendiz"));
                inasistencia.put("HorasInasistencia", rs.getInt("HorasInasistencia"));
                return inasistencia;
            });

            // Retornar lista vacía si no hay resultados
            if (inasistencias.isEmpty()) {
                System.out.println("No se encontraron inasistencias para el documento y la semana proporcionados.");
                return Collections.emptyList();
            }

            System.out.println("Número de registros obtenidos: " + inasistencias.size());
            return inasistencias;
        } catch (Exception e) {
            System.out.println("Error al obtener inasistencias por semana: " + e.getMessage());
            throw new RuntimeException("Error al obtener inasistencias por semana.", e);
        }
    }



    /**
     * Método para guardar el soporte PDF y asociarlo a un registro de actividad.
     *
     * @param idRegistroActividad ID del registro de actividad.
     * @param file                Archivo PDF a guardar.
     * @throws IOException Si ocurre un error al leer el archivo.
     */
    public void guardarSoportePDF(int idRegistroActividad, MultipartFile file) throws IOException {
        // Convertir el archivo a bytes
        byte[] pdfBytes = file.getBytes();

        // Insertar el PDF en la tabla 'soporte' y obtener el ID generado
        String sqlInsertSoporte = "INSERT INTO soporte (SoportePDF) VALUES (?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sqlInsertSoporte, Statement.RETURN_GENERATED_KEYS);
            ps.setBytes(1, pdfBytes);
            return ps;
        }, keyHolder);

        int idSoporte = keyHolder.getKey().intValue();

        System.out.println("Se ha insertado el soporte PDF en la tabla soporte con ID: " + idSoporte);

        // Actualizar el campo 'IDSoporte' en 'registroactividad' para el registro específico
        String sqlUpdateRegistroActividad = """
                    UPDATE registroactividad
                    SET IDSoporte = ?
                    WHERE ID = ?
                """;

        int filasActualizadas = jdbcTemplate.update(sqlUpdateRegistroActividad, idSoporte, idRegistroActividad);

        if (filasActualizadas == 0) {
            throw new IllegalArgumentException("No se encontró el registro de actividad con ID: " + idRegistroActividad);
        }

        System.out.println("Se ha actualizado el registroactividad con IDSoporte: " + idSoporte);
    }
}




