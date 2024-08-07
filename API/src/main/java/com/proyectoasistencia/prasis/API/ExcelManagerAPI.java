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
        String sqlAsistencia = "INSERT INTO registroasistencias (instructor, competencia, ambiente, ficha, IDProgramaFormacion, IDArchivo) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement psAsistencia = conexion.prepareStatement(sqlAsistencia)) {
            psAsistencia.setString(1, params.get("Instructor"));
            psAsistencia.setString(2, params.get("Competencia"));
            psAsistencia.setString(3, params.get("Ambiente"));
            psAsistencia.setInt(4, Integer.parseInt(params.get("Ficha")));
            psAsistencia.setInt(5, Integer.parseInt(params.get("IDProgramaFormacion")));
            psAsistencia.setInt(6, archivoId);
            psAsistencia.executeUpdate();
        }
    }

    @GetMapping("/ListarAsistencias")
    public ResponseEntity<List<Map<String, Object>>> listarAsistencias(
            @RequestParam String instructor,
            @RequestParam(required = false) String ambiente,
            @RequestParam(required = false) Integer idProgramaFormacion,
            @RequestParam(required = false) Integer ficha
    ) {
        try {
            System.out.println(ambiente);
            System.out.println(idProgramaFormacion);
            System.out.println(ficha);
            StringBuilder sql = new StringBuilder("SELECT * FROM registroasistencias INNER JOIN asistencia ON asistencia.ID = registroasistencias.IDArchivo WHERE registroasistencias.Instructor = ?");
            List<Object> params = new ArrayList<>();
            params.add(instructor);

            if (ambiente != null && !ambiente.isEmpty()) {
                sql.append(" AND registroasistencias.Ambiente = ?");
                params.add(ambiente);
            }

            if (idProgramaFormacion != null) {
                sql.append(" AND registroasistencias.IDProgramaFormacion = ?");
                params.add(idProgramaFormacion);
            }

            if (ficha != null) {
                sql.append(" AND registroasistencias.Ficha = ?");
                params.add(ficha);
            }

            List<Map<String, Object>> asistencias = jdbcTemplate.query(sql.toString(), params.toArray(), new RowMapper<Map<String, Object>>() {
                @Override
                public Map<String, Object> mapRow(ResultSet rs, int rowNum) throws SQLException {
                    Map<String, Object> asistencia = new HashMap<>();
                    asistencia.put("instructor", rs.getString("Instructor"));
                    asistencia.put("competencia", rs.getString("Competencia"));
                    asistencia.put("ambiente", rs.getString("Ambiente"));
                    asistencia.put("ficha", rs.getInt("Ficha"));
                    asistencia.put("IDProgramaFormacion", rs.getInt("IDProgramaFormacion"));
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
