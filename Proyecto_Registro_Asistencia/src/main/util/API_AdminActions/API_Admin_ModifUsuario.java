package main.util.API_AdminActions;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import main.AdminFrames.APISecPass;

/**
 * Clase DB_Admin_ModifInstructor que contiene el método para modificar los datos de un instructor en la base de datos.
 * @author Jeisson Leon
 */
public class API_Admin_ModifUsuario {

    public void AdminModifPerfilUsuario(Integer DocumentoComparativo, Map<String, Object> usuarioPUTModel) {
        System.out.println(usuarioPUTModel);
        try {
            APISecPass APIPass = new APISecPass();
            URL url = new URL("http://localhost:8080/ModificarInstructor/" + DocumentoComparativo);
            String pass = APIPass.GetAPIPass();
            String userCredentials = "user:" + pass;
            String basicAuth = "Basic " + new String(Base64.getEncoder().encode(userCredentials.getBytes()));

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("PUT");
            conn.setRequestProperty("Accept", "application/json");
            conn.setRequestProperty("Authorization", basicAuth);
            conn.setRequestProperty("Content-Type", "application/json; utf-8");

            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
            }

            conn.setDoOutput(true);
            try(OutputStream os = conn.getOutputStream()) {
                JSONObject json = new JSONObject(usuarioPUTModel);
                byte[] input = json.toString().getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            // Obtiene la respuesta del servidor
            int code = conn.getResponseCode();
            System.out.println("Response Code : " + code);

            // Cierra la conexión
            conn.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
