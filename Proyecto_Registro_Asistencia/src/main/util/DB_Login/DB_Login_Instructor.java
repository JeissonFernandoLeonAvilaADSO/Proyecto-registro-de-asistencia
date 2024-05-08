/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.util.DB_Login;

import main.AdminFrames.APISecPass;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;

import org.json.JSONObject;

/**
 *
 * @author IZHAR
 */
public class DB_Login_Instructor {

    public JSONObject InstructorCred;
    public boolean LogInstructor(String UserInstructor, String PassInstructor) {
        try {
            APISecPass APIPass = new APISecPass();
            URL url = new URL("http://localhost:8080/Registro/Instructor/" + UserInstructor + "/" + PassInstructor);
            String pass = APIPass.GetAPIPass();
            String userCredentials = "user:" + pass; // Reemplaza "username:password" con tus credenciales
            String basicAuth = "Basic " + new String(Base64.getEncoder().encode(userCredentials.getBytes()));

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");
            conn.setRequestProperty("Authorization", basicAuth);

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
                InstructorCred = instructor;
                return true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

}
