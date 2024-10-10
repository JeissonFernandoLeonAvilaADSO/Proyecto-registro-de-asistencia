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
     * @param numeroFicha        Número de la ficha (Integer).
     * @param programaFormacion  Programa de formación (String).
     * @param jornadaFormacion   Jornada de formación (String).
     * @return Respuesta del servidor como String.
     */
    public static String crearFicha(Integer numeroFicha, String programaFormacion, String jornadaFormacion) {
        String endpoint = "/crear";
        try {
            // Validaciones básicas
            if (numeroFicha == null || numeroFicha <= 0) {
                throw new IllegalArgumentException("El número de ficha debe ser un valor positivo y no nulo.");
            }
            if (programaFormacion == null || programaFormacion.trim().isEmpty()) {
                throw new IllegalArgumentException("El campo 'ProgramaFormacion' no puede estar vacío.");
            }
            if (jornadaFormacion == null || jornadaFormacion.trim().isEmpty()) {
                throw new IllegalArgumentException("El campo 'JornadaFormacion' no puede estar vacío.");
            }

            // Construir los parámetros URL-encoded
            StringBuilder urlParameters = new StringBuilder();
            urlParameters.append("numeroFicha=").append(URLEncoder.encode(numeroFicha.toString(), "UTF-8"));
            urlParameters.append("&");
            urlParameters.append("programaFormacion=").append(URLEncoder.encode(programaFormacion, "UTF-8"));
            urlParameters.append("&");
            urlParameters.append("jornadaFormacion=").append(URLEncoder.encode(jornadaFormacion, "UTF-8"));

            // Crear la URL completa
            URL url = new URL(BASE_URL + endpoint);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            // Configurar la conexión
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
            conn.setRequestProperty("Content-Length", String.valueOf(urlParameters.toString().getBytes(StandardCharsets.UTF_8).length));

            // Escribir los parámetros en el cuerpo de la solicitud
            try (OutputStream os = conn.getOutputStream()) {
                os.write(urlParameters.toString().getBytes(StandardCharsets.UTF_8));
                os.flush();
            }

            // Leer la respuesta del servidor
            int responseCode = conn.getResponseCode();
            InputStream is = (responseCode >= 200 && responseCode < 300) ? conn.getInputStream() : conn.getErrorStream();

            BufferedReader br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
            StringBuilder response = new StringBuilder();
            String linea;
            while ((linea = br.readLine()) != null) {
                response.append(linea.trim());
            }
            br.close();

            // Manejar la respuesta según el código de estado
            if (responseCode == HttpURLConnection.HTTP_OK || responseCode == HttpURLConnection.HTTP_CREATED) {
                JOptionPane.showMessageDialog(null, "Ficha creada exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Error al crear la ficha: " + response.toString(), "Error", JOptionPane.ERROR_MESSAGE);
            }

            return response.toString();

        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(null, "Validación de datos: " + e.getMessage(), "Error de Validación", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error de conexión: " + e.getMessage(), "Error de Conexión", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Método para actualizar una ficha existente.
     *
     * @param id                 ID de la ficha a actualizar (Integer).
     * @param numeroFicha        Nuevo número de la ficha (Integer).
     * @param programaFormacion  Nuevo programa de formación (String).
     * @param jornadaFormacion   Nueva jornada de formación (String).
     * @return Respuesta del servidor como String.
     */
    public static String actualizarFicha(Integer id, Integer numeroFicha, String programaFormacion, String jornadaFormacion) {
        String endpoint = "/actualizar";
        try {
            // Validaciones básicas
            if (id == null || id <= 0) {
                throw new IllegalArgumentException("El ID de la ficha debe ser un valor positivo y no nulo.");
            }
            if (numeroFicha == null || numeroFicha <= 0) {
                throw new IllegalArgumentException("El número de ficha debe ser un valor positivo y no nulo.");
            }
            if (programaFormacion == null || programaFormacion.trim().isEmpty()) {
                throw new IllegalArgumentException("El campo 'ProgramaFormacion' no puede estar vacío.");
            }
            if (jornadaFormacion == null || jornadaFormacion.trim().isEmpty()) {
                throw new IllegalArgumentException("El campo 'JornadaFormacion' no puede estar vacío.");
            }

            // Construir los parámetros URL-encoded
            StringBuilder urlParameters = new StringBuilder();
            urlParameters.append("id=").append(URLEncoder.encode(id.toString(), "UTF-8"));
            urlParameters.append("&");
            urlParameters.append("numeroFicha=").append(URLEncoder.encode(numeroFicha.toString(), "UTF-8"));
            urlParameters.append("&");
            urlParameters.append("programaFormacion=").append(URLEncoder.encode(programaFormacion, "UTF-8"));
            urlParameters.append("&");
            urlParameters.append("jornadaFormacion=").append(URLEncoder.encode(jornadaFormacion, "UTF-8"));

            // Crear la URL completa
            URL url = new URL(BASE_URL + endpoint);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            // Configurar la conexión
            conn.setRequestMethod("PUT");
            conn.setDoOutput(true);
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
            conn.setRequestProperty("Content-Length", String.valueOf(urlParameters.toString().getBytes(StandardCharsets.UTF_8).length));

            // Escribir los parámetros en el cuerpo de la solicitud
            try (OutputStream os = conn.getOutputStream()) {
                os.write(urlParameters.toString().getBytes(StandardCharsets.UTF_8));
                os.flush();
            }

            // Leer la respuesta del servidor
            int responseCode = conn.getResponseCode();
            InputStream is = (responseCode >= 200 && responseCode < 300) ? conn.getInputStream() : conn.getErrorStream();

            BufferedReader br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
            StringBuilder response = new StringBuilder();
            String linea;
            while ((linea = br.readLine()) != null) {
                response.append(linea.trim());
            }
            br.close();

            // Manejar la respuesta según el código de estado
            if (responseCode == HttpURLConnection.HTTP_OK) {
                JOptionPane.showMessageDialog(null, "Ficha actualizada exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Error al actualizar la ficha: " + response.toString(), "Error", JOptionPane.ERROR_MESSAGE);
            }

            return response.toString();

        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(null, "Validación de datos: " + e.getMessage(), "Error de Validación", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error de conexión: " + e.getMessage(), "Error de Conexión", JOptionPane.ERROR_MESSAGE);
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
            // Validaciones básicas
            if (id == null || id <= 0) {
                throw new IllegalArgumentException("El ID de la ficha debe ser un valor positivo y no nulo.");
            }

            // Construir los parámetros URL-encoded (si es necesario)
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
            int responseCode = conn.getResponseCode();
            InputStream is = (responseCode >= 200 && responseCode < 300) ? conn.getInputStream() : conn.getErrorStream();

            BufferedReader br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
            StringBuilder response = new StringBuilder();
            String linea;
            while ((linea = br.readLine()) != null) {
                response.append(linea.trim());
            }
            br.close();

            if (responseCode >= 200 && responseCode < 300) {
                JOptionPane.showMessageDialog(null, "Ficha eliminada exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                return response.toString();
            } else {
                // Error en la solicitud
                String errorMessage = response.toString();

                // Analizar el mensaje de error para detectar violación de integridad referencial
                if (errorMessage.contains("constraint") || errorMessage.contains("foreign key")) {
                    // Devolver el mensaje personalizado
                    JOptionPane.showMessageDialog(null, "Error: No se pudo eliminar el dato porque cuenta con relaciones pendientes.", "Error", JOptionPane.ERROR_MESSAGE);
                    return "Error: No se pudo eliminar el dato porque cuenta con relaciones pendientes.";
                } else {
                    // Devolver el mensaje de error original
                    return "Error HTTP " + responseCode + ": " + errorMessage;
                }
            }

        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(null, "Validación de datos: " + e.getMessage(), "Error de Validación", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error de conexión: " + e.getMessage(), "Error de Conexión", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Método para asociar una ficha con una clase de formación.
     *
     * @param numeroFicha Número de la ficha (Integer).
     * @param nombreClase Nombre de la clase de formación (String).
     * @return Respuesta del servidor como String.
     */
    public static String asociarFichaConClase(Integer numeroFicha, String nombreClase) {
        String endpoint = "/asociarFichaClase";
        try {
            // Validación de parámetros
            if (numeroFicha == null || numeroFicha <= 0) {
                JOptionPane.showMessageDialog(null, "El número de ficha es inválido.", "Error", JOptionPane.ERROR_MESSAGE);
                return null;
            }
            if (nombreClase == null || nombreClase.trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "El nombre de la clase de formación es inválido.", "Error", JOptionPane.ERROR_MESSAGE);
                return null;
            }

            System.out.println("Asociando Ficha Número " + numeroFicha + " con Clase de Formación '" + nombreClase + "'");

            // Construir los parámetros URL-encoded
            StringBuilder urlParameters = new StringBuilder();
            urlParameters.append("numeroFicha=").append(URLEncoder.encode(numeroFicha.toString(), "UTF-8"));
            urlParameters.append("&");
            urlParameters.append("nombreClase=").append(URLEncoder.encode(nombreClase, "UTF-8"));

            // Crear la URL completa
            URL url = new URL(BASE_URL + endpoint);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            // Configurar la conexión
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
            conn.setRequestProperty("Content-Length", String.valueOf(urlParameters.toString().getBytes().length));
            System.out.println("Parámetros enviados: " + urlParameters.toString());

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
                JOptionPane.showMessageDialog(null, "Ficha asociada exitosamente a la clase de formación.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Error al asociar la ficha con la clase de formación: " + response.toString(), "Error", JOptionPane.ERROR_MESSAGE);
            }

            return response.toString();

        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error de conexión: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Método para editar una asociación entre una ficha y una clase de formación.
     *
     * @param nombreClaseAnterior Nombre de la clase de formación actual (String).
     * @param nombreClaseNueva    Nombre de la nueva clase de formación (String).
     * @param numeroFichaAnterior Número de la ficha actual (Integer).
     * @param numeroFichaNuevo    Número de la nueva ficha (Integer).
     * @return Respuesta del servidor como String.
     */
    public static String editarAsociacionFichaConClase(String nombreClaseAnterior, String nombreClaseNueva, Integer numeroFichaAnterior, Integer numeroFichaNuevo) {
        String endpoint = "/editarAsociacion";
        try {
            // Validación de parámetros
            if (numeroFichaAnterior == null || numeroFichaAnterior <= 0 || numeroFichaNuevo == null || numeroFichaNuevo <= 0) {
                JOptionPane.showMessageDialog(null, "Los números de ficha son inválidos.", "Error", JOptionPane.ERROR_MESSAGE);
                return null;
            }
            if (nombreClaseAnterior == null || nombreClaseAnterior.trim().isEmpty() || nombreClaseNueva == null || nombreClaseNueva.trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Los nombres de las clases de formación son inválidos.", "Error", JOptionPane.ERROR_MESSAGE);
                return null;
            }

            System.out.println("Editando asociación de Ficha Número " + numeroFichaAnterior + " y Clase '" + nombreClaseAnterior + "' con nueva Ficha Número " + numeroFichaNuevo + " y Clase '" + nombreClaseNueva + "'");

            // Construir los parámetros URL-encoded
            StringBuilder urlParameters = new StringBuilder();
            urlParameters.append("nombreClaseAnterior=").append(URLEncoder.encode(nombreClaseAnterior, "UTF-8"));
            urlParameters.append("&");
            urlParameters.append("nombreClaseNueva=").append(URLEncoder.encode(nombreClaseNueva, "UTF-8"));
            urlParameters.append("&");
            urlParameters.append("numeroFichaAnterior=").append(URLEncoder.encode(numeroFichaAnterior.toString(), "UTF-8"));
            urlParameters.append("&");
            urlParameters.append("numeroFichaNuevo=").append(URLEncoder.encode(numeroFichaNuevo.toString(), "UTF-8"));

            // Crear la URL completa
            URL url = new URL(BASE_URL + endpoint);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            // Configurar la conexión
            conn.setRequestMethod("PUT");
            conn.setDoOutput(true);
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
            conn.setRequestProperty("Content-Length", String.valueOf(urlParameters.toString().getBytes().length));
            System.out.println("Parámetros enviados: " + urlParameters.toString());

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
                JOptionPane.showMessageDialog(null, "Asociación de la ficha con la clase de formación editada exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Error al editar la asociación: " + response.toString(), "Error", JOptionPane.ERROR_MESSAGE);
            }

            return response.toString();

        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error de conexión: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Método para eliminar una asociación entre una ficha y una clase de formación.
     *
     * @param numeroFicha Número de la ficha (Integer).
     * @param nombreClase Nombre de la clase de formación (String).
     * @return Respuesta del servidor como String.
     */
    public static String eliminarAsociacionFichaConClase(Integer numeroFicha, String nombreClase) {
        String endpoint = "/eliminarAsociacion";
        try {
            // Validación de parámetros
            if (numeroFicha == null || numeroFicha <= 0) {
                JOptionPane.showMessageDialog(null, "El número de ficha es inválido.", "Error", JOptionPane.ERROR_MESSAGE);
                return null;
            }
            if (nombreClase == null || nombreClase.trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "El nombre de la clase de formación es inválido.", "Error", JOptionPane.ERROR_MESSAGE);
                return null;
            }

            System.out.println("Eliminando asociación de Ficha Número " + numeroFicha + " con Clase de Formación '" + nombreClase + "'");

            // Construir los parámetros URL-encoded
            StringBuilder urlParameters = new StringBuilder();
            urlParameters.append("numeroFicha=").append(URLEncoder.encode(numeroFicha.toString(), "UTF-8"));
            urlParameters.append("&");
            urlParameters.append("nombreClase=").append(URLEncoder.encode(nombreClase, "UTF-8"));

            // Crear la URL completa
            URL url = new URL(BASE_URL + endpoint + "?" + urlParameters.toString());
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            // Configurar la conexión
            conn.setRequestMethod("DELETE");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");

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
                JOptionPane.showMessageDialog(null, "Asociación eliminada exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Error al eliminar la asociación: " + response.toString(), "Error", JOptionPane.ERROR_MESSAGE);
            }

            return response.toString();

        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error de conexión: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            return null;
        }
    }
}
