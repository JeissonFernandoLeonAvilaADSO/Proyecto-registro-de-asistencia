package com.proyectoasistencia.prasis.services.DataTablesServices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class RolDataService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // Obtener todos los roles
    public List<Map<String, Object>> obtenerTodosLosRoles() {
        String sql = "SELECT * FROM rol";
        return jdbcTemplate.queryForList(sql);
    }

    // Obtener el ID de un rol por su nombre
    public Integer obtenerIdRolPorNombre(String nombreRol) {
        String sql = "SELECT ID FROM rol WHERE TipoRol = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new Object[]{nombreRol}, Integer.class);
        } catch (EmptyResultDataAccessException e) {
            throw new IllegalArgumentException("No se encontró el rol: " + nombreRol);
        }
    }

    // Crear un nuevo rol
    public void crearRol(String nombreRol) {
        String sql = "INSERT INTO rol (TipoRol) VALUES (?)";
        jdbcTemplate.update(sql, nombreRol);
    }

    // Actualizar un rol existente
    public void actualizarRol(Integer id, String nombreRol) {
        String sql = "UPDATE rol SET TipoRol = ? WHERE ID = ?";
        jdbcTemplate.update(sql, nombreRol, id);
    }

    // Eliminar un rol
    public void eliminarRol(Integer id) {
        String sql = "DELETE FROM rol WHERE ID = ?";
        jdbcTemplate.update(sql, id);
    }
}

