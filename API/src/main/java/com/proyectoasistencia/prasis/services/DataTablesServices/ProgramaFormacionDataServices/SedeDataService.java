package com.proyectoasistencia.prasis.services.DataTablesServices.ProgramaFormacionDataServices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class SedeDataService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Map<String, Object>> getAllSedes() {
        String sql = "SELECT * FROM sede";
        return jdbcTemplate.queryForList(sql);
    }

    public Map<String, Object> getSedeById(int id) {
        String sql = "SELECT * FROM sede WHERE ID = ?";
        try {
            return jdbcTemplate.queryForMap(sql, id);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public Integer getSedeIdByCentroFormacion(String centroFormacion) {
        String sql = "SELECT ID FROM sede WHERE CentroFormacion = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new Object[]{centroFormacion}, Integer.class);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    // Crear una nueva sede
    public void createSede(String nombreSede) {
        String sql = "INSERT INTO sede (CentroFormacion) VALUES (?)";
        jdbcTemplate.update(sql, nombreSede);
    }

    // Actualizar una sede existente
    public boolean updateSede(int id, String nombreSede) {
        String sql = "UPDATE sede SET CentroFormacion = ? WHERE ID = ?";
        int updatedRows = jdbcTemplate.update(sql, nombreSede, id);
        return updatedRows > 0;
    }

    // Eliminar una sede existente
    public boolean deleteSede(int id) {
        String sql = "DELETE FROM sede WHERE ID = ?";
        int deletedRows = jdbcTemplate.update(sql, id);
        return deletedRows > 0;
    }
}