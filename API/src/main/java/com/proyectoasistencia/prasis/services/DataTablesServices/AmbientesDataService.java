package com.proyectoasistencia.prasis.services.DataTablesServices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class AmbientesDataService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Integer obtenerIdAmbientePorNombre(String nombreAmbiente) {
        String sql = "SELECT ID FROM ambientes WHERE Ambiente = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new Object[]{nombreAmbiente}, Integer.class);
        } catch (EmptyResultDataAccessException e) {
            throw new IllegalArgumentException("No se encontró el ambiente: " + nombreAmbiente);
        }
    }

    // CRUD básico
    public List<Map<String, Object>> obtenerTodosLosAmbientes() {
        String sql = "SELECT * FROM ambientes";
        return jdbcTemplate.queryForList(sql);
    }

    public void crearAmbiente(String nombreAmbiente) {
        String sql = "INSERT INTO ambientes (Ambiente) VALUES (?)";
        jdbcTemplate.update(sql, nombreAmbiente);
    }

    public void actualizarAmbiente(Integer id, String nombreAmbiente) {
        String sql = "UPDATE ambientes SET Ambiente = ? WHERE ID = ?";
        jdbcTemplate.update(sql, nombreAmbiente, id);
    }

    public void eliminarAmbiente(Integer id) {
        String sql = "DELETE FROM ambientes WHERE ID = ?";
        jdbcTemplate.update(sql, id);
    }
}

