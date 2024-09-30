package com.proyectoasistencia.prasis.controllers.DataTablesControllers;

import com.proyectoasistencia.prasis.services.DataTablesServices.RolDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/Data/Rol")
public class RolDataController {

    @Autowired
    private RolDataService rolDataService;

    // Obtener todos los roles
    @GetMapping("/todos")
    public ResponseEntity<List<Map<String, Object>>> obtenerTodosLosRoles() {
        List<Map<String, Object>> roles = rolDataService.obtenerTodosLosRoles();
        return ResponseEntity.ok(roles);
    }

    // Crear un nuevo rol
    @PostMapping("/crear")
    public ResponseEntity<String> crearRol(@RequestParam String nombreRol) {
        try {
            // Limpiar posibles duplicados en el valor recibido
            String nombreRolLimpio = nombreRol.trim().replaceAll(",.*", "");  // Eliminar cualquier repetición o duplicación después de una coma

            System.out.println("Rol Recibido (Limpio): " + nombreRolLimpio);
            rolDataService.crearRol(nombreRolLimpio);

            return ResponseEntity.ok("Rol creado exitosamente.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al crear el rol: " + e.getMessage());
        }
    }

    // Actualizar un rol existente
    @PutMapping("/actualizar")
    public ResponseEntity<String> actualizarRol(@RequestParam Integer id, @RequestParam String nombreRol) {
        try {
            // Limpiar posibles duplicados en el valor recibido
            String nombreRolLimpio = nombreRol.trim().replaceAll(",.*", "");  // Eliminar cualquier repetición o duplicación después de una coma

            System.out.println("Rol Recibido (Limpio): " + nombreRolLimpio);
            rolDataService.actualizarRol(id, nombreRolLimpio);

            return ResponseEntity.ok("Rol actualizado exitosamente.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al actualizar el rol: " + e.getMessage());
        }
    }

    // Eliminar un rol
    @DeleteMapping("/eliminar")
    public ResponseEntity<String> eliminarRol(@RequestParam Integer id) {
        try {
            rolDataService.eliminarRol(id);
            return ResponseEntity.ok("Rol eliminado exitosamente.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al eliminar el rol: " + e.getMessage());
        }
    }
}
