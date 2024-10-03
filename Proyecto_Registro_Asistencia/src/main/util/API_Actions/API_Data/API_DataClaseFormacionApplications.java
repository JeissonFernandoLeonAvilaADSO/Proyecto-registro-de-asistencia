package main.util.API_Actions.API_Data;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.swing.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class API_DataClaseFormacionApplications {

    public static void crearClaseFormacion(String nombreClase, String documentoInstructor) {
        try {
            // URL del endpoint al que deseas conectarte
            URL url = new URL("http://localhost:8080/Data/ClaseFormacion/crear");

            // Crear la conexión
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            // Establecer el método de solicitud a POST
            conn.setRequestMethod("POST");

            // Habilitar el envío de datos a través de la conexión
            conn.setDoOutput(true);

            // Establecer las propiedades de la solicitud
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");

            // Crear los parámetros de la solicitud
            String urlParameters = "nombreClase=" + URLEncoder.encode(nombreClase, "UTF-8") +
                    "&documentoInstructor=" + URLEncoder.encode(documentoInstructor, "UTF-8");

            // Enviar los parámetros a través del OutputStream
            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = urlParameters.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            // Obtener el código de respuesta
            int responseCode = conn.getResponseCode();
            System.out.println("Código de respuesta: " + responseCode);

            // Leer la respuesta del servidor
            BufferedReader br;
            if (responseCode >= 200 && responseCode < 300) {
                // Respuesta exitosa
                br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            } else {
                // Error en la respuesta
                br = new BufferedReader(new InputStreamReader(conn.getErrorStream(), "UTF-8"));
            }

            String responseLine;
            StringBuilder respuesta = new StringBuilder();
            while ((responseLine = br.readLine()) != null) {
                respuesta.append(responseLine.trim());
            }
            br.close();

            // Mostrar la respuesta del servidor
            System.out.println("Respuesta del servidor: " + respuesta.toString());

        } catch (Exception e) {
            System.out.println("Error al conectarse al servidor: " + e.getMessage());
            e.printStackTrace();
        }
    }


    public static List<Map<String, Object>> obtenerClasesConInstructor() {
        List<Map<String, Object>> listaClases = new ArrayList<>();

        try {
            // Conectar al endpoint
            URL url = new URL("http://localhost:8080/Data/ClaseFormacion/todos");
            HttpURLConnection conexion = (HttpURLConnection) url.openConnection();

            // Leer la respuesta del servidor
            BufferedReader lector = new BufferedReader(new InputStreamReader(conexion.getInputStream(), StandardCharsets.UTF_8));
            StringBuilder respuesta = new StringBuilder();
            String linea;
            while ((linea = lector.readLine()) != null) {
                respuesta.append(linea);
            }
            lector.close();

            // Convertir la respuesta en JSONArray
            JSONArray jsonArray = new JSONArray(respuesta.toString());

            // Convertir cada JSONObject en un Map y agregarlo a la lista
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject objetoJson = jsonArray.getJSONObject(i);
                Map<String, Object> mapa = objetoJson.toMap();
                listaClases.add(mapa);
            }

        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }

        return listaClases;
    }

    public static String actualizarClase(int id, String nombreClase, String documentoInstructor) {
        String responseMessage = "";
        try {
            // Construir la URL con los parámetros como query string
            String urlStr = String.format("http://localhost:8080/Data/ClaseFormacion/actualizar?id=%d&nombreClase=%s&documentoInstructor=%s",
                    id,
                    URLEncoder.encode(nombreClase, "UTF-8"),
                    URLEncoder.encode(documentoInstructor, "UTF-8"));
            URL url = new URL(urlStr);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            // Configurar la conexión para método PUT
            conn.setRequestMethod("PUT");
            conn.setDoOutput(true); // Habilitar envío de datos
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");

            // Enviar los parámetros en el cuerpo de la solicitud (opcional, ya que están en la URL)
            String urlParameters = "";
            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = urlParameters.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            // Obtener la respuesta del servidor
            int responseCode = conn.getResponseCode();
            InputStream is = (responseCode >= 200 && responseCode < 300) ? conn.getInputStream() : conn.getErrorStream();

            BufferedReader br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
            StringBuilder respuesta = new StringBuilder();
            String linea;
            while ((linea = br.readLine()) != null) {
                respuesta.append(linea.trim());
            }
            br.close();

            responseMessage = respuesta.toString();

            // Mostrar mensaje al usuario
            if (responseCode >= 200 && responseCode < 300) {
                JOptionPane.showMessageDialog(null, responseMessage, "Éxito", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, responseMessage, "Error", JOptionPane.ERROR_MESSAGE);
            }

        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error al conectar con el servidor:\n" + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
        return responseMessage;
    }

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

            // Obtener la respuesta del servidor
            int responseCode = conn.getResponseCode();
            InputStream is = (responseCode >= 200 && responseCode < 300) ? conn.getInputStream() : conn.getErrorStream();

            BufferedReader br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
            StringBuilder respuesta = new StringBuilder();
            String linea;
            while ((linea = br.readLine()) != null) {
                respuesta.append(linea.trim());
            }
            br.close();

            responseMessage = respuesta.toString();

            // Mostrar mensaje al usuario
            if (responseCode >= 200 && responseCode < 300) {
                JOptionPane.showMessageDialog(null, responseMessage, "Éxito", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, responseMessage, "Error", JOptionPane.ERROR_MESSAGE);
            }

        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error al conectar con el servidor:\n" + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
        return responseMessage;
    }


}
