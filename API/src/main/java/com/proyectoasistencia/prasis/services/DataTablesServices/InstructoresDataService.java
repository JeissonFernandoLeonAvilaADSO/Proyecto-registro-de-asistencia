package com.proyectoasistencia.prasis.services.DataTablesServices;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class InstructoresDataService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // Método para obtener todos los instructores
    public List<Map<String, Object>> obtenerTodosLosInstructores() {
        String sql = "SELECT i.ID, CONCAT(p.Nombres, ' ', p.Apellidos) AS NombreInstructor, p.Documento, p.Correo "
                + "FROM instructor i "
                + "INNER JOIN perfilusuario p ON i.IDPerfilUsuario = p.ID";

        return jdbcTemplate.queryForList(sql);
    }

    public String obtenerDocumentoPorNombreEIDClase(String nombreInstructor) {
        String sql = "SELECT p.Documento " +
                "FROM instructor i " +
                "INNER JOIN perfilusuario p ON i.IDPerfilUsuario = p.ID " +
                "WHERE CONCAT(p.Nombres, ' ', p.Apellidos) = ? ";

        try {
            return jdbcTemplate.queryForObject(sql, new Object[]{nombreInstructor}, String.class);
        } catch (Exception e) {
            // Manejar el caso cuando no se encuentra ningún documento
            return null;
        }
    }

}
