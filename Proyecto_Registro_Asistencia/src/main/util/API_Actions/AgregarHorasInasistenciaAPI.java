package main.util.API_Actions;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;

public class AgregarHorasInasistenciaAPI {
    public boolean SubirHorasInasistencias(Map<String, List<Map<String, Object>>> listaAprendices) {
        try {
            // Configurar la URL para el endpoint correcto
            URL url = new URL("http://localhost:8080/Horas/ActualizarHoras");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json; charset=utf-8");
            conn.setRequestProperty("Accept", "application/json");
            conn.setDoOutput(true);

            // Crear el JSON desde el mapa de aprendices
            JSONObject obj = new JSONObject(listaAprendices);
            byte[] json = obj.toString().getBytes("UTF-8");

            // Enviar la solicitud
            try (OutputStream os = conn.getOutputStream()) {
                os.write(json, 0, json.length);
            }

            // Manejo de la respuesta
            int responseCode = conn.getResponseCode();
            System.out.println("Response Code : " + responseCode);

            if (responseCode != HttpURLConnection.HTTP_OK) {
                // Leer la respuesta de error si existe
                if (conn.getErrorStream() != null) {
                    try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getErrorStream()))) {
                        String line;
                        while ((line = br.readLine()) != null) {
                            System.out.println(line);
                        }
                    }
                }
                // Lanza excepción con el código de error
                System.out.println("Datos enviados a la API: " + listaAprendices);

                throw new RuntimeException("Failed : HTTP error code : " + responseCode);
            }

            conn.disconnect();
            return true; // Retorna true si la solicitud fue exitosa
        } catch (Exception e) {
            e.printStackTrace();
            return false; // Retorna false si ocurre cualquier error
        }
    }
}