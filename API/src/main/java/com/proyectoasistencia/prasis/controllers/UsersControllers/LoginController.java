package com.proyectoasistencia.prasis.controllers.UsersControllers;

import com.proyectoasistencia.prasis.services.UsersServices.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping
    public ResponseEntity<Map<String, Object>> login(@RequestBody Map<String, String> loginRequest) {
        try {
            // Obtener los valores codificados
            String encodedUser = loginRequest.get("user");
            String encodedPassword = loginRequest.get("password");
            String role = loginRequest.get("role");

            // Decodificar los valores
            String user = URLDecoder.decode(encodedUser, StandardCharsets.UTF_8.name());
            String password = URLDecoder.decode(encodedPassword, StandardCharsets.UTF_8.name());

            // Depuración: Verificando datos de la solicitud
            System.out.println("Recibido login request. Usuario: " + user + ", Rol: " + role);

            Map<String, Object> loginResponse = loginService.login(user, password, role);

            // Depuración: Login exitoso
            System.out.println("Login exitoso para el usuario: " + user);

            return ResponseEntity.ok(loginResponse);

        } catch (UnsupportedEncodingException e) {
            // Depuración: Error en la decodificación
            System.err.println("Error en la decodificación de datos: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", "Error en la decodificación de datos."));
        } catch (RuntimeException e) {
            // Depuración: Error en login
            System.err.println("Error en el login: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", e.getMessage()));
        }
    }
}
