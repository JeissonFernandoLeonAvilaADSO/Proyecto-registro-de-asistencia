package com.proyectoasistencia.prasis.controllers.DataTablesControllers;

import com.proyectoasistencia.prasis.services.DataTablesServices.ClaseFormacionDataService;
import com.proyectoasistencia.prasis.services.DataTablesServices.FichasDataService;
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

    // Endpoint para obtener las fichas por nombre del programa de formación
    @GetMapping("/porPrograma")
    public ResponseEntity<List<Map<String, Object>>> obtenerFichasPorPrograma(@RequestParam String nombrePrograma) {
        try {
            // Decodificar el nombre del programa en caso de que venga codificado
            String nombreProgramaDecodificado = URLDecoder.decode(nombrePrograma, StandardCharsets.UTF_8.name());
            System.out.println("Programa decodificado en el backend: " + nombreProgramaDecodificado);

            // Llamar al servicio para obtener las fichas por el programa de formación
            List<Map<String, Object>> fichas = fichasDataService.obtenerFichasPorPrograma(nombreProgramaDecodificado);

            // Retornar la lista de fichas en el response
            return ResponseEntity.ok(fichas);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Collections.emptyList());
        } catch (Exception e) {
            System.out.println("Error al obtener las fichas por programa: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.emptyList());
        }
    }

    // Obtener todas las fichas por documento del instructor
    @GetMapping("/instructor/{documento}")
    public ResponseEntity<List<Map<String, Object>>> obtenerFichasPorInstructor(@PathVariable String documento) {
        try {
            // Decodificar el documento en caso de que venga codificado
            String documentoDecodificado = URLDecoder.decode(documento, StandardCharsets.UTF_8.name());

            // Llamar al servicio para obtener las fichas por el documento del instructor
            List<Map<String, Object>> fichas = fichasDataService.obtenerFichasPorDocumentoInstructor(documentoDecodificado);

            // Retornar la lista de fichas en el response
            return ResponseEntity.ok(fichas);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.singletonList(Map.of("mensaje", e.getMessage())));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.singletonList(Map.of("error", "Error al obtener las fichas por instructor: " + e.getMessage())));
        }
    }

    /**
     * Endpoint para asociar una ficha con una clase de formación.
     *
     * @param numeroFicha El número de la ficha.
     * @param nombreClase El nombre de la clase de formación.
     * @return ResponseEntity con mensaje de éxito o error.
     */
    @PostMapping("/asociarFichaClase")
    public ResponseEntity<String> asociarFichaConClase(@RequestParam Integer numeroFicha,
                                                       @RequestParam String nombreClase) {
        System.out.println("Asociando Ficha Número " + numeroFicha + " con Clase de Formación '" + nombreClase + "'");
        try {
            // Obtener el ID de la ficha
            Integer idFicha = fichasDataService.obtenerIdFichaPorNumero(numeroFicha);
            System.out.println("ID de la ficha obtenida: " + idFicha);

            // Obtener el ID de la clase de formación
            Integer idClaseFormacion = claseFormacionDataService.obtenerIdClasePorNombre(nombreClase);
            System.out.println("ID de la clase de formación obtenida: " + idClaseFormacion);

            // Asociar la ficha con la clase de formación
            fichasDataService.asociarFichaConClase(idClaseFormacion, idFicha);
            System.out.println("Ficha asociada exitosamente a la clase de formación.");

            return ResponseEntity.ok("Ficha asociada exitosamente a la clase de formación.");
        } catch (IllegalArgumentException e) {
            System.out.println("Error al asociar ficha con clase de formación: " + e.getMessage());
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error inesperado al asociar ficha con clase de formación: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error inesperado: " + e.getMessage());
        }
    }

    // Método para editar la relación entre una ficha y una clase de formación
    @PutMapping("/editarAsociacion")
    public ResponseEntity<String> editarAsociacionFichaAClase(@RequestParam String nombreClaseAnterior,
                                                              @RequestParam String nombreClaseNueva,
                                                              @RequestParam Integer numeroFichaAnterior,
                                                              @RequestParam Integer numeroFichaNuevo) {
        try {
            // Obtener los IDs de las clases y fichas anteriores y nuevas
            Integer idClaseAnterior = claseFormacionDataService.obtenerIdClasePorNombre(nombreClaseAnterior);
            Integer idClaseNueva = claseFormacionDataService.obtenerIdClasePorNombre(nombreClaseNueva);
            Integer idFichaAnterior = fichasDataService.obtenerIdFichaPorNumero(numeroFichaAnterior);
            Integer idFichaNueva = fichasDataService.obtenerIdFichaPorNumero(numeroFichaNuevo);

            if (idClaseAnterior == null || idClaseNueva == null || idFichaAnterior == null || idFichaNueva == null) {
                return ResponseEntity.badRequest().body("No se pudo encontrar la clase de formación o la ficha especificada.");
            }

            // Editar la relación entre la clase de formación y la ficha
            fichasDataService.editarAsociacionFichaAClase(idClaseAnterior, idClaseNueva, idFichaAnterior, idFichaNueva);
            return ResponseEntity.ok("Asociación de la ficha con la clase de formación editada exitosamente.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al editar la asociación: " + e.getMessage());
        }
    }

    // Método para eliminar la relación entre una ficha y una clase de formación
    @DeleteMapping("/eliminarAsociacion")
    public ResponseEntity<String> eliminarAsociacionFichaAClase(@RequestParam String nombreClase,
                                                                @RequestParam Integer numeroFicha) {
        try {
            // Obtener IDs de la clase anterior y la ficha anterior
            Integer idClase = claseFormacionDataService.obtenerIdClasePorNombre(nombreClase);
            Integer idFicha = fichasDataService.obtenerIdFichaPorNumero(numeroFicha);

            if (idClase == null || idFicha == null) {
                return ResponseEntity.badRequest().body("No se pudo encontrar la clase de formación o la ficha especificada.");
            }

            // Eliminar la relación
            fichasDataService.eliminarAsociacionFichaAClase(idClase, idFicha);
            return ResponseEntity.ok("Asociación eliminada exitosamente.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al eliminar la asociación: " + e.getMessage());
        }
    }
}

