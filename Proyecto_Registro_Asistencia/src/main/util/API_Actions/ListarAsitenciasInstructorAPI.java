/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.util.API_Actions;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.swing.table.DefaultTableModel;

import main.util.models.ComboBoxModels;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author Propietario
 */
public class ListarAsitenciasInstructorAPI {
public List<Map<String, Object>> obtenerAsistencias(String instructor) throws Exception {
        String urlString = "http://localhost:8080/Archives/ListarAsistencias?instructor=" + instructor;
        URL url = new URL(urlString);
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

        JSONArray jsonArray = new JSONArray(sb.toString());
        List<Map<String, Object>> asistencias = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            Map<String, Object> asistencia = jsonObject.toMap();
            asistencias.add(asistencia);
        }

        return asistencias;
    }

public DefaultTableModel llenarTablaAsistencias(String instructor) {
        String[] columnNames = {"Instructor", "Competencia", "Ambiente", "Ficha", "Programa Formación", "Fecha", "Archivo"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        ComboBoxModels Programas = new ComboBoxModels();

        try {
            List<Map<String, Object>> asistencias = obtenerAsistencias(instructor);
            for (Map<String, Object> asistencia : asistencias) {
                Object[] rowData = new Object[columnNames.length];
                rowData[0] = asistencia.get("instructor");
                rowData[1] = asistencia.get("competencia");
                rowData[2] = asistencia.get("ambiente");
                rowData[3] = asistencia.get("ficha");
                rowData[4] = Programas.BoxProgramaFormacionModel().get((Integer) asistencia.get("IDProgramaFormacion") - 1); // "IDProgramaFormacion"  "Programa Formación"
                rowData[5] = asistencia.get("fecha");
                rowData[6] = asistencia.get("IDArchivo"); // "IDArchivo"  "Archivo"
                tableModel.addRow(rowData);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return tableModel;
    }

}
