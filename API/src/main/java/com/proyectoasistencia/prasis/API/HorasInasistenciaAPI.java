package com.proyectoasistencia.prasis.API;



import com.proyectoasistencia.prasis.controller.PerfilUsuarioController;
import com.proyectoasistencia.prasis.models.PerfilUsuarioModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/Horas")
public class HorasInasistenciaAPI {

    private final JdbcTemplate jdbcTemplate;
    private final PerfilUsuarioController perfilUsuarioController;

    @Autowired
    public HorasInasistenciaAPI(JdbcTemplate jdbcTemplate, PerfilUsuarioController perfilUsuarioController) {
        this.jdbcTemplate = jdbcTemplate;
        this.perfilUsuarioController = perfilUsuarioController;
    }


    @RequestMapping("/ActualizarHoras")
    public ResponseEntity<String> actualizarHoras(
            @RequestBody Map<String, List<Map<String, Object>>> body) {

        List<Map<String, Object>> listaAprendices = body.get("aprendices");
        ConversionSubTablasAPI conversion = new ConversionSubTablasAPI(jdbcTemplate);

        for (Map<String, Object> aprendiz : listaAprendices) {
            // Obtener el usuario basado en el documento
            PerfilUsuarioModel usuario = perfilUsuarioController.getUsuario(aprendiz.get("Documento").toString());
            if (usuario == null) {
                return ResponseEntity.badRequest().body("Usuario con documento " + aprendiz.get("Documento") + " no encontrado.");
            }

            // Obtener el ID del instructor desde la solicitud o sesión
            Integer idInstructor;
            try {
                idInstructor = Integer.parseInt(aprendiz.get("IDInstructor").toString());
            } catch (Exception e) {
                return ResponseEntity.badRequest().body("ID de Instructor no proporcionado o en formato incorrecto.");
            }

            // Obtener el ID de la clase basado en el nombre de la clase y el IDInstructor
            String nombreClase = aprendiz.get("NombreClase").toString();
            ResponseEntity<Integer> responseIdClaseFormacion = conversion.ClaseToID(nombreClase, idInstructor);
            if (!responseIdClaseFormacion.getStatusCode().is2xxSuccessful() || responseIdClaseFormacion.getBody() == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error en la conversión de Clase Formación para " + nombreClase + " y IDInstructor: " + idInstructor);
            }
            Integer idClaseFormacion = responseIdClaseFormacion.getBody();

            Integer idUsuario = usuario.getID();
            if (idUsuario == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al obtener ID de Usuario.");
            }

            // Validar las horas de inasistencia
            Integer horasInasistencia;
            try {
                horasInasistencia = Integer.parseInt(aprendiz.get("HorasInasistencia").toString());
            } catch (NumberFormatException e) {
                return ResponseEntity.badRequest().body("Formato incorrecto para Horas Inasistencia.");
            }

            // Consultar si ya existe un registro para el usuario y el ID de clase
            try {
                String consultaExistencia = "SELECT COUNT(*) FROM horasinasistencia WHERE IDPerfilUsuario = ? AND IDClaseFormacion = ?";
                Integer count = jdbcTemplate.queryForObject(consultaExistencia, Integer.class, idUsuario, idClaseFormacion);

                if (count != null && count > 0) {
                    // Si existe, actualizar el registro sumando las horas de inasistencia
                    String consultaUpdate = "UPDATE horasinasistencia SET HorasInasistencia = HorasInasistencia + ? WHERE IDPerfilUsuario = ? AND IDClaseFormacion = ?";
                    jdbcTemplate.update(consultaUpdate, horasInasistencia, idUsuario, idClaseFormacion);
                } else {
                    // Si no existe, insertar un nuevo registro
                    String consultaInsert = "INSERT INTO horasinasistencia (IDPerfilUsuario, IDClaseFormacion, HorasInasistencia) VALUES (?, ?, ?)";
                    jdbcTemplate.update(consultaInsert, idUsuario, idClaseFormacion, horasInasistencia);
                }
            } catch (Exception e) {
                System.out.println("Error al actualizar la base de datos: " + e.getMessage());
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al actualizar la base de datos.");
            }
        }

        return ResponseEntity.ok("Horas actualizadas con éxito");
    }


