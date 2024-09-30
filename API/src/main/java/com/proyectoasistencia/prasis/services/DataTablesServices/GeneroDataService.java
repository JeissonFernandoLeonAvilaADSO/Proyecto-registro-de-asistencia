package com.proyectoasistencia.prasis.services.DataTablesServices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class GeneroDataService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // Obtener todos los géneros
    public List<Map<String, Object>> obtenerTodosLosGeneros() {
        String sql = "SELECT * FROM genero";
        return jdbcTemplate.queryForList(sql);
    }

    // Obtener el ID de un género por su nombre
    public Integer obtenerIdGeneroPorNombre(String nombreGenero) {
        String sql = "SELECT ID FROM genero WHERE TiposGeneros = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new Object[]{nombreGenero}, Integer.class);
        } catch (EmptyResultDataAccessException e) {
            throw new IllegalArgumentException("No se encontró el género: " + nombreGenero);
        }
    }

    // Crear un nuevo género
    public void crearGenero(String nombreGenero) {
        String sql = "INSERT INTO genero (TiposGeneros) VALUES (?)";
        jdbcTemplate.update(sql, nombreGenero);
    }

    // Actualizar un género existente
    public void actualizarGenero(Integer id, String nombreGenero) {
        String sql = "UPDATE genero SET TiposGeneros = ? WHERE ID = ?";
        jdbcTemplate.update(sql, nombreGenero, id);
    }

    // Eliminar un género
    public void eliminarGenero(Integer id) {
        String sql = "DELETE FROM genero WHERE ID = ?";
        jdbcTemplate.update(sql, id);
    }
}
