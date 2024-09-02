package main.util.API_AdminActions;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

import org.json.JSONObject;


/**
 * Clase DB_Admin_ModifInstructor que contiene el método para modificar los datos de un instructor en la base de datos.
 * @author Jeisson Leon
 */
public class API_Admin_ModifUsuario {

    public void AdminModifPerfilUsuario(Integer documentoComparativo, Map<String, Object> usuarioPUTModel) {
        try {
            // Verifica que la URL tenga el prefijo correcto para coincidir con el servidor
            URL url = new URL("http://localhost:8080/ModificarUsuario/" + documentoComparativo);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("PUT");
            conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            conn.setRequestProperty("Accept", "application/json");
            conn.setDoOutput(true);

            JSONObject json = new JSONObject(usuarioPUTModel);
            byte[] input = json.toString().getBytes("utf-8");
            try (OutputStream os = conn.getOutputStream()) {
                os.write(input, 0, input.length);
            }

            int responseCode = conn.getResponseCode();
            if (responseCode != HttpURLConnection.HTTP_OK) {
                // Manejo de errores
                if (conn.getErrorStream() != null) {
                    try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getErrorStream()))) {
                        String line;
                        while ((line = br.readLine()) != null) {
                            System.out.println(line);
                        }
                    }
                }
                throw new RuntimeException("Failed : HTTP error code : " + responseCode);
            }

            System.out.println("Response Code : " + responseCode);
            conn.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
