/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.util.API_Login;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import main.util.models.UserSession;
import org.json.JSONObject;


/**
 *
 * @author IZHAR
 */

import org.json.JSONObject;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;


public class API_Login_Instructor_Admin {

    public boolean LogUser(String username, String password, String role) {
        try {
            if (role.equalsIgnoreCase("instructor")) {
                // Para el rol "Instructor", proceder con el flujo normal
                String jsonInputString = String.format(
                        "{\"user\": \"%s\", \"password\": \"%s\", \"role\": \"%s\"}",
                        URLEncoder.encode(username, StandardCharsets.UTF_8.toString()),
                        URLEncoder.encode(password, StandardCharsets.UTF_8.toString()),
                        role
                );

                // Depuración: imprimir el cuerpo de la solicitud JSON
                System.out.println("Cuerpo de la solicitud: " + jsonInputString);

                URL url = new URL("http://localhost:8080/login");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Accept", "application/json");
                conn.setRequestProperty("Content-Type", "application/json; utf-8");
                conn.setDoOutput(true);

                try (OutputStream os = conn.getOutputStream()) {
                    byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);
                    os.write(input, 0, input.length);
                }

                // Depuración: verificar el código de respuesta del servidor
                int responseCode = conn.getResponseCode();
                System.out.println("Código de respuesta: " + responseCode);

                InputStream is;
                if (responseCode >= 200 && responseCode < 300) {
                    is = conn.getInputStream();
                } else {
                    is = conn.getErrorStream();
                }

                BufferedReader br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
                StringBuilder responseBuilder = new StringBuilder();
                String linea;
                while ((linea = br.readLine()) != null) {
                    responseBuilder.append(linea);
                }

                String responseBody = responseBuilder.toString();

                // Depuración: imprimir la respuesta completa del servidor
                System.out.println("Respuesta del servidor: " + responseBody);

                if (responseCode == 200) {
                    JSONObject jsonResponse = new JSONObject(responseBody);

                    // Solo manejar la sesión si el rol es "Instructor"
                    if (jsonResponse.getString("Role").equalsIgnoreCase("Instructor")) {
                        UserSession userSession = UserSession.getInstance();

                        userSession.setNombres(jsonResponse.getString("FullName"));
                        userSession.setTipoDoc(jsonResponse.getString("TipoDocumento"));
                        userSession.setDocumento(jsonResponse.getString("Documento"));
                        userSession.setRole(jsonResponse.getString("Role"));
                        userSession.setClaseFormacion(jsonResponse.getString("ClaseFormacion"));

                        // Depuración: imprimir los datos de la sesión
                        System.out.println("Datos de sesión: " + userSession);
                    }

                    return true; // El login fue exitoso
                } else if (responseCode == HttpURLConnection.HTTP_UNAUTHORIZED) {
                    // Manejar el error específico de falta de asociación a clase de formación
                    if (responseBody.contains("No se encontró ninguna clase para el instructor con documento")) {
                        // Mostrar diálogo específico para falta de asociación
                        JOptionPane.showMessageDialog(null,
                                "No se encontró ninguna clase asociada a tu cuenta.\nPor favor, solicita que te asocien a una clase de formación.",
                                "Asociación Necesaria",
                                JOptionPane.WARNING_MESSAGE);
                    } else {
                        // Manejar otros errores de autenticación
                        JOptionPane.showMessageDialog(null,
                                "Error en el login: " + responseBody,
                                "Error de Autenticación",
                                JOptionPane.ERROR_MESSAGE);
                    }
                    return false; // El login falló
                } else {
                    // Manejar otros códigos de respuesta no exitosos
                    JOptionPane.showMessageDialog(null,
                            "Código de respuesta: " + responseCode + "\nError: " + responseBody,
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                    return false; // El login falló
                }
            } else if (role.equalsIgnoreCase("admin")) {
                // Si es "Admin", usar el flujo específico de administrador
                System.out.println("Autenticando como Admin");
                API_Login_Admin loginAdmin = new API_Login_Admin();
                System.out.println("usuario: " + username);
                System.out.println("pass: " + password);
                boolean isAdminLoggedIn = loginAdmin.LogAdmin(username, password);
                System.out.println("Resultado de login de Admin: " + isAdminLoggedIn);
                return isAdminLoggedIn;
            }
        } catch (Exception e) {
            // Depuración: imprimir traza de la excepción
            System.out.println("Excepción durante la autenticación: ");
            e.printStackTrace();
        }
        return false;
    }
}