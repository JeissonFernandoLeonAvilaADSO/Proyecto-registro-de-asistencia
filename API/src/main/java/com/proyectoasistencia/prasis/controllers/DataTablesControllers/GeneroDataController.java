package com.proyectoasistencia.prasis.controllers.DataTablesControllers;

import com.proyectoasistencia.prasis.services.DataTablesServices.GeneroDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/Data/Genero")
public class GeneroDataController {

    @Autowired
    private GeneroDataService generoDataService;

    // Obtener todos los géneros
    @GetMapping("/todos")
    public ResponseEntity<List<Map<String, Object>>> obtenerTodosLosGeneros() {
        List<Map<String, Object>> generos = generoDataService.obtenerTodosLosGeneros();
        return ResponseEntity.ok(generos);
    }

    // Crear un nuevo género
    @PostMapping("/crear")
    public ResponseEntity<String> crearGenero(@RequestParam String nombreGenero) {
        try {
            // Limpiar posibles duplicados en el valor recibido
            String nombreGeneroLimpio = nombreGenero.trim().replaceAll(",.*", "");  // Eliminar cualquier repetición o duplicación después de una coma
            generoDataService.crearGenero(nombreGeneroLimpio);
            return ResponseEntity.ok("Género creado exitosamente.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al crear el género: " + e.getMessage());
        }
    }

    // Actualizar un género existente
    @PutMapping("/actualizar")
    public ResponseEntity<String> actualizarGenero(@RequestParam Integer id, @RequestParam String nombreGenero) {
        try {
            // Limpiar posibles duplicados en el valor recibido
            String nombreGeneroLimpio = nombreGenero.trim().replaceAll(",.*", "");  // Eliminar cualquier repetición o duplicación después de una coma
            generoDataService.actualizarGenero(id, nombreGeneroLimpio);
            return ResponseEntity.ok("Género actualizado exitosamente.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al actualizar el género: " + e.getMessage());
        }
    }

    // Eliminar un género
    @DeleteMapping("/eliminar")
    public ResponseEntity<String> eliminarGenero(@RequestParam Integer id) {
        try {
            generoDataService.eliminarGenero(id);
            return ResponseEntity.ok("Género eliminado exitosamente.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al eliminar el género: " + e.getMessage());
        }
    }
}

