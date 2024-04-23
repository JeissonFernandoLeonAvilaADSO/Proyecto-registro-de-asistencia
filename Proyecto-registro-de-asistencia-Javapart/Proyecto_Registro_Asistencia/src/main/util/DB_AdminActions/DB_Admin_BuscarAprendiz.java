package main.util.DB_AdminActions;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DB_Admin_BuscarAprendiz {

    public String ResultadoCedulaAprendiz;
    public String ResultadoNombreAprendiz;
    public String ResultadoApellidoAprendiz;
    public String ResultadoCorreoAprendiz;
    public String ResultadoProgramAprendiz;
    public String ResultadoContraAprendiz;

    public boolean AdminBuscarAprendiz (int IDAprendiz){
        // URL de la base de datos a la que se va a conectar.
        String url = "jdbc:mysql://localhost:3306/db_proyecto_asistencia";
        // Nombre de usuario para la conexión a la base de datos.
        String usuarioDB = "root";
        // Contraseña para la conexión a la base de datos.
        String contraDB = "";

        // Intenta establecer una conexión con la base de datos.
        try (Connection conexion = DriverManager.getConnection(url, usuarioDB, contraDB)) {

            // Consulta SQL para verificar las credenciales del usuario.
            String consulta = "SELECT * FROM `instructor` WHERE ID = ?;";

            // Prepara la consulta SQL.
            try (PreparedStatement ps = conexion.prepareStatement(consulta)) {
                // Establece los parámetros de la consulta.
                ps.setInt(1, IDAprendiz);

                // Ejecuta la consulta y obtiene los resultados en base a si se modificaron columnas.
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        // Si hay un resultado, las credenciales son válidas.
                        ResultadoCedulaAprendiz = rs.getString("id");
                        ResultadoNombreAprendiz = rs.getString("nombres");
                        ResultadoApellidoAprendiz = rs.getString("apellidos");
                        ResultadoCorreoAprendiz = rs.getString("correo");
                        ResultadoProgramAprendiz = rs.getString("prograForm");
                        ResultadoContraAprendiz = rs.getString("contraseña");
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
