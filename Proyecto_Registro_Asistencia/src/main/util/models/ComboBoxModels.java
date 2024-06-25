package main.util.models;

import org.json.JSONArray;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ComboBoxModels {

    public ArrayList<String> BoxTipoGeneroModel() throws Exception {
        return getDataFromAPI("GeneroData");
    }

    public ArrayList<String> BoxRolModel() throws Exception {
        return getDataFromAPI("RolData");
    }

    public ArrayList<String> BoxSedeModel() throws Exception {
        return getDataFromAPI("SedeData");
    }

    public ArrayList<String> BoxTipoDocModel() throws Exception {
        return getDataFromAPI("TipoDocData");
    }

    public ArrayList<String> BoxJornadaFormacionModel() throws Exception {
        return getDataFromAPI("JornadaFormacionData");
    }

    public ArrayList<String> BoxNivelFormacionModel() throws Exception {
        return getDataFromAPI("NivelFormacionData");
    }

    public ArrayList<String> BoxProgramaFormacionModel() throws Exception {
        return getDataFromAPI("ProgramaFormacionData");
    }
    
    public ArrayList<String> BoxAmbientesModel() throws Exception {
        return getDataFromAPI("AmbientesData");
    }
    
    public ArrayList<String> BoxFichasModel() throws Exception {
        return getDataFromAPI("FichasData");
    }

    private ArrayList<String> getDataFromAPI(String endpoint) throws Exception {
        URL url = new URL("http://localhost:8080/" + endpoint);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Accept", "application/json");

        int responseCode = conn.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            ArrayList<String> dataList = new ArrayList<>();
            JSONArray jsonArray = new JSONArray(response.toString());
            for (int i = 0; i < jsonArray.length(); i++) {
                dataList.add(jsonArray.getString(i));
            }
            return dataList;
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
