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
        return obtenerID("TipoDoc_Str_to_ID", tipoDocStr);
    }

    public Integer ObtenerIDTipoGenero(String tipoGeneroStr) {
        return obtenerID("TipoGenero_Str_to_ID", tipoGeneroStr);
    }

    public Integer ObtenerIDTipoSede(String tipoSedeStr) {
        return obtenerID("TipoSede_Str_to_ID", tipoSedeStr);
    }

    public Integer ObtenerIDTipoRol(String tipoRolStr) {
        return obtenerID("TipoRol_Str_to_ID", tipoRolStr);
    }

    public Integer ObtenerIDProgramaFormacion(String programaFormacionStr) {
        return obtenerID("ProgramaFormacion_Str_to_ID", programaFormacionStr);
    }

    public Integer ObtenerIDNivelFormacion(String nivelFormacionStr) {
        return obtenerID("NivelFormacion_Str_to_ID", nivelFormacionStr);
    }

    public Integer ObtenerIDJornadaFormacion(String jornadaFormacionStr) {
        return obtenerID("JornadaFormacion_Str_to_ID", jornadaFormacionStr);
    }

    private Integer obtenerID(String endpoint, String tipoStr) {
        try {
            String encodedTipoStr = URLEncoder.encode(tipoStr, StandardCharsets.UTF_8.toString());
            URL url = new URL("http://localhost:8080/Conversion/" + endpoint + "/" + encodedTipoStr);
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
