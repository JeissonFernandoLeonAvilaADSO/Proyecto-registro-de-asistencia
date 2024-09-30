/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.util.API_AdminActions.API_Admin_UsersApplications;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Date;

import main.util.models.UsersModels.AprendizModel;
import org.json.JSONObject;

/**
 * Clase para crear usuarios de instructores en la base de datos.
 * @author JeissonLeon
 */
public class API_Admin_AprendizApplications {

    // Este método toma el objeto AprendizModel y lo envía a la base de datos.
    public void CrearAprendiz(AprendizModel aprendiz) {
        HttpURLConnection conn = null;
        try {
            // Mostrar los detalles del aprendiz para depuración
            System.out.println("Detalles del aprendiz: " + aprendiz);

            // Crear la conexión a la URL del controlador Aprendiz
            URL url = new URL("http://localhost:8080/Aprendiz");
            conn = (HttpURLConnection) url.openConnection();

            // Configurar la solicitud como POST y establecer las propiedades del contenido
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            conn.setRequestProperty("Accept", "application/json");
            conn.setDoOutput(true);

            // Convertir el objeto AprendizModel a JSON usando org.json.JSONObject
            JSONObject json = new JSONObject();
            json.put("User", aprendiz.getUser());
            json.put("Password", aprendiz.getPassword());
            json.put("Documento", aprendiz.getDocumento());
            json.put("TipoDocumento", aprendiz.getTipoDocumento());
            json.put("Nombres", aprendiz.getNombres());
            json.put("Apellidos", aprendiz.getApellidos());
            json.put("FecNacimiento", aprendiz.getFechaNacimiento().toString());  // Convertir la fecha a String
            json.put("Telefono", aprendiz.getTelefono());
            json.put("Correo", aprendiz.getCorreo());
            json.put("Genero", aprendiz.getGenero());
            json.put("Residencia", aprendiz.getResidencia());
            json.put("NumeroFicha", aprendiz.getFicha());  // Asegúrate de que es un número

            // Mostrar el JSON creado para depuración
            System.out.println("JSON a enviar: " + json.toString());

            // Convertir el JSON a bytes y enviarlo
            byte[] input = json.toString().getBytes("utf-8");

            try (OutputStream os = conn.getOutputStream()) {
                os.write(input, 0, input.length);
                System.out.println("Solicitud enviada correctamente.");
            }

            // Verificar si la respuesta del servidor es correcta (201 Created)
            int responseCode = conn.getResponseCode();
            if (responseCode != HttpURLConnection.HTTP_CREATED) {
                // Imprimir el mensaje de error del servidor si la respuesta no es HTTP_CREATED
                System.out.println("Error en la respuesta del servidor. Código de respuesta: " + responseCode);
                if (conn.getErrorStream() != null) {
                    try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getErrorStream()))) {
                        String line;
                        while ((line = br.readLine()) != null) {
                            System.out.println("Error del servidor: " + line);
                        }
                    }
                }
            } else {
                // Imprimir la respuesta correcta del servidor
                System.out.println("Usuario creado correctamente. Código de respuesta: " + responseCode);

                // Leer y mostrar la respuesta del servidor (opcional)
                try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = br.readLine()) != null) {
                        response.append(line.trim());
                    }
                    System.out.println("Respuesta del servidor: " + response.toString());
                }
            }
        } catch (Exception e) {
            // Imprimir la excepción para depuración
            System.out.println("Ocurrió un error al intentar crear el usuario:");
            e.printStackTrace();
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
    }

    public AprendizModel UpdateAprendiz(String documento, AprendizModel aprendiz) {
        try {
            // Definir la URL de la API
            URL url = new URL("http://localhost:8080/Aprendiz/" + documento);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            // Configurar la solicitud como PUT
            conn.setRequestMethod("PUT");
            conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            conn.setRequestProperty("Accept", "application/json");
            conn.setDoOutput(true);

            // Construir el objeto JSON con los nombres correctos
            JSONObject json = new JSONObject();
            json.put("User", aprendiz.getUser());
            json.put("Password", aprendiz.getPassword());
            json.put("Documento", aprendiz.getDocumento());
            json.put("TipoDocumento", aprendiz.getTipoDocumento());
            json.put("Nombres", aprendiz.getNombres());
            json.put("Apellidos", aprendiz.getApellidos());
            json.put("FecNacimiento", aprendiz.getFechaNacimiento().toString());  // Formatear la fecha
            json.put("Telefono", aprendiz.getTelefono());
            json.put("Correo", aprendiz.getCorreo());
            json.put("Genero", aprendiz.getGenero());
            json.put("Residencia", aprendiz.getResidencia());
            json.put("NumeroFicha", aprendiz.getFicha());

            // Depuración de los datos enviados
            System.out.println("Enviando los siguientes datos de aprendiz: " + json.toString());

            // Escribir los datos JSON en la conexión
            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = json.toString().getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            // Verificar el código de respuesta
            int responseCode = conn.getResponseCode();
            System.out.println("Código de respuesta de la API: " + responseCode);
            if (responseCode == HttpURLConnection.HTTP_OK) {
                // Leer la respuesta desde la API
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                System.out.println("Respuesta de la API: " + response.toString());

                // Convertir la respuesta JSON en un objeto AprendizModel
                JSONObject responseJson = new JSONObject(response.toString());
                AprendizModel updatedAprendiz = new AprendizModel(
                        responseJson.getString("Usuario"),
                        responseJson.getString("Contraseña"),
                        responseJson.getString("Documento"),
                        responseJson.getString("TipoDocumento"),
                        responseJson.getString("Nombres"),
                        responseJson.getString("Apellidos"),
                        Date.valueOf(responseJson.getString("FechaNacimiento")),  // Conversión de fecha a SQL Date
                        responseJson.getString("Teléfono"),
                        responseJson.getString("Correo"),
                        responseJson.getString("Género"),
                        responseJson.getString("Residencia"),
                        responseJson.getInt("Ficha"),
                        responseJson.getString("ProgramaFormacion"),
                        responseJson.getString("JornadaFormacion"),
                        responseJson.getString("NivelFormacion"),
                        responseJson.getString("Sede"),
                        responseJson.getString("Area")
                );

                System.out.println("Datos del aprendiz actualizados correctamente: " + updatedAprendiz);
                return updatedAprendiz;
            } else {
                System.out.println("Error: Código de respuesta inesperado - " + responseCode);
                return null;
            }

        } catch (Exception e) {
            System.out.println("Error al intentar actualizar el aprendiz: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public boolean DeleteAprendiz(String documento) {
        try {
            // Definir la URL de la API para eliminar un aprendiz por documento
            URL url = new URL("http://localhost:8080/Aprendiz/" + documento);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            // Configurar la solicitud como DELETE
            conn.setRequestMethod("DELETE");
            conn.setRequestProperty("Accept", "application/json");

            // Verificar el código de respuesta
            int responseCode = conn.getResponseCode();
            System.out.println("Código de respuesta de la API: " + responseCode);

            if (responseCode == HttpURLConnection.HTTP_NO_CONTENT) {
                System.out.println("Aprendiz eliminado correctamente.");
                return true;
            } else if (responseCode == HttpURLConnection.HTTP_NOT_FOUND) {
                System.out.println("Error: No se encontró el aprendiz con el documento proporcionado.");
                return false;
            } else {
                System.out.println("Error: Código de respuesta inesperado - " + responseCode);
                return false;
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error al intentar eliminar el aprendiz: " + e.getMessage());
            return false;
        }
    }
}