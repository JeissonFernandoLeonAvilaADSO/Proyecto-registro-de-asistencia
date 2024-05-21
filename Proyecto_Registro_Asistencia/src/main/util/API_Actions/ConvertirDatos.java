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
    public Integer ObtenerIDTipoDoc(String tipoDocStr) {
        try {
            String encodedTipoDocStr = URLEncoder.encode(tipoDocStr, StandardCharsets.UTF_8.toString());
            URL url = new URL("http://localhost:8080/Conversion/TipoDoc_Str_to_ID/" + encodedTipoDocStr);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed: HTTP error code: " + conn.getResponseCode());
            }

            try (BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
                String inputLine;
                StringBuilder content = new StringBuilder();
                while ((inputLine = in.readLine()) != null) {
                    content.append(inputLine);
                }
                // Cierra las conexiones
                return Integer.valueOf(content.toString());
            }
        } catch (Exception e) {
            // Maneja la excepción apropiadamente (lanza o registra)
            e.printStackTrace();
        }
        return null;
    }

    public Integer ObtenerIDTipoGenero(String tipoGeneroStr) {
        try {
            String encodedTipoGeneroStr = URLEncoder.encode(tipoGeneroStr, StandardCharsets.UTF_8.toString());
            URL url = new URL("http://localhost:8080/Conversion/TipoGenero_Str_to_ID/" + encodedTipoGeneroStr);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed: HTTP error code: " + conn.getResponseCode());
            }

            try (BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
                String inputLine;
                StringBuilder content = new StringBuilder();
                while ((inputLine = in.readLine()) != null) {
                    content.append(inputLine);
                }
                // Cierra las conexiones
                return Integer.valueOf(content.toString());
            }
        } catch (Exception e) {
            // Maneja la excepción apropiadamente (lanza o registra)
            e.printStackTrace();
        }
        return null;
    }

    public Integer ObtenerIDTipoSede(String tipoSedeStr) {
        try {
            String encodedTipoSedeStr = URLEncoder.encode(tipoSedeStr, StandardCharsets.UTF_8.toString());
            URL url = new URL("http://localhost:8080/Conversion/TipoSede_Str_to_ID/" + encodedTipoSedeStr);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed: HTTP error code: " + conn.getResponseCode());
            }

            try (BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
                String inputLine;
                StringBuilder content = new StringBuilder();
                while ((inputLine = in.readLine()) != null) {
                    content.append(inputLine);
                }
                // Cierra las conexiones
                return Integer.valueOf(content.toString());
            }
        } catch (Exception e) {
            // Maneja la excepción apropiadamente (lanza o registra)
            e.printStackTrace();
        }
        return null;
    }

    public Integer ObtenerIDTipoRol(String tipoRolStr) {
        try {
            String encodedTipoRolStr = URLEncoder.encode(tipoRolStr, StandardCharsets.UTF_8.toString());
            URL url = new URL("http://localhost:8080/Conversion/TipoRol_Str_to_ID/" + encodedTipoRolStr);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed: HTTP error code: " + conn.getResponseCode());
            }

            try (BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
                String inputLine;
                StringBuilder content = new StringBuilder();
                while ((inputLine = in.readLine()) != null) {
                    content.append(inputLine);
                }
                // Cierra las conexiones
                return Integer.valueOf(content.toString());
            }
        } catch (Exception e) {
            // Maneja la excepción apropiadamente (lanza o registra)
            e.printStackTrace();
        }
        return null;
    }
}
