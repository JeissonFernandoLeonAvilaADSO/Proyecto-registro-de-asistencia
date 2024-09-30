package main.util.API_Actions;

import org.apache.poi.poifs.crypt.Decryptor;
import org.apache.poi.poifs.crypt.EncryptionInfo;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONArray;
import org.json.JSONObject;

import java.awt.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class API_AsistenciasApplications {
    public String enviarAsistencia(JSONObject jsonData) {
        try {
            // La URL del endpoint al que se enviará la solicitud
            URL url = new URL("http://localhost:8080/Asistencia/SubirAsistencia");

            // Crear la conexión HTTP
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json; utf-8");
            conn.setRequestProperty("Accept", "application/json");
            conn.setDoOutput(true);

            // Escribir el JSON en la solicitud
            try(OutputStream os = conn.getOutputStream()) {
                byte[] input = jsonData.toString().getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            // Leer la respuesta del servidor
            int responseCode = conn.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8));
                StringBuilder response = new StringBuilder();
                String responseLine;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
                br.close();
                return "Asistencia enviada exitosamente: " + response.toString();
            } else {
                return "Error al enviar la asistencia: HTTP " + responseCode;
            }

        } catch (IOException e) {
            e.printStackTrace();
            return "Ocurrió un error al enviar la asistencia: " + e.getMessage();
        }
    }


    public List<Map<String, Object>> obtenerAsistenciasPorInstructor(String documentoInstructor) {
        List<Map<String, Object>> asistencias = new ArrayList<>();

        try {
            // Crear la URL para llamar al endpoint
            String urlString = "http://localhost:8080/Asistencia/listar/InstructorAsis?documentoInstructor=" + documentoInstructor;
            URL url = new URL(urlString);

            // Abrir la conexión
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
            JSONArray jsonArray = new JSONArray(response.toString());

            // Convertir el JSONArray en una lista de mapas
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                asistencias.add(jsonObject.toMap());
            }

            // Cerrar la conexión
            conn.disconnect();

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error al obtener las asistencias: " + e.getMessage());
        }

        return asistencias;
    }

    public static void abrirExcel(String filePath, String password) {
        try {
            // Leer el archivo XLSX
            Path path = Paths.get(filePath);
            byte[] fileContent = Files.readAllBytes(path);

            // Crear un Workbook desde los bytes leídos
            try (Workbook workbook = new XSSFWorkbook(new ByteArrayInputStream(fileContent))) {

                // Guardar el archivo temporalmente y abrirlo
                Path tempFile = Files.createTempFile("asistencia_temp_", ".xlsx");
                try (FileOutputStream fos = new FileOutputStream(tempFile.toFile())) {
                    workbook.write(fos);
                }

                // Abrir el archivo Excel
                Desktop.getDesktop().open(tempFile.toFile());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Map<String, Object>> obtenerAsistenciasFiltradas(String documentoInstructor, String ambiente, String programaFormacion, Integer ficha) {
        List<Map<String, Object>> asistencias = new ArrayList<>();
        try {
            String urlString = String.format("http://localhost:8080/Asistencia/FiltroListarAsistencias?DocumentoInstructor=%s", documentoInstructor);

            if (ambiente != null && !ambiente.isEmpty()) {
                urlString += "&ambiente=" + URLEncoder.encode(ambiente, "UTF-8");
            }
            if (programaFormacion != null && !programaFormacion.isEmpty()) {
                urlString += "&ProgramaFormacion=" + URLEncoder.encode(programaFormacion, "UTF-8");
            }
            if (ficha != null) {
                urlString += "&ficha=" + ficha;
            }

            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            StringBuilder sb = new StringBuilder();
            String output;
            while ((output = br.readLine()) != null) {
                sb.append(output);
            }
            conn.disconnect();

            JSONArray jsonArray = new JSONArray(sb.toString());
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                asistencias.add(jsonObject.toMap());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return asistencias;
    }

}