package com.proyectoasistencia.prasis.controllers.DataTablesControllers;

import com.proyectoasistencia.prasis.services.DataTablesServices.ClaseFormacionDataService;
import com.proyectoasistencia.prasis.services.DataTablesServices.FichasDataService;
import com.proyectoasistencia.prasis.services.DataTablesServices.InstructoresDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/Data/Fichas")
public class FichasDataController {

    @Autowired
    private FichasDataService fichasDataService;

    @Autowired
    private ClaseFormacionDataService claseFormacionDataService;

    @Autowired
    private InstructoresDataService instructoresDataService;

    // Obtener todas las fichas
    @GetMapping("/todos")
    public ResponseEntity<List<Map<String, Object>>> obtenerTodasLasFichas() {
        System.out.println("Obteniendo Fichas");
        List<Map<String, Object>> fichas = fichasDataService.obtenerTodasLasFichas();
        System.out.println(fichas);
        return ResponseEntity.ok(fichas);
    }

    // Nuevo endpoint para obtener todas las fichas con detalles
    @GetMapping("/todosConDetalles")
    public ResponseEntity<List<Map<String, Object>>> obtenerTodasLasFichasConDetalles() {
        try {
            List<Map<String, Object>> fichas = fichasDataService.obtenerTodasLasFichasConDetalles();
            return ResponseEntity.ok(fichas);
        } catch (Exception e) {
            System.out.println("Error al obtener las fichas con detalles: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonList(Map.of("error", "Error al obtener las fichas con detalles: " + e.getMessage())));
        }
    }

    // Crear una nueva ficha
    @PostMapping("/crear")
    public ResponseEntity<String> crearFicha(@RequestParam Integer numeroFicha,
                                             @RequestParam String programaFormacion,
                                             @RequestParam String jornadaFormacion) {
        try {
            // Decodificar los parámetros si es necesario
            Integer decodeNumeroFicha = Integer.parseInt(URLDecoder.decode(String.valueOf(numeroFicha), StandardCharsets.UTF_8.name()));
            String decodeProgramaFormacion = URLDecoder.decode(programaFormacion, StandardCharsets.UTF_8.name());
            String decodeJornadaFormacion = URLDecoder.decode(jornadaFormacion, StandardCharsets.UTF_8.name());

            fichasDataService.crearFicha(decodeNumeroFicha, decodeProgramaFormacion, decodeJornadaFormacion);
            return ResponseEntity.ok("Ficha creada exitosamente.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al crear la ficha: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al crear la ficha: " + e.getMessage());
        }
    }

    // Actualizar una ficha existente
    @PutMapping("/actualizar")
    public ResponseEntity<String> actualizarFicha(@RequestParam Integer id,
                                                  @RequestParam Integer numeroFicha,
                                                  @RequestParam String programaFormacion,
                                                  @RequestParam String jornadaFormacion) {
        try {
            // Decodificar los parámetros si es necesario
            Integer decodeId = Integer.parseInt(URLDecoder.decode(String.valueOf(id), StandardCharsets.UTF_8.name()));
            Integer decodeNumeroFicha = Integer.parseInt(URLDecoder.decode(String.valueOf(numeroFicha), StandardCharsets.UTF_8.name()));
            String decodeProgramaFormacion = URLDecoder.decode(programaFormacion, StandardCharsets.UTF_8.name());
            String decodeJornadaFormacion = URLDecoder.decode(jornadaFormacion, StandardCharsets.UTF_8.name());

            fichasDataService.actualizarFicha(decodeId, decodeNumeroFicha, decodeProgramaFormacion, decodeJornadaFormacion);
            return ResponseEntity.ok("Ficha actualizada exitosamente.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al actualizar la ficha: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al actualizar la ficha: " + e.getMessage());
        }
    }

    // Eliminar una ficha
    @DeleteMapping("/eliminar")
    public ResponseEntity<String> eliminarFicha(@RequestParam Integer id) {
        try {
            // Decodificar el parámetro si es necesario
            Integer decodeId = Integer.parseInt(URLDecoder.decode(String.valueOf(id), StandardCharsets.UTF_8.name()));

            fichasDataService.eliminarFicha(decodeId);
            return ResponseEntity.ok("Ficha eliminada exitosamente.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al eliminar la ficha: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al eliminar la ficha: " + e.getMessage());
        }
    }

    @GetMapping("/programaFormacion/{numeroFicha}")
    public ResponseEntity<Map<String, Object>> obtenerProgramaFormacionPorFicha(@PathVariable Integer numeroFicha) {
        try {
            Map<String, Object> programaFormacion = fichasDataService.obtenerProgramaFormacionPorFicha(numeroFicha);
            return ResponseEntity.ok(programaFormacion);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.singletonMap("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.singletonMap("error", "Error al obtener el programa de formación: " + e.getMessage()));
        }
    }

    /**
     * Endpoint para obtener las fichas por nombre del programa de formación.
     *
     * @param nombrePrograma Nombre del programa de formación.
     * @return ResponseEntity con la lista de fichas o mensaje de error.
     */
    @GetMapping("/porPrograma")
    public ResponseEntity<Map<String, Object>> obtenerFichasPorPrograma(@RequestParam String nombrePrograma) {
        try {
            // Decodificar el nombre del programa en caso de que venga codificado
            String nombreProgramaDecodificado = URLDecoder.decode(nombrePrograma, StandardCharsets.UTF_8.name());
            System.out.println("Programa decodificado en el backend: " + nombreProgramaDecodificado);

            // Llamar al servicio para obtener las fichas por el programa de formación
            List<Map<String, Object>> fichas = fichasDataService.obtenerFichasPorPrograma(nombreProgramaDecodificado);

            // Retornar la lista de fichas en el response con un mensaje
            return ResponseEntity.ok(Map.of(
                    "data", fichas,
                    "mensaje", "Fichas obtenidas exitosamente por el programa de formación."
            ));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of(
                    "mensaje", e.getMessage()
            ));
        } catch (Exception e) {
            System.out.println("Error al obtener las fichas por programa: " + e.getMessage());
            return ResponseEntity.status(500).body(Map.of(
                    "mensaje", "Error interno del servidor al obtener las fichas por programa."
            ));
        }
    }

    /**
     * Endpoint para obtener las fichas por documento del instructor.
     *
     * @param documento Documento del instructor.
     * @return ResponseEntity con la lista de fichas o mensaje de error.
     */
    @GetMapping("/instructor/{documento}")
    public ResponseEntity<Map<String, Object>> obtenerFichasPorInstructor(@PathVariable String documento) {
        try {
            // Decodificar el documento en caso de que venga codificado
            String documentoDecodificado = URLDecoder.decode(documento, StandardCharsets.UTF_8.name());
            System.out.println("Documento decodificado en el backend: " + documentoDecodificado);

            // Llamar al servicio para obtener las fichas por el documento del instructor
            List<Map<String, Object>> fichas = fichasDataService.obtenerFichasPorDocumentoInstructor(documentoDecodificado);

            // Retornar la lista de fichas en el response con un mensaje
            return ResponseEntity.ok(Map.of(
                    "data", fichas,
                    "mensaje", "Fichas obtenidas exitosamente por el documento del instructor."
            ));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(404).body(Map.of(
                    "mensaje", e.getMessage()
            ));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of(
                    "mensaje", "Error interno del servidor al obtener las fichas por instructor."
            ));
        }
    }

