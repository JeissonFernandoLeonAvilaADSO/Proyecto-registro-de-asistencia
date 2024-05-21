package main.util.models;

import main.AdminFrames.APISecPass;
import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ComboBoxModels {
    public ArrayList<String> BoxTipoGeneroModel() throws Exception {

        URL url = new URL("http://localhost:8080/GeneroData");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Accept", "application/json");

        // Obtiene la respuesta
        int responseCode = conn.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) { // Si el código de respuesta es 200 (HTTP_OK)
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            // Imprime la respuesta
            System.out.println(response.toString());

            // Aquí puedes procesar la respuesta para llenar tu comboBox
            // Por ejemplo, si la respuesta es una lista de Strings en formato JSON, puedes hacer algo como esto:
            ArrayList<String> tiposDocumento = new ArrayList<>();
            JSONArray jsonArray = new JSONArray(response.toString());
            for (int i = 0; i < jsonArray.length(); i++) {
                tiposDocumento.add(jsonArray.getString(i));
            }
            // Luego, puedes usar esta lista para llenar tu comboBox
            return tiposDocumento;
        } else {
            System.out.println("GET request not worked");
        }

        conn.disconnect();
        return null;
    }

    public ArrayList<String> BoxRolModel() throws Exception {

        URL url = new URL("http://localhost:8080/RolData");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Accept", "application/json");

        // Obtiene la respuesta
        int responseCode = conn.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) { // Si el código de respuesta es 200 (HTTP_OK)
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            // Imprime la respuesta
            System.out.println(response.toString());

            // Aquí puedes procesar la respuesta para llenar tu comboBox
            // Por ejemplo, si la respuesta es una lista de Strings en formato JSON, puedes hacer algo como esto:
            ArrayList<String> tiposDocumento = new ArrayList<>();
            JSONArray jsonArray = new JSONArray(response.toString());
            for (int i = 0; i < jsonArray.length(); i++) {
                tiposDocumento.add(jsonArray.getString(i));
            }
            // Luego, puedes usar esta lista para llenar tu comboBox
            return tiposDocumento;
        } else {
            System.out.println("GET request not worked");
        }

        conn.disconnect();
        return null;
    }

    public ArrayList<String> BoxSedeModel() throws Exception {

        URL url = new URL("http://localhost:8080/SedeData");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Accept", "application/json");

        // Obtiene la respuesta
        int responseCode = conn.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) { // Si el código de respuesta es 200 (HTTP_OK)
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            // Imprime la respuesta
            System.out.println(response.toString());

            // Aquí puedes procesar la respuesta para llenar tu comboBox
            // Por ejemplo, si la respuesta es una lista de Strings en formato JSON, puedes hacer algo como esto:
            ArrayList<String> tiposDocumento = new ArrayList<>();
            JSONArray jsonArray = new JSONArray(response.toString());
            for (int i = 0; i < jsonArray.length(); i++) {
                tiposDocumento.add(jsonArray.getString(i));
            }
            // Luego, puedes usar esta lista para llenar tu comboBox
            return tiposDocumento;
        } else {
            System.out.println("GET request not worked");
        }

        conn.disconnect();
        return null;
    }

    public ArrayList<String> BoxTipoDocModel() throws Exception {

        URL url = new URL("http://localhost:8080/TipoDocData");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Accept", "application/json");

        // Obtiene la respuesta
        int responseCode = conn.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) { // Si el código de respuesta es 200 (HTTP_OK)
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            // Imprime la respuesta
            System.out.println(response.toString());

            // Aquí puedes procesar la respuesta para llenar tu comboBox
            // Por ejemplo, si la respuesta es una lista de Strings en formato JSON, puedes hacer algo como esto:
            ArrayList<String> tiposDocumento = new ArrayList<>();
            JSONArray jsonArray = new JSONArray(response.toString());
            for (int i = 0; i < jsonArray.length(); i++) {
                tiposDocumento.add(jsonArray.getString(i));
            }
            // Luego, puedes usar esta lista para llenar tu comboBox
            return tiposDocumento;
        } else {
            System.out.println("GET request not worked");
        }

        conn.disconnect();
        return null;
    }


    public ArrayList<String> BoxJornadaFormacionModel() throws Exception {

        URL url = new URL("http://localhost:8080/JornadaFormacionData");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Accept", "application/json");

        // Obtiene la respuesta
        int responseCode = conn.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) { // Si el código de respuesta es 200 (HTTP_OK)
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            // Imprime la respuesta
            System.out.println(response.toString());

            // Aquí puedes procesar la respuesta para llenar tu comboBox
            // Por ejemplo, si la respuesta es una lista de Strings en formato JSON, puedes hacer algo como esto:
            ArrayList<String> tiposDocumento = new ArrayList<>();
            JSONArray jsonArray = new JSONArray(response.toString());
            for (int i = 0; i < jsonArray.length(); i++) {
                tiposDocumento.add(jsonArray.getString(i));
            }
            // Luego, puedes usar esta lista para llenar tu comboBox
            return tiposDocumento;
        } else {
            System.out.println("GET request not worked");
        }

        conn.disconnect();
        return null;
    }

    public ArrayList<String> BoxNivelFormacionModel() throws Exception {

        URL url = new URL("http://localhost:8080/NivelFormacionData");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Accept", "application/json");

        // Obtiene la respuesta
        int responseCode = conn.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) { // Si el código de respuesta es 200 (HTTP_OK)
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            // Imprime la respuesta
            System.out.println(response.toString());

            // Aquí puedes procesar la respuesta para llenar tu comboBox
            // Por ejemplo, si la respuesta es una lista de Strings en formato JSON, puedes hacer algo como esto:
            ArrayList<String> tiposDocumento = new ArrayList<>();
            JSONArray jsonArray = new JSONArray(response.toString());
            for (int i = 0; i < jsonArray.length(); i++) {
                tiposDocumento.add(jsonArray.getString(i));
            }
            // Luego, puedes usar esta lista para llenar tu comboBox
            return tiposDocumento;
        } else {
            System.out.println("GET request not worked");
        }

        conn.disconnect();
        return null;
    }

    public ArrayList<String> BoxProgramaFormacionModel() throws Exception {

        APISecPass APIPass = new APISecPass();
        URL url = new URL("http://localhost:8080/ProgramaFormacionData");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Accept", "application/json");

        // Obtiene la respuesta
        int responseCode = conn.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) { // Si el código de respuesta es 200 (HTTP_OK)
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            // Imprime la respuesta
            System.out.println(response.toString());

            // Aquí puedes procesar la respuesta para llenar tu comboBox
            // Por ejemplo, si la respuesta es una lista de Strings en formato JSON, puedes hacer algo como esto:
            ArrayList<String> tiposDocumento = new ArrayList<>();
            JSONArray jsonArray = new JSONArray(response.toString());
            for (int i = 0; i < jsonArray.length(); i++) {
                tiposDocumento.add(jsonArray.getString(i));
            }
            // Luego, puedes usar esta lista para llenar tu comboBox
            return tiposDocumento;
        } else {
            System.out.println("GET request not worked");
        }

        conn.disconnect();
        return null;
    }

    public String[] toArray(List<String> list) {
        return list.toArray(new String[0]);
    }
}
