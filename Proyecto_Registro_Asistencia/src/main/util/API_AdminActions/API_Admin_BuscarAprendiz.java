package main.util.API_AdminActions;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class API_Admin_BuscarAprendiz {

    public String ResultadoDocumentoAprendiz;
    public String ResultadoNombreAprendiz;
    public String ResultadoApellidoAprendiz;
    public String ResultadoTipoDocuAprendiz;
    public String ResultadoGeneroAprendiz;
    public String ResultadoNumeroFichaAprendiz;
    public String ResultadoProgramAprendiz;
    public String ResultadoJornadaAprendiz;
    public String ResultadoNivelAprendiz;
    public String ResultadoCentroAprendiz;
    public String ResultadoCorreoAprendiz;
    public int ResultadoTelAprendiz;
    
    public boolean AdminBuscarAprendiz (int IDAprendiz){
        // URL de la base de datos a la que se va a conectar.
        String url = "jdbc:mysql://localhost:3307/db_proyecto_asistencia";
        // Nombre de usuario para la conexión a la base de datos.
        String usuarioDB = "root";
        // Contraseña para la conexión a la base de datos.
        String contraDB = "";

        // Intenta establecer una conexión con la base de datos.
        try (Connection conexion = DriverManager.getConnection(url, usuarioDB, contraDB)) {

            // Consulta SQL para verificar las credenciales del usuario.
            String consulta = "SELECT * FROM `aprendiz` WHERE ID = ?;";

            // Prepara la consulta SQL.
            try (PreparedStatement ps = conexion.prepareStatement(consulta)) {
                // Establece los parámetros de la consulta.
                ps.setInt(1, IDAprendiz);

                // Ejecuta la consulta y obtiene los resultados en base a si se modificaron columnas.
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        // Si hay un resultado, las credenciales son válidas.
                        ResultadoDocumentoAprendiz = rs.getString("id");
                        ResultadoNombreAprendiz = rs.getString("nombres");
                        ResultadoApellidoAprendiz = rs.getString("apellidos");
                        ResultadoTipoDocuAprendiz = rs.getString("tipoDocu");
                        ResultadoGeneroAprendiz = rs.getString("genero");
                        ResultadoNumeroFichaAprendiz = rs.getString("numFicha");
                        ResultadoProgramAprendiz = rs.getString("prograForm");
                        ResultadoJornadaAprendiz = rs.getString("jornadaForm");
                        ResultadoNivelAprendiz = rs.getString("nivelForm");
                        ResultadoCentroAprendiz = rs.getString("centroForm");
                        ResultadoCorreoAprendiz = rs.getString("correo");
                        ResultadoTelAprendiz = rs.getInt("telefono");
                        return true;
                    } else {
                        // Si no hay resultados, las credenciales no son válidas.

                        return false;
                    }
                }
            }
        } catch (Exception e) {
            // Imprime la traza de la pila de la excepción en caso de que ocurra un error.
            e.printStackTrace();
        }
        // Si llega hasta aquí, algo salió mal, por lo que retorna false.
        return false;
    }


}
