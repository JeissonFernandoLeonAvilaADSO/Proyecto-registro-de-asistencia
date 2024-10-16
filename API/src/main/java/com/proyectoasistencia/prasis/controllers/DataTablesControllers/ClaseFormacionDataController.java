package com.proyectoasistencia.prasis.controllers.DataTablesControllers;

import com.proyectoasistencia.prasis.services.DataTablesServices.ClaseFormacionDataService;
import com.proyectoasistencia.prasis.services.DataTablesServices.ProgramaFormacionDataServices.JornadaFormacionDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/Data/ClaseFormacion")
public class ClaseFormacionDataController {

    @Autowired
    private ClaseFormacionDataService claseFormacionDataService;

    private static final Logger logger = LoggerFactory.getLogger(ClaseFormacionDataController.class);

    /**
     * Obtener todas las clases de formación.
     *
     * @return Respuesta con mensaje y datos de las clases de formación.
     */
    @GetMapping("/todas")
    public ResponseEntity<?> obtenerTodasLasClases() {
        logger.info("Endpoint /todas llamado para obtener todas las clases de formación.");
        try {
            List<Map<String, Object>> clases = claseFormacionDataService.obtenerTodasLasClases();
            logger.info("Clases obtenidas: {}", clases);
            return ResponseEntity.ok(Map.of(
                    "mensaje", "Clases de formación obtenidas exitosamente.",
                    "data", clases
            ));
        } catch (Exception e) {
            logger.error("Error al obtener todas las clases de formación: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("mensaje", "Error al obtener todas las clases de formación."));
        }
    }

    /**
     * Endpoint para obtener todas las clases de formación agrupadas por NombreClase.
     *
     * @return Lista de mapas con NombreClase y sus IDs concatenados.
     */
    @GetMapping("/todos")
    public List<Map<String, Object>> obtenerClasesAgrupadas() {
        return claseFormacionDataService.obtenerClasesAgrupadas();
    }



    /**
     * Crear una nueva clase de formación.
     *
     * @param nombreClase        Nombre de la clase de formación.
     * @param jornadaFormacion   Nombre de la jornada de formación.
     * @return Respuesta con mensaje de éxito o error.
     */
    @PostMapping("/crear")
    public ResponseEntity<?> crearClase(@RequestParam String nombreClase,
                                        @RequestParam String jornadaFormacion) {
        logger.info("Endpoint /crear llamado para crear clase: {}, Jornada: {}", nombreClase, jornadaFormacion);
        try {
            claseFormacionDataService.crearClase(nombreClase, jornadaFormacion);
            logger.info("Clase creada exitosamente: {}", nombreClase);
            return ResponseEntity.ok(Map.of("mensaje", "Clase creada exitosamente."));
        } catch (IllegalArgumentException e) {
            logger.warn("Error al crear clase: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("mensaje", e.getMessage()));
        } catch (Exception e) {
            logger.error("Error al crear clase: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("mensaje", "Error al crear la clase de formación."));
        }
    }

    /**
     * Actualizar una clase de formación existente.
     *
     * @param id                  ID de la clase de formación.
     * @param nombreClase         Nuevo nombre de la clase de formación.
     * @param jornadaFormacion    Nuevo nombre de la jornada de formación.
     * @return Respuesta con mensaje de éxito o error.
     */
    @PutMapping("/actualizar")
    public ResponseEntity<?> actualizarClase(@RequestParam Integer id,
                                             @RequestParam String nombreClase,
                                             @RequestParam String jornadaFormacion) {
        logger.info("Endpoint /actualizar llamado para actualizar clase ID: {}, Nombre: {}, Jornada: {}", id, nombreClase, jornadaFormacion);
        try {
            claseFormacionDataService.actualizarClase(id, nombreClase, jornadaFormacion);
            logger.info("Clase actualizada exitosamente: ID {}", id);
            return ResponseEntity.ok(Map.of("mensaje", "Clase actualizada exitosamente."));
        } catch (IllegalArgumentException e) {
            logger.warn("Error al actualizar clase: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("mensaje", e.getMessage()));
        } catch (Exception e) {
            logger.error("Error al actualizar clase: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("mensaje", "Error al actualizar la clase de formación."));
        }
    }

    /**
     * Eliminar una clase de formación existente.
     *
     * @param id ID de la clase de formación a eliminar.
     * @return Respuesta con mensaje de éxito o error.
     */
    @DeleteMapping("/eliminar")
    public ResponseEntity<?> eliminarClase(@RequestParam Integer id) {
        logger.info("Endpoint /eliminar llamado para eliminar clase ID: {}", id);
        try {
            claseFormacionDataService.eliminarClase(id);
            logger.info("Clase eliminada exitosamente: ID {}", id);
            return ResponseEntity.ok(Map.of("mensaje", "Clase eliminada exitosamente."));
        } catch (IllegalArgumentException e) {
            logger.warn("Error al eliminar clase: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("mensaje", e.getMessage()));
        } catch (Exception e) {
            logger.error("Error al eliminar clase: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("mensaje", "Error al eliminar la clase de formación."));
        }
    }

    /**
     * Obtener el ID de una clase de formación por su nombre y jornada.
     *
     * @param nombreClase      Nombre de la clase de formación.
     * @param jornadaFormacion Nombre de la jornada de formación.
     * @return Respuesta con mensaje y ID de la clase de formación.
     */
    @GetMapping("/buscarPorNombre")
    public ResponseEntity<?> obtenerIdClasePorNombre(@RequestParam String nombreClase,
                                                     @RequestParam String jornadaFormacion) {
        logger.info("Endpoint /buscarPorNombre llamado para buscar ID de la clase: {}, Jornada: {}", nombreClase, jornadaFormacion);
        try {
            Integer idClase = claseFormacionDataService.obtenerIdClasePorNombre(nombreClase, jornadaFormacion);
            logger.info("ID de la clase '{}' obtenido: {}", nombreClase, idClase);
            return ResponseEntity.ok(Map.of(
                    "mensaje", "ID de la clase obtenida exitosamente.",
                    "data", Map.of("IDClase", idClase)
            ));
        } catch (IllegalArgumentException e) {
            logger.warn("Clase de formación no encontrada: {}", nombreClase);
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("mensaje", e.getMessage()));
        } catch (Exception e) {
            logger.error("Error al buscar ID de la clase de formación: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("mensaje", "Error al buscar ID de la clase de formación."));
        }
    }
}