package main.util.API_AdminActions.API_Admin_UsersApplications;

import main.util.models.UsersModels.InstructorModel;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class API_Admin_InstructorApplications {
    public void CrearInstructor(InstructorModel instructor) {
        try {
            // Definir la URL de la API a la que vas a hacer el POST
            URL url = new URL("http://localhost:8080/Instructor");  // Asegúrate de que esta URL sea la correcta
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            // Configurar el tipo de petición y los headers
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            conn.setRequestProperty("Accept", "application/json");
            conn.setDoOutput(true);  // Para enviar datos en la solicitud

            // Convertir el modelo InstructorModel a un objeto JSON
            JSONObject instructorJson = convertirInstructorAJson(instructor);

            // Enviar el JSON en el cuerpo de la solicitud
            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = instructorJson.toString().getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            // Leer la respuesta del servidor
            int responseCode = conn.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_CREATED) {
                System.out.println("Instructor creado exitosamente.");

                // Leer la respuesta
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                System.out.println("Respuesta del servidor: " + response.toString());
            } else {
                // Si hubo un error, leer el error response
                System.out.println("Error en la creación del instructor. Código: " + responseCode);
                BufferedReader errorReader = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
                String errorLine;
                StringBuilder errorResponse = new StringBuilder();
                while ((errorLine = errorReader.readLine()) != null) {
                    errorResponse.append(errorLine);
                }
                errorReader.close();
                System.out.println("Respuesta de error del servidor: " + errorResponse.toString());
            }

            // Cerrar la conexión
            conn.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Método para convertir el modelo InstructorModel a un JSON
    private JSONObject convertirInstructorAJson(InstructorModel instructor) {
        JSONObject json = new JSONObject();

        json.put("User", instructor.getUser());
        json.put("Password", instructor.getPassword());
        json.put("Documento", instructor.getDocumento());
        json.put("TipoDocumento", instructor.getTipoDocumento());
        json.put("Nombres", instructor.getNombres());
        json.put("Apellidos", instructor.getApellidos());
        json.put("FecNacimiento", instructor.getFechaNacimiento());
        json.put("Telefono", instructor.getTelefono());
        json.put("Correo", instructor.getCorreo());
        json.put("Genero", instructor.getGenero());
        json.put("Residencia", instructor.getResidencia());
        json.put("ClaseFormacion", instructor.getClaseFormacion());

        // Convertir la lista de fichas a un JSONArray
        JSONArray fichasJsonArray = new JSONArray(instructor.getFichas());
        json.put("Fichas", fichasJsonArray);

        return json;
    }

    public InstructorModel UpdateInstructor(String documento, InstructorModel instructor) {
        try {
            // Definir la URL de la API
            URL url = new URL("http://localhost:8080/Instructor/" + documento);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            // Configurar la solicitud como PUT
            conn.setRequestMethod("PUT");
            conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            conn.setRequestProperty("Accept", "application/json");
            conn.setDoOutput(true);

            // Construir el objeto JSON con los nombres correctos
            JSONObject json = new JSONObject();
            json.put("User", instructor.getUser());
            json.put("Password", instructor.getPassword());
            json.put("Documento", instructor.getDocumento());
            json.put("TipoDocumento", instructor.getTipoDocumento());
            json.put("Nombres", instructor.getNombres());
            json.put("Apellidos", instructor.getApellidos());
            json.put("FecNacimiento", instructor.getFechaNacimiento().toString());  // Formatear la fecha
            json.put("Telefono", instructor.getTelefono());
            json.put("Correo", instructor.getCorreo());
            json.put("Genero", instructor.getGenero());
            json.put("Residencia", instructor.getResidencia());
            json.put("ClaseFormacion", instructor.getClaseFormacion());
            json.put("Fichas", new JSONArray(instructor.getFichas()));  // Convertir lista de fichas a JSON array
            json.put("ProgramasFormacion", new JSONArray(instructor.getProgramasFormacion()));
            json.put("JornadasFormacion", new JSONArray(instructor.getJornadasFormacion()));
            json.put("NivelesFormacion", new JSONArray(instructor.getNivelesFormacion()));
            json.put("Sedes", new JSONArray(instructor.getSedes()));
            json.put("Areas", new JSONArray(instructor.getAreas()));

            // Depuración de los datos enviados
            System.out.println("Enviando los siguientes datos de instructor: " + json.toString());

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

                // Convertir la respuesta JSON en un objeto InstructorModel
                JSONObject responseJson = new JSONObject(response.toString());

                InstructorModel updatedInstructor = new InstructorModel(
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
                        new ArrayList<>(),
                        // Verificar si los arrays existen y no son nulos
                        responseJson.has("Fichas") && !responseJson.isNull("Fichas") ? convertJsonArrayToIntegerList(responseJson.getJSONArray("Fichas")) : new ArrayList<>(),
                        responseJson.has("ProgramasFormacion") && !responseJson.isNull("ProgramasFormacion") ? convertJsonArrayToStringList(responseJson.getJSONArray("ProgramasFormacion")) : new ArrayList<>(),
                        responseJson.has("JornadasFormacion") && !responseJson.isNull("JornadasFormacion") ? convertJsonArrayToStringList(responseJson.getJSONArray("JornadasFormacion")) : new ArrayList<>(),
                        responseJson.has("NivelesFormacion") && !responseJson.isNull("NivelesFormacion") ? convertJsonArrayToStringList(responseJson.getJSONArray("NivelesFormacion")) : new ArrayList<>(),
                        responseJson.has("Sedes") && !responseJson.isNull("Sedes") ? convertJsonArrayToStringList(responseJson.getJSONArray("Sedes")) : new ArrayList<>(),
                        responseJson.has("Areas") && !responseJson.isNull("Areas") ? convertJsonArrayToStringList(responseJson.getJSONArray("Areas")) : new ArrayList<>()
                );

                if (responseCode == HttpURLConnection.HTTP_OK) {
                    JOptionPane.showMessageDialog(null, "Datos del Instructor actualizados correctamente");
                } else {
                    JOptionPane.showMessageDialog(null, "Error al actualizar la Instructor");
                }
                return updatedInstructor;
            } else {
                System.out.println("Error: Código de respuesta inesperado - " + responseCode);
                return null;
            }

        } catch (Exception e) {
            System.out.println("Error al intentar actualizar el instructor: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    // Métodos auxiliares para convertir JSONArray en listas de tipos
    private List<Integer> convertJsonArrayToIntegerList(JSONArray jsonArray) {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            list.add(jsonArray.getInt(i));
        }
        return list;
    }

    private List<String> convertJsonArrayToStringList(JSONArray jsonArray) {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            list.add(jsonArray.getString(i));
        }
        return list;
    }

    public boolean DeleteInstructor(String documento) {
        try {
            // Definir la URL de la API para eliminar un instructor por documento
            URL url = new URL("http://localhost:8080/Instructor/" + documento);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            // Configurar la solicitud como DELETE
            conn.setRequestMethod("DELETE");
            conn.setRequestProperty("Accept", "application/json");

            // Verificar el código de respuesta
            int responseCode = conn.getResponseCode();
            System.out.println("Código de respuesta de la API: " + responseCode);

            if (responseCode == HttpURLConnection.HTTP_NO_CONTENT) {
                System.out.println("Instructor eliminado correctamente.");
                return true;
            } else if (responseCode == HttpURLConnection.HTTP_NOT_FOUND) {
                System.out.println("Error: No se encontró el instructor con el documento proporcionado.");
                return false;
            } else {
                System.out.println("Error: Código de respuesta inesperado - " + responseCode);
                return false;
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error al intentar eliminar el instructor: " + e.getMessage());
            return false;
        }
    }
}
