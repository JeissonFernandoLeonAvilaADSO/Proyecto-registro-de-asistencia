package main.util.models;

import org.json.JSONArray;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONObject;

import javax.swing.*;

public class ComboBoxModels {

    // Método para generar ComboBoxModel basado en el tipo de tabla
    public DefaultComboBoxModel<String> generarComboBoxModelPorTipo(String tipo) {
        System.out.println("Generando ComboBoxModel para el tipo: " + tipo);
        DefaultComboBoxModel<String> comboBoxModel = new DefaultComboBoxModel<>();
        List<String> valores = obtenerValoresPorTipo(tipo);

        if (valores != null && !valores.isEmpty()) {
            comboBoxModel.addElement("Seleccionar...");
            for (String valor : valores) {
                comboBoxModel.addElement(valor);
            }
        } else {
            comboBoxModel.addElement("No hay datos disponibles");
        }

        System.out.println("ComboBoxModel generado: " + comboBoxModel);
        return comboBoxModel;
    }

    // Método para obtener los valores desde el endpoint correspondiente
    private static List<String> obtenerValoresPorTipo(String tipo) {
        List<String> valores = new ArrayList<>();
        try {
            URL url = new URL("http://localhost:8080/Data/" + tipo + "/todos");
//            System.out.println("Conectando a la URL: " + url);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            if (conn.getResponseCode() != 200) {
                System.out.println("Error HTTP: " + conn.getResponseCode());
                throw new RuntimeException("Error HTTP: " + conn.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String output;
            StringBuilder response = new StringBuilder();

            while ((output = br.readLine()) != null) {
                response.append(output);
            }

            // Convertir la respuesta JSON en un JSONArray
            JSONArray jsonArray = new JSONArray(response.toString());
//            System.out.println("Respuesta obtenida del API: " + jsonArray);

            // Recorrer el JSONArray y agregar los valores principales a la lista
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                switch (tipo) {
                    case "ProgramaFormacion":
                        valores.add(jsonObject.getString("ProgramaFormacion"));
                        break;
                    case "Genero":
                        valores.add(jsonObject.getString("TiposGeneros"));
                        break;
                    case "Rol":
                        valores.add(jsonObject.getString("TipoRol"));
                        break;
                    case "TipoDocumento":
                        valores.add(jsonObject.getString("TipoDocumento"));
                        break;
                    case "Area":
                        valores.add(jsonObject.getString("Area"));
                        break;
                    case "ClaseFormacion":
                        valores.add(jsonObject.getString("NombreClase"));
                        break;
                    case "Departamentos":
                        valores.add(jsonObject.getString("nombre_departamento"));
                        break;
                    case "Municipios":
                        valores.add(jsonObject.getString("nombre_municipio"));
                        break;
                    case "Barrios":
                        valores.add(jsonObject.getString("nombre_barrio"));
                        break;
                    case "Instructores":
                        valores.add(jsonObject.getString("NombreInstructor"));
                        break;
                    case "Fichas":
                        valores.add(String.valueOf(jsonObject.get("NumeroFicha")));
                        break;
                    case "Ambientes":
                        valores.add(jsonObject.getString("Ambiente"));
                        break;
                    case "Actividades":
                        valores.add(jsonObject.getString("TipoActividad"));
                        break;
                    default:
                        System.out.println("Campo 'Nombre' no encontrado en el tipo: " + tipo);
                        valores.add(jsonObject.getString(tipo)); // Si no existe un caso específico
                        break;
                }
            }

            conn.disconnect();
        } catch (Exception e) {
            System.out.println("Error obteniendo valores para el tipo " + tipo + ": " + e.getMessage());
            e.printStackTrace();
        }

//        System.out.println("Valores obtenidos: " + valores);
        return valores;
    }
}
