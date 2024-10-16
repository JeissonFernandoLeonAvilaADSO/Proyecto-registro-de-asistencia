package com.proyectoasistencia.prasis.services.DataTablesServices;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class InstructoresDataService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static final Logger logger = LoggerFactory.getLogger(InstructoresDataService.class);


    // Método para obtener todos los instructores
    public List<Map<String, Object>> obtenerTodosLosInstructores() {
        String sql = "SELECT p.ID, CONCAT(p.Nombres, ' ', p.Apellidos) AS NombreInstructor, p.Documento, p.Correo "
                + "FROM perfilusuario p";

        return jdbcTemplate.queryForList(sql);
    }

    public String obtenerDocumentoPorNombreEIDClase(String nombreInstructor) {
        String sql = "SELECT p.Documento " +
                "FROM perfilusuario p " +
                "WHERE CONCAT(p.Nombres, ' ', p.Apellidos) = ? ";

        try {
            return jdbcTemplate.queryForObject(sql, new Object[]{nombreInstructor}, String.class);
        } catch (Exception e) {
            // Manejar el caso cuando no se encuentra ningún documento
            return null;
        }
    }

    /**
     * Obtener el ID de un instructor por su documento.
     *
     * @param documentoInstructor Documento del instructor.
     * @return ID del instructor, o null si no se encuentra.
     */
    public Integer obtenerIdInstructorPorDocumento(String documentoInstructor) {
        // Validación de entrada
        if (documentoInstructor == null || documentoInstructor.trim().isEmpty()) {
            logger.error("El documento del instructor no puede estar vacío.");
            return null;
        }

        String sql = "SELECT ID FROM perfilusuario WHERE Documento = ? AND IDRol = 1";

        try {
            // Ejecutar la consulta para obtener el ID del instructor
            Integer id = jdbcTemplate.queryForObject(sql, new Object[]{documentoInstructor}, Integer.class);
            return id;
        } catch (EmptyResultDataAccessException e) {
            // No se encontró ningún instructor con el documento proporcionado
            logger.warn("No se encontró ningún instructor con el documento: {}", documentoInstructor);
            return null;
        } catch (IncorrectResultSizeDataAccessException e) {
            // Se encontraron múltiples instructores con el mismo documento, lo cual puede ser un error de datos
            logger.error("Se encontraron múltiples instructores con el documento: {}", documentoInstructor);
            return null;
        } catch (Exception e) {
            // Manejar otras excepciones
            logger.error("Error al obtener el ID del instructor para el documento {}: {}", documentoInstructor, e.getMessage());
            return null;
        }
    }

}
