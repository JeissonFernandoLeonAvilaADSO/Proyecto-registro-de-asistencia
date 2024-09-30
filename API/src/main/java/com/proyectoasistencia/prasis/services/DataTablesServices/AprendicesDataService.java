package com.proyectoasistencia.prasis.services.DataTablesServices;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Map;

@Service
public class AprendicesDataService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // Método para obtener todos los aprendices relacionados con una ficha
    public List<Map<String, Object>> obtenerAprendicesPorFicha(Integer Ficha) {
        String sql = """
            
                SELECT a.ID, CONCAT(p.Nombres, ' ', p.Apellidos) AS NombreAprendiz, p.Documento
            FROM aprendiz a
                     INNER JOIN perfilusuario p ON a.IDPerfilUsuario = p.ID
                     INNER JOIN fichas f ON a.IDFicha = f.ID
            WHERE f.NumeroFicha = ?""";

        System.out.println("Ejecutando consulta para obtener aprendices relacionados con la ficha: " + Ficha);

        List<Map<String, Object>> aprendices = jdbcTemplate.queryForList(sql, Ficha);
        System.out.println("Aprendices obtenidos: " + aprendices);

        return aprendices;
    }


}
