/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.util.API_Login;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;

import main.util.models.UserSession;
import org.json.JSONArray;
import org.json.JSONObject;


/**
 *
 * @author IZHAR
 */

import org.json.JSONObject;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;


public class API_Login_Instructor_Admin {

    public boolean LogUser(String username, String password, String role) {
        try {
            if (role.equalsIgnoreCase("instructor")) {
                return logInstructor(username, password, role);
            } else if (role.equalsIgnoreCase("admin")) {
                return logAdmin(username, password);
            } else {
                JOptionPane.showMessageDialog(null, "Rol no válido. Solo 'Instructor' y 'Admin' están permitidos.", "Error de Rol", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        } catch (Exception e) {
            // Depuración: imprimir traza de la excepción
            System.out.println("Excepción durante la autenticación: ");
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error en el proceso de autenticación. Por favor, intente de nuevo.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    /**
     * Método para manejar el inicio de sesión de los instructores.
     */
    private boolean logInstructor(String username, String password, String role) throws Exception {
        String jsonInputString = String.format(
                "{\"user\": \"%s\", \"password\": \"%s\", \"role\": \"%s\"}",
                URLEncoder.encode(username, StandardCharsets.UTF_8.toString()),
                URLEncoder.encode(password, StandardCharsets.UTF_8.toString()),
                role
        );

        // Depuración: imprimir el cuerpo de la solicitud JSON
        System.out.println("Cuerpo de la solicitud: " + jsonInputString);

        URL url = new URL("http://localhost:8080/login");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Accept", "application/json");
        conn.setRequestProperty("Content-Type", "application/json; utf-8");
        conn.setDoOutput(true);

        // Enviar solicitud
        try (OutputStream os = conn.getOutputStream()) {
            byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
        }

        int responseCode = conn.getResponseCode();
        System.out.println("Código de respuesta: " + responseCode);

        InputStream is;
        if (responseCode >= 200 && responseCode < 300) {
            is = conn.getInputStream();
        } else {
            is = conn.getErrorStream();
        }

        String responseBody = readResponse(is);
        System.out.println("Respuesta del servidor: " + responseBody);

        if (responseCode == HttpURLConnection.HTTP_OK) {
            return handleInstructorResponse(responseBody);
        } else if (responseCode == HttpURLConnection.HTTP_UNAUTHORIZED) {
            handleUnauthorizedResponse(responseBody);
            return false;
        } else {
            JOptionPane.showMessageDialog(null,
                    "Código de respuesta: " + responseCode + "\nError: " + responseBody,
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    /**
     * Método para manejar el inicio de sesión de administradores.
     */
    private boolean logAdmin(String username, String password) throws Exception {
        System.out.println("Autenticando como Admin");
        API_Login_Admin loginAdmin = new API_Login_Admin();
        boolean isAdminLoggedIn = loginAdmin.LogAdmin(username, password);
        System.out.println("Resultado de login de Admin: " + isAdminLoggedIn);
        return isAdminLoggedIn;
    }

    private boolean handleInstructorResponse(String responseBody) throws Exception {
        JSONObject jsonResponse = new JSONObject(responseBody);

        if (jsonResponse.getString("Role").equalsIgnoreCase("Instructor")) {
            UserSession userSession = UserSession.getInstance();
            userSession.setNombres(jsonResponse.getString("FullName"));
            userSession.setTipoDoc(jsonResponse.getString("TipoDocumento"));
            userSession.setDocumento(jsonResponse.getString("Documento"));
            userSession.setRole(jsonResponse.getString("Role"));

            // Obtener las clases del instructor y almacenarlas en la sesión o mostrarlas en la interfaz
            JSONArray clasesArray = jsonResponse.getJSONArray("Clases");
            List<Map<String, Object>> clasesList = new ArrayList<>();
            for (int i = 0; i < clasesArray.length(); i++) {
                JSONObject claseObj = clasesArray.getJSONObject(i);
                clasesList.add(claseObj.toMap());
            }
            userSession.setClasesFormacion(clasesList); // Guardar las clases en la sesión

            return true;
        } else {
            JOptionPane.showMessageDialog(null, "El rol proporcionado no es válido.", "Error de Rol", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    /**
     * Manejo de respuestas no autorizadas (401).
     */
    private void handleUnauthorizedResponse(String responseBody) {
        if (responseBody.contains("No se encontró ninguna clase para el instructor con documento")) {
            JOptionPane.showMessageDialog(null,
                    "No se encontró ninguna clase asociada a tu cuenta.\nPor favor, solicita que te asocien a una clase de formación.",
                    "Asociación Necesaria",
                    JOptionPane.WARNING_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null,
                    "Error en el login: " + responseBody,
                    "Error de Autenticación",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Método para leer la respuesta del servidor desde el InputStream.
     */
    private String readResponse(InputStream is) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
        StringBuilder responseBuilder = new StringBuilder();
        String linea;
        while ((linea = br.readLine()) != null) {
            responseBuilder.append(linea);
        }
        br.close();
        return responseBuilder.toString();
    }
}
