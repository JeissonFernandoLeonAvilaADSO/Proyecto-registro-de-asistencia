package com.proyectoasistencia.prasis.services.DataTablesServices.ProgramaFormacionDataServices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class AreaDataService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Map<String, Object>> getAllAreas() {
        String sql = "SELECT * FROM areas";
        return jdbcTemplate.queryForList(sql);
    }

    public Map<String, Object> getAreaById(int id) {
        String sql = "SELECT * FROM areas WHERE ID = ?";
        try {
            return jdbcTemplate.queryForMap(sql, id);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public Integer getAreaIdByValue(String area) {
        String sql = "SELECT ID FROM areas WHERE Area = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new Object[]{area}, Integer.class);
        } catch (EmptyResultDataAccessException e) {
            return null; // Si no se encuentra el área, retorna null
        }
    }

    public void createArea(String nombreArea) {
        String sql = "INSERT INTO areas (Area) VALUES (?)";
        jdbcTemplate.update(sql, nombreArea);
    }

    // Actualizar un área
    public boolean updateArea(int id, String nombreArea) {
        String sql = "UPDATE areas SET Area = ? WHERE ID = ?";
        int updatedRows = jdbcTemplate.update(sql, nombreArea, id);
        return updatedRows > 0;
    }

    // Eliminar un área
    public boolean deleteArea(int id) {
        String sql = "DELETE FROM areas WHERE ID = ?";
        int deletedRows = jdbcTemplate.update(sql, id);
        return deletedRows > 0;
    }
}
