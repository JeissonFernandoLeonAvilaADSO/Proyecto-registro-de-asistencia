package com.proyectoasistencia.prasis.services.DataTablesServices.ResidenciaDataService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class MunicipioDataService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private DepartamentoDataService departamentoDataService;

    public Integer obtenerIdMunicipioPorNombre(String nombreMunicipio) {
        String sql = "SELECT id_municipio FROM municipios WHERE nombre_municipio = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new Object[]{nombreMunicipio}, Integer.class);
        } catch (EmptyResultDataAccessException e) {
            throw new IllegalArgumentException("No se encontró el municipio: " + nombreMunicipio);
        }
    }

    // CRUD básico (listar, crear, actualizar, eliminar)
    public List<Map<String, Object>> obtenerTodosLosMunicipios() {
        String sql = "SELECT * FROM municipios";
        return jdbcTemplate.queryForList(sql);
    }

    public void crearMunicipio(String nombreMunicipio, String nombreDepartamento) {
        Integer idDepartamento = departamentoDataService.obtenerIdDepartamentoPorNombre(nombreDepartamento);
        String sql = "INSERT INTO municipios (nombre_municipio, id_departamento) VALUES (?, ?)";
        jdbcTemplate.update(sql, nombreMunicipio, idDepartamento);
    }

    public void actualizarMunicipio(Integer id, String nombreMunicipio, String nombreDepartamento) {
        Integer idDepartamento = departamentoDataService.obtenerIdDepartamentoPorNombre(nombreDepartamento);
        String sql = "UPDATE municipios SET nombre_municipio = ?, id_departamento = ? WHERE id_municipio = ?";
        jdbcTemplate.update(sql, nombreMunicipio, idDepartamento, id);
    }

    public void eliminarMunicipio(Integer id) {
        String sql = "DELETE FROM municipios WHERE id_municipio = ?";
        jdbcTemplate.update(sql, id);
    }
}
