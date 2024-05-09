package main.util.API_AdminActions;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;
import main.AdminFrames.APISecPass;

/**
 * Clase DB_Admin_ModifInstructor que contiene el método para modificar los datos de un instructor en la base de datos.
 * @author Jeisson Leon
 */
public class API_Admin_ModifInstructor {

    public void AdminModifInstructor(int IDInstructor,
                                    boolean CambiarDocumento,
                                    boolean CambiarTipoDoc,
                                    boolean CambiarNombres,
                                    boolean CambiarApellidos,
                                    boolean CambiarGenero,
                                    boolean CambiarTelefono,
                                    boolean CambiarArea,
                                    boolean CambiarRol,
                                    boolean CambiarSede,
                                    int NuevoDocumento,
                                    int NuevoTipoDoc,
                                    String NuevosNombres,
                                    String NuevosApellidos,
                                    int NuevoGenero,
                                    int NuevoTelefono,
                                    String NuevoArea,
                                    int NuevoRol,
                                    int NuevaSede) {

         try {
            // Crea la conexión
          APISecPass APIPass = new APISecPass();
            URL url = new URL("http://localhost:8080/ModificarInstructor/" + IDInstructor);
            String pass = APIPass.GetAPIPass();
            String userCredentials = "user:" + pass; // Reemplaza "username:password" con tus credenciales
            String basicAuth = "Basic " + new String(Base64.getEncoder().encode(userCredentials.getBytes()));

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("PUT");
            conn.setRequestProperty("Accept", "application/json");
            conn.setRequestProperty("Authorization", basicAuth);

            // Crea el cuerpo de la solicitud
            String jsonInputString = "{"
                    + "\"CambiarDocumento\": \"" + CambiarDocumento + "\","
                    + "\"CambiarTipoDoc\": \"" + CambiarTipoDoc + "\","
                    + "\"CambiarNombres\": \"" + CambiarNombres + "\","
                    + "\"CambiarApellidos\": \"" + CambiarApellidos + "\","
                    + "\"CambiarGenero\": \"" + CambiarGenero + "\","
                    + "\"CambiarTelefono\": \"" + CambiarTelefono + "\","
                    + "\"CambiarArea\": \"" + CambiarArea + "\","
                    + "\"CambiarRol\": \"" + CambiarRol + "\","
                    + "\"CambiarSede\": \"" + CambiarSede + "\","
                    + "\"NuevoDocumento\": " + NuevoDocumento + ","
                    + "\"NuevoTipoDoc\": " + NuevoTipoDoc + ","
                    + "\"NuevosNombres\": \"" + NuevosNombres + "\","
                    + "\"NuevosApellidos\": \"" + NuevosApellidos + "\","
                    + "\"NuevoGenero\": " + NuevoGenero + ","
                    + "\"NuevoTelefono\": " + NuevoTelefono + ","
                    + "\"NuevoArea\": \"" + NuevoArea + "\","
                    + "\"NuevoRol\": " + NuevoRol + ","
                    + "\"NuevaSede\": " + NuevaSede
                    + "}";

            // Envia la solicitud
            try(OutputStream os = conn.getOutputStream()) {
                byte[] input = jsonInputString.getBytes("utf-8");
                os.write(input, 0, input.length);           
            }

            // Obtiene la respuesta
            int code = conn.getResponseCode();
            System.out.println(code);

            conn.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
