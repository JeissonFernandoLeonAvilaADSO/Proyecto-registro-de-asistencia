package main.util.API_AdminActions;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;

import org.json.JSONObject;

import main.AdminFrames.APISecPass;

/**
 * Clase DB_Admin_ModifInstructor que contiene el método para modificar los datos de un instructor en la base de datos.
 * @author Jeisson Leon
 */
public class API_Admin_ModifUsuario {

    public void AdminModifPerfilUsuario(int DocumentoComparativo,
                                        boolean CambiarID,
                                        boolean CambiarUsuario,
                                        boolean CambiarPass,
                                        boolean CambiarDocumento,
                                        boolean CambiarTipoDoc,
                                        boolean CambiarNombres,
                                        boolean CambiarApellidos,
                                        boolean CambiarGenero,
                                        boolean CambiarTelefono,
                                        boolean CambiarProgramaFormacion,
                                        boolean CambiarNivelFormacion,
                                        boolean CambiarNumeroFicha,
                                        boolean CambiarJornada,
                                        boolean CambiarArea,
                                        boolean CambiarSede,
                                        boolean CambiarCorreo,
                                        boolean CambiarRol,
                                        int NuevoID,
                                        String NuevoUsuario,
                                        String NuevoPass,
                                        int NuevoDocumento,
                                        int NuevoTipoDoc,
                                        String NuevosNombres,
                                        String NuevosApellidos,
                                        int NuevoGenero,
                                        int NuevoTelefono,
                                        int NuevoProgramaFormacion,
                                        int NuevaFicha,
                                        int NuevaJornada,
                                        int NuevoNivelFormacion,
                                        String NuevoArea,
                                        String NuevoCorreo,
                                        int NuevoRol,
                                        int NuevaSede) {


        try {
            APISecPass APIPass = new APISecPass();
            URL url = new URL("http://localhost:8080/ObtenerInstructor/" + DocumentoComparativo);
            String pass = APIPass.GetAPIPass();
            String userCredentials = "user:" + pass; // Reemplaza "username:password" con tus credenciales
            String basicAuth = "Basic " + new String(Base64.getEncoder().encode(userCredentials.getBytes()));

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("PUT");
            conn.setRequestProperty("Accept", "application/json");
            conn.setRequestProperty("Authorization", basicAuth);

            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
            }

            // Crea un objeto UsuarioPUTModel y establece sus campos
            JSONObject usuarioPUTModel = new JSONObject();
            usuarioPUTModel.put("CambiarID", CambiarID);
            usuarioPUTModel.put("NuevoID", NuevoID);
            usuarioPUTModel.put("CambiarUsuario", CambiarUsuario);
            usuarioPUTModel.put("NuevoUsuario", NuevoUsuario);
            usuarioPUTModel.put("CambiarPass", CambiarPass);
            usuarioPUTModel.put("NuevoPass", NuevoPass);
            usuarioPUTModel.put("CambiarDocumento", CambiarDocumento);
            usuarioPUTModel.put("NuevoDocumento", NuevoDocumento);
            usuarioPUTModel.put("CambiarTipoDoc", CambiarTipoDoc);
            usuarioPUTModel.put("NuevoTipoDoc", NuevoTipoDoc);
            usuarioPUTModel.put("CambiarNombres", CambiarNombres);
            usuarioPUTModel.put("NuevosNombres", NuevosNombres);
            usuarioPUTModel.put("CambiarApellidos", CambiarApellidos);
            usuarioPUTModel.put("NuevosApellidos", NuevosApellidos);
            usuarioPUTModel.put("CambiarGenero", CambiarGenero);
            usuarioPUTModel.put("NuevoGenero", NuevoGenero);
            usuarioPUTModel.put("CambiarTelefono", CambiarTelefono);
            usuarioPUTModel.put("NuevoTelefono", NuevoTelefono);
            usuarioPUTModel.put("CambiarProgramaFormacion", CambiarProgramaFormacion);
            usuarioPUTModel.put("NuevoProgramaFormacion", NuevoProgramaFormacion);
            usuarioPUTModel.put("CambiarNivelFormacion", CambiarNivelFormacion);
            usuarioPUTModel.put("NuevoNivelFormacion", NuevoNivelFormacion);
            usuarioPUTModel.put("CambiarNumeroFicha", CambiarNumeroFicha);
            usuarioPUTModel.put("NuevaFicha", NuevaFicha);
            usuarioPUTModel.put("CambiarJornada", CambiarJornada);
            usuarioPUTModel.put("NuevaJornada", NuevaJornada);
            usuarioPUTModel.put("CambiarArea", CambiarArea);
            usuarioPUTModel.put("NuevoArea", NuevoArea);
            usuarioPUTModel.put("CambiarSede", CambiarSede);
            usuarioPUTModel.put("NuevaSede", NuevaSede);
            usuarioPUTModel.put("CambiarCorreo", CambiarCorreo);
            usuarioPUTModel.put("NuevoCorreo", NuevoCorreo);
            usuarioPUTModel.put("CambiarRol", CambiarRol);
            usuarioPUTModel.put("NuevoRol", NuevoRol);

            try(OutputStream os = conn.getOutputStream()) {
                byte[] input = usuarioPUTModel.toString().getBytes("utf-8");
                os.write(input, 0, input.length);
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
