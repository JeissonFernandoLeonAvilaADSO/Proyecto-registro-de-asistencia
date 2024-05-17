/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.util.API_Login;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


/**
 *
 * @author IZHAR
 */
public class API_Login_Instructor {

    public int InstructorCred;
    public boolean LogInstructor(String UserInstructor, String PassInstructor) {
        try {
            URL url = new URL("http://localhost:8080/Registro/Instructor/" + UserInstructor + "/" + PassInstructor);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");
            
            if (conn.getResponseCode() == 200) {
                try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = br.readLine()) != null) {
                        response.append(line);
                    }
                    InstructorCred = Integer.parseInt(response.toString()); // Asigna el valor a la variable de instancia
                    return true;
                }
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
