package com.proyectoasistencia.prasis.services.DataTablesServices.ProgramaFormacionDataServices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class NivelFormacionDataService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Map<String, Object>> getAllNivelesFormacion() {
        String sql = "SELECT * FROM nivelformacion";
        return jdbcTemplate.queryForList(sql);
    }

    public Map<String, Object> getNivelFormacionById(int id) {
        String sql = "SELECT * FROM nivelformacion WHERE ID = ?";
        try {
            return jdbcTemplate.queryForMap(sql, id);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public Integer getNivelIdByValue(String nivel) {
        String sql = "SELECT ID FROM nivelformacion WHERE NivelFormacion = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new Object[]{nivel}, Integer.class);
        } catch (EmptyResultDataAccessException e) {
            return null; // Si no se encuentra el nivel, retorna null
        }
    }

    // Crear un nuevo nivel de formación
    public void createNivelFormacion(String nivelFormacion) {
        String sql = "INSERT INTO nivelformacion (NivelFormacion) VALUES (?)";
        jdbcTemplate.update(sql, nivelFormacion);
    }

    // Actualizar un nivel de formación existente
    public boolean updateNivelFormacion(int id, String nivelFormacion) {
        String sql = "UPDATE nivelformacion SET NivelFormacion = ? WHERE ID = ?";
        int updatedRows = jdbcTemplate.update(sql, nivelFormacion, id);
        return updatedRows > 0;
    }

    // Eliminar un nivel de formación existente
    public boolean deleteNivelFormacion(int id) {
        String sql = "DELETE FROM nivelformacion WHERE ID = ?";
        int deletedRows = jdbcTemplate.update(sql, id);
        return deletedRows > 0;
    }
}

