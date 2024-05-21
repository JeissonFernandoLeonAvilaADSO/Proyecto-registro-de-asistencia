/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.util.API_Login;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;
import main.AdminFrames.APISecPass;

/**
 * Clase para verificar el inicio de sesión de un administrador en la base de datos.
 * @author JeissonLeon
 */
public class API_Login_Admin {


    public boolean LogAdmin(String userAdmin, String passAdmin) {
        try {
            APISecPass APIPass = new APISecPass();
            var url = new URL("http://localhost:8080/Registro/Administrador/" + userAdmin + "/" + passAdmin);
            String pass = APIPass.GetAPIPass();
            String userCredentials = "user:" + pass; // Reemplaza "username:password" con tus credenciales
            String basicAuth = "Basic " + new String(Base64.getEncoder().encode(userCredentials.getBytes()));

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");
            conn.setRequestProperty("Authorization", basicAuth);

            int responseCode = conn.getResponseCode();
            if (responseCode == 200) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String response = reader.readLine();
                return Boolean.parseBoolean(response);
            } else {
                System.out.println("Error al consultar el controlador. Código de respuesta: " + responseCode);
                // Aquí puedes manejar específicamente el código de respuesta 401 si lo deseas
            }
        } catch (Exception e) {
            e.printStackTrace();
            // Maneja excepciones específicas aquí (por ejemplo, MalformedURLException, IOException, etc.)
        }
        return false;
    }
}




