package com.proyectoasistencia.prasis.controllers.DataTablesControllers.ProgramaFormacionDataControllers;

import com.proyectoasistencia.prasis.services.DataTablesServices.ProgramaFormacionDataServices.AreaDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/Data/Areas")
public class AreaDataController {

    @Autowired
    private AreaDataService areaDataService;

    @GetMapping("/todos")
    public List<Map<String, Object>> getAllAreas() {
        return areaDataService.getAllAreas();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getAreaById(@PathVariable int id) {
        Map<String, Object> area = areaDataService.getAreaById(id);
        if (area != null) {
            return ResponseEntity.ok(area);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Crear un área
    @PostMapping("/crear")
    public ResponseEntity<String> crearArea(@RequestParam String nombreArea) {
        try {
            // Limpiar y evitar duplicados en el valor
            String nombreAreaLimpio = nombreArea.trim().replaceAll(",.*", "");
            areaDataService.createArea(nombreAreaLimpio);
            return ResponseEntity.ok("Área creada exitosamente.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al crear el área: " + e.getMessage());
        }
    }

    // Actualizar un área existente
    @PutMapping("/actualizar")
    public ResponseEntity<String> actualizarArea(@RequestParam Integer id, @RequestParam String nombreArea) {
        try {
            // Limpiar y evitar duplicados en el valor
            String nombreAreaLimpio = nombreArea.trim().replaceAll(",.*", "");
            areaDataService.updateArea(id, nombreAreaLimpio);
            return ResponseEntity.ok("Área actualizada exitosamente.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al actualizar el área: " + e.getMessage());
        }
    }

    // Eliminar un área
    @DeleteMapping("/eliminar")
    public ResponseEntity<String> eliminarArea(@RequestParam Integer id) {
        try {
            areaDataService.deleteArea(id);
            return ResponseEntity.ok("Área eliminada exitosamente.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al eliminar el área: " + e.getMessage());
        }
    }
}
