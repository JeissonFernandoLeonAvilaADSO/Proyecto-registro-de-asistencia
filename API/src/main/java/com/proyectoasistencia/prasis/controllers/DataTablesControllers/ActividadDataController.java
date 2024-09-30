package com.proyectoasistencia.prasis.controllers.DataTablesControllers;

import com.proyectoasistencia.prasis.services.DataTablesServices.ActividadDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/Data/Actividades")
public class ActividadDataController {

    @Autowired
    private ActividadDataService actividadDataService;

    @GetMapping("/todos")
    public List<Map<String, Object>> obtenerTodasLasActividades() {
        return actividadDataService.obtenerTodasLasActividades();
    }

    @PostMapping("/crear")
    public ResponseEntity<String> crearActividad(@RequestParam String nombreActividad) {
        try {
            // Limpiar y evitar duplicados en el valor
            String tipoActividadLimpio = nombreActividad.trim().replaceAll(",.*", "");
            actividadDataService.crearActividad(tipoActividadLimpio);
            return ResponseEntity.ok("Actividad creada exitosamente.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al crear la actividad: " + e.getMessage());
        }
    }

    // Actualizar una actividad existente
    @PutMapping("/actualizar")
    public ResponseEntity<String> actualizarActividad(@RequestParam Integer id, @RequestParam String nombreActividad) {
        try {
            // Limpiar y evitar duplicados en el valor
            String tipoActividadLimpio = nombreActividad.trim().replaceAll(",.*", "");
            actividadDataService.actualizarActividad(id, tipoActividadLimpio);
            return ResponseEntity.ok("Actividad actualizada exitosamente.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al actualizar la actividad: " + e.getMessage());
        }
    }

    // Eliminar una actividad
    @DeleteMapping("/eliminar")
    public ResponseEntity<String> eliminarActividad(@RequestParam Integer id) {
        try {
            actividadDataService.eliminarActividad(id);
            return ResponseEntity.ok("Actividad eliminada exitosamente.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al eliminar la actividad: " + e.getMessage());
        }
    }
}

