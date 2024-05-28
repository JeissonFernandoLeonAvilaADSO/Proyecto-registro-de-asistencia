package main.util.API_AdminActions;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import javax.swing.JOptionPane;

public class API_Admin_DeletUsuario {
    public String AdminEliminarUsuario(Integer IDUsuario) {
        try {
            URL url = new URL("http://localhost:8080/EliminarUsuario/" + IDUsuario);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("DELETE");
            conn.setRequestProperty("Accept", "application/json");

            int responseCode = conn.getResponseCode();
            if (responseCode != 200) {
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
                StringBuilder errorResponse = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) {
                    errorResponse.append(line);
                }
                br.close();
                throw new RuntimeException("Failed : HTTP error code : " + responseCode + " : " + errorResponse.toString());
            }

            try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
                StringBuilder response = new StringBuilder();
                String output;
                while ((output = br.readLine()) != null) {
                    response.append(output);
                }
                return response.toString();
            }

        } catch (Exception e) {
            e.printStackTrace();
            return "Error: " + e.getMessage();
        }
    }
}
