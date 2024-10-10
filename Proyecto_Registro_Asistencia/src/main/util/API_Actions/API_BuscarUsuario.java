package main.util.API_Actions;



import org.json.JSONArray;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import main.util.models.UsersModels.AprendizModel;
import main.util.models.UsersModels.InstructorModel;

import javax.swing.*;


public class API_BuscarUsuario {

    public AprendizModel buscarAprendizPorDocumento(String documento) {
        String apiUrl = "http://localhost:8080/Aprendiz/" + documento;
        System.out.println("Realizando petición GET a: " + apiUrl);
        String jsonResponse = hacerPeticionGET(apiUrl);

        if (jsonResponse == null || jsonResponse.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "No se pudo obtener una respuesta del servidor.", "Error de Conexión", JOptionPane.ERROR_MESSAGE);
            return null;
        }

        try {
            System.out.println("Respuesta JSON recibida: " + jsonResponse);
            JSONObject json = new JSONObject(jsonResponse);
            System.out.println("Respuesta parseada: " + json);

            // Verificar si la respuesta contiene el atributo "mensaje"
            if (json.has("mensaje")) {
                String mensaje = json.getString("mensaje");
                JOptionPane.showMessageDialog(null, mensaje, "Información", JOptionPane.INFORMATION_MESSAGE);
                return null;
            } else {
                // Convertir el JSON en AprendizModel
                return convertirJsonAAprendizModel(json);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al procesar la información del aprendiz: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            return null;
        }
    }

    public InstructorModel buscarInstructorPorDocumento(String documento) {
        String apiUrl = "http://localhost:8080/Instructor/" + documento;
        System.out.println("Realizando petición GET a: " + apiUrl);
        String jsonResponse = hacerPeticionGET(apiUrl);

        if (jsonResponse == null || jsonResponse.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "No se pudo obtener una respuesta del servidor.", "Error de Conexión", JOptionPane.ERROR_MESSAGE);
            return null;
        }

        try {
            System.out.println("Respuesta JSON recibida: " + jsonResponse);
            JSONObject json = new JSONObject(jsonResponse);

            // Verificar si la respuesta contiene el atributo "mensaje"
            if (json.has("mensaje")) {
                String mensaje = json.getString("mensaje");
                JOptionPane.showMessageDialog(null, mensaje, "Información", JOptionPane.INFORMATION_MESSAGE);
                return null;
            } else {
                // Convertir el JSON en InstructorModel
                return convertirJsonAInstructorModel(json);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al procesar la información del instructor: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            return null;
        }
    }



    private String hacerPeticionGET(String apiUrl) {
        StringBuilder resultado = new StringBuilder();
        HttpURLConnection conn = null;
        BufferedReader in = null;
        try {
            URL url = new URL(apiUrl);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            int responseCode = conn.getResponseCode();
            InputStream is;

            if (responseCode >= 200 && responseCode < 300) {  // Respuestas exitosas
                is = conn.getInputStream();
            } else {  // Respuestas de error
                is = conn.getErrorStream();
            }

            if (is != null) {
                in = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    resultado.append(inputLine);
                }
            } else {
                System.out.println("No se pudo obtener el cuerpo de la respuesta.");
                return null;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (conn != null) {
                conn.disconnect();
            }
        }

        return resultado.toString();  // Retornar la respuesta como String (en formato JSON)
    }

