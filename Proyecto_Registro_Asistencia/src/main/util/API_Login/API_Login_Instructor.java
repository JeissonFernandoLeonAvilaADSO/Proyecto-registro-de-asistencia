/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.util.API_Login;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;


/**
 *
 * @author IZHAR
 */
public class API_Login_Instructor {

    public Map<String, Object> instructorCred;

    public boolean LogInstructor(String UserInstructor, String PassInstructor) {
        try {
            String encodedUser = URLEncoder.encode(UserInstructor, StandardCharsets.UTF_8.toString());
            String encodedPass = URLEncoder.encode(PassInstructor, StandardCharsets.UTF_8.toString());
            URL url = new URL("http://localhost:8080/Registro/Instructor/" + encodedUser + "/" + encodedPass);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            if (conn.getResponseCode() == 200) {
                try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8))) {
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = br.readLine()) != null) {
                        response.append(line);
                    }

                    ObjectMapper mapper = new ObjectMapper();
                    instructorCred = mapper.readValue(response.toString(), new TypeReference<Map<String, Object>>() {});
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
