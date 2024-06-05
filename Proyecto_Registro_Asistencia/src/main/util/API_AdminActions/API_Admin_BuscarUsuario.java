package main.util.API_AdminActions;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONObject;


public class API_Admin_BuscarUsuario {
    public JSONObject AdminBuscarUsuario(Integer IDUsuario) {

        try {
            URL url = new URL("http://localhost:8080/ObtenerUsuario/" + IDUsuario);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
            }

            try (BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())))) {
                String output;
                JSONObject instructor = null;
                while ((output = br.readLine()) != null) {
                    instructor = new JSONObject(output);
                }
                System.out.println(instructor);
                return instructor;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}


