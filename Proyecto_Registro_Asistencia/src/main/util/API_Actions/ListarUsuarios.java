/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.util.API_Actions;

import org.json.JSONArray;
import org.json.JSONObject;

import javax.swing.table.DefaultTableModel;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 *
 * @author Propietario
 */
public class ListarUsuarios {
    public static DefaultTableModel getAprendices(int ficha) {
        DefaultTableModel model = new DefaultTableModel(new String[]{"Documento", "Nombre"}, 0);
            String URL = "http://localhost:8080/ListarUsuarios/ListarAprendices?ficha=";
        try {
            URL url = new URL(URL + ficha);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
            StringBuilder sb = new StringBuilder();
            String output;
            while ((output = br.readLine()) != null) {
                sb.append(output);
            }

            conn.disconnect();

            JSONObject jsonResponse = new JSONObject(sb.toString());
            JSONArray aprendicesArray = jsonResponse.getJSONArray("aprendices");

            for (int i = 0; i < aprendicesArray.length(); i++) {
                JSONObject aprendiz = aprendicesArray.getJSONObject(i);
                String documento = aprendiz.getString("Documento");
                String nombre = aprendiz.getString("Nombres") + " " + aprendiz.getString("Apellidos");
                model.addRow(new Object[]{documento, nombre});
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return model;
    }
}
