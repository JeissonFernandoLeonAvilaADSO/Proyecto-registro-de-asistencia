/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.util.API_AdminActions;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import org.json.JSONObject;
import main.util.models.ModTableRequest;

/**
 *
 * @author Propietario
 */
public class API_Admin_DataManager {
    public void enviarDatosTabla(String endpoint, ModTableRequest request) {
        try {
            URL url = new URL("http://localhost:8080/DataMg/" + endpoint);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            conn.setRequestProperty("Accept", "application/json");
            conn.setDoOutput(true);

            // Convertir ModTableRequest a JSON
            JSONObject json = new JSONObject();
            json.put("TablaAnterior", new JSONObject(request.getTablaAnterior()));
            json.put("TablaEntrante", new JSONObject(request.getTablaEntrante()));

            // Enviar la solicitud
            byte[] input = json.toString().getBytes("utf-8");
            try (OutputStream os = conn.getOutputStream()) {
                os.write(input, 0, input.length);
            }

            if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
                throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
            }

            // Cerrar la conexión
            conn.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
