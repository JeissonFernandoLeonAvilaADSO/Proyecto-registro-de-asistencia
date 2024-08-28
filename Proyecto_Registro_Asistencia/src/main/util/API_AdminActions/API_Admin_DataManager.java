/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.util.API_AdminActions;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONObject;
import main.util.models.ModTableRequest;

/**
 *
 * @author Propietario
 */
public class API_Admin_DataManager {
    public void enviarDatosTabla(String endpoint, ModTableRequest request) {
        System.out.println(request.getTablaAnterior());
        System.out.println(request.getTablaEntrante());
        if (endpoint == null || endpoint.isEmpty() || request == null) {
            System.err.println("El endpoint o el request no pueden ser nulos o vacíos.");
            return;
        }

        try {
            URL url = new URL("http://localhost:8080/DataMg/" + endpoint);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            conn.setRequestProperty("Accept", "application/json");
            conn.setDoOutput(true);

            JSONObject json = new JSONObject();
            json.put("TablaAnterior", new JSONObject(request.getTablaAnterior()));
            json.put("TablaEntrante", new JSONObject(request.getTablaEntrante()));

            // Depuración: Imprimir los JSON antes de enviar
            System.out.println("Datos enviados: " + json.toString());

            byte[] input = json.toString().getBytes("utf-8");
            try (OutputStream os = conn.getOutputStream()) {
                os.write(input, 0, input.length);
            }

            int responseCode = conn.getResponseCode();
            if (responseCode != HttpURLConnection.HTTP_OK) {
                System.err.println("Error: Código de respuesta HTTP " + responseCode);
            } else {
                System.out.println("Datos enviados correctamente a " + endpoint);
            }

            conn.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

