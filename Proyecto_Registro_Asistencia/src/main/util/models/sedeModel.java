/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.util.models;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import main.AdminFrames.APISecPass;
import org.json.JSONArray;

/**
 *
 * @author Propietario
 */
public class sedeModel {
    public ArrayList<String> BoxSedeModel() throws Exception {

        APISecPass APIPass = new APISecPass();
        URL url = new URL("http://localhost:8080/SedeData");
        String pass = APIPass.GetAPIPass();
        String userCredentials = "user:" + pass;
        String basicAuth = "Basic " + new String(Base64.getEncoder().encode(userCredentials.getBytes()));

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Accept", "application/json");
        conn.setRequestProperty("Authorization", basicAuth);

        // Obtiene la respuesta
        int responseCode = conn.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) { // Si el código de respuesta es 200 (HTTP_OK)
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            // Imprime la respuesta
            System.out.println(response.toString());

            // Aquí puedes procesar la respuesta para llenar tu comboBox
            // Por ejemplo, si la respuesta es una lista de Strings en formato JSON, puedes hacer algo como esto:
            ArrayList<String> tiposDocumento = new ArrayList<>();
            JSONArray jsonArray = new JSONArray(response.toString());
            for (int i = 0; i < jsonArray.length(); i++) {
                tiposDocumento.add(jsonArray.getString(i));
            }
            // Luego, puedes usar esta lista para llenar tu comboBox
            return tiposDocumento;
        } else {
            System.out.println("GET request not worked");
        }

        conn.disconnect();
        return null;
    }
    
    public String[] toArray(List<String> list) {
        return list.toArray(new String[0]);
    }
}