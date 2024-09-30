package com.proyectoasistencia.prasis.controllers.DataTablesControllers.ResidenciaDataControllers;

import com.proyectoasistencia.prasis.services.DataTablesServices.ResidenciaDataService.DepartamentoDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/Data/Departamentos")
public class DepartamentoDataController {

    @Autowired
    private DepartamentoDataService departamentoDataService;

    @GetMapping("/todos")
    public List<Map<String, Object>> obtenerTodosLosDepartamentos() {
        return departamentoDataService.obtenerTodosLosDepartamentos();
    }

    // Crear un nuevo departamento
    @PostMapping("/crear")
    public ResponseEntity<String> crearDepartamento(@RequestParam String nombreDepartamento) {
        try {
            // Limpiar y evitar duplicados en el valor
            String nombreDepartamentoLimpio = nombreDepartamento.trim().replaceAll(",.*", "");
            departamentoDataService.crearDepartamento(nombreDepartamentoLimpio);
            return ResponseEntity.ok("Departamento creado exitosamente.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al crear el departamento: " + e.getMessage());
        }
    }

    // Actualizar un departamento existente
    @PutMapping("/actualizar")
    public ResponseEntity<String> actualizarDepartamento(@RequestParam Integer id, @RequestParam String nombreDepartamento) {
        try {
            // Limpiar y evitar duplicados en el valor
            String nombreDepartamentoLimpio = nombreDepartamento.trim().replaceAll(",.*", "");
            departamentoDataService.actualizarDepartamento(id, nombreDepartamentoLimpio);
            return ResponseEntity.ok("Departamento actualizado exitosamente.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al actualizar el departamento: " + e.getMessage());
        }
    }

    // Eliminar un departamento
    @DeleteMapping("/eliminar")
    public ResponseEntity<String> eliminarDepartamento(@RequestParam Integer id) {
        try {
            departamentoDataService.eliminarDepartamento(id);
            return ResponseEntity.ok("Departamento eliminado exitosamente.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al eliminar el departamento: " + e.getMessage());
        }
    }
}
