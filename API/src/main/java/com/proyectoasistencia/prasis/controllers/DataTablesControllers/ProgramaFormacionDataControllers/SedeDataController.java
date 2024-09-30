package com.proyectoasistencia.prasis.controllers.DataTablesControllers.ProgramaFormacionDataControllers;

import com.proyectoasistencia.prasis.services.DataTablesServices.ProgramaFormacionDataServices.SedeDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/Data/Sede")
public class SedeDataController {

    @Autowired
    private SedeDataService sedeDataService;

    @GetMapping("/todos")
    public List<Map<String, Object>> getAllSedes() {
        return sedeDataService.getAllSedes();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getSedeById(@PathVariable int id) {
        Map<String, Object> sede = sedeDataService.getSedeById(id);
        if (sede != null) {
            return ResponseEntity.ok(sede);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @PostMapping("/crear")
    public ResponseEntity<String> createSede(@RequestParam String nombreSede) {
        try {
            // Limpiar el valor recibido
            String nombreSedeLimpio = nombreSede.trim().replaceAll(",.*", "");
            sedeDataService.createSede(nombreSedeLimpio);
            return ResponseEntity.ok("Sede creada exitosamente.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al crear la sede: " + e.getMessage());
        }
    }

    @PutMapping("/actualizar")
    public ResponseEntity<String> updateSede(@RequestParam Integer id, @RequestParam String nombreSede) {
        try {
            String nombreSedeLimpio = nombreSede.trim().replaceAll(",.*", "");
            sedeDataService.updateSede(id, nombreSedeLimpio);
            return ResponseEntity.ok("Sede actualizada exitosamente.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al actualizar la sede: " + e.getMessage());
        }
    }

    @DeleteMapping("/eliminar")
    public ResponseEntity<String> deleteSede(@RequestParam Integer id) {
        try {
            sedeDataService.deleteSede(id);
            return ResponseEntity.ok("Sede eliminada exitosamente.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al eliminar la sede: " + e.getMessage());
        }
    }
}

