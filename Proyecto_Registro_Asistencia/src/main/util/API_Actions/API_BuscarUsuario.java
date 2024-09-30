package main.util.API_Actions;



import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import main.util.models.UsersModels.AprendizModel;
import main.util.models.UsersModels.InstructorModel;

public class API_BuscarUsuario {

    // Método para buscar un Aprendiz por documento
    public AprendizModel buscarAprendizPorDocumento(String documento) {
        String apiUrl = "http://localhost:8080/Aprendiz/" + documento;  // Asegúrate de que la URL sea correcta
        String jsonResponse = hacerPeticionGET(apiUrl);
        
        // Si el resultado no es null, convertirlo en un objeto AprendizModel
        if (jsonResponse != null) {
            return convertirJsonAAprendizModel(jsonResponse);
        } else {
            return null;  // Retornar null si no se encuentra el aprendiz
        }
    }

    // Método para buscar un Instructor por documento
    public InstructorModel buscarInstructorPorDocumento(String documento) {
        String apiUrl = "http://localhost:8080/Instructor/" + documento;  // Asegúrate de que la URL sea correcta
        String jsonResponse = hacerPeticionGET(apiUrl);
        
        // Si el resultado no es null, convertirlo en un objeto InstructorModel
        if (jsonResponse != null) {
            return convertirJsonAInstructorModel(jsonResponse);
        } else {
            return null;  // Retornar null si no se encuentra el instructor
        }
    }

    // Método común para realizar peticiones GET
    private String hacerPeticionGET(String apiUrl) {
        StringBuilder resultado = new StringBuilder();
        try {
            URL url = new URL(apiUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            int responseCode = conn.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {  // 200 OK
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    resultado.append(inputLine);
                }
                in.close();
            } else if (responseCode == HttpURLConnection.HTTP_NOT_FOUND) {  // 404 NOT FOUND
                System.out.println("Usuario no encontrado.");
                return null;
            } else {
                System.out.println("Error en la solicitud: " + responseCode);
                return null;
            }

            conn.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return resultado.toString();  // Retornar la respuesta como String (en formato JSON)
    }

    // Método para convertir el JSON de Aprendiz en AprendizModel
    private AprendizModel convertirJsonAAprendizModel(String jsonResponse) {
        JSONObject json = new JSONObject(jsonResponse);

        // Crear y devolver un AprendizModel a partir del JSON
        AprendizModel aprendiz = new AprendizModel(
                json.getString("User"),
                json.getString("Password"),
                json.getString("Documento"),
                json.getString("TipoDocumento"),
                json.getString("Nombres"),
                json.getString("Apellidos"),
                Date.valueOf(json.getString("FecNacimiento")),  // Convertir la fecha en java.sql.Date
                json.getString("Telefono"),
                json.getString("Correo"),
                json.getString("Genero"),
                json.getString("Residencia"),
                json.getInt("NumeroFicha"),  // Número de ficha como int
                json.getString("ProgramaFormacion"),
                json.getString("JornadaFormacion"),
                json.getString("NivelFormacion"),
                json.getString("Sede"),
                json.getString("Area")
        );

        return aprendiz;
    }

    // Método para convertir el JSON de Instructor en InstructorModel
    private InstructorModel convertirJsonAInstructorModel(String jsonResponse) {
        JSONObject json = new JSONObject(jsonResponse);

        // Crear listas para las fichas y otros atributos
        List<Integer> fichas = new ArrayList<>();
        for (Object ficha : json.getJSONArray("Fichas")) {
            fichas.add((Integer) ficha);
        }

        List<String> programasFormacion = new ArrayList<>();
        for (Object programa : json.getJSONArray("ProgramasFormacion")) {
            programasFormacion.add((String) programa);
        }

        List<String> jornadasFormacion = new ArrayList<>();
        for (Object jornada : json.getJSONArray("JornadasFormacion")) {
            jornadasFormacion.add((String) jornada);
        }

        List<String> nivelesFormacion = new ArrayList<>();
        for (Object nivel : json.getJSONArray("NivelesFormacion")) {
            nivelesFormacion.add((String) nivel);
        }

        List<String> sedes = new ArrayList<>();
        for (Object sede : json.getJSONArray("CentrosFormacion")) {
            sedes.add((String) sede);
        }

        List<String> areas = new ArrayList<>();
        for (Object area : json.getJSONArray("Areas")) {
            areas.add((String) area);
        }

        // Crear y devolver un InstructorModel a partir del JSON
        InstructorModel instructor = new InstructorModel(
                json.getString("User"),
                json.getString("Password"),
                json.getString("Documento"),
                json.getString("TipoDocumento"),
                json.getString("Nombres"),
                json.getString("Apellidos"),
                Date.valueOf(json.getString("FecNacimiento")),  // Convertir la fecha en java.sql.Date
                json.getString("Telefono"),
                json.getString("Correo"),
                json.getString("Genero"),
                json.getString("Residencia"),
                json.getString("ClaseFormacion"),
                fichas,
                programasFormacion,
                jornadasFormacion,
                nivelesFormacion,
                sedes,
                areas
        );

        return instructor;
    }
}
