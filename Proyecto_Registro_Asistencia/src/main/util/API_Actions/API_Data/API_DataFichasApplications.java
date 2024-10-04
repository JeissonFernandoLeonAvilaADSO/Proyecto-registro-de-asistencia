package main.util.API_Actions.API_Data;

import javax.swing.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class API_DataFichasApplications {
    // URL base de tu API. Asegúrate de ajustarla según tu configuración.
    private static final String BASE_URL = "http://localhost:8080/Data/Fichas";
    /**
     * Método para crear una nueva ficha.
     *
     * @param numeroFicha      Número de la ficha (Integer).
     * @param programaFormacion Programa de formación (String).
     * @return Respuesta del servidor como String.
     */
    public static String crearFicha(Integer numeroFicha, String programaFormacion) {
        String endpoint = "/crear";
        try {
            System.out.println(numeroFicha);
            System.out.println(programaFormacion);
            // Construir los parámetros URL-encoded
            StringBuilder urlParameters = new StringBuilder();
            urlParameters.append("numeroFicha=").append(URLEncoder.encode(numeroFicha.toString(), "UTF-8"));
            urlParameters.append("&");
            urlParameters.append("programaFormacion=").append(URLEncoder.encode(programaFormacion, "UTF-8"));

            // Crear la URL completa
            URL url = new URL(BASE_URL + endpoint);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            // Configurar la conexión
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
            conn.setRequestProperty("Content-Length", String.valueOf(urlParameters.toString().getBytes().length));
            System.out.println(urlParameters);

            // Escribir los parámetros en el cuerpo de la solicitud
            try (OutputStream os = conn.getOutputStream()) {
                os.write(urlParameters.toString().getBytes(StandardCharsets.UTF_8));
                os.flush();
            }

            // Leer la respuesta del servidor
            InputStream is;
            if (conn.getResponseCode() >= 200 && conn.getResponseCode() < 300) {
                is = conn.getInputStream();
            } else {
                is = conn.getErrorStream();
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
            StringBuilder response = new StringBuilder();
            String linea;
            while ((linea = br.readLine()) != null) {
                response.append(linea.trim());
            }
            br.close();

            // Manejar la respuesta según el código de estado
            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                JOptionPane.showMessageDialog(null, "Ficha creada exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Error al crear la ficha: " + response.toString(), "Error", JOptionPane.ERROR_MESSAGE);
            }

            return response.toString();

        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error de conexión: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Método para actualizar una ficha existente.
     *
     * @param id                ID de la ficha a actualizar (Integer).
     * @param numeroFicha       Nuevo número de la ficha (Integer).
     * @param programaFormacion Nuevo programa de formación (String).
     * @return Respuesta del servidor como String.
     */
    public static String actualizarFicha(Integer id, Integer numeroFicha, String programaFormacion) {
        String endpoint = "/actualizar";
        try {
            // Construir los parámetros URL-encoded
            StringBuilder urlParameters = new StringBuilder();
            urlParameters.append("id=").append(URLEncoder.encode(id.toString(), "UTF-8"));
            urlParameters.append("&");
            urlParameters.append("numeroFicha=").append(URLEncoder.encode(numeroFicha.toString(), "UTF-8"));
            urlParameters.append("&");
            urlParameters.append("programaFormacion=").append(URLEncoder.encode(programaFormacion, "UTF-8"));

            // Crear la URL completa
            URL url = new URL(BASE_URL + endpoint);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            // Configurar la conexión
            conn.setRequestMethod("PUT");
            conn.setDoOutput(true);
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
            conn.setRequestProperty("Content-Length", String.valueOf(urlParameters.toString().getBytes().length));

            // Escribir los parámetros en el cuerpo de la solicitud
            try (OutputStream os = conn.getOutputStream()) {
                os.write(urlParameters.toString().getBytes(StandardCharsets.UTF_8));
                os.flush();
            }

            // Leer la respuesta del servidor
            InputStream is;
            if (conn.getResponseCode() >= 200 && conn.getResponseCode() < 300) {
                is = conn.getInputStream();
            } else {
                is = conn.getErrorStream();
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
            StringBuilder response = new StringBuilder();
            String linea;
            while ((linea = br.readLine()) != null) {
                response.append(linea.trim());
            }
            br.close();

            // Manejar la respuesta según el código de estado
            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                JOptionPane.showMessageDialog(null, "Ficha actualizada exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Error al actualizar la ficha: " + response.toString(), "Error", JOptionPane.ERROR_MESSAGE);
            }

            return response.toString();

        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error de conexión: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Método para eliminar una ficha existente.
     *
     * @param id ID de la ficha a eliminar (Integer).
     * @return Respuesta del servidor como String.
     */
    public static String eliminarFicha(Integer id) {
        String endpoint = "/eliminar";
        try {
            // Construir los parámetros URL-encoded
            StringBuilder urlParameters = new StringBuilder();
            urlParameters.append("id=").append(URLEncoder.encode(id.toString(), "UTF-8"));

            // Crear la URL completa con los parámetros en la URL
            URL url = new URL(BASE_URL + endpoint + "?" + urlParameters.toString());
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            // Configurar la conexión
            conn.setRequestMethod("DELETE");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
            // Nota: En una solicitud DELETE, generalmente no se envía cuerpo. Los parámetros se pasan en la URL.

            // Leer la respuesta del servidor
            InputStream is;
            if (conn.getResponseCode() >= 200 && conn.getResponseCode() < 300) {
                is = conn.getInputStream();
            } else {
                is = conn.getErrorStream();
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
            StringBuilder response = new StringBuilder();
            String linea;
            while ((linea = br.readLine()) != null) {
                response.append(linea.trim());
            }
            br.close();

            // Manejar la respuesta según el código de estado
            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                JOptionPane.showMessageDialog(null, "Ficha eliminada exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Error al eliminar la ficha: " + response.toString(), "Error", JOptionPane.ERROR_MESSAGE);
            }

            return response.toString();

        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error de conexión: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            return null;
        }
    }
}
