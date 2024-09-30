package com.proyectoasistencia.prasis.controllers.DataTablesControllers.ProgramaFormacionDataControllers;

import com.proyectoasistencia.prasis.services.DataTablesServices.ProgramaFormacionDataServices.JornadaFormacionDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/Data/JornadaFormacion")
public class JornadaFormacionDataController {

    @Autowired
    private JornadaFormacionDataService jornadaFormacionDataService;

    @GetMapping("/todos")
    public List<Map<String, Object>> getAllJornadasFormacion() {
        return jornadaFormacionDataService.getAllJornadasFormacion();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getJornadaFormacionById(@PathVariable int id) {
        Map<String, Object> jornada = jornadaFormacionDataService.getJornadaFormacionById(id);
        if (jornada != null) {
            return ResponseEntity.ok(jornada);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Crear una nueva jornada de formación
    @PostMapping("/crear")
    public ResponseEntity<String> createJornadaFormacion(@RequestParam String nombreJornada) {
        try {
            // Limpiar el valor recibido
            String jornadasFormacionLimpio = nombreJornada.trim().replaceAll(",.*", "");
            jornadaFormacionDataService.createJornadaFormacion(jornadasFormacionLimpio);
            System.out.println(jornadasFormacionLimpio);
            return ResponseEntity.ok("Jornada de formación creada exitosamente.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al crear la jornada de formación: " + e.getMessage());
        }
    }

    // Actualizar una jornada de formación existente
    @PutMapping("/actualizar")
    public ResponseEntity<String> updateJornadaFormacion(@RequestParam Integer id, @RequestParam String nombreJornada) {
        try {
            // Limpiar el valor recibido
            String jornadasFormacionLimpio = nombreJornada.trim().replaceAll(",.*", "");
            jornadaFormacionDataService.updateJornadaFormacion(id, jornadasFormacionLimpio);
            return ResponseEntity.ok("Jornada de formación actualizada exitosamente.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al actualizar la jornada de formación: " + e.getMessage());
        }
    }

    // Eliminar una jornada de formación
    @DeleteMapping("/eliminar")
    public ResponseEntity<String> deleteJornadaFormacion(@RequestParam Integer id) {
        try {
            jornadaFormacionDataService.deleteJornadaFormacion(id);
            return ResponseEntity.ok("Jornada de formación eliminada exitosamente.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al eliminar la jornada de formación: " + e.getMessage());
        }
    }
}

