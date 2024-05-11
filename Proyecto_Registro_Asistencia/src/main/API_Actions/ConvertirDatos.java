/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.API_Actions;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;
import main.AdminFrames.APISecPass;

/**
 *
 * @author IZHAR
 */
public class ConvertirDatos {
    public Integer ObtenerIDTipoDoc(String TipoDocStr){
        try {
            APISecPass APIPass = new APISecPass();
            URL url = new URL("http://localhost:8080/Conversion/TipoDoc_Str_to_ID/" + TipoDocStr);
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
            return Integer.parseInt(content.toString());
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public Integer ObtenerIDTipoGenero(String TipoGeneroStr){
        try {
            APISecPass APIPass = new APISecPass();
            URL url = new URL("http://localhost:8080/Conversion/TipoGenero_Str_to_ID/" + TipoGeneroStr);
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
            return Integer.parseInt(content.toString());
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
        
    public Integer ObtenerIDTipoSede(String TipoSedeStr){
        try {
            APISecPass APIPass = new APISecPass();
            URL url = new URL("http://localhost:8080/Conversion/TipoSede_Str_to_ID/" + TipoSedeStr);
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
            return Integer.parseInt(content.toString());
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
            
    public Integer ObtenerIDTipoRol(String TipoRolStr){
        try {
            APISecPass APIPass = new APISecPass();
            URL url = new URL("http://localhost:8080/Conversion/TipoRol_Str_to_ID/" + TipoRolStr);
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
            return Integer.parseInt(content.toString());
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