    private AprendizModel convertirJsonAAprendizModel(JSONObject json) {
        // Manejar la fecha de nacimiento
        String fechaNacimientoStr = json.optString("FecNacimiento");
        Date fechaNacimiento = null;
        if (!fechaNacimientoStr.isEmpty()) {
            try {
                fechaNacimiento = Date.valueOf(fechaNacimientoStr);
            } catch (IllegalArgumentException e) {
                System.out.println("Error al convertir la fecha: " + fechaNacimientoStr);
                e.printStackTrace();
            }
        }

//        // Imprimir los valores obtenidos del JSON para depuración
//        System.out.println("User: " + json.optString("User"));
//        System.out.println("Password: " + json.optString("Password"));
//        System.out.println("Documento: " + json.optString("Documento"));
//        System.out.println("TipoDocumento: " + json.optString("TipoDocumento"));
//        System.out.println("Nombres: " + json.optString("Nombres"));
//        System.out.println("Apellidos: " + json.optString("Apellidos"));
//        System.out.println("FechaNacimiento: " + fechaNacimiento);
//        System.out.println("Telefono: " + json.optString("Telefono"));
//        System.out.println("Correo: " + json.optString("Correo"));
//        System.out.println("Genero: " + json.optString("Genero"));
//        System.out.println("Residencia: " + json.optString("Residencia"));
//        System.out.println("NumeroFicha: " + json.optInt("NumeroFicha"));
//        System.out.println("ProgramaFormacion: " + json.optString("ProgramaFormacion"));
//        System.out.println("JornadaFormacion: " + json.optString("JornadaFormacion"));
//        System.out.println("NivelFormacion: " + json.optString("NivelFormacion"));
//        System.out.println("Sede: " + json.optString("Sede"));
//        System.out.println("Area: " + json.optString("Area"));

        // Crear y devolver un AprendizModel a partir del JSON
        AprendizModel aprendiz = new AprendizModel(
                json.optString("User"),
                json.optString("Password"),
                json.optString("Documento"),
                json.optString("TipoDocumento"),
                json.optString("Nombres"),
                json.optString("Apellidos"),
                fechaNacimiento,
                json.optString("Telefono"),
                json.optString("Correo"),
                json.optString("Genero"),
                json.optString("Residencia"),
                json.optInt("NumeroFicha"),
                json.optString("ProgramaFormacion"),
                json.optString("JornadaFormacion"),
                json.optString("NivelFormacion"),
                json.optString("Sede"),
                json.optString("Area")
        );

        return aprendiz;
    }

    private InstructorModel convertirJsonAInstructorModel(JSONObject json) {
        // Crear listas para las fichas y otros atributos
        List<Integer> fichas = new ArrayList<>();
        JSONArray fichasArray = json.optJSONArray("Fichas"); // Ajustar el nombre de la clave con mayúscula inicial
        if (fichasArray != null) {
            for (int i = 0; i < fichasArray.length(); i++) {
                fichas.add(fichasArray.getInt(i));
            }
        }

        List<String> programasFormacion = jsonArrayToList(json.optJSONArray("ProgramasFormacion"));
        List<String> jornadasFormacion = jsonArrayToList(json.optJSONArray("JornadasFormacion"));
        List<String> nivelesFormacion = jsonArrayToList(json.optJSONArray("NivelesFormacion"));
        List<String> sedes = jsonArrayToList(json.optJSONArray("CentrosFormacion")); // Ajustar el nombre de la clave con mayúscula inicial
        List<String> areas = jsonArrayToList(json.optJSONArray("Areas"));

        // Manejar la fecha de nacimiento
        String fechaNacimientoStr = json.optString("FecNacimiento");
        Date fechaNacimiento = null;
        if (!fechaNacimientoStr.isEmpty()) {
            try {
                fechaNacimiento = Date.valueOf(fechaNacimientoStr);
            } catch (IllegalArgumentException e) {
                System.out.println("Error al convertir la fecha: " + fechaNacimientoStr);
                e.printStackTrace();
            }
        }

        // Imprimir los valores obtenidos del JSON para depuración
        System.out.println("User: " + json.optString("User"));
        System.out.println("Password: " + json.optString("Password"));
        System.out.println("Documento: " + json.optString("Documento"));
        System.out.println("TipoDocumento: " + json.optString("TipoDocumento"));
        System.out.println("Nombres: " + json.optString("Nombres"));
        System.out.println("Apellidos: " + json.optString("Apellidos"));
        System.out.println("FechaNacimiento: " + fechaNacimiento);
        System.out.println("Telefono: " + json.optString("Telefono"));
        System.out.println("Correo: " + json.optString("Correo"));
        System.out.println("Genero: " + json.optString("Genero"));
        System.out.println("Residencia: " + json.optString("Residencia"));
        System.out.println("ClaseFormacion: " + json.optString("ClaseFormacion"));
        System.out.println("Fichas: " + fichas);
        System.out.println("ProgramasFormacion: " + programasFormacion);
        System.out.println("JornadasFormacion: " + jornadasFormacion);
        System.out.println("NivelesFormacion: " + nivelesFormacion);
        System.out.println("Sedes: " + sedes);
        System.out.println("Areas: " + areas);

        // Crear y devolver un InstructorModel a partir del JSON
        InstructorModel instructor = new InstructorModel(
                json.optString("User"),
                json.optString("Password"),
                json.optString("Documento"),
                json.optString("TipoDocumento"),
                json.optString("Nombres"),
                json.optString("Apellidos"),
                fechaNacimiento,
                json.optString("Telefono"),
                json.optString("Correo"),
                json.optString("Genero"),
                json.optString("Residencia"),
                json.optString("ClaseFormacion"),
                fichas,
                programasFormacion,
                jornadasFormacion,
                nivelesFormacion,
                sedes,
                areas
        );

        return instructor;
    }

