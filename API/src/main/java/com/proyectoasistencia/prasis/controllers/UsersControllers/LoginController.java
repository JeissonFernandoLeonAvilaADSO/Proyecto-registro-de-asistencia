package com.proyectoasistencia.prasis.controllers.UsersControllers;

import com.proyectoasistencia.prasis.services.UsersServices.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping
    public ResponseEntity<Map<String, Object>> login(@RequestBody Map<String, String> loginRequest) {
        try {
            String user = loginRequest.get("user"); // Nombre de usuario
            String password = loginRequest.get("password");
            String role = loginRequest.get("role");

            // Depuración: Verificando datos de la solicitud
            System.out.println("Recibido login request. Usuario: " + user + ", Rol: " + role);

            Map<String, Object> loginResponse = loginService.login(user, password, role);

            // Depuración: Login exitoso
            System.out.println("Login exitoso para el usuario: " + user);

            return ResponseEntity.ok(loginResponse);

        } catch (RuntimeException e) {
            // Depuración: Error en login
            System.err.println("Error en el login: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", e.getMessage()));
        }
    }
}
