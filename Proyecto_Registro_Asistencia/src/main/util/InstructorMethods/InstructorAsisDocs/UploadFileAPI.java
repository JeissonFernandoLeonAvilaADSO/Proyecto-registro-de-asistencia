/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.util.InstructorMethods.InstructorAsisDocs;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.util.Map;



/**
 *
 * @author Propietario
 */
public class UploadFileAPI {
    public void uploadFileAPI(File file, Map<String, Object> params) {
        String targetUrl = "http://localhost:8080/Archives/ExcelUp";
        String boundary = "===" + System.currentTimeMillis() + "===";
        String lineEnd = "\r\n";
        String twoHyphens = "--";

        String mimeType = null;
        try {
            mimeType = Files.probeContentType(file.toPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (mimeType == null) {
            mimeType = "application/octet-stream"; // Tipo MIME por defecto
        }

        try {
            System.out.println(params);
            HttpURLConnection connection = (HttpURLConnection) new URL(targetUrl).openConnection();
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);

            try (OutputStream outputStream = connection.getOutputStream();
                 FileInputStream inputStream = new FileInputStream(file)) {

                // Escribir parámetros
                for (Map.Entry<String, Object> entry : params.entrySet()) {
                    writeParam(outputStream, boundary, entry.getKey(), entry.getValue().toString());
                }

                // Escribir archivo
                outputStream.write((twoHyphens + boundary + lineEnd).getBytes());
                outputStream.write(("Content-Disposition: form-data; name=\"archivo\"; filename=\"" + file.getName() + "\"" + lineEnd).getBytes());
                outputStream.write(("Content-Type: " + mimeType + lineEnd).getBytes());
                outputStream.write(lineEnd.getBytes());

                byte[] buffer = new byte[4096];
                int bytesRead;
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }

                outputStream.write(lineEnd.getBytes());
                outputStream.write((twoHyphens + boundary + twoHyphens + lineEnd).getBytes());

                outputStream.flush();

                int responseCode = connection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    System.out.println("Archivo subido exitosamente.");
                } else {
                    System.out.println("Error al subir el archivo: " + responseCode);
                    try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getErrorStream()))) {
                        String line;
                        while ((line = br.readLine()) != null) {
                            System.out.println(line);
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void writeParam(OutputStream outputStream, String boundary, String name, String value) throws IOException {
        String lineEnd = "\r\n";
        String twoHyphens = "--";
        outputStream.write((twoHyphens + boundary + lineEnd).getBytes());
        outputStream.write(("Content-Disposition: form-data; name=\"" + name + "\"" + lineEnd).getBytes());
        outputStream.write(lineEnd.getBytes());
        outputStream.write(value.getBytes());
        outputStream.write(lineEnd.getBytes());
    }
}
