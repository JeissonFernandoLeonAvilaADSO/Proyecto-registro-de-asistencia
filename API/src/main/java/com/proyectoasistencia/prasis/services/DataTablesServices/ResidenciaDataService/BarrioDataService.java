package com.proyectoasistencia.prasis.services.DataTablesServices.ResidenciaDataService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class BarrioDataService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private MunicipioDataService municipioDataService;

    public Integer obtenerIdBarrioPorNombre(String nombreBarrio) {
        String sql = "SELECT id_barrio FROM barrios WHERE nombre_barrio = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new Object[]{nombreBarrio}, Integer.class);
        } catch (EmptyResultDataAccessException e) {
            throw new IllegalArgumentException("No se encontró el barrio: " + nombreBarrio);
        }
    }

    // CRUD básico (listar, crear, actualizar, eliminar)
    public List<Map<String, Object>> obtenerTodosLosBarrios() {
        String sql = "SELECT * FROM barrios";
        return jdbcTemplate.queryForList(sql);
    }

    public void crearBarrio(String nombreBarrio, String nombreMunicipio) {
        Integer idMunicipio = municipioDataService.obtenerIdMunicipioPorNombre(nombreMunicipio);
        String sql = "INSERT INTO barrios (nombre_barrio, id_municipio) VALUES (?, ?)";
        jdbcTemplate.update(sql, nombreBarrio, idMunicipio);
    }

    public void actualizarBarrio(Integer id, String nombreBarrio, String nombreMunicipio) {
        Integer idMunicipio = municipioDataService.obtenerIdMunicipioPorNombre(nombreMunicipio);
        String sql = "UPDATE barrios SET nombre_barrio = ?, id_municipio = ? WHERE id_barrio = ?";
        jdbcTemplate.update(sql, nombreBarrio, idMunicipio, id);
    }

    public void eliminarBarrio(Integer id) {
        String sql = "DELETE FROM barrios WHERE id_barrio = ?";
        jdbcTemplate.update(sql, id);
    }
}