    // Método auxiliar para convertir JSONArray a List<String>
    private List<String> jsonArrayToList(JSONArray jsonArray) {
        List<String> list = new ArrayList<>();
        if (jsonArray != null) {
            for (int i = 0; i < jsonArray.length(); i++) {
                list.add(jsonArray.getString(i));
            }
        }
        return list;
    }

    // Método para habilitar un aprendiz
    public boolean habilitarAprendiz(String documento) {
        String apiUrl = "http://localhost:8080/Aprendiz/habilitar/" + documento;
        String jsonResponse = hacerPeticionPUT(apiUrl);
        return procesarRespuesta(jsonResponse);
    }

    // Método para inhabilitar un aprendiz
    public boolean inhabilitarAprendiz(String documento) {
        String apiUrl = "http://localhost:8080/Aprendiz/inhabilitar/" + documento;
        String jsonResponse = hacerPeticionPUT(apiUrl);
        return procesarRespuesta(jsonResponse);
    }

    // Método para habilitar un instructor
    public boolean habilitarInstructor(String documento) {
        String apiUrl = "http://localhost:8080/Instructor/habilitar/" + documento;
        String jsonResponse = hacerPeticionPUT(apiUrl);
        return procesarRespuesta(jsonResponse);
    }

    // Método para inhabilitar un instructor
    public boolean inhabilitarInstructor(String documento) {
        String apiUrl = "http://localhost:8080/Instructor/inhabilitar/" + documento;
        String jsonResponse = hacerPeticionPUT(apiUrl);
        return procesarRespuesta(jsonResponse);
    }

    // Método para hacer peticiones PUT
    private String hacerPeticionPUT(String apiUrl) {
        StringBuilder resultado = new StringBuilder();
        HttpURLConnection conn = null;
        BufferedReader in = null;
        try {
            URL url = new URL(apiUrl);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("PUT");
            conn.setRequestProperty("Accept", "application/json");
            conn.setDoOutput(true); // Permite enviar datos en el cuerpo de la solicitud si es necesario

            int responseCode = conn.getResponseCode();
            InputStream is;

            if (responseCode >= 200 && responseCode < 300) {  // Respuestas exitosas
                is = conn.getInputStream();
            } else {  // Respuestas de error
                is = conn.getErrorStream();
            }

            if (is != null) {
                in = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    resultado.append(inputLine);
                }
            } else {
                System.out.println("No se pudo obtener el cuerpo de la respuesta.");
                return null;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (conn != null) {
                conn.disconnect();
            }
        }

        return resultado.toString();  // Retornar la respuesta como String (en formato JSON)
    }

    // Método para procesar la respuesta y mostrar mensajes
    private boolean procesarRespuesta(String jsonResponse) {
        if (jsonResponse == null || jsonResponse.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "No se pudo obtener una respuesta del servidor.", "Error de Conexión", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        try {
            JSONObject json = new JSONObject(jsonResponse);
            String mensaje = json.optString("mensaje");
            JOptionPane.showMessageDialog(null, mensaje, "Información", JOptionPane.INFORMATION_MESSAGE);
            return true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al procesar la respuesta: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            return false;
        }
    }



}