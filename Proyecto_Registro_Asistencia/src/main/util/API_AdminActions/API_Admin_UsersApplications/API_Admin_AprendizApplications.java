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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import main.util.models.UsersModels.AprendizModel;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.swing.*;

/**
 * Clase para crear usuarios de instructores en la base de datos.
 * @author JeissonLeon
 */
public class API_Admin_AprendizApplications {

    // Este método toma el objeto AprendizModel y lo envía a la base de datos.
    public void CrearAprendiz(AprendizModel aprendiz, List<Integer> fichas) {
        HttpURLConnection conn = null;
        try {
            // Mostrar los detalles del aprendiz para depuración
            System.out.println("Detalles del aprendiz: " + aprendiz);
            System.out.println("Fichas asociadas: " + fichas);

            // Crear la conexión a la URL del controlador Aprendiz
            URL url = new URL("http://localhost:8080/Aprendiz");
            conn = (HttpURLConnection) url.openConnection();

            // Configurar la solicitud como POST y establecer las propiedades del contenido
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            conn.setRequestProperty("Accept", "application/json");
            conn.setDoOutput(true);

            // Construir el objeto JSON con el aprendiz y las fichas
            JSONObject jsonAprendiz = new JSONObject();
            jsonAprendiz.put("User", aprendiz.getUser());
            jsonAprendiz.put("Password", aprendiz.getPassword());
            jsonAprendiz.put("Documento", aprendiz.getDocumento());
            jsonAprendiz.put("TipoDocumento", aprendiz.getTipoDocumento());
            jsonAprendiz.put("Nombres", aprendiz.getNombres());
            jsonAprendiz.put("Apellidos", aprendiz.getApellidos());
            jsonAprendiz.put("FecNacimiento", aprendiz.getFechaNacimiento().toString());
            jsonAprendiz.put("Telefono", aprendiz.getTelefono());
            jsonAprendiz.put("Correo", aprendiz.getCorreo());
            jsonAprendiz.put("Genero", aprendiz.getGenero());
            jsonAprendiz.put("Residencia", aprendiz.getResidencia());
            jsonAprendiz.put("Vinculaciones", new ArrayList<>());

            // Construir el array de fichas
            JSONArray fichasArray = new JSONArray();
            for (Integer ficha : fichas) {
                fichasArray.put(ficha);
            }

            // Crear el JSON principal que contiene el aprendiz y las fichas
            JSONObject jsonPrincipal = new JSONObject();
            jsonPrincipal.put("aprendiz", jsonAprendiz);
            jsonPrincipal.put("fichas", fichasArray);

            // Mostrar el JSON creado para depuración
            System.out.println("JSON a enviar: " + jsonPrincipal.toString());

            // Convertir el JSON a bytes y enviarlo
            byte[] input = jsonPrincipal.toString().getBytes("utf-8");
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

            // Construir el objeto JSON con los datos del aprendiz
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

            // Agregar las vinculaciones al JSON
            JSONArray vinculacionesArray = new JSONArray();
            for (Map<String, Object> vinculacion : aprendiz.getVinculaciones()) {
                JSONObject vinculacionJson = new JSONObject();
                vinculacionJson.put("Ficha", vinculacion.get("Ficha"));
                vinculacionJson.put("Area", vinculacion.get("Area"));
                vinculacionJson.put("Sede", vinculacion.get("Sede"));
                vinculacionJson.put("ClaseFormacion", vinculacion.get("ClaseFormacion"));
                vinculacionJson.put("JornadaFormacion", vinculacion.get("JornadaFormacion"));
                vinculacionJson.put("NombreInstructor", vinculacion.get("NombreInstructor"));
                vinculacionJson.put("NivelFormacion", vinculacion.get("NivelFormacion"));
                vinculacionJson.put("ProgramaFormacion", vinculacion.get("ProgramaFormacion"));
                vinculacionesArray.put(vinculacionJson);
            }
            json.put("Vinculaciones", vinculacionesArray); // Añadir las vinculaciones al JSON

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
                        responseJson.getString("User"),
                        responseJson.getString("Password"),
                        responseJson.getString("Documento"),
                        responseJson.getString("TipoDocumento"),
                        responseJson.getString("Nombres"),
                        responseJson.getString("Apellidos"),
                        Date.valueOf(responseJson.getString("FecNacimiento")),  // Conversión de fecha a SQL Date
                        responseJson.getString("Telefono"),
                        responseJson.getString("Correo"),
                        responseJson.getString("Genero"),
                        responseJson.getString("Residencia"),
                        new ArrayList<>() // Inicializar vinculaciones
                );

                // Procesar las vinculaciones de la respuesta
                if (responseJson.has("Vinculaciones")) {
                    JSONArray vinculacionesRespuesta = responseJson.getJSONArray("Vinculaciones");
                    for (int i = 0; i < vinculacionesRespuesta.length(); i++) {
                        JSONObject vinculacionJson = vinculacionesRespuesta.getJSONObject(i);
                        Map<String, Object> vinculacion = new HashMap<>();
                        vinculacion.put("Ficha", vinculacionJson.getInt("Ficha"));
                        vinculacion.put("Area", vinculacionJson.getString("Area"));
                        vinculacion.put("Sede", vinculacionJson.getString("Sede"));
                        vinculacion.put("ClaseFormacion", vinculacionJson.getString("ClaseFormacion"));
                        vinculacion.put("JornadaFormacion", vinculacionJson.getString("JornadaFormacion"));
                        vinculacion.put("NombreInstructor", vinculacionJson.getString("NombreInstructor"));
                        vinculacion.put("NivelFormacion", vinculacionJson.getString("NivelFormacion"));
                        vinculacion.put("ProgramaFormacion", vinculacionJson.getString("ProgramaFormacion"));
                        updatedAprendiz.getVinculaciones().add(vinculacion);
                    }
                }

                if (responseCode == HttpURLConnection.HTTP_OK) {
                    JOptionPane.showMessageDialog(null, "Datos del aprendiz actualizados correctamente");
                } else {
                    JOptionPane.showMessageDialog(null, "Error al actualizar el aprendiz");
                }
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

    public List<AprendizModel> obtenerAprendicesCompletosPorFicha(int numeroFicha) {
        HttpURLConnection conn = null;
        try {
            // Definir la URL del servicio
            URL url = new URL("http://localhost:8080/Data/Aprendices/CompletosPorFicha/" + numeroFicha);
            System.out.println(url);
            conn = (HttpURLConnection) url.openConnection();

            // Configurar la solicitud como GET
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            // Verificar la respuesta del servidor
            int responseCode = conn.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                // Leer la respuesta del servidor
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = in.readLine()) != null) {
                    response.append(line);
                }
                in.close();

                // Convertir la respuesta en un JSONArray
                JSONArray jsonArray = new JSONArray(response.toString());
                List<AprendizModel> aprendices = new ArrayList<>();

                // Iterar a través del JSONArray y crear los objetos AprendizModel
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                    // Crear AprendizModel a partir del JSONObject
                    AprendizModel aprendiz = new AprendizModel(
                            jsonObject.getString("User"),
                            jsonObject.getString("Password"),
                            jsonObject.getString("Documento"),
                            jsonObject.getString("TipoDocumento"),
                            jsonObject.getString("Nombres"),
                            jsonObject.getString("Apellidos"),
                            Date.valueOf(jsonObject.getString("FecNacimiento")),  // Convertir la fecha
                            jsonObject.getString("Telefono"),
                            jsonObject.getString("Correo"),
                            jsonObject.getString("Genero"),
                            jsonObject.getString("Residencia"),
                            new ArrayList<>()
                    );

                    // Agregar a la lista de aprendices
                    aprendices.add(aprendiz);
                }

                // Devolver la lista de aprendices
                return aprendices;
            } else {
                System.out.println("Error: Código de respuesta inesperado - " + responseCode);
                return new ArrayList<>();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
    }
}