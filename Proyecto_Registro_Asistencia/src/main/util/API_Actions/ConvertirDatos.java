/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.util.API_Actions;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;


/**
 *
 * @author IZHAR
 */
public class ConvertirDatos {
    public Integer ObtenerIDTipoDoc(String TipoDocStr){
        try {
            String encodedTipoDocStr = URLEncoder.encode(TipoDocStr, StandardCharsets.UTF_8.toString());
            URL url = new URL("http://localhost:8080/Conversion/TipoDoc_Str_to_ID/" + encodedTipoDocStr);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
            }
            
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuilder content = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }

            // Cierra las conexiones
            in.close();
            conn.disconnect();

            // Retorna el ID obtenido
            return Integer.valueOf(content.toString());
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public Integer ObtenerIDTipoGenero(String TipoGeneroStr){
        try {
            String encodedTipoDocStr = URLEncoder.encode(TipoGeneroStr, StandardCharsets.UTF_8.toString());
            URL url = new URL("http://localhost:8080/Conversion/TipoGenero_Str_to_ID/" + TipoGeneroStr);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
            }
            
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuilder content = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }

            // Cierra las conexiones
            in.close();
            conn.disconnect();

            // Retorna el ID obtenido
            return Integer.valueOf(content.toString());
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
        
    public Integer ObtenerIDTipoSede(String TipoSedeStr){
        try {
            String encodedTipoDocStr = URLEncoder.encode(TipoSedeStr, StandardCharsets.UTF_8.toString());
            URL url = new URL("http://localhost:8080/Conversion/TipoSede_Str_to_ID/" + TipoSedeStr);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
            }
            
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuilder content = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }

            // Cierra las conexiones
            in.close();
            conn.disconnect();

            // Retorna el ID obtenido
            return Integer.valueOf(content.toString());
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
            
    public Integer ObtenerIDTipoRol(String TipoRolStr){
        try {
            String encodedTipoDocStr = URLEncoder.encode(TipoRolStr, StandardCharsets.UTF_8.toString());
            URL url = new URL("http://localhost:8080/Conversion/TipoRol_Str_to_ID/" + TipoRolStr);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
            }
            
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuilder content = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }

            // Cierra las conexiones
            in.close();
            conn.disconnect();

            // Retorna el ID obtenido
            return Integer.valueOf(content.toString());
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
