package com.proyectoasistencia.prasis.controllers.UsersControllers;

import com.proyectoasistencia.prasis.models.UsersModels.AdminModel;
import com.proyectoasistencia.prasis.services.UsersServices.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    // Login de administrador
    @PostMapping("/login")
    public ResponseEntity<AdminModel> loginAdmin(@RequestBody AdminModel adminRequest) {
        try {
            AdminModel admin = adminService.login(adminRequest.getUser(), adminRequest.getPassword());
            return ResponseEntity.ok(admin);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }

    // Obtener todos los administradores
    @GetMapping("/all")
    public List<AdminModel> obtenerTodosLosAdministradores() {
        return adminService.obtenerTodosLosAdministradores();
    }

    // Crear un nuevo administrador
    @PostMapping("/create")
    public ResponseEntity<String> crearAdministrador(@RequestBody AdminModel admin) {
        adminService.crearAdministrador(admin);
        return ResponseEntity.ok("Administrador creado con éxito.");
    }

    // Actualizar un administrador
    @PutMapping("/update/{user}")
    public ResponseEntity<String> actualizarAdministrador(@PathVariable String user, @RequestBody AdminModel admin) {
        adminService.actualizarAdministrador(user, admin);
        return ResponseEntity.ok("Administrador actualizado con éxito.");
    }

    // Eliminar un administrador
    @DeleteMapping("/delete/{user}")
    public ResponseEntity<String> eliminarAdministrador(@PathVariable String user) {
        adminService.eliminarAdministrador(user);
        return ResponseEntity.ok("Administrador eliminado con éxito.");
    }
}
