package com.proyectoasistencia.prasis.controllers.DataTablesControllers;


import com.proyectoasistencia.prasis.services.DataTablesServices.AmbientesDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/Data/Ambientes")
public class AmbientesDataController {

    @Autowired
    private AmbientesDataService ambientesDataService;

    @GetMapping("/todos")
    public List<Map<String, Object>> obtenerTodosLosAmbientes() {
        return ambientesDataService.obtenerTodosLosAmbientes();
    }

    // Crear un nuevo ambiente
    @PostMapping("/crear")
    public ResponseEntity<String> crearAmbiente(@RequestParam String nombreAmbiente) {
        try {
            // Limpiar y evitar duplicados en el valor
            String nombreAmbienteLimpio = nombreAmbiente.trim().replaceAll(",.*", "");
            ambientesDataService.crearAmbiente(nombreAmbienteLimpio);
            return ResponseEntity.ok("Ambiente creado exitosamente.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al crear el ambiente: " + e.getMessage());
        }
    }

    // Actualizar un ambiente existente
    @PutMapping("/actualizar")
    public ResponseEntity<String> actualizarAmbiente(@RequestParam Integer id, @RequestParam String nombreAmbiente) {
        try {
            // Limpiar y evitar duplicados en el valor
            String nombreAmbienteLimpio = nombreAmbiente.trim().replaceAll(",.*", "");
            ambientesDataService.actualizarAmbiente(id, nombreAmbienteLimpio);
            return ResponseEntity.ok("Ambiente actualizado exitosamente.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al actualizar el ambiente: " + e.getMessage());
        }
    }

    // Eliminar un ambiente
    @DeleteMapping("/eliminar")
    public ResponseEntity<String> eliminarAmbiente(@RequestParam Integer id) {
        try {
            ambientesDataService.eliminarAmbiente(id);
            return ResponseEntity.ok("Ambiente eliminado exitosamente.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al eliminar el ambiente: " + e.getMessage());
        }
    }
}