    @RequestMapping("/ObtenerUsuarioHorasInasistencia")
    public ResponseEntity<List<Map<String, Object>>> ObtenerUsuarioHorasInasistencia(@RequestParam("documento") Integer documento) {
        String consulta = """
            SELECT 
                horasinasistencia.HorasInasistencia,
                c.NombreClase,
                f.NumeroFicha,
                pf.ProgramaFormacion,
                CONCAT(pu_aprendiz.Nombres, ' ', pu_aprendiz.Apellidos) AS NombreAprendiz,
                CONCAT(pu_instructor.Nombres, ' ', pu_instructor.Apellidos) AS NombreInstructor
            FROM
                horasinasistencia
                INNER JOIN perfilusuario pu_aprendiz ON horasinasistencia.IDPerfilUsuario = pu_aprendiz.ID
                INNER JOIN claseformacion c ON horasinasistencia.IDClaseFormacion = c.ID
                INNER JOIN fichas f ON c.IDFicha = f.ID
                INNER JOIN programaformacion pf ON f.IDProgramaFormacion = pf.ID
                INNER JOIN perfilusuario pu_instructor ON c.IDInstructor = pu_instructor.ID
            WHERE
                pu_aprendiz.Documento = ?
        """;

        try {
            List<Map<String, Object>> resultados = jdbcTemplate.query(consulta, new Object[]{documento}, new RowMapper<Map<String, Object>>() {
                @Override
                public Map<String, Object> mapRow(ResultSet rs, int rowNum) throws SQLException {
                    Map<String, Object> row = new HashMap<>();
                    row.put("HorasInasistencia", rs.getInt("HorasInasistencia"));
                    row.put("NombreClase", rs.getString("NombreClase"));
                    row.put("NumeroFicha", rs.getString("NumeroFicha"));
                    row.put("ProgramaFormacion", rs.getString("ProgramaFormacion"));
                    row.put("NombreAprendiz", rs.getString("NombreAprendiz"));  // Ajusta para usar el alias correcto
                    row.put("NombreInstructor", rs.getString("NombreInstructor"));  // Ajusta para usar el alias correcto
                    return row;
                }
            });

            if (resultados.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }

            return ResponseEntity.ok(resultados);

        } catch (Exception e) {
            e.printStackTrace(); // Para depuración, imprime la traza de la excepción
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @RequestMapping("/ObtenerUsuarioHorasInasistenciaPorFecha")
    public ResponseEntity<List<Map<String, Object>>> ObtenerUsuarioHorasInasistenciaPorFecha(
            @RequestParam("documento") Integer documento, @RequestParam("fecha") String fecha) {
        String consulta = """
                    SELECT
                        ra.Fecha,
                        cf.NombreClase,
                        CONCAT(pf.Nombres, ' ', pf.Apellidos) AS Instructor,
                        CONCAT(pa.Nombres, ' ', pa.Apellidos) AS Aprendiz,
                        r.HorasInasistencia
                    FROM
                        RegistroActividad r
                            INNER JOIN horasinasistencia h ON r.IDHorasInasistencia = h.ID
                            INNER JOIN registroasistencias ra ON r.IDRegistroAsistencia = ra.ID
                            INNER JOIN perfilusuario pa ON h.IDPerfilUsuario = pa.ID
                            INNER JOIN claseformacion cf ON h.IDClaseFormacion = cf.ID
                            INNER JOIN perfilusuario pf ON cf.IDInstructor = pf.ID
                    WHERE
                        DATE(ra.Fecha) = ? AND pa.Documento = ?""";

        try {
            List<Map<String, Object>> resultados = jdbcTemplate.query(consulta, new Object[]{fecha, documento}, new RowMapper<Map<String, Object>>() {
                @Override
                public Map<String, Object> mapRow(ResultSet rs, int rowNum) throws SQLException {
                    Map<String, Object> row = new HashMap<>();
                    row.put("Fecha", rs.getString("Fecha"));
                    row.put("NombreClase", rs.getString("NombreClase")); // Cambiado a getString
                    row.put("Instructor", rs.getString("Instructor"));   // Cambiado a getString
                    row.put("Aprendiz", rs.getString("Aprendiz"));
                    row.put("HorasInasistencia", rs.getInt("HorasInasistencia")); // Cambiado a getInt
                    return row;
                }
            });

            if (resultados.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }

            return ResponseEntity.ok(resultados);

        } catch (Exception e) {
            e.printStackTrace(); // Para depuración, imprime la traza de la excepción
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @RequestMapping("/ObtenerUsuarioHorasInasistenciaPorRangoFechas")
    public ResponseEntity<List<Map<String, Object>>> ObtenerUsuarioHorasInasistenciaPorRangoFechas(
            @RequestParam("documento") Integer documento,
            @RequestParam("fechaInicio") String fechaInicio) {

        String consulta = """
            SELECT
                ra.Fecha,
                cf.NombreClase,
                CONCAT(pf.Nombres, ' ', pf.Apellidos) AS Instructor,
                CONCAT(pa.Nombres, ' ', pa.Apellidos) AS Aprendiz,
                r.HorasInasistencia
            FROM
                RegistroActividad r
                    INNER JOIN horasinasistencia h ON r.IDHorasInasistencia = h.ID
                    INNER JOIN registroasistencias ra ON r.IDRegistroAsistencia = ra.ID
                    INNER JOIN perfilusuario pa ON h.IDPerfilUsuario = pa.ID
                    INNER JOIN claseformacion cf ON h.IDClaseFormacion = cf.ID
                    INNER JOIN perfilusuario pf ON cf.IDInstructor = pf.ID
            WHERE
                ra.Fecha BETWEEN ? AND DATE_ADD(?, INTERVAL 7 DAY) 
                AND pa.Documento = ?""";

        try {
            List<Map<String, Object>> resultados = jdbcTemplate.query(
                    consulta,
                    new Object[]{fechaInicio, fechaInicio, documento},
                    new RowMapper<Map<String, Object>>() {
                        @Override
                        public Map<String, Object> mapRow(ResultSet rs, int rowNum) throws SQLException {
                            Map<String, Object> row = new HashMap<>();
                            row.put("Fecha", rs.getString("Fecha"));
                            row.put("NombreClase", rs.getString("NombreClase"));
                            row.put("Instructor", rs.getString("Instructor"));
                            row.put("Aprendiz", rs.getString("Aprendiz"));
                            row.put("HorasInasistencia", rs.getInt("HorasInasistencia"));
                            return row;
                        }
                    });

            if (resultados.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }

            return ResponseEntity.ok(resultados);

        } catch (Exception e) {
            e.printStackTrace(); // Para depuración, imprime la traza de la excepción
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/ObtenerHistoricoInAsistencias")
    public ResponseEntity<List<Map<String, Object>>> obtenerHistoricoInAsistencias(@RequestParam("documento") Integer documento) {
        String consulta = """
                SELECT
                    ra.Fecha,
                    cf.NombreClase,
                    CONCAT(pf.Nombres, ' ', pf.Apellidos) AS Instructor,
                    CONCAT(pa.Nombres, ' ', pa.Apellidos) AS Aprendiz,
                    r.HorasInasistencia
                FROM
                    RegistroActividad r
                        INNER JOIN horasinasistencia h ON r.IDHorasInasistencia = h.ID
                        INNER JOIN registroasistencias ra ON r.IDRegistroAsistencia = ra.ID
                        INNER JOIN perfilusuario pa ON h.IDPerfilUsuario = pa.ID
                        INNER JOIN claseformacion cf ON h.IDClaseFormacion = cf.ID
                        INNER JOIN perfilusuario pf ON cf.IDInstructor = pf.ID
                WHERE
                    pa.Documento = ?""";

        try {
            List<Map<String, Object>> resultados = jdbcTemplate.query(consulta, new Object[]{documento}, new RowMapper<Map<String, Object>>() {
                @Override
                public Map<String, Object> mapRow(ResultSet rs, int rowNum) throws SQLException {
                    Map<String, Object> row = new HashMap<>();
                    row.put("Fecha", rs.getString("Fecha"));
                    row.put("NombreClase", rs.getString("NombreClase"));
                    row.put("Instructor", rs.getString("Instructor"));
                    row.put("Aprendiz", rs.getString("Aprendiz"));
                    row.put("HorasInasistencia", rs.getInt("HorasInasistencia"));

                    return row;
                }
            });

            if (resultados.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }

            return ResponseEntity.ok(resultados);

        } catch (Exception e) {
            e.printStackTrace(); // Para depuración, imprime la traza de la excepción
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping("/AgregarSoporte")
    public ResponseEntity<String> agregarSoporte(@RequestParam("fecha") String fecha,
                                                 @RequestParam("file") MultipartFile file) {
        // Validar que el archivo sea un PDF
        if (file == null || !file.getContentType().equals("application/pdf")) {
            return ResponseEntity.badRequest().body("Debe subir un archivo PDF.");
        }

        try {
            // Buscar los IDs de registro en la tabla registroasistencias basándose en la fecha proporcionada
            String consultaIdRegistro = "SELECT ID FROM registroasistencias WHERE DATE(Fecha) = ?";
            List<Integer> idsRegistroAsistencia = jdbcTemplate.query(
                    consultaIdRegistro,
                    new Object[]{fecha},
                    (rs, rowNum) -> rs.getInt("ID"));

            // Validar si se encontraron registros
            if (idsRegistroAsistencia.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontraron registros para la fecha proporcionada.");
            }

            // Iterar sobre los IDs obtenidos y actualizar el soporte en la tabla registroactividad
            for (Integer idRegistroAsistencia : idsRegistroAsistencia) {
                String consultaUpdateSoporte = "UPDATE registroactividad SET Soporte = ? WHERE IDRegistroAsistencia = ?";
                jdbcTemplate.update(consultaUpdateSoporte, file.getBytes(), idRegistroAsistencia);
            }

            return ResponseEntity.ok("Soporte PDF agregado exitosamente.");

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al agregar el soporte PDF.");
        }
    }

    @RequestMapping("/ObtenerHorasInasistenciaPorFicha")
    public ResponseEntity<List<Map<String, Object>>> obtenerHorasInasistenciaPorFicha(
            @RequestParam("numeroFicha") Integer numeroFicha,
            @RequestParam("fecha") String fecha) {

        // Consulta para obtener los aprendices de la ficha
        String consultaAprendices = """
        SELECT
            CONCAT(pu.Nombres, ' ', pu.Apellidos) AS Nombres,
            CONCAT(
                    UPPER(
                            CONCAT(
                                    LEFT(td.TipoDocumento, 1),
                                    IF(LOCATE(' ', td.TipoDocumento) > 0,
                                       CONCAT('.',
                                              LEFT(SUBSTRING_INDEX(td.TipoDocumento, ' ', -1), 1)
                                       ),
                                       ''
                                    )
                            )
                    ),
                    ' ', pu.Documento
            ) AS Documento,
            CONCAT(pf.ProgramaFormacion, ' ', f.NumeroFicha) AS Ficha,
            pu.ID AS IDUsuario
        FROM
            perfilusuario AS pu
                INNER JOIN fichas AS f ON pu.IDFicha = f.ID
                INNER JOIN tipodocumento AS td ON pu.IDTipoDocumento = td.ID
                INNER JOIN programaformacion AS pf ON f.IDProgramaFormacion = pf.ID
        WHERE
            f.NumeroFicha = ?
    """;

        // Consulta para obtener las horas de inasistencia y el soporte
        String consultaHorasInasistencia = """
        SELECT ra.HorasInasistencia, ra.Soporte 
        FROM registroactividad AS ra
            INNER JOIN registroasistencias AS ras ON ra.IDRegistroAsistencia = ras.ID
            INNER JOIN horasinasistencia AS ha ON ra.IDHorasInasistencia = ha.ID
        WHERE DATE(ras.Fecha) = ? AND ha.IDPerfilUsuario = ?
    """;

        try {
            // Ejecutar la consulta para obtener los aprendices de la ficha
            List<Map<String, Object>> aprendices = jdbcTemplate.query(
                    consultaAprendices,
                    new Object[]{numeroFicha},
                    (rs, rowNum) -> {
                        Map<String, Object> row = new HashMap<>();
                        row.put("Nombres", rs.getString("Nombres"));
                        row.put("Documento", rs.getString("Documento"));
                        row.put("Ficha", rs.getString("Ficha"));
                        row.put("IDUsuario", rs.getInt("IDUsuario"));
                        row.put("HorasInasistencia", 0);  // Valor predeterminado
                        row.put("Soporte", "No Aplica");  // Valor predeterminado
                        return row;
                    });

            // Iterar sobre los aprendices y actualizar con las horas y soportes si existen
            for (Map<String, Object> aprendiz : aprendices) {
                Integer idUsuario = (Integer) aprendiz.get("IDUsuario");

                // Ejecutar la consulta para obtener las horas de inasistencia y soporte del aprendiz
                List<Map<String, Object>> horasInasistencia = jdbcTemplate.query(
                        consultaHorasInasistencia,
                        new Object[]{fecha, idUsuario},
                        (rs, rowNum) -> {
                            Map<String, Object> row = new HashMap<>();
                            row.put("HorasInasistencia", rs.getInt("HorasInasistencia"));
                            byte[] soporte = rs.getBytes("Soporte");
                            row.put("Soporte", soporte != null && soporte.length > 0 ? soporte : "Soporte no Adjunto");
                            return row;
                        });

                // Si hay resultados, actualizar el aprendiz con las horas y soporte
                if (!horasInasistencia.isEmpty()) {
                    Map<String, Object> registro = horasInasistencia.get(0); // Solo debe haber un registro para la fecha y usuario
                    aprendiz.put("HorasInasistencia", registro.get("HorasInasistencia"));
                    aprendiz.put("Soporte", registro.get("Soporte"));
                }
                aprendiz.remove("IDUsuario");
            }

            return ResponseEntity.ok(aprendices);

        } catch (Exception e) {
            e.printStackTrace(); // Para depuración
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}


