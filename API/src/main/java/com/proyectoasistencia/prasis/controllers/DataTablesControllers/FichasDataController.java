package com.proyectoasistencia.prasis.controllers.DataTablesControllers;

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

    // Obtener todas las fichas
    @GetMapping("/todos")
    public ResponseEntity<List<Map<String, Object>>> obtenerTodasLasFichas() {
        System.out.println("Obteniendo Fichas");
        List<Map<String, Object>> fichas = fichasDataService.obtenerTodasLasFichas();
        System.out.println(fichas);
        return ResponseEntity.ok(fichas);
    }

    // Crear una nueva ficha
    @PostMapping("/crear")
    public ResponseEntity<String> crearFicha(@RequestParam Integer numeroFicha, @RequestParam String programaFormacion) {
        try {
            Integer DecodeNumeroFicha = Integer.parseInt(URLDecoder.decode(String.valueOf(numeroFicha), StandardCharsets.UTF_8.name()));
            String DecodeProgramaFormacion = URLDecoder.decode(programaFormacion, StandardCharsets.UTF_8.name());
            fichasDataService.crearFicha(DecodeNumeroFicha, DecodeProgramaFormacion);
            return ResponseEntity.ok("Ficha creada exitosamente.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al crear la ficha: " + e.getMessage());
        }
    }

    // Actualizar una ficha existente
    @PutMapping("/actualizar")
    public ResponseEntity<String> actualizarFicha(@RequestParam Integer id,
                                                  @RequestParam Integer numeroFicha,
                                                  @RequestParam String programaFormacion) {
        try {
            fichasDataService.actualizarFicha(id, numeroFicha, programaFormacion);
            return ResponseEntity.ok("Ficha actualizada exitosamente.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al actualizar la ficha: " + e.getMessage());
        }
    }

    // Eliminar una ficha
    @DeleteMapping("/eliminar")
    public ResponseEntity<String> eliminarFicha(@RequestParam Integer id) {
        try {
            fichasDataService.eliminarFicha(id);
            return ResponseEntity.ok("Ficha eliminada exitosamente.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al eliminar la ficha: " + e.getMessage());
        }
    }

    @GetMapping("/programaFormacion/{numeroFicha}")
    public ResponseEntity<Map<String, Object>> obtenerProgramaFormacionPorFicha(@PathVariable Integer numeroFicha) {
        try {
            Map<String, Object> programaFormacion = fichasDataService.obtenerProgramaFormacionPorFicha(numeroFicha);
            return ResponseEntity.ok(programaFormacion);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.singletonMap("error", e.getMessage()));
        }
    }

    // Endpoint para obtener las fichas por nombre del programa de formación
    @GetMapping("/porPrograma")
    public ResponseEntity<List<Map<String, Object>>> obtenerFichasPorPrograma(@RequestParam String nombrePrograma) {
        try {
            // Decodificar el nombre del programa en caso de que venga codificado
            String nombreProgramaDecodificado = URLDecoder.decode(nombrePrograma, StandardCharsets.UTF_8.toString());
            System.out.println("Programa decodificado en el backend: " + nombreProgramaDecodificado);

            // Llamar al servicio para obtener las fichas por el programa de formación
            List<Map<String, Object>> fichas = fichasDataService.obtenerFichasPorPrograma(nombreProgramaDecodificado);

            // Retornar la lista de fichas en el response
            return ResponseEntity.ok(fichas);
        } catch (Exception e) {
            System.out.println("Error al obtener las fichas por programa: " + e.getMessage());
            return ResponseEntity.badRequest().body(null); // O puedes devolver un mensaje de error más descriptivo
        }
    }

    // Obtener todas las fichas por documento del instructor
    @GetMapping("/instructor/{documento}")
    public ResponseEntity<List<Map<String, Object>>> obtenerFichasPorInstructor(@PathVariable String documento) {
        try {
            // Llamar al servicio para obtener las fichas por el documento del instructor
            List<Map<String, Object>> fichas = fichasDataService.obtenerFichasPorDocumentoInstructor(documento);

            if (fichas.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.singletonList(Map.of("mensaje", "No se encontraron fichas para el instructor con documento: " + documento)));
            }

            return ResponseEntity.ok(fichas);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Collections.singletonList(Map.of("error", e.getMessage())));
        }
    }
}

