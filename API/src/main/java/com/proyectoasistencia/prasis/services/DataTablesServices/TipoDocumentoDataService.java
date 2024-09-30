package com.proyectoasistencia.prasis.services.DataTablesServices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class TipoDocumentoDataService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // Obtener todos los tipos de documento
    public List<Map<String, Object>> obtenerTodosLosTiposDocumento() {
        String sql = "SELECT * FROM tipodocumento";
        return jdbcTemplate.queryForList(sql);
    }

    // Obtener el ID de un tipo de documento por su nombre
    public Integer obtenerIdTipoDocumentoPorNombre(String tipoDocumento) {
        String sql = "SELECT ID FROM tipodocumento WHERE TipoDocumento = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new Object[]{tipoDocumento}, Integer.class);
        } catch (EmptyResultDataAccessException e) {
            throw new IllegalArgumentException("No se encontró el tipo de documento: " + tipoDocumento);
        }
    }

    // Crear un nuevo tipo de documento
    public void crearTipoDocumento(String tipoDocumento) {
        String sql = "INSERT INTO tipodocumento (TipoDocumento) VALUES (?)";
        System.out.println(sql + " " + tipoDocumento);
        jdbcTemplate.update(sql, tipoDocumento);
    }

    // Actualizar un tipo de documento existente
    public void actualizarTipoDocumento(Integer id, String tipoDocumento) {
        String sql = "UPDATE tipodocumento SET TipoDocumento = ? WHERE ID = ?";
        jdbcTemplate.update(sql, tipoDocumento, id);
    }

    // Eliminar un tipo de documento
    public void eliminarTipoDocumento(Integer id) {
        String sql = "DELETE FROM tipodocumento WHERE ID = ?";
        jdbcTemplate.update(sql, id);
    }
}

