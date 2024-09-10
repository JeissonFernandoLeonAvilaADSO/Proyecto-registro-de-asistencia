package com.proyectoasistencia.prasis.API;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/Archives")
public class ExcelManagerAPI {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private DataSource dataSource;

    @Autowired
    private DataSourceTransactionManager transactionManager;

    @Autowired
    private ConversionSubTablasAPI conversionSubTablasAPI;

    @PostMapping("/ExcelUp")
    public ResponseEntity<String> agregarAsistencia(
            @RequestParam("archivo") MultipartFile archivo,
            @RequestParam Map<String, String> params) {

        if (archivo == null || archivo.isEmpty()) {
            return new ResponseEntity<>("El archivo está nulo o vacío", HttpStatus.BAD_REQUEST);
        }

        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        TransactionStatus status = transactionManager.getTransaction(def);

        try (Connection conexion = dataSource.getConnection()) {
            // Subir el archivo a la base de datos y obtener su ID
            int archivoId = subirArchivo(conexion, archivo);

            // Insertar los datos de asistencia junto con el ID del archivo
            insertarAsistencia(conexion, params, archivoId);

            transactionManager.commit(status);
            return new ResponseEntity<>("Asistencia agregada exitosamente", HttpStatus.OK);
        } catch (SQLException | IOException e) {
            transactionManager.rollback(status);
            e.printStackTrace();
            return new ResponseEntity<>("Error al agregar la asistencia: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private int subirArchivo(Connection conexion, MultipartFile archivo) throws SQLException, IOException {
        String sqlArchivo = "INSERT INTO asistencia (archivo_excel) VALUES (?)";
        try (PreparedStatement psArchivo = conexion.prepareStatement(sqlArchivo, Statement.RETURN_GENERATED_KEYS)) {
            psArchivo.setBytes(1, archivo.getBytes());
            psArchivo.executeUpdate();

            try (ResultSet rs = psArchivo.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                } else {
                    throw new SQLException("No se pudo obtener el ID del archivo subido");
                }
            }
        }
    }

    private void insertarAsistencia(Connection conexion, Map<String, String> params, int archivoId) throws SQLException {
        // Validar que todos los campos obligatorios estén presentes
        System.out.println(params);
        System.out.println(archivoId);
        if (!params.containsKey("DocumentoInstructor") ||
                !params.containsKey("Ambiente") ||
                !params.containsKey("Ficha") ||
                !params.containsKey("ProgramaFormacion") ||
                !params.containsKey("Clase")) {
            throw new SQLException("Todos los campos son obligatorios.");
        }

        // Obtener IDs necesarios usando ConversionSubTablasAPI
        Integer idInstructor = conversionSubTablasAPI.obtenerIDInstructorPorDocumento(params.get("DocumentoInstructor")).getBody();
        Integer idAmbiente = conversionSubTablasAPI.Ambiente(params.get("Ambiente")).getBody();
        Integer idFicha = conversionSubTablasAPI.obtenerIDPorFicha(Integer.parseInt(params.get("Ficha"))).getBody();
        Integer idProgramaFormacion = conversionSubTablasAPI.ProgramaFormacion(params.get("ProgramaFormacion")).getBody();
        Integer idClaseFormacion = conversionSubTablasAPI.ClaseToID(params.get("Clase"), idInstructor).getBody();

        // Validar que todos los IDs se obtuvieron correctamente
        if (idInstructor == null || idAmbiente == null || idFicha == null || idProgramaFormacion == null || idClaseFormacion == null) {
            throw new SQLException("Error al obtener uno de los IDs necesarios.");
        }

        // Inserción en la base de datos
        String sqlAsistencia = """
        INSERT INTO registroasistencias (IDInstructor, 
                                 IDClaseFormacion, 
                                 IDAmbiente, 
                                 IDFicha,
                                 IDArchivo) VALUES (?, ?, ?, ?, ?)""";
        try (PreparedStatement psAsistencia = conexion.prepareStatement(sqlAsistencia)) {
            psAsistencia.setInt(1, idInstructor);
            psAsistencia.setInt(2, idClaseFormacion);
            psAsistencia.setInt(3, idAmbiente);
            psAsistencia.setInt(4, idFicha);
            psAsistencia.setInt(5, archivoId);
            psAsistencia.executeUpdate();
        }
    }

    @GetMapping("/ListarAsistencias")
    public ResponseEntity<List<Map<String, Object>>> listarAsistencias(
            @RequestParam(value = "IDInstructor") Integer IDinstructor,
            @RequestParam(required = false, value = "ambiente") String ambiente,
            @RequestParam(required = false, value = "ProgramaFormacion") String ProgramaFormacion,
            @RequestParam(required = false, value = "ficha") Integer ficha
    ) {
        try {
            System.out.println(ambiente);
            System.out.println(ProgramaFormacion);
            System.out.println(ficha);
            StringBuilder sql = new StringBuilder("""
                                    SELECT CONCAT(pu.Nombres, ' ', pu.Apellidos) AS Instructor,
                                        cf.NombreClase,
                                        amb.Ambiente,
                                        fc.NumeroFicha,
                                        ra.Fecha,
                                        ra.IDArchivo,
                                        pf.ProgramaFormacion
                                    FROM registroasistencias ra
                                        INNER JOIN asistencia ac ON ra.IDArchivo = ac.ID
                                        INNER JOIN perfilusuario pu ON ra.IDInstructor = pu.ID
                                        INNER JOIN claseformacion cf ON ra.IDClaseFormacion = cf.ID
                                        INNER JOIN ambientes amb ON ra.IDAmbiente = amb.ID
                                        INNER JOIN fichas fc ON ra.IDFicha = fc.ID
                                        INNER JOIN programaformacion pf ON fc.IDProgramaFormacion = pf.ID
                                    WHERE pu.Documento = ?""");
            List<Object> params = new ArrayList<>();
            params.add(IDinstructor);

            if (ambiente != null && !ambiente.isEmpty()) {
                sql.append(" AND amb.Ambiente = ?");
                params.add(ambiente);
            }

            if (ProgramaFormacion != null) {
                sql.append(" AND pf.ProgramaFormacion = ?");
                params.add(ProgramaFormacion);
            }

            if (ficha != null) {
                sql.append(" AND fc.NumeroFicha = ?");
                params.add(ficha);
            }

            List<Map<String, Object>> asistencias = jdbcTemplate.query(sql.toString(), params.toArray(), new RowMapper<Map<String, Object>>() {
                @Override
                public Map<String, Object> mapRow(ResultSet rs, int rowNum) throws SQLException {
                    Map<String, Object> asistencia = new HashMap<>();
                    asistencia.put("instructor", rs.getString("Instructor"));
                    asistencia.put("clase", rs.getString("NombreClase"));
                    asistencia.put("ambiente", rs.getString("Ambiente"));
                    asistencia.put("ficha", rs.getInt("NumeroFicha"));
                    asistencia.put("ProgramaFormacion", rs.getString("ProgramaFormacion"));
                    asistencia.put("fecha", rs.getDate("Fecha"));
                    asistencia.put("IDArchivo", rs.getInt("IDArchivo"));
                    return asistencia;
                }
            });
            return new ResponseEntity<>(asistencias, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



    @GetMapping("/descargarArchivo/{id}")
    public ResponseEntity<byte[]> descargarArchivo(@PathVariable int id) {
        String sql = "SELECT archivo_excel FROM asistencia WHERE ID = ?";
        byte[] archivoExcel = jdbcTemplate.queryForObject(sql, new Object[]{id}, byte[].class);

        if (archivoExcel == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", "asistencia_" + id + ".xlsx");

        return new ResponseEntity<>(archivoExcel, headers, HttpStatus.OK);
    }


}
