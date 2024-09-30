package com.proyectoasistencia.prasis.services.DataTablesServices.ResidenciaDataService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class DepartamentoDataService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Integer obtenerIdDepartamentoPorNombre(String nombreDepartamento) {
        String sql = "SELECT ID FROM departamentos WHERE nombre_departamento = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new Object[]{nombreDepartamento}, Integer.class);
        } catch (EmptyResultDataAccessException e) {
            throw new IllegalArgumentException("No se encontró el departamento: " + nombreDepartamento);
        }
    }

    // CRUD básico (listar, crear, actualizar, eliminar)
    public List<Map<String, Object>> obtenerTodosLosDepartamentos() {
        String sql = "SELECT * FROM departamentos";
        return jdbcTemplate.queryForList(sql);
    }

    public void crearDepartamento(String nombreDepartamento) {
        String sql = "INSERT INTO departamentos (nombre_departamento) VALUES (?)";
        jdbcTemplate.update(sql, nombreDepartamento);
    }

    public void actualizarDepartamento(Integer id, String nombreDepartamento) {
        String sql = "UPDATE departamentos SET nombre_departamento = ? WHERE ID = ?";
        jdbcTemplate.update(sql, nombreDepartamento, id);
    }

    public void eliminarDepartamento(Integer id) {
        String sql = "DELETE FROM departamentos WHERE ID = ?";
        jdbcTemplate.update(sql, id);
    }
}
