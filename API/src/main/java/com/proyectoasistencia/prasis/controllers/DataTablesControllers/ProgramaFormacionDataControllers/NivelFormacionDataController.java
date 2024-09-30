package com.proyectoasistencia.prasis.controllers.DataTablesControllers.ProgramaFormacionDataControllers;

import com.proyectoasistencia.prasis.services.DataTablesServices.ProgramaFormacionDataServices.NivelFormacionDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/Data/NivelFormacion")
public class NivelFormacionDataController {

    @Autowired
    private NivelFormacionDataService nivelFormacionDataService;

    @GetMapping("/todos")
    public List<Map<String, Object>> getAllNivelesFormacion() {
        return nivelFormacionDataService.getAllNivelesFormacion();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getNivelFormacionById(@PathVariable int id) {
        Map<String, Object> nivel = nivelFormacionDataService.getNivelFormacionById(id);
        if (nivel != null) {
            return ResponseEntity.ok(nivel);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Crear un nivel de formación
    @PostMapping("/crear")
    public ResponseEntity<String> crearNivelFormacion(@RequestParam String nombreNivelFormacion) {
        try {
            // Limpiar y evitar duplicados en el valor
            String nombreNivelFormacionLimpio = nombreNivelFormacion.trim().replaceAll(",.*", "");
            nivelFormacionDataService.createNivelFormacion(nombreNivelFormacionLimpio);
            return ResponseEntity.ok("Nivel de formación creado exitosamente.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al crear el nivel de formación: " + e.getMessage());
        }
    }

    // Actualizar un nivel de formación existente
    @PutMapping("/actualizar")
    public ResponseEntity<String> actualizarNivelFormacion(@RequestParam Integer id, @RequestParam String nombreNivelFormacion) {
        try {
            // Limpiar y evitar duplicados en el valor
            String nombreNivelFormacionLimpio = nombreNivelFormacion.trim().replaceAll(",.*", "");
            nivelFormacionDataService.updateNivelFormacion(id, nombreNivelFormacionLimpio);
            return ResponseEntity.ok("Nivel de formación actualizado exitosamente.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al actualizar el nivel de formación: " + e.getMessage());
        }
    }

    // Eliminar un nivel de formación
    @DeleteMapping("/eliminar")
    public ResponseEntity<String> eliminarNivelFormacion(@RequestParam Integer id) {
        try {
            nivelFormacionDataService.deleteNivelFormacion(id);
            return ResponseEntity.ok("Nivel de formación eliminado exitosamente.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al eliminar el nivel de formación: " + e.getMessage());
        }
    }
}

