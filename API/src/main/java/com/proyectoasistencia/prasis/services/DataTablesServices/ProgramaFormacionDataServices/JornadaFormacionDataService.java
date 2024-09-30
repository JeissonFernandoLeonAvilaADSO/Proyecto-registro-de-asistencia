package com.proyectoasistencia.prasis.services.DataTablesServices.ProgramaFormacionDataServices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class JornadaFormacionDataService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Map<String, Object>> getAllJornadasFormacion() {
        String sql = "SELECT * FROM jornadaformacion";
        return jdbcTemplate.queryForList(sql);
    }

    public Map<String, Object> getJornadaFormacionById(int id) {
        String sql = "SELECT * FROM jornadaformacion WHERE ID = ?";
        try {
            return jdbcTemplate.queryForMap(sql, id);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public Integer getJornadaIdByValue(String jornada) {
        String sql = "SELECT ID FROM jornadaformacion WHERE JornadasFormacion = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new Object[]{jornada}, Integer.class);
        } catch (EmptyResultDataAccessException e) {
            return null; // Si no se encuentra la jornada, retorna null
        }
    }

    // Crear una nueva jornada de formación
    public void createJornadaFormacion(String jornadasFormacion) {
        String sql = "INSERT INTO jornadaformacion (JornadasFormacion) VALUES (?)";
        jdbcTemplate.update(sql, jornadasFormacion);
    }

    // Actualizar una jornada de formación existente
    public boolean updateJornadaFormacion(int id, String jornadasFormacion) {
        String sql = "UPDATE jornadaformacion SET JornadasFormacion = ? WHERE ID = ?";
        int updatedRows = jdbcTemplate.update(sql, jornadasFormacion, id);
        return updatedRows > 0;
    }

    // Eliminar una jornada de formación existente
    public boolean deleteJornadaFormacion(int id) {
        String sql = "DELETE FROM jornadaformacion WHERE ID = ?";
        int deletedRows = jdbcTemplate.update(sql, id);
        return deletedRows > 0;
    }
}

