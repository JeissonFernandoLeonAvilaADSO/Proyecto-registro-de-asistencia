package main.util.API_Actions;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;

public class AgregarHorasInasistenciaAPI {
    public boolean SubirHorasInasistencias(Integer ficha,
                                           Map<String, List<Map<String, Object>>> ListaAprendices){
        try {
            URL url = new URL("http://localhost:8080/Horas/HorasInasistencia?ficha=" + ficha);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json; charset=utf-8");
            conn.setRequestProperty("Accept", "application/json");
            conn.setDoOutput(true);

            JSONObject obj = new JSONObject(ListaAprendices);
            byte[] json = obj.toString().getBytes("UTF-8");

            try (OutputStream os = conn.getOutputStream()) {
                os.write(json, 0, json.length);
            }

            if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
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
            int code = conn.getResponseCode();
            System.out.println("Response Code : " + code);

            conn.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }
}
