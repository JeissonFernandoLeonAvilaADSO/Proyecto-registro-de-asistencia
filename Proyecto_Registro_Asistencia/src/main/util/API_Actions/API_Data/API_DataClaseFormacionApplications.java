package main.util.API_Actions.API_Data;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.swing.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class API_DataClaseFormacionApplications {

    /**
     * Crear una nueva clase de formación.
     *
     * @param nombreClase        Nombre de la clase de formación.
     * @param jornadaFormacion   Nombre de la jornada de formación.
     */
    public static void crearClaseFormacion(String nombreClase, String jornadaFormacion) {
        try {
            // URL del endpoint
            URL url = new URL("http://localhost:8080/Data/ClaseFormacion/crear");

            // Crear la conexión
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            // Establecer método POST
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);

            // Establecer propiedades de la solicitud
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");

            // Crear parámetros de la solicitud
            String urlParameters = "nombreClase=" + URLEncoder.encode(nombreClase, "UTF-8") +
                    "&jornadaFormacion=" + URLEncoder.encode(jornadaFormacion, "UTF-8");

            // Enviar parámetros
            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = urlParameters.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            // Obtener código de respuesta
            int responseCode = conn.getResponseCode();
            System.out.println("Código de respuesta: " + responseCode);

            // Leer respuesta
            BufferedReader br;
            if (responseCode >= 200 && responseCode < 300) {
                br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            } else {
                br = new BufferedReader(new InputStreamReader(conn.getErrorStream(), "UTF-8"));
            }

            String responseLine;
            StringBuilder respuesta = new StringBuilder();
            while ((responseLine = br.readLine()) != null) {
                respuesta.append(responseLine.trim());
            }
            br.close();

            // Parsear respuesta JSON
            JSONObject jsonResponse = new JSONObject(respuesta.toString());

            // Obtener mensaje
            String mensaje = jsonResponse.optString("mensaje", "No se recibió mensaje del servidor.");

            // Mostrar mensaje al usuario
            if (responseCode >= 200 && responseCode < 300) {
                JOptionPane.showMessageDialog(null, mensaje, "Éxito", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
            }

            // Imprimir respuesta completa en consola
            System.out.println("Respuesta del servidor: " + respuesta.toString());

        } catch (Exception e) {
            System.out.println("Error al conectarse al servidor: " + e.getMessage());
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al conectarse al servidor:\n" + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Actualizar una clase de formación existente.
     *
     * @param id                  ID de la clase de formación.
     * @param nombreClase         Nuevo nombre de la clase de formación.
     * @param jornadaFormacion    Nuevo nombre de la jornada de formación.
     * @return Mensaje de respuesta del servidor.
     */
    public static String actualizarClase(int id, String nombreClase, String jornadaFormacion) {
        String responseMessage = "";
        try {
            // URL del endpoint
            URL url = new URL("http://localhost:8080/Data/ClaseFormacion/actualizar");

            // Crear la conexión
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            // Establecer método PUT
            conn.setRequestMethod("PUT");
            conn.setDoOutput(true);

            // Establecer propiedades de la solicitud
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");

            // Crear parámetros de la solicitud
            String urlParameters = "id=" + URLEncoder.encode(String.valueOf(id), "UTF-8") +
                    "&nombreClase=" + URLEncoder.encode(nombreClase, "UTF-8") +
                    "&jornadaFormacion=" + URLEncoder.encode(jornadaFormacion, "UTF-8");

            // Enviar parámetros
            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = urlParameters.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            // Obtener código de respuesta
            int responseCode = conn.getResponseCode();
            System.out.println("Código de respuesta: " + responseCode);

            // Leer respuesta
            BufferedReader br;
            if (responseCode >= 200 && responseCode < 300) {
                br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            } else {
                br = new BufferedReader(new InputStreamReader(conn.getErrorStream(), "UTF-8"));
            }

            String responseLine;
            StringBuilder respuesta = new StringBuilder();
            while ((responseLine = br.readLine()) != null) {
                respuesta.append(responseLine.trim());
            }
            br.close();

            // Parsear respuesta JSON
            JSONObject jsonResponse = new JSONObject(respuesta.toString());

            // Obtener mensaje
            String mensaje = jsonResponse.optString("mensaje", "No se recibió mensaje del servidor.");

            // Mostrar mensaje al usuario
            if (responseCode >= 200 && responseCode < 300) {
                JOptionPane.showMessageDialog(null, mensaje, "Éxito", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
            }

            // Imprimir respuesta completa en consola
            System.out.println("Respuesta del servidor: " + respuesta.toString());

            responseMessage = mensaje;

        } catch (Exception e) {
            System.out.println("Error al actualizar clase: " + e.getMessage());
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al actualizar clase:\n" + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
        return responseMessage;
    }

    /**
     * Eliminar una clase de formación existente.
     *
     * @param id ID de la clase de formación a eliminar.
     * @return Mensaje de respuesta del servidor.
     */
    public static String eliminarClase(int id) {
        String responseMessage = "";
        try {
            // Construir la URL con el parámetro id como query string
            String urlStr = String.format("http://localhost:8080/Data/ClaseFormacion/eliminar?id=%d", id);
            URL url = new URL(urlStr);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            // Configurar la conexión para método DELETE
            conn.setRequestMethod("DELETE");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");

            // Obtener el código de respuesta
            int responseCode = conn.getResponseCode();
            System.out.println("Código de respuesta: " + responseCode);

            // Leer la respuesta del servidor
            BufferedReader br;
            if (responseCode >= 200 && responseCode < 300) {
                br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            } else {
                br = new BufferedReader(new InputStreamReader(conn.getErrorStream(), "UTF-8"));
            }

            String responseLine;
            StringBuilder respuesta = new StringBuilder();
            while ((responseLine = br.readLine()) != null) {
                respuesta.append(responseLine.trim());
            }
            br.close();

            // Parsear la respuesta JSON
            JSONObject jsonResponse = new JSONObject(respuesta.toString());

            // Obtener mensaje
            String mensaje = jsonResponse.optString("mensaje", "No se recibió mensaje del servidor.");

            // Manejar mensajes de error específicos
            if (responseCode >= 200 && responseCode < 300) {
                JOptionPane.showMessageDialog(null, mensaje, "Éxito", JOptionPane.INFORMATION_MESSAGE);
                responseMessage = mensaje;
            } else {
                // Verificar si el mensaje indica una violación de integridad referencial
                if (mensaje.toLowerCase().contains("relaciones pendientes") ||
                        mensaje.toLowerCase().contains("constraint") ||
                        mensaje.toLowerCase().contains("foreign key")) {
                    // Devolver el mensaje personalizado
                    JOptionPane.showMessageDialog(null, "Error: No se pudo eliminar el dato porque cuenta con relaciones pendientes.", "Error", JOptionPane.ERROR_MESSAGE);
                    responseMessage = "Error: No se pudo eliminar el dato porque cuenta con relaciones pendientes.";
                } else {
                    // Devolver el mensaje de error original
                    JOptionPane.showMessageDialog(null, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
                    responseMessage = "Error HTTP " + responseCode + ": " + mensaje;
                }
            }

            // Imprimir respuesta completa en consola
            System.out.println("Respuesta del servidor: " + respuesta.toString());

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al eliminar clase:\n" + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
        return responseMessage;
    }

    /**
     * Obtener todas las clases de formación.
     *
     * @return Lista de mapas que representan las clases de formación.
     */
    public static List<Map<String, Object>> obtenerTodasLasClases() {
        List<Map<String, Object>> listaClases = new ArrayList<>();

        try {
            // Conectar al endpoint
            URL url = new URL("http://localhost:8080/Data/ClaseFormacion/todas");
            HttpURLConnection conexion = (HttpURLConnection) url.openConnection();

            // Configurar la conexión para método GET
            conexion.setRequestMethod("GET");
            conexion.setRequestProperty("Accept", "application/json");

            // Obtener el código de respuesta
            int responseCode = conexion.getResponseCode();
            System.out.println("Código de respuesta: " + responseCode);

            // Leer la respuesta del servidor
            InputStream is;
            if (responseCode >= 200 && responseCode < 300) {
                is = conexion.getInputStream();
            } else {
                is = conexion.getErrorStream();
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
            StringBuilder respuesta = new StringBuilder();
            String linea;
            while ((linea = br.readLine()) != null) {
                respuesta.append(linea.trim());
            }
            br.close();

            // Parsear la respuesta JSON
            JSONObject jsonResponse = new JSONObject(respuesta.toString());

            // Obtener el mensaje
            String mensaje = jsonResponse.optString("mensaje", "No se recibió mensaje del servidor.");
            System.out.println("Mensaje del servidor: " + mensaje);

            // Obtener los datos
            JSONArray dataArray = jsonResponse.optJSONArray("data");
            if (dataArray != null) {
                for (int i = 0; i < dataArray.length(); i++) {
                    JSONObject objetoJson = dataArray.getJSONObject(i);
                    Map<String, Object> mapa = objetoJson.toMap();
                    listaClases.add(mapa);
                }
            } else {
                JOptionPane.showMessageDialog(null, "No se recibió ningún dato del servidor.", "Información", JOptionPane.INFORMATION_MESSAGE);
            }

            // Mostrar el mensaje al usuario
            if (responseCode >= 200 && responseCode < 300) {
                JOptionPane.showMessageDialog(null, mensaje, "Éxito", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
            }

            // Mostrar la respuesta completa en consola
            System.out.println("Respuesta del servidor: " + respuesta.toString());

        } catch (Exception e) {
            System.out.println("Error al conectarse al servidor: " + e.getMessage());
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al conectarse al servidor:\n" + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }

        return listaClases;
    }
}