    /**
     * Obtener todas las asociaciones entre fichas, instructores y clases de formación.
     */
    @GetMapping("/obtenerAsociaciones")
    public ResponseEntity<Map<String, Object>> obtenerAsociaciones() {
        try {
            List<Map<String, Object>> asociaciones = fichasDataService.obtenerAsociaciones();
            return ResponseEntity.ok(Map.of(
                    "data", asociaciones,
                    "mensaje", "Asociaciones obtenidas exitosamente."
            ));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of(
                    "mensaje", "Error interno al obtener las asociaciones."
            ));
        }
    }

    /**
     * Asociar una ficha con un instructor y una clase de formación.
     */
    @PostMapping("/asociarFichaInstructorClase")
    public ResponseEntity<Map<String, Object>> asociarFichaInstructorClase(
            @RequestParam Integer numeroFicha,
            @RequestParam String documentoInstructor,
            @RequestParam String nombreClase,
            @RequestParam String jornadaClase) {
        try {
            Integer idInstructor = instructoresDataService.obtenerIdInstructorPorDocumento(documentoInstructor);
            if (idInstructor == null) {
                throw new IllegalArgumentException("Instructor no encontrado.");
            }

            Integer idClaseFormacion = claseFormacionDataService.obtenerIdClasePorNombre(nombreClase, jornadaClase);
            if (idClaseFormacion == null) {
                throw new IllegalArgumentException("Clase de formación no encontrada.");
            }

            Integer idFicha = fichasDataService.obtenerIdFichaPorNumero(numeroFicha);
            if (idFicha == null) {
                throw new IllegalArgumentException("Ficha no encontrada.");
            }

            fichasDataService.asociarFichaInstructorClase(idFicha, idInstructor, idClaseFormacion);
            return ResponseEntity.ok(Map.of("mensaje", "Asociación exitosa."));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("mensaje", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("mensaje", "Error interno."));
        }
    }

    /**
     * Editar una asociación existente entre ficha, instructor y clase de formación.
     */
    @PutMapping("/editarAsociacion")
    public ResponseEntity<Map<String, Object>> editarAsociacionFichaInstructorClase(
            @RequestParam String nombreClaseAnterior,
            @RequestParam String nombreClaseNueva,
            @RequestParam Integer numeroFichaAnterior,
            @RequestParam Integer numeroFichaNuevo,
            @RequestParam String documentoInstructorAnterior,
            @RequestParam String documentoInstructorNuevo,
            @RequestParam String jornadaClase) {
        try {
            Integer idClaseAnterior = claseFormacionDataService.obtenerIdClasePorNombre(nombreClaseAnterior, jornadaClase);
            if (idClaseAnterior == null) {
                throw new IllegalArgumentException("Clase de formación anterior no encontrada.");
            }

            Integer idClaseNueva = claseFormacionDataService.obtenerIdClasePorNombre(nombreClaseNueva, jornadaClase);
            if (idClaseNueva == null) {
                throw new IllegalArgumentException("Nueva clase de formación no encontrada.");
            }

            Integer idFichaAnterior = fichasDataService.obtenerIdFichaPorNumero(numeroFichaAnterior);
            if (idFichaAnterior == null) {
                throw new IllegalArgumentException("Ficha anterior no encontrada.");
            }

            Integer idFichaNuevo = fichasDataService.obtenerIdFichaPorNumero(numeroFichaNuevo);
            if (idFichaNuevo == null) {
                throw new IllegalArgumentException("Nueva ficha no encontrada.");
            }

            Integer idInstructorAnterior = instructoresDataService.obtenerIdInstructorPorDocumento(documentoInstructorAnterior);
            if (idInstructorAnterior == null) {
                throw new IllegalArgumentException("Instructor anterior no encontrado.");
            }

            Integer idInstructorNuevo = instructoresDataService.obtenerIdInstructorPorDocumento(documentoInstructorNuevo);
            if (idInstructorNuevo == null) {
                throw new IllegalArgumentException("Nuevo instructor no encontrado.");
            }

            fichasDataService.editarAsociacionFichaInstructorClase(
                    idClaseAnterior, idClaseNueva,
                    idFichaAnterior, idFichaNuevo,
                    idInstructorAnterior, idInstructorNuevo
            );

            return ResponseEntity.ok(Map.of("mensaje", "Asociación editada exitosamente."));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("mensaje", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("mensaje", "Error interno."));
        }
    }

    /**
     * Eliminar una asociación existente entre ficha, instructor y clase de formación.
     */
    @DeleteMapping("/eliminarAsociacion")
    public ResponseEntity<Map<String, Object>> eliminarAsociacionFichaInstructorClase(
            @RequestParam String nombreClase,
            @RequestParam Integer numeroFicha,
            @RequestParam String documentoInstructor,
            @RequestParam String jornadaClase) {
        try {
            Integer idClase = claseFormacionDataService.obtenerIdClasePorNombre(nombreClase, jornadaClase);
            if (idClase == null) {
                throw new IllegalArgumentException("Clase de formación no encontrada.");
            }

            Integer idFicha = fichasDataService.obtenerIdFichaPorNumero(numeroFicha);
            if (idFicha == null) {
                throw new IllegalArgumentException("Ficha no encontrada.");
            }

            Integer idInstructor = instructoresDataService.obtenerIdInstructorPorDocumento(documentoInstructor);
            if (idInstructor == null) {
                throw new IllegalArgumentException("Instructor no encontrado.");
            }

            fichasDataService.eliminarAsociacionFichaInstructorClase(idClase, idFicha, idInstructor);
            return ResponseEntity.ok(Map.of("mensaje", "Asociación eliminada exitosamente."));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("mensaje", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("mensaje", "Error interno."));
        }
    }
}

