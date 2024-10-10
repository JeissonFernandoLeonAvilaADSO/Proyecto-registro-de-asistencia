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
    // URL base de tu API. Asegúrate de ajustarla según tu configuración.
    private static final String BASE_URL = "http://localhost:8080/Data/ProgramaFormacion";

    /**
     * Método para crear un nuevo Programa de Formación.
     *
     * @param programaFormacion  Programa de formación (String).
     * @param centroFormacion    Centro de formación (String).
     * @param nivelFormacion     Nivel de formación (String).
     * @param area               Área (String).
     * @return Respuesta del servidor como String.
     */
    public static String crearProgramaFormacion(String programaFormacion, String centroFormacion,
                                                String nivelFormacion, String area) {

        String responseMessage = "";
        try {
            // Validaciones básicas
            if (programaFormacion == null || programaFormacion.trim().isEmpty()) {
                throw new IllegalArgumentException("El campo 'ProgramaFormacion' no puede estar vacío.");
            }
            if (centroFormacion == null || centroFormacion.trim().isEmpty()) {
                throw new IllegalArgumentException("El campo 'CentroFormacion' no puede estar vacío.");
            }
            if (nivelFormacion == null || nivelFormacion.trim().isEmpty()) {
                throw new IllegalArgumentException("El campo 'NivelFormacion' no puede estar vacío.");
            }
            if (area == null || area.trim().isEmpty()) {
                throw new IllegalArgumentException("El campo 'Área' no puede estar vacío.");
            }

            // Construir el Map con los datos necesarios
            Map<String, Object> programa = new HashMap<>();
            programa.put("ProgramaFormacion", programaFormacion);
            programa.put("CentroFormacion", centroFormacion);
            programa.put("NivelFormacion", nivelFormacion);
            programa.put("Area", area);

            // Convertir el Map a JSONObject
            JSONObject jsonProgram = new JSONObject(programa);

            // Crear la conexión al endpoint
            URL url = new URL(BASE_URL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            // Configurar la conexión para método POST
            conn.setRequestMethod("POST");
            conn.setDoOutput(true); // Habilitar envío de datos
            conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");

            // Escribir el JSON en el cuerpo de la solicitud
            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = jsonProgram.toString().getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
                os.flush();
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

            // Manejar la respuesta según el código de estado
            if (responseCode == HttpURLConnection.HTTP_OK || responseCode == HttpURLConnection.HTTP_CREATED) {
                JOptionPane.showMessageDialog(null, "Programa de Formación creado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Error al crear el Programa de Formación: " + responseMessage, "Error", JOptionPane.ERROR_MESSAGE);
            }

        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(null, "Validación de datos: " + e.getMessage(), "Error de Validación", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        } catch (IOException | JSONException e) {
            JOptionPane.showMessageDialog(null, "Error al conectar con el servidor:\n" + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
        return responseMessage;
    }

    /**
     * Método para actualizar un Programa de Formación existente.
     *
     * @param id                 ID del Programa de Formación a actualizar (int).
     * @param programaFormacion  Nuevo programa de formación (String).
     * @param centroFormacion    Nuevo centro de formación (String).
     * @param nivelFormacion     Nuevo nivel de formación (String).
     * @param area               Nueva área (String).
     * @return Respuesta del servidor como String.
     */
    public static String actualizarProgramaFormacion(int id, String programaFormacion, String centroFormacion,
                                                     String nivelFormacion, String area) {
        String endpoint = "/" + id;
        String responseMessage = "";
        try {
            // Validaciones básicas
            if (id <= 0) {
                throw new IllegalArgumentException("El ID del Programa de Formación debe ser un valor positivo.");
            }
            if (programaFormacion == null || programaFormacion.trim().isEmpty()) {
                throw new IllegalArgumentException("El campo 'ProgramaFormacion' no puede estar vacío.");
            }
            if (centroFormacion == null || centroFormacion.trim().isEmpty()) {
                throw new IllegalArgumentException("El campo 'CentroFormacion' no puede estar vacío.");
            }
            if (nivelFormacion == null || nivelFormacion.trim().isEmpty()) {
                throw new IllegalArgumentException("El campo 'NivelFormacion' no puede estar vacío.");
            }
            if (area == null || area.trim().isEmpty()) {
                throw new IllegalArgumentException("El campo 'Área' no puede estar vacío.");
            }

            // Construir el Map con los datos necesarios
            Map<String, Object> programa = new HashMap<>();
            programa.put("ProgramaFormacion", programaFormacion);
            programa.put("CentroFormacion", centroFormacion);
            programa.put("NivelFormacion", nivelFormacion);
            programa.put("Area", area);

            // Convertir el Map a JSONObject
            JSONObject jsonProgram = new JSONObject(programa);

            // Crear la conexión al endpoint
            URL url = new URL(BASE_URL + endpoint);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            // Configurar la conexión para método PUT
            conn.setRequestMethod("PUT");
            conn.setDoOutput(true); // Habilitar envío de datos
            conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");

            // Escribir el JSON en el cuerpo de la solicitud
            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = jsonProgram.toString().getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
                os.flush();
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

            // Manejar la respuesta según el código de estado
            if (responseCode == HttpURLConnection.HTTP_OK) {
                JOptionPane.showMessageDialog(null, "Programa de Formación actualizado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            } else if (responseCode == HttpURLConnection.HTTP_NOT_FOUND) {
                JOptionPane.showMessageDialog(null, "Programa de Formación no encontrado.", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Error al actualizar el Programa de Formación: " + responseMessage, "Error", JOptionPane.ERROR_MESSAGE);
            }

        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(null, "Validación de datos: " + e.getMessage(), "Error de Validación", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        } catch (IOException | JSONException e) {
            JOptionPane.showMessageDialog(null, "Error al conectar con el servidor:\n" + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
        return responseMessage;
    }

    /**
     * Método para eliminar un Programa de Formación existente.
     *
     * @param idProgramaFormacion ID del Programa de Formación a eliminar (int).
     * @return Respuesta del servidor como String.
     */
    public static String eliminarProgramaFormacion(int idProgramaFormacion) {
        String endpoint = "/" + idProgramaFormacion;
        String responseMessage = "";
        try {
            // Validaciones básicas
            if (idProgramaFormacion <= 0) {
                throw new IllegalArgumentException("El ID del Programa de Formación debe ser un valor positivo.");
            }

            // Construir la URL con el ID del Programa de Formación
            URL url = new URL(BASE_URL + endpoint);
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

            if (responseCode >= 200 && responseCode < 300) {
                JOptionPane.showMessageDialog(null, "Ficha eliminada exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                return responseMessage.toString();
            } else {
                // Analizar el mensaje de error para detectar violación de integridad referencial
                if (responseMessage.contains("constraint") || responseMessage.contains("foreign key")) {
                    // Devolver el mensaje personalizado
                    JOptionPane.showMessageDialog(null, "Error: No se pudo eliminar el programa de formacion porque cuenta con relaciones pendientes.", "Error", JOptionPane.ERROR_MESSAGE);
                    return "Error: No se pudo eliminar el dato porque cuenta con relaciones pendientes.";
                } else {
                    // Devolver el mensaje de error original
                    return "Error HTTP " + responseCode + ": " + responseMessage;
                }
            }

        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(null, "Validación de datos: " + e.getMessage(), "Error de Validación", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error al conectar con el servidor:\n" + e.getMessage(),
                    "Error de Conexión", JOptionPane.ERROR_MESSAGE);
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
