/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.util.API_AdminActions;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import org.json.JSONObject;

/**
 * Clase para crear usuarios de instructores en la base de datos.
 * @author JeissonLeon
 */
// Esta clase se utiliza para crear un instructor en la base de datos.
public class API_Admin_CreateUsuario {

    // Este método toma los detalles del instructor como parámetros y los inserta en la base de datos.
    public void CrearPerfilUsuario(Map<String, Object> usuarioModel) {
        try {
            System.out.println(usuarioModel);
            URL url = new URL("http://localhost:8080/AgregarUsuario");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            conn.setRequestProperty("Accept", "application/json");
            conn.setDoOutput(true);

            JSONObject json = new JSONObject(usuarioModel);
            byte[] input = json.toString().getBytes("utf-8");
            try (OutputStream os = conn.getOutputStream()) {
                os.write(input, 0, input.length);
            }

            if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
                // Imprime el mensaje de error del servidor
                if (conn.getErrorStream() != null) {
                    try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getErrorStream()))) {
                        String line;
                        while ((line = br.readLine()) != null) {
                            System.out.println(line);
                        }
                    }
                }
                throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
            }

            // Obtiene la respuesta del servidor
            int code = conn.getResponseCode();
            System.out.println("Response Code : " + code);

            // Cierra la conexión
            conn.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

