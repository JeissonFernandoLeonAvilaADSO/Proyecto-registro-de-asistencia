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

    public List<Map<String, Object>> BoxFichasFormModel() throws Exception {
        return getFichasFormacionDataFromAPI("Fichas-FormacionData");
    }

    private ArrayList<String> getDataFromAPI(String endpoint) throws Exception {
        HttpURLConnection conn = null;
        try {
            URL url = new URL("http://localhost:8080/" + endpoint);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            int responseCode = conn.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();

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
                throw new Exception("Failed to fetch data from API. Response code: " + responseCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Error occurred while fetching data from API", e);
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
    }

    private List<Map<String, Object>> getFichasFormacionDataFromAPI(String endpoint) throws Exception {
        HttpURLConnection conn = null;
        try {
            URL url = new URL("http://localhost:8080/" + endpoint);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            int responseCode = conn.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                List<Map<String, Object>> dataList = new ArrayList<>();
                JSONArray jsonArray = new JSONArray(response.toString());
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    Map<String, Object> row = new HashMap<>();
                    row.put("ProgramaFormacion", jsonObject.getString("ProgramaFormacion"));
                    row.put("NumeroFicha", jsonObject.getString("NumeroFicha"));
                    dataList.add(row);
                }
                return dataList;
            } else {
                throw new Exception("Failed to fetch data from API. Response code: " + responseCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Error occurred while fetching data from API", e);
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
    }

    public String[] toArray(List<String> list) {
        return list.toArray(new String[0]);
    }
}
