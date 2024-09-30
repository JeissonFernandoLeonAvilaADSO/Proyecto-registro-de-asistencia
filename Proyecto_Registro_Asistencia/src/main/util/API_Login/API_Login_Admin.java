/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.util.API_Login;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * Clase para verificar el inicio de sesión de un administrador en la base de datos.
 * @author JeissonLeon
 */
public class API_Login_Admin {

    public boolean LogAdmin(String userAdmin, String passAdmin) {
        try {
            // Construir el cuerpo de la solicitud con el formato JSON
            String jsonInputString = String.format(
                    "{\"user\": \"%s\", \"password\": \"%s\", \"role\": \"%s\"}",
                    URLEncoder.encode(userAdmin, StandardCharsets.UTF_8.toString()),
                    URLEncoder.encode(passAdmin, StandardCharsets.UTF_8.toString()),
                    "admin" // el rol es "admin"
            );

            // Depuración: imprime el cuerpo de la solicitud
            System.out.println("Cuerpo de la solicitud: " + jsonInputString);

            // Crear la URL y abrir la conexión HTTP
            URL url = new URL("http://localhost:8080/login");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Accept", "application/json");
            conn.setRequestProperty("Content-Type", "application/json; utf-8");
            conn.setDoOutput(true);

            // Enviar el cuerpo de la solicitud
            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            // Leer la respuesta del servidor
            int responseCode = conn.getResponseCode();
            System.out.println("Código de respuesta: " + responseCode);

            if (responseCode == 200) {
                // Si la respuesta es exitosa, leer el cuerpo de la respuesta
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8))) {
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }
                    // Depuración: imprime la respuesta
                    System.out.println("Respuesta del servidor: " + response.toString());

                    // Aquí puedes procesar la respuesta según tus necesidades
                    return true; // El login fue exitoso
                }
            } else {
                System.out.println("Error al consultar el controlador. Código de respuesta: " + responseCode);
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false; // Retornar false si ocurre algún error
        }
    }
}



