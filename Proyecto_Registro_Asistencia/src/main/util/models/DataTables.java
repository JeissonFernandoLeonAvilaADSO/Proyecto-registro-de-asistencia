package main.util.models;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;

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

    // Métodos específicos para obtener datos de cada tabla
    public Map<Integer, String> obtenerProgramaFormacion() {
        return obtenerDatosDesdeAPI("ProgramaFormacion");
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

    public Map<Integer, String> obtenerBarrios() {
        return obtenerDatosDesdeAPI("Barrios");
    }

    public Map<Integer, String> obtenerDepartamentos() {
        return obtenerDatosDesdeAPI("Departamentos");
    }

    public Map<Integer, String> obtenerMunicipios() {
        return obtenerDatosDesdeAPI("Municipios");
    }

    public Map<Integer, String> obtenerActividades() {
        return obtenerDatosDesdeAPI("Actividades");
    }

    public Map<Integer, String> obtenerAmbientes() {
        return obtenerDatosDesdeAPI("Ambientes");
    }

    public Map<Integer, String> obtenerClasesFormacion() {
        return obtenerDatosDesdeAPI("ClaseFormacion");
    }

    public Map<Integer, String> obtenerFichas() {
        return obtenerDatosDesdeAPI("Fichas");
    }
    // Método para obtener instructores para la ComboBox
    public Map<Integer, String> obtenerInstructores() {
        return obtenerDatosDesdeAPI("ClaseFormacion/Instructores");
    }

    // Método para obtener las clases con sus respectivos instructores
    public List<Map<String, Object>> obtenerClasesConInstructor() {
        List<Map<String, Object>> resultados = new ArrayList<>();

        try {
            URL url = new URL("http://localhost:8080/Data/ClaseFormacion/Instructores/todos");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            if (conn.getResponseCode() == 200) {
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) {
                    response.append(line);
                }

                // Parsear el JSON
                JSONArray jsonArray = new JSONArray(response.toString());

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                    // Extraer los valores de las claves del JSON
                    Integer id = jsonObject.getInt("ID");
                    Integer idClase = jsonObject.getInt("IDClase");
                    String nombreInstructor = jsonObject.getString("NombreInstructor");
                    String documentoInstructor = jsonObject.getString("DocumentoInstructor");
                    String correoInstructor = jsonObject.getString("CorreoInstructor");

                    // Manejar el campo NombreClase, asignar "Sin Clase Asignada" si es null
                    String nombreClase = jsonObject.isNull("NombreClase") ? "Sin Clase Asignada" : jsonObject.getString("NombreClase");

                    // Agregar los valores en un mapa
                    Map<String, Object> claseInstructorMap = new HashMap<>();
                    claseInstructorMap.put("ID", id);
                    claseInstructorMap.put("IDClase", idClase);
                    claseInstructorMap.put("NombreInstructor", nombreInstructor);
                    claseInstructorMap.put("DocumentoInstructor", documentoInstructor);
                    claseInstructorMap.put("CorreoInstructor", correoInstructor);
                    claseInstructorMap.put("NombreClase", nombreClase);  // Asegurarse de que siempre tenga un valor

                    resultados.add(claseInstructorMap);
                }

            } else {
                throw new RuntimeException("Error HTTP: " + conn.getResponseCode());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return resultados;
    }

    public String obtenerDocumentoPorNombre(String nombreInstructor) throws IOException {
        URL url = new URL("http://localhost:8080/Data/Instructores/obtenerDocumento?nombreInstructor=" + URLEncoder.encode(nombreInstructor, "UTF-8"));
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        if (conn.getResponseCode() == 200) {
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String documento = br.readLine();
            br.close();
            return documento;
        } else {
            throw new RuntimeException("Error al obtener el documento del instructor");
        }
    }

    public String actualizarClaseFormacion(Integer id, String nombreClase, String documentoInstructor) {
        try {
            // Crear la URL con el endpoint correspondiente
            URL url = new URL("http://localhost:8080/Data/ClaseFormacion/actualizar");

            // Configurar la conexión HTTP
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("PUT");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setDoOutput(true);  // Para enviar datos en el cuerpo de la solicitud

            // Crear los parámetros que se enviarán en la solicitud
            String parametros = "id=" + id + "&nombreClase=" + URLEncoder.encode(nombreClase, "UTF-8") +
                    "&documentoInstructor=" + URLEncoder.encode(documentoInstructor, "UTF-8");

            // Escribir los parámetros en el cuerpo de la solicitud
            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = parametros.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            // Leer la respuesta del servidor
            int responseCode = conn.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8));
                StringBuilder response = new StringBuilder();
                String line;

                while ((line = in.readLine()) != null) {
                    response.append(line);
                }

                // Cerrar las conexiones
                in.close();
                conn.disconnect();

                // Retornar el mensaje de éxito
                System.out.println("Respuesta del servidor: " + response.toString());
                return response.toString();
            } else {
                System.out.println("Error al actualizar la clase. Código de respuesta: " + responseCode);
                return "Error al actualizar la clase. Código de respuesta: " + responseCode;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return "Error: " + e.getMessage();
        }
    }

    // Método para eliminar una clase por su ID
    public void eliminarClase(int idClase) {
        try {
            // Configurar la URL del endpoint
            URL url = new URL("http://localhost:8080/Data/ClaseFormacion/eliminar?id=" + idClase);

            // Configurar la conexión HTTP DELETE
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("DELETE");
            conn.setRequestProperty("Accept", "application/json");

            // Verificar la respuesta
            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
            }

            // Leer la respuesta del servidor
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String output;
            StringBuilder response = new StringBuilder();
            while ((output = br.readLine()) != null) {
                response.append(output);
            }

            System.out.println("Respuesta del servidor: " + response.toString());

            conn.disconnect();

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error al eliminar la clase");
        }
    }

    // Método para obtener los aprendices asociados a una ficha
    public List<Map<String, Object>> obtenerAprendicesPorFicha(Integer Ficha) {
        List<Map<String, Object>> aprendices = new ArrayList<>();

        try {
            // Construir la URL para el endpoint
            URL url = new URL("http://localhost:8080/Data/Aprendices/porFicha?Ficha=" + Ficha);
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

                    // Mapear los datos del aprendiz a un Map
                    Map<String, Object> aprendiz = new HashMap<>();
                    aprendiz.put("ID", jsonObject.getInt("ID"));
                    aprendiz.put("NombreAprendiz", jsonObject.getString("NombreAprendiz"));
                    aprendiz.put("Documento", jsonObject.getString("Documento"));

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
            JSONArray jsonArray = new JSONArray(response.toString());

            // Convertir el JSONArray en una lista de mapas
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                fichas.add(jsonObject.toMap());
            }

            conn.disconnect();

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error al obtener las fichas por programa de formación: " + e.getMessage());
        }

        return fichas;
    }

    public Map<String, Object> obtenerClasePorDocumentoInstructor(String documentoInstructor) {
        Map<String, Object> claseFormacion = null;
        try {
            // Codificar el documento del instructor para asegurar que se pase correctamente por la URL
            String documentoCodificado = java.net.URLEncoder.encode(documentoInstructor, "UTF-8");

            // Construir la URL para llamar al endpoint
            String urlString = "http://localhost:8080/Data/ClaseFormacion/porDocumentoInstructor?documentoInstructor=" + documentoCodificado;
            URL url = new URL(urlString);

            // Abrir conexión
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            // Verificar si la respuesta es exitosa (HTTP 200)
            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Error HTTP: " + conn.getResponseCode());
            }

            // Leer la respuesta del servidor
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                response.append(line);
            }

            // Parsear la respuesta JSON
            JSONObject jsonObject = new JSONObject(response.toString());
            claseFormacion = jsonObject.toMap();

            conn.disconnect();

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error al obtener la clase de formación por documento del instructor: " + e.getMessage());
        }

        return claseFormacion;
    }
}
