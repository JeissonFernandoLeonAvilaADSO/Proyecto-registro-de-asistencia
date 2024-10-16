package main.util.API_Actions.API_Data;


import org.json.JSONArray;
import org.json.JSONObject;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import javax.swing.JOptionPane;

public class API_DataInstructorFichaClaseApplications {

    /**
     * Asociar una ficha con un instructor y una clase de formación.
     */
    public static void asociarFichaInstructorClase(String nombreClase, Integer numeroFicha, String documentoInstructor, String jornadaClase) {
        try {
            // URL del endpoint
            URL url = new URL("http://localhost:8080/Data/Fichas/asociarFichaInstructorClase");

            // Crear la conexión
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            // Establecer método POST
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);

            // Establecer propiedades de la solicitud
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");

            // Crear parámetros de la solicitud
            String urlParameters = "nombreClase=" + URLEncoder.encode(nombreClase, "UTF-8") +
                    "&numeroFicha=" + URLEncoder.encode(numeroFicha.toString(), "UTF-8") +
                    "&documentoInstructor=" + URLEncoder.encode(documentoInstructor, "UTF-8") +
                    "&jornadaClase=" + URLEncoder.encode(jornadaClase, "UTF-8");

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
            System.out.println("Error al asociar ficha, instructor y clase de formación: " + e.getMessage());
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al asociar ficha, instructor y clase de formación:\n" + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Editar una asociación existente entre ficha, instructor y clase de formación.
     */
    public static void editarAsociacionFichaInstructorClase(String nombreClaseAnterior, String nombreClaseNueva,
                                                            Integer numeroFichaAnterior, Integer numeroFichaNuevo,
                                                            String documentoInstructorAnterior, String documentoInstructorNuevo,
                                                            String jornadaClase) {
        try {
            // URL del endpoint
            URL url = new URL("http://localhost:8080/Data/Fichas/editarAsociacion");

            // Crear la conexión
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            // Establecer método PUT
            conn.setRequestMethod("PUT");
            conn.setDoOutput(true);

            // Establecer propiedades de la solicitud
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");

            // Crear parámetros de la solicitud
            String urlParameters = "nombreClaseAnterior=" + URLEncoder.encode(nombreClaseAnterior, "UTF-8") +
                    "&nombreClaseNueva=" + URLEncoder.encode(nombreClaseNueva, "UTF-8") +
                    "&numeroFichaAnterior=" + URLEncoder.encode(numeroFichaAnterior.toString(), "UTF-8") +
                    "&numeroFichaNuevo=" + URLEncoder.encode(numeroFichaNuevo.toString(), "UTF-8") +
                    "&documentoInstructorAnterior=" + URLEncoder.encode(documentoInstructorAnterior, "UTF-8") +
                    "&documentoInstructorNuevo=" + URLEncoder.encode(documentoInstructorNuevo, "UTF-8") +
                    "&jornadaClase=" + URLEncoder.encode(jornadaClase, "UTF-8");

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
            System.out.println("Error al editar asociación: " + e.getMessage());
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al editar asociación:\n" + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Eliminar una asociación existente entre ficha, instructor y clase de formación.
     */
    public static void eliminarAsociacionFichaInstructorClase(String nombreClase, Integer numeroFicha, String documentoInstructor, String jornadaClase) {
        try {
            // Construir la URL con los parámetros
            String urlParameters = "nombreClase=" + URLEncoder.encode(nombreClase, "UTF-8") +
                    "&numeroFicha=" + URLEncoder.encode(numeroFicha.toString(), "UTF-8") +
                    "&documentoInstructor=" + URLEncoder.encode(documentoInstructor, "UTF-8") +
                    "&jornadaClase=" + URLEncoder.encode(jornadaClase, "UTF-8");
            URL url = new URL("http://localhost:8080/Data/Fichas/eliminarAsociacion?" + urlParameters);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            // Configurar la conexión para método DELETE
            conn.setRequestMethod("DELETE");
            conn.setRequestProperty("Accept", "application/json");

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
            } else {
                // Verificar si el mensaje indica una violación de integridad referencial
                if (mensaje.toLowerCase().contains("relaciones pendientes") ||
                        mensaje.toLowerCase().contains("constraint") ||
                        mensaje.toLowerCase().contains("foreign key")) {
                    // Devolver el mensaje personalizado
                    JOptionPane.showMessageDialog(null, "Error: No se pudo eliminar el dato porque cuenta con relaciones pendientes.", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    // Devolver el mensaje de error original
                    JOptionPane.showMessageDialog(null, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
                }
            }

            // Imprimir respuesta completa en consola
            System.out.println("Respuesta del servidor: " + respuesta.toString());

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al eliminar asociación:\n" + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    /**
     * Obtener todas las asociaciones entre fichas, instructores y clases de formación.
     */
    public static void obtenerAsociaciones() {
        try {
            // URL del endpoint
            URL url = new URL("http://localhost:8080/Data/Fichas/obtenerAsociaciones");

            // Crear la conexión
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            // Establecer método GET
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

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

            // Obtener datos
            JSONArray dataArray = jsonResponse.optJSONArray("data");

            if (dataArray != null) {
                StringBuilder asociaciones = new StringBuilder();
                for (int i = 0; i < dataArray.length(); i++) {
                    JSONObject asociacion = dataArray.getJSONObject(i);
                    asociaciones.append("Ficha ID: ").append(asociacion.getInt("IDFicha"))
                            .append(", Instructor ID: ").append(asociacion.getInt("IDInstructor"))
                            .append(", Clase ID: ").append(asociacion.getInt("IDClaseFormacion"))
                            .append("\n");
                }
                JOptionPane.showMessageDialog(null, asociaciones.toString(), "Asociaciones", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, mensaje, "Información", JOptionPane.INFORMATION_MESSAGE);
            }

            // Imprimir respuesta completa en consola
            System.out.println("Respuesta del servidor: " + respuesta.toString());

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al obtener asociaciones:\n" + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
}

