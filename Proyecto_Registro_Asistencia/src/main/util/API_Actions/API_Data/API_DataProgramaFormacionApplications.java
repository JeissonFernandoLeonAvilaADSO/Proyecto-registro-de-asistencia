package main.util.API_Actions.API_Data;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.swing.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class API_DataProgramaFormacionApplications {
    public static String crearProgramaFormacion(String programaFormacion, String centroFormacion,
                                                String jornadasFormacion, String nivelFormacion, String area) {
        String responseMessage = "";
        try {
            // Construir el Map con los datos necesarios
            Map<String, Object> programa = new HashMap<>();
            programa.put("ProgramaFormacion", programaFormacion);
            programa.put("CentroFormacion", centroFormacion);
            programa.put("JornadasFormacion", jornadasFormacion);
            programa.put("NivelFormacion", nivelFormacion);
            programa.put("Area", area);

            // Convertir el Map a JSONObject
            JSONObject jsonProgram = new JSONObject(programa);

            // Crear la conexión al endpoint
            URL url = new URL("http://localhost:8080/Data/ProgramaFormacion");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            // Configurar la conexión para método POST
            conn.setRequestMethod("POST");
            conn.setDoOutput(true); // Habilitar envío de datos
            conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");

            // Escribir el JSON en el cuerpo de la solicitud
            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = jsonProgram.toString().getBytes(StandardCharsets.UTF_8);
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
                System.out.println(responseMessage);
//                JOptionPane.showMessageDialog(null, responseMessage, "Error", JOptionPane.ERROR_MESSAGE);
            }

        } catch (IOException | JSONException e) {
            JOptionPane.showMessageDialog(null, "Error al conectar con el servidor:\n" + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
        return responseMessage;
    }

    // Método para actualizar un Programa de Formación existente
    public static String actualizarProgramaFormacion(int id, String programaFormacion, String centroFormacion,
                                                     String jornadasFormacion, String nivelFormacion, String area) {
        String responseMessage = "";
        try {
            // Construir el Map con los datos necesarios
            Map<String, Object> programa = new HashMap<>();
            programa.put("ProgramaFormacion", programaFormacion);
            programa.put("CentroFormacion", centroFormacion);
            programa.put("JornadasFormacion", jornadasFormacion);
            programa.put("NivelFormacion", nivelFormacion);
            programa.put("Area", area);

            // Convertir el Map a JSONObject
            JSONObject jsonProgram = new JSONObject(programa);

            // Crear la conexión al endpoint con el ID en la URL
            URL url = new URL("http://localhost:8080/Data/ProgramaFormacion/" + id);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            // Configurar la conexión para método PUT
            conn.setRequestMethod("PUT");
            conn.setDoOutput(true); // Habilitar envío de datos
            conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");

            // Escribir el JSON en el cuerpo de la solicitud
            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = jsonProgram.toString().getBytes(StandardCharsets.UTF_8);
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
            } else if (responseCode == HttpURLConnection.HTTP_NOT_FOUND) {
                JOptionPane.showMessageDialog(null, "Programa de formación no encontrado.", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, responseMessage, "Error", JOptionPane.ERROR_MESSAGE);
            }

        } catch (IOException | JSONException e) {
            JOptionPane.showMessageDialog(null, "Error al conectar con el servidor:\n" + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
        return responseMessage;
    }

    public static String eliminarProgramaFormacion(int idProgramaFormacion) {
        String responseMessage = "";
        try {
            // Construir la URL con el ID del Programa de Formación
            String urlStr = String.format("http://localhost:8080/Data/ProgramaFormacion/%d", idProgramaFormacion);
            URL url = new URL(urlStr);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            // Configurar la conexión para método DELETE
            conn.setRequestMethod("DELETE");
            conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");

            // No se envían parámetros en el cuerpo para DELETE
            conn.setDoOutput(false);

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

            // Mostrar mensaje al usuario según el código de respuesta
            if (responseCode >= 200 && responseCode < 300) {
                JOptionPane.showMessageDialog(null, responseMessage, "Éxito", JOptionPane.INFORMATION_MESSAGE);
            } else if (responseCode == HttpURLConnection.HTTP_NOT_FOUND) {
                JOptionPane.showMessageDialog(null, "Programa de formación no encontrado.", "Error", JOptionPane.ERROR_MESSAGE);
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


    public static List<Map<String, Object>> obtenerProgramasFormacion() {
        List<Map<String, Object>> listaProgramas = new ArrayList<>();

        try {
            URL url = new URL("http://localhost:8080/Data/ProgramaFormacion/todos"); // Asegúrate de que este endpoint exista
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            // Leer la respuesta completa usando BufferedReader
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8));
            StringBuilder respuesta = new StringBuilder();
            String linea;
            while ((linea = reader.readLine()) != null) {
                respuesta.append(linea);
            }
            reader.close();

            // Convertir la respuesta en JSONArray
            JSONArray jsonArray = new JSONArray(respuesta.toString());

            // Convertir cada JSONObject en un Map y agregarlo a la lista
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject objetoJson = jsonArray.getJSONObject(i);
                Map<String, Object> mapa = objetoJson.toMap();
                listaProgramas.add(mapa);
            }

        } catch (IOException | JSONException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al obtener los programas de formación:\n" + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }

        return listaProgramas;
    }
}
