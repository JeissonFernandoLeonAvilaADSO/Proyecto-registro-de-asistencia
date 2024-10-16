package com.proyectoasistencia.prasis.services.DataTablesServices;

import com.proyectoasistencia.prasis.services.UsersServices.InstructorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
public class ClaseFormacionDataService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static final Logger logger = LoggerFactory.getLogger(ClaseFormacionDataService.class);

    /**
     * Obtener todas las clases de formación.
     *
     * @return Lista de clases de formación.
     */
    public List<Map<String, Object>> obtenerTodasLasClases() {
        String sql = """
                SELECT claseformacion.ID, claseformacion.NombreClase, jornadaformacion.JornadasFormacion FROM claseformacion
                                INNER JOIN jornadaformacion  on claseformacion.IDJornadaFormacion = jornadaformacion.ID""";
        logger.info("Ejecutando consulta para obtener todas las clases de formación: {}", sql);
        try {
            List<Map<String, Object>> clases = jdbcTemplate.queryForList(sql);
            logger.info("Clases de formación obtenidas: {}", clases);
            return clases;
        } catch (DataAccessException e) {
            logger.error("Error al obtener todas las clases de formación: {}", e.getMessage());
            throw new RuntimeException("Error al obtener todas las clases de formación.", e);
        }
    }

    /**
     * Obtener todas las clases de formación agrupadas por NombreClase, concatenando sus IDs.
     *
     * @return Lista de mapas con NombreClase y IDs concatenados.
     */
    public List<Map<String, Object>> obtenerClasesAgrupadas() {
        String sql = "SELECT claseformacion.NombreClase, GROUP_CONCAT(claseformacion.ID) AS IDs " +
                "FROM claseformacion " +
                "INNER JOIN jornadaformacion ON claseformacion.IDJornadaFormacion = jornadaformacion.ID " +
                "GROUP BY claseformacion.NombreClase";

        return jdbcTemplate.queryForList(sql);
    }



    /**
     * Crear una nueva clase de formación.
     *
     * @param nombreClase      Nombre de la clase de formación.
     * @param jornadaFormacion Nombre de la jornada de formación.
     */
    @Transactional(rollbackFor = Exception.class)
    public void crearClase(String nombreClase, String jornadaFormacion) {
        String sqlInsertClase = "INSERT INTO claseformacion (NombreClase, IDJornadaFormacion) VALUES (?, ?)";
        logger.info("Creando clase de formación: {}, Jornada: {}", nombreClase, jornadaFormacion);

        try {
            // Obtener IDJornadaFormacion a partir del valor de jornadaFormacion
            Integer idJornadaFormacion = obtenerIdJornadaFormacionPorValor(jornadaFormacion);
            if (idJornadaFormacion == null) {
                throw new IllegalArgumentException("Jornada de formación no encontrada: " + jornadaFormacion);
            }
            logger.info("ID de la jornada de formación obtenida: {}", idJornadaFormacion);

            // Insertar en claseformacion
            jdbcTemplate.update(sqlInsertClase, nombreClase, idJornadaFormacion);
            logger.info("Clase de formación '{}' insertada exitosamente.", nombreClase);
        } catch (DataAccessException e) {
            logger.error("Error al crear clase de formación: {}", e.getMessage());
            throw new RuntimeException("Error al crear clase de formación.", e);
        }
    }

    /**
     * Actualizar una clase de formación existente.
     *
     * @param id                ID de la clase de formación.
     * @param nombreClase       Nuevo nombre de la clase de formación.
     * @param jornadaFormacion  Nuevo nombre de la jornada de formación.
     */
    @Transactional(rollbackFor = Exception.class)
    public void actualizarClase(Integer id, String nombreClase, String jornadaFormacion) {
        String sqlActualizarClase = "UPDATE claseformacion SET NombreClase = ?, IDJornadaFormacion = ? WHERE ID = ?";
        logger.info("Actualizando clase de formación ID: {}, Nuevo Nombre: {}, Nueva Jornada: {}", id, nombreClase, jornadaFormacion);

        try {
            // Obtener IDJornadaFormacion a partir del valor de jornadaFormacion
            Integer idJornadaFormacion = obtenerIdJornadaFormacionPorValor(jornadaFormacion);
            if (idJornadaFormacion == null) {
                throw new IllegalArgumentException("Jornada de formación no encontrada: " + jornadaFormacion);
            }
            logger.info("ID de la jornada de formación obtenida: {}", idJornadaFormacion);

            // Actualizar la clase de formación
            int filasAfectadas = jdbcTemplate.update(sqlActualizarClase, nombreClase, idJornadaFormacion, id);
            if (filasAfectadas == 0) {
                throw new IllegalArgumentException("Clase de formación con ID " + id + " no encontrada.");
            }
            logger.info("Clase de formación ID {} actualizada exitosamente.", id);
        } catch (DataAccessException e) {
            logger.error("Error al actualizar clase de formación: {}", e.getMessage());
            throw new RuntimeException("Error al actualizar clase de formación.", e);
        }
    }

    /**
     * Eliminar una clase de formación existente.
     *
     * @param id ID de la clase de formación a eliminar.
     */
    @Transactional(rollbackFor = Exception.class)
    public void eliminarClase(Integer id) {
        String sqlEliminarClase = "DELETE FROM claseformacion WHERE ID = ?";
        logger.info("Eliminando clase de formación ID: {}", id);

        try {
            // Eliminar la clase de formación
            int filasAfectadas = jdbcTemplate.update(sqlEliminarClase, id);
            if (filasAfectadas == 0) {
                throw new IllegalArgumentException("Clase de formación con ID " + id + " no encontrada.");
            }
            logger.info("Clase de formación ID {} eliminada exitosamente.", id);
        } catch (DataAccessException e) {
            logger.error("Error al eliminar clase de formación: {}", e.getMessage());
            throw new RuntimeException("Error al eliminar clase de formación.", e);
        }
    }

    /**
     * Obtener el ID de una clase de formación por su nombre y jornada.
     *
     * @param nombreClase      Nombre de la clase de formación.
     * @param jornadaFormacion Nombre de la jornada de formación.
     * @return ID de la clase de formación.
     */
    public Integer obtenerIdClasePorNombre(String nombreClase, String jornadaFormacion) {
        String sql = "SELECT ID FROM claseformacion WHERE NombreClase = ? AND IDJornadaFormacion = ?";
        logger.info("Ejecutando consulta para obtener ID de clase: {}, Jornada: {}", nombreClase, jornadaFormacion);
        try {
            // Obtener IDJornadaFormacion a partir del valor de jornadaFormacion
            Integer idJornadaFormacion = obtenerIdJornadaFormacionPorValor(jornadaFormacion);
            if (idJornadaFormacion == null) {
                throw new IllegalArgumentException("Jornada de formación no encontrada: " + jornadaFormacion);
            }
            logger.info("ID de la jornada de formación obtenida: {}", idJornadaFormacion);

            Integer idClase = jdbcTemplate.queryForObject(sql, new Object[]{nombreClase, idJornadaFormacion}, Integer.class);
            if (idClase == null) {
                throw new IllegalArgumentException("Clase de formación no encontrada con nombre: " + nombreClase + " y jornada: " + jornadaFormacion);
            }
            logger.info("ID de la clase '{}' obtenido: {}", nombreClase, idClase);
            return idClase;
        } catch (EmptyResultDataAccessException e) {
            logger.warn("No se encontró la clase de formación con nombre: {} y jornada: {}", nombreClase, jornadaFormacion);
            throw new IllegalArgumentException("Clase de formación no encontrada con nombre: " + nombreClase + " y jornada: " + jornadaFormacion, e);
        } catch (DataAccessException e) {
            logger.error("Error al obtener ID de clase de formación: {}", e.getMessage());
            throw new RuntimeException("Error al obtener ID de clase de formación.", e);
        }
    }

    /**
     * Obtener el ID de una jornada de formación por su valor.
     *
     * @param jornadaFormacion Valor de la jornada de formación.
     * @return ID de la jornada de formación, o null si no se encuentra.
     */
    public Integer obtenerIdJornadaFormacionPorValor(String jornadaFormacion) {
        String sql = "SELECT ID FROM jornadaformacion WHERE JornadasFormacion = ?";
        logger.info("Ejecutando consulta para obtener ID de jornada de formación: {}", jornadaFormacion);
        try {
            Integer idJornada = jdbcTemplate.queryForObject(sql, new Object[]{jornadaFormacion}, Integer.class);
            logger.info("ID de la jornada de formación '{}' obtenido: {}", jornadaFormacion, idJornada);
            return idJornada;
        } catch (EmptyResultDataAccessException e) {
            logger.warn("Jornada de formación no encontrada: {}", jornadaFormacion);
            return null;
        } catch (DataAccessException e) {
            logger.error("Error al obtener ID de jornada de formación: {}", e.getMessage());
            throw new RuntimeException("Error al obtener ID de jornada de formación.", e);
        }
    }

    public List<Map<String, Object>> obtenerClasePorDocumentoInstructor(String documentoInstructor) {
        String sql = """
            SELECT cf.NombreClase, jf.JornadasFormacion, f.NumeroFicha
            FROM claseformacion_instructor_ficha cif
            INNER JOIN perfilusuario pu ON cif.IDPerfilUsuario = pu.ID
            INNER JOIN claseformacion cf ON cif.IDClaseFormacion = cf.ID
            INNER JOIN fichas f ON cif.IDFicha = f.ID
            INNER JOIN jornadaformacion jf ON cf.IDJornadaFormacion = jf.ID
            WHERE pu.Documento = ?
        """;

        return jdbcTemplate.queryForList(sql, documentoInstructor);
    }
}

