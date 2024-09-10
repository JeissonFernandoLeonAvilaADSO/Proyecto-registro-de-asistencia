package com.proyectoasistencia.prasis.API;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/ListarUsuarios")
public class ListarAprendicesAPI {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @GetMapping("/ListarAprendices")
    public ResponseEntity<Map<String, Object>> ListarAprendices(@RequestParam("ficha") Integer ficha) {
        Map<String, Object> respuesta = new HashMap<>();
        String consulta = """
                        SELECT Documento, Nombres, Apellidos, td.TipoDocumento from perfilusuario pu
                            INNER JOIN tipodocumento td ON pu.IDTipoDocumento = td.ID
                            INNER JOIN fichas fc ON pu.IDFicha = fc.ID                                                          \s
                        WHERE NumeroFicha = ?""";
        try {
            List<Map<String, Object>> aprendices = jdbcTemplate.query(consulta, new Object[]{ficha}, new RowMapper<Map<String, Object>>() {
                @Override
                public Map<String, Object> mapRow(ResultSet rs, int rowNum) throws SQLException {
                    Map<String, Object> aprendiz = new HashMap<>();
                    aprendiz.put("Documento", rs.getString("Documento"));
                    aprendiz.put("Nombres", rs.getString("Nombres"));
                    aprendiz.put("Apellidos", rs.getString("Apellidos"));
                    aprendiz.put("TipoDocumento", rs.getString("TipoDocumento"));
                    return aprendiz;
                }
            });
            respuesta.put("aprendices", aprendices);
            return ResponseEntity.ok(respuesta);
        } catch (Exception e) {
            e.printStackTrace();
            respuesta.put("error", "Ocurrió un error al listar los aprendices.");
            return ResponseEntity.status(500).body(respuesta);
        }
    }
}
