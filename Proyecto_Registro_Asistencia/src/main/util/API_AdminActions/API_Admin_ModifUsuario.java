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

    public void AdminModifPerfilUsuario(Integer DocumentoComparativo,
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
                                        Integer NuevoID,
                                        String NuevoUsuario,
                                        String NuevoPass,
                                        Integer NuevoDocumento,
                                        Integer NuevoTipoDoc,
                                        String NuevosNombres,
                                        String NuevosApellidos,
                                        Integer NuevoGenero,
                                        Integer NuevoTelefono,
                                        Integer NuevoProgramaFormacion,
                                        Integer NuevaFicha,
                                        Integer NuevaJornada,
                                        Integer NuevoNivelFormacion,
                                        String NuevoArea,
                                        String NuevoCorreo,
                                        Integer NuevoRol,
                                        Integer NuevaSede) {


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
            usuarioPUTModel.put("CambiarUsuario", CambiarUsuario);
            usuarioPUTModel.put("CambiarPass", CambiarPass);
            usuarioPUTModel.put("CambiarDocumento", CambiarDocumento);
            usuarioPUTModel.put("CambiarTipoDoc", CambiarTipoDoc);
            usuarioPUTModel.put("CambiarNombres", CambiarNombres);
            usuarioPUTModel.put("CambiarApellidos", CambiarApellidos);
            usuarioPUTModel.put("CambiarGenero", CambiarGenero);
            usuarioPUTModel.put("CambiarTelefono", CambiarTelefono);
            usuarioPUTModel.put("CambiarProgramaFormacion", CambiarProgramaFormacion);
            usuarioPUTModel.put("CambiarNivelFormacion", CambiarNivelFormacion);
            usuarioPUTModel.put("CambiarNumeroFicha", CambiarNumeroFicha);
            usuarioPUTModel.put("CambiarJornada", CambiarJornada);
            usuarioPUTModel.put("CambiarArea", CambiarArea);
            usuarioPUTModel.put("CambiarSede", CambiarSede);
            usuarioPUTModel.put("CambiarCorreo", CambiarCorreo);
            usuarioPUTModel.put("CambiarRol", CambiarRol);
            usuarioPUTModel.put("NuevoID", NuevoID);
            usuarioPUTModel.put("NuevoUsuario", NuevoUsuario);
            usuarioPUTModel.put("NuevoPass", NuevoPass);
            usuarioPUTModel.put("NuevoDocumento", NuevoDocumento);
            usuarioPUTModel.put("NuevoTipoDoc", NuevoTipoDoc);
            usuarioPUTModel.put("NuevosNombres", NuevosNombres);
            usuarioPUTModel.put("NuevosApellidos", NuevosApellidos);
            usuarioPUTModel.put("NuevoGenero", NuevoGenero);
            usuarioPUTModel.put("NuevoTelefono", NuevoTelefono);
            usuarioPUTModel.put("NuevoProgramaFormacion", NuevoProgramaFormacion);
            usuarioPUTModel.put("NuevoNivelFormacion", NuevoNivelFormacion);
            usuarioPUTModel.put("NuevaFicha", NuevaFicha);
            usuarioPUTModel.put("NuevaJornada", NuevaJornada);
            usuarioPUTModel.put("NuevoArea", NuevoArea);
            usuarioPUTModel.put("NuevaSede", NuevaSede);
            usuarioPUTModel.put("NuevoCorreo", NuevoCorreo);
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
