package com.proyectoasistencia.prasis.services.DataTablesServices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ActividadDataService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Integer obtenerIdActividadPorNombre(String tipoActividad) {
        String sql = "SELECT ID FROM actividad WHERE TipoActividad = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new Object[]{tipoActividad}, Integer.class);
        } catch (EmptyResultDataAccessException e) {
            throw new IllegalArgumentException("No se encontró la actividad: " + tipoActividad);
        }
    }

    // CRUD básico
    public List<Map<String, Object>> obtenerTodasLasActividades() {
        String sql = "SELECT * FROM actividad";
        return jdbcTemplate.queryForList(sql);
    }

    public void crearActividad(String tipoActividad) {
        String sql = "INSERT INTO actividad (TipoActividad) VALUES (?)";
        jdbcTemplate.update(sql, tipoActividad);
    }

    public void actualizarActividad(Integer id, String tipoActividad) {
        String sql = "UPDATE actividad SET TipoActividad = ? WHERE ID = ?";
        jdbcTemplate.update(sql, tipoActividad, id);
    }

    public void eliminarActividad(Integer id) {
        String sql = "DELETE FROM actividad WHERE ID = ?";
        jdbcTemplate.update(sql, id);
    }
}
