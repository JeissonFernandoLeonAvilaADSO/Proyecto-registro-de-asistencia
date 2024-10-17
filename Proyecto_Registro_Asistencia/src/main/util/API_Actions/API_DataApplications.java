package main.util.API_Actions;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class API_DataApplications {

    protected String enviarDatoAAPI(String endpoint, String accion, String nombreCampo, String valor, Integer id) {
        try {
            // Definir el método HTTP basado en la acción
            String requestMethod;
            String urlString = "http://localhost:8080/Data/" + endpoint + "/" + accion;
            String params = "";

            switch (accion.toLowerCase()) {
                case "crear":
                    requestMethod = "POST";
                    params = nombreCampo + "=" + URLEncoder.encode(valor, "UTF-8");
                    break;
                case "actualizar":
                    requestMethod = "PUT";
                    params = "id=" + id + "&" + nombreCampo + "=" + URLEncoder.encode(valor, "UTF-8");
                    break;
                case "eliminar":
                    requestMethod = "DELETE";
                    urlString += "?id=" + id;  // Eliminar solo necesita pasar el ID en la URL
                    break;
                default:
                    throw new IllegalArgumentException("Acción no soportada: " + accion);
            }

            // Imprimir los valores antes del envío para depuración
            System.out.println("Valor antes de codificar: " + valor);
            System.out.println("URL generada: " + urlString);
            System.out.println("Parámetros enviados: " + params);

            // Abrir la conexión
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod(requestMethod);
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setDoOutput(true); // Necesario para enviar datos en POST y PUT

            // Solo para POST y PUT: enviar los datos en el cuerpo de la solicitud
            if (requestMethod.equals("POST") || requestMethod.equals("PUT")) {
                try (OutputStream os = conn.getOutputStream()) {
                    byte[] input = params.getBytes(StandardCharsets.UTF_8);
                    os.write(input, 0, input.length);
                    // Imprimir los datos enviados al servidor
                    System.out.println("Datos enviados al servidor: " + new String(input, StandardCharsets.UTF_8));
                }
            }

            // Verificar la respuesta del servidor
            int responseCode = conn.getResponseCode();
            if (responseCode != HttpURLConnection.HTTP_OK) {
                throw new RuntimeException("Error HTTP: " + responseCode);
            }

            // Leer la respuesta
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                response.append(line);
            }

            // Imprimir la respuesta del servidor
            System.out.println("Respuesta del servidor: " + response.toString());

            conn.disconnect();
            // Manejar las respuestas según el código HTTP
            if (responseCode >= 200 && responseCode < 300) {
                switch (accion.toLowerCase()) {
                    case "crear":
                        JOptionPane.showMessageDialog(null, "Dato creado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                        break;
                    case "actualizar":
                        JOptionPane.showMessageDialog(null, "Dato actualizado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                        break;
                    case "eliminar":
                        JOptionPane.showMessageDialog(null, "Dato eliminado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                        break;
                }
                return response.toString();
            } else {
                // Manejo de errores en caso de una respuesta HTTP no exitosa
                String errorMessage = response.toString();
                System.out.println("Error al procesar la solicitud: " + errorMessage);
                if (errorMessage.contains("integridad referencial")) {
                    JOptionPane.showMessageDialog(null, "No se pudo completar la acción porque existen relaciones pendientes.", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "Error: " + errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
                }
                return "Error HTTP " + responseCode + ": " + errorMessage;
            }
        } catch (IOException e) {
            // Manejo de excepciones al conectar con la API
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error de conexión: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return "Error de conexión: " + e.getMessage();
        } catch (Exception e) {
            // Manejo de cualquier otra excepción
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "No se pudo completar la acción porque el dato aun tiene relaciones pendientes.", "Error", JOptionPane.ERROR_MESSAGE);
            return "Error inesperado: " + e.getMessage();
        }
    }
}