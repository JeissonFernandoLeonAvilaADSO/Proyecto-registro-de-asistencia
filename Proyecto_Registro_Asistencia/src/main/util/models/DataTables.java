package main.util.models;

import main.util.models.UsersModels.AprendizModel;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.swing.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.sql.Date;

public class DataTables {

    public Map<String, Object> obtenerProgramaFormacionPorFicha(int ficha) {
        try {
            URL url = new URL("http://localhost:8080/Data/Fichas/programaFormacion/" + ficha);
//            System.out.println("Conectando a la URL para obtener programa de formación por ficha: " + url);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            if (conn.getResponseCode() != 200) {
                System.out.println("Error HTTP: " + conn.getResponseCode());
                throw new RuntimeException("Error HTTP: " + conn.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                response.append(line);
            }

            // Parsear la respuesta JSON
            JSONObject jsonResponse = new JSONObject(response.toString());
//            System.out.println("Respuesta obtenida del API: " + jsonResponse);

            // Convertir el JSON a un Map<String, Object>
            Map<String, Object> programaFormacionMap = new HashMap<>();
            jsonResponse.keys().forEachRemaining(key -> {
                programaFormacionMap.put(key, jsonResponse.get(key));
            });

//            System.out.println("Programa de formación obtenido: " + programaFormacionMap);
            return programaFormacionMap;

        } catch (Exception e) {
            System.out.println("Error al obtener el programa de formación: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    // Método genérico para obtener datos de cualquier tabla
    private Map<Integer, String> obtenerDatosDesdeAPI(String endpoint) {
        Map<Integer, String> resultados = new LinkedHashMap<>();
        try {
            URL url = new URL("http://localhost:8080/Data/" + endpoint + "/todos");
//            System.out.println("Conectando a la URL: " + url);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            if (conn.getResponseCode() != 200) {
                System.out.println("Error HTTP: " + conn.getResponseCode());
                throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                response.append(line);
            }

            JSONArray jsonArray = new JSONArray(response.toString());
//            System.out.println("Respuesta obtenida del API: " + jsonArray);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String valorPrincipal;
                Integer id = jsonObject.getInt("ID");

                switch (endpoint) {
                    case "Rol":
                        valorPrincipal = jsonObject.getString("TipoRol");
                        break;
                    case "Genero":
                        valorPrincipal = jsonObject.getString("TiposGeneros");
                        break;
                    case "Ambientes":
                        valorPrincipal = jsonObject.getString("Ambiente");
                        break;
                    case "Actividades":
                        valorPrincipal = jsonObject.getString("TipoActividad");
                        break;
                    case "Fichas":
                        valorPrincipal = String.valueOf(jsonObject.get("NumeroFicha"));
                        break;
                    case "Departamentos":
                        valorPrincipal = jsonObject.getString("nombre_departamento");
                        break;
                    case "Municipios":
                        valorPrincipal = jsonObject.getString("nombre_municipio");
                        break;
                    case "Barrios":
                        valorPrincipal = jsonObject.getString("nombre_barrio");
                        break;
                    case "Areas":
                        valorPrincipal = jsonObject.getString("Area");
                        break;
                    case "JornadaFormacion":
                        valorPrincipal = jsonObject.getString("JornadasFormacion");
                        break;
                    case "Sede":
                        valorPrincipal = jsonObject.getString("CentroFormacion");
                        break;
                    case "ClaseFormacion/Instructores":
                        valorPrincipal = jsonObject.getString("NombreInstructor");
                        break;
                    default:
                        valorPrincipal = jsonObject.getString(endpoint); // Ajustar según el campo específico
                        break;
                }

                resultados.put(id, valorPrincipal);
            }

            conn.disconnect();
        } catch (Exception e) {
            System.out.println("Error obteniendo datos desde la API para el endpoint " + endpoint + ": " + e.getMessage());
            e.printStackTrace();
        }

//        System.out.println("Datos obtenidos del endpoint " + endpoint + ": " + resultados);
        return resultados;
    }

    public Map<Integer, String> obtenerGeneros() {
        return obtenerDatosDesdeAPI("Genero");
    }

    public Map<Integer, String> obtenerRoles() {
        return obtenerDatosDesdeAPI("Rol");
    }

    public Map<Integer, String> obtenerTipoDocumentos() {
        return obtenerDatosDesdeAPI("TipoDocumento");
    }

    public Map<Integer, String> obtenerAreas() {
        return obtenerDatosDesdeAPI("Areas");
    }

    public Map<Integer, String> obtenerJornadasFormacion() {
        return obtenerDatosDesdeAPI("JornadaFormacion");
    }

    public Map<Integer, String> obtenerNivelesFormacion() {
        return obtenerDatosDesdeAPI("NivelFormacion");
    }

    public Map<Integer, String> obtenerSedes() {
        return obtenerDatosDesdeAPI("Sede");
    }

    public Map<Integer, String> obtenerActividades() {
        return obtenerDatosDesdeAPI("Actividades");
    }

    public Map<Integer, String> obtenerAmbientes() {
        return obtenerDatosDesdeAPI("Ambientes");
    }



    public List<Map<String, Object>> obtenerFichas() {
        List<Map<String, Object>> fichas = new ArrayList<>();
        try {
            URL url = new URL("http://localhost:8080/Data/Fichas/todosConDetalles");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            // Verificar respuesta
            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Error HTTP: " + conn.getResponseCode());
            }

            // Leer la respuesta
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                response.append(line);
            }

            // Parsear el JSON
            JSONArray jsonArray = new JSONArray(response.toString());

            // Convertir el JSONArray en una lista de mapas
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Map<String, Object> fichaMap = new HashMap<>();

                // Obtener los campos necesarios
                Integer idFicha = jsonObject.getInt("ID");
                Integer numeroFicha = jsonObject.getInt("NumeroFicha");
                String programaFormacion = jsonObject.getString("ProgramaFormacion");
                String jornadaFormacion = jsonObject.getString("JornadaFormacion");

                // Añadir al mapa
                fichaMap.put("ID", idFicha);
                fichaMap.put("NumeroFicha", numeroFicha);
                fichaMap.put("ProgramaFormacion", programaFormacion);
                fichaMap.put("JornadaFormacion", jornadaFormacion);

                // Añadir el mapa a la lista
                fichas.add(fichaMap);
            }

            conn.disconnect();
        } catch (Exception e) {
            System.out.println("Error al obtener las fichas: " + e.getMessage());
            e.printStackTrace();
        }

        return fichas;
    }

    // Método para obtener las clases con sus respectivos instructores
    public static List<Map<String, Object>> obtenerClases() {
        List<Map<String, Object>> resultados = new ArrayList<>();

        try {
            URL url = new URL("http://localhost:8080/Data/ClaseFormacion/todas");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            // Verificar la respuesta del servidor
            if (conn.getResponseCode() == 200) {
                // Leer la respuesta del servidor
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) {
                    response.append(line);
                }
                br.close();

                // Parsear el JSON completo
                JSONObject jsonResponse = new JSONObject(response.toString());

                // Obtener el mensaje del servidor (opcional)
                String mensaje = jsonResponse.optString("mensaje", "No se recibió mensaje del servidor.");
                System.out.println("Mensaje del servidor: " + mensaje);

                // Obtener el arreglo de datos
                JSONArray dataArray = jsonResponse.getJSONArray("data");

                // Iterar sobre cada objeto en el arreglo de datos
                for (int i = 0; i < dataArray.length(); i++) {
                    JSONObject jsonObject = dataArray.getJSONObject(i);

                    // Extraer los valores de las claves del JSON
                    Integer id = jsonObject.getInt("ID");
                    String nombreClase = jsonObject.getString("NombreClase");
                    String jornadasFormacion = jsonObject.getString("JornadasFormacion");

                    // Agregar los valores en un mapa
                    Map<String, Object> claseMap = new HashMap<>();
                    claseMap.put("ID", id);
                    claseMap.put("NombreClase", nombreClase);
                    claseMap.put("JornadasFormacion", jornadasFormacion);

                    resultados.add(claseMap);
                }
                
                // Mostrar la respuesta completa en consola (opcional)
                System.out.println("Respuesta del servidor: " + response.toString());

            } else {
                // Manejar respuestas de error
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getErrorStream(), StandardCharsets.UTF_8));
                StringBuilder errorResponse = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) {
                    errorResponse.append(line);
                }
                br.close();

                // Parsear el error como JSONObject
                JSONObject errorJson = new JSONObject(errorResponse.toString());
                String mensajeError = errorJson.optString("mensaje", "Error al obtener las clases.");

                // Mostrar mensaje de error al usuario
                JOptionPane.showMessageDialog(null, mensajeError, "Error", JOptionPane.ERROR_MESSAGE);
                System.err.println("Error HTTP: " + conn.getResponseCode() + " - " + mensajeError);
            }

        } catch (JSONException je) {
            // Manejar errores de parseo JSON
            JOptionPane.showMessageDialog(null, "Error al parsear la respuesta del servidor.", "Error", JOptionPane.ERROR_MESSAGE);
            je.printStackTrace();
        } catch (IOException ioe) {
            // Manejar errores de conexión
            JOptionPane.showMessageDialog(null, "Error de conexión al servidor:\n" + ioe.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            ioe.printStackTrace();
        } catch (Exception e) {
            // Manejar otros tipos de errores
            JOptionPane.showMessageDialog(null, "Error al obtener las clases:\n" + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }

        return resultados;
    }

    // Método para obtener los aprendices asociados a una ficha y mapearlos a AprendizModel
    public List<AprendizModel> obtenerAprendicesPorFicha(Integer ficha) {
        List<AprendizModel> aprendices = new ArrayList<>();

        try {
            // Construir la URL para el endpoint
            URL url = new URL("http://localhost:8080/Data/Aprendices/CompletosPorFicha/" + ficha);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            // Verificar si la respuesta es exitosa
            if (conn.getResponseCode() == 200) {
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8));
                StringBuilder response = new StringBuilder();
                String line;

                // Leer la respuesta del servidor
                while ((line = br.readLine()) != null) {
                    response.append(line);
                }

                // Parsear la respuesta JSON
                JSONArray jsonArray = new JSONArray(response.toString());

                // Recorrer el JSONArray y extraer los datos de cada aprendiz
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                    // Crear una nueva instancia de AprendizModel
                    AprendizModel aprendiz = new AprendizModel(
                            jsonObject.getString("User"),
                            jsonObject.getString("Password"),
                            jsonObject.getString("Documento"),
                            jsonObject.getString("TipoDocumento"),
                            jsonObject.getString("Nombres"),
                            jsonObject.getString("Apellidos"),
                            Date.valueOf(jsonObject.getString("FecNacimiento")), // Convertir String a Date
                            jsonObject.getString("Telefono"),
                            jsonObject.getString("Correo"),
                            jsonObject.getString("Genero"),
                            jsonObject.getString("Residencia"),
                            new ArrayList<>()
                    );

                    // Procesar las vinculaciones
                    JSONArray vinculacionesArray = jsonObject.getJSONArray("Vinculaciones");
                    List<Map<String, Object>> vinculaciones = new ArrayList<>();

                    for (int j = 0; j < vinculacionesArray.length(); j++) {
                        JSONObject vinculacionObj = vinculacionesArray.getJSONObject(j);
                        Map<String, Object> vinculacion = new HashMap<>();

                        vinculacion.put("Ficha", vinculacionObj.getInt("Ficha"));
                        vinculacion.put("Area", vinculacionObj.getString("Area"));
                        vinculacion.put("Sede", vinculacionObj.getString("Sede"));
                        vinculacion.put("ClaseFormacion", vinculacionObj.getString("ClaseFormacion"));
                        vinculacion.put("JornadaFormacion", vinculacionObj.getString("JornadaFormacion"));
                        vinculacion.put("NombreInstructor", vinculacionObj.getString("NombreInstructor"));
                        vinculacion.put("NivelFormacion", vinculacionObj.getString("NivelFormacion"));
                        vinculacion.put("ProgramaFormacion", vinculacionObj.getString("ProgramaFormacion"));

                        // Agregar la vinculacion a la lista de vinculaciones
                        vinculaciones.add(vinculacion);
                    }

                    // Establecer las vinculaciones al aprendiz
                    aprendiz.setVinculaciones(vinculaciones);

                    // Agregar el aprendiz a la lista
                    aprendices.add(aprendiz);
                }
            } else {
                throw new RuntimeException("Error HTTP: " + conn.getResponseCode());
            }

            conn.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error al obtener los aprendices por ficha: " + e.getMessage());
        }

        return aprendices;
    }

        // Método para obtener fichas por programa de formación
    public List<Map<String, Object>> obtenerFichasPorPrograma(String nombrePrograma) {
        List<Map<String, Object>> fichas = new ArrayList<>();
        try {
            // Codificar el nombre del programa
            String nombreProgramaCodificado = URLEncoder.encode(nombrePrograma, StandardCharsets.UTF_8.toString());
            System.out.println("Programa codificado: " + nombreProgramaCodificado);  // Log de diagnóstico

            // Construir la URL
            String urlString = "http://localhost:8080/Data/Fichas/porPrograma?nombrePrograma=" + nombreProgramaCodificado;
            System.out.println("URL construida: " + urlString);  // Log de diagnóstico

            // Hacer la solicitud
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            // Verificar respuesta
            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Error HTTP: " + conn.getResponseCode());
            }

            // Leer la respuesta
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                response.append(line);
            }

            // Parsear el JSON
            JSONObject jsonResponse = new JSONObject(response.toString());

            // Verificar que haya un campo 'data' en la respuesta
            if (jsonResponse.has("data")) {
                JSONArray jsonArray = jsonResponse.getJSONArray("data");

                // Convertir el JSONArray en una lista de mapas
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    fichas.add(jsonObject.toMap());
                }
            } else {
                throw new RuntimeException("Respuesta inesperada: No se encontró el campo 'data' en la respuesta.");
            }

            conn.disconnect();

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error al obtener las fichas por programa de formación: " + e.getMessage());
        }

        return fichas;
    }

    public List<Map<String, Object>> obtenerVinculacionesPorFicha(int ficha) {
        String apiUrl = "http://localhost:8080/Aprendiz/vinculaciones/" + ficha;
        String jsonResponse = hacerPeticionGET(apiUrl);

        if (jsonResponse == null || jsonResponse.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "No se pudo obtener una respuesta del servidor.", "Error de Conexión", JOptionPane.ERROR_MESSAGE);
            return null;
        }

        try {
            System.out.println("Respuesta JSON recibida: " + jsonResponse);
            JSONArray jsonArray = new JSONArray(jsonResponse);

            // Lista para almacenar las vinculaciones
            List<Map<String, Object>> vinculaciones = new ArrayList<>();

            // Iterar sobre el arreglo JSON para convertirlo a una lista de mapas
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObj = jsonArray.getJSONObject(i);

                Map<String, Object> vinculacion = new HashMap<>();
                vinculacion.put("Ficha", jsonObj.getInt("NumeroFicha"));
                vinculacion.put("Area", jsonObj.getString("Area"));
                vinculacion.put("Sede", jsonObj.getString("Sede"));
                vinculacion.put("ClaseFormacion", jsonObj.getString("ClaseFormacion"));
                vinculacion.put("JornadaFormacion", jsonObj.getString("JornadaFormacion"));
                vinculacion.put("DocumentoInstructor", jsonObj.getString("DocumentoInstructor"));
                vinculacion.put("NivelFormacion", jsonObj.getString("NivelFormacion"));
                vinculacion.put("ProgramaFormacion", jsonObj.getString("ProgramaFormacion"));
                vinculacion.put("NombreInstructor", jsonObj.getString("NombreInstructor"));

                vinculaciones.add(vinculacion);
            }

            return vinculaciones;

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al procesar la información de vinculaciones: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
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

            if (responseCode >= 200 && responseCode < 300) {
                is = conn.getInputStream();
            } else {
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

        return resultado.toString();
    }
}
