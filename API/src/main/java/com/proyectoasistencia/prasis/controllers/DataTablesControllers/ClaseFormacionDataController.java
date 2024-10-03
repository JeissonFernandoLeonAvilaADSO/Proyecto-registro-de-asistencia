package com.proyectoasistencia.prasis.controllers.DataTablesControllers;

import com.proyectoasistencia.prasis.services.DataTablesServices.ClaseFormacionDataService;
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

    // Obtener todas las clases de formación
    @GetMapping("/todas")
    public ResponseEntity<List<Map<String, Object>>> obtenerTodasLasClases() {
        System.out.println("Obteniendo todas las clases de formación...");
        List<Map<String, Object>> clases = claseFormacionDataService.obtenerTodasLasClases();
        System.out.println("Clases obtenidas: " + clases);
        return ResponseEntity.ok(clases);
    }

    // Obtener el ID de una clase por nombre
    @GetMapping("/buscarPorNombre")
    public ResponseEntity<Integer> obtenerIdClasePorNombre(@RequestParam String nombreClase) {
        System.out.println("Buscando ID de la clase: " + nombreClase);
        Integer idClase = claseFormacionDataService.obtenerIdClasePorNombre(nombreClase);
        System.out.println("ID de la clase " + nombreClase + ": " + idClase);
        return ResponseEntity.ok(idClase);
    }

    @GetMapping("/todos")
    public List<Map<String, Object>> obtenerClasesConInstructor() {
        System.out.println("Obteniendo clases con su instructor...");
        List<Map<String, Object>> clasesConInstructor = claseFormacionDataService.obtenerClasesConInstructor();
        System.out.println("Clases con instructor: " + clasesConInstructor);
        return clasesConInstructor;
    }

//    // Obtener instructores para la ComboBox
//    @GetMapping("/Instructores/todos")
//    public List<Map<String, Object>> obtenerInstructoresParaComboBox() {
//        System.out.println("Obteniendo instructores para la ComboBox...");
//        List<Map<String, Object>> instructores = claseFormacionDataService.obtenerInstructoresParaComboBox();
//        System.out.println("Instructores obtenidos: " + instructores);
//        return instructores;
//    }

    // Crear una nueva clase de formación
    @PostMapping("/crear")
    public ResponseEntity<String> crearClase(@RequestParam String nombreClase, @RequestParam String documentoInstructor) {
        System.out.println("Creando clase: " + nombreClase + ", Instructor: " + documentoInstructor);
        try {
            claseFormacionDataService.crearClase(nombreClase, documentoInstructor);
            System.out.println("Clase creada exitosamente.");
            return ResponseEntity.ok("Clase creada exitosamente.");
        } catch (Exception e) {
            System.out.println("Error al crear la clase: " + e.getMessage());
            return ResponseEntity.badRequest().body("Error al crear la clase: " + e.getMessage());
        }
    }

    // Actualizar una clase de formación existente
    @PutMapping("/actualizar")
    public ResponseEntity<String> actualizarClase(@RequestParam Integer id,
                                                  @RequestParam String nombreClase,
                                                  @RequestParam String documentoInstructor) {
        System.out.println("Actualizando clase con ID: " + id + ", Nombre: " + nombreClase + ", Instructor: " + documentoInstructor);
        try {
            claseFormacionDataService.actualizarClase(id, nombreClase, documentoInstructor);
            System.out.println("Clase actualizada exitosamente.");
            return ResponseEntity.ok("Clase actualizada exitosamente.");
        } catch (Exception e) {
            System.out.println("Error al actualizar la clase: " + e.getMessage());
            return ResponseEntity.badRequest().body("Error al actualizar la clase: " + e.getMessage());
        }
    }

    // Eliminar una clase de formación
    @DeleteMapping("/eliminar")
    public ResponseEntity<String> eliminarClase(@RequestParam Integer id) {
        System.out.println("Eliminando clase con ID: " + id);
        try {
            claseFormacionDataService.eliminarClase(id);
            System.out.println("Clase eliminada exitosamente.");
            return ResponseEntity.ok("Clase eliminada exitosamente.");
        } catch (Exception e) {
            System.out.println("Error al eliminar la clase: " + e.getMessage());
            return ResponseEntity.badRequest().body("Error al eliminar la clase: " + e.getMessage());
        }
    }

    @GetMapping("/porDocumentoInstructor")
    public ResponseEntity<Map<String, Object>> obtenerClasePorDocumentoInstructor(@RequestParam String documentoInstructor) {
        try {
            Map<String, Object> claseFormacion = claseFormacionDataService.obtenerClasePorDocumentoInstructor(documentoInstructor);
            if (claseFormacion != null) {
                return ResponseEntity.ok(claseFormacion);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}

