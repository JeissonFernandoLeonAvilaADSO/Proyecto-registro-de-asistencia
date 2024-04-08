/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.util.DB_Login;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;

/**
 * Clase para verificar el inicio de sesión de un administrador en la base de datos.
 * @author JeissonLeon
 */
public class DB_Login_Admin {

    /**
     * Verifica si las credenciales de inicio de sesión son válidas.
     *
     * @param UserAdmin Nombre de usuario ingresado.
     * @param PassAdmin Contraseña ingresada.
     * @return true si las credenciales son válidas, false si no lo son.
     */
public boolean LogAdmin(String UserAdmin, String PassAdmin) {
    // URL de la base de datos a la que se va a conectar.
    String url = "jdbc:mysql://localhost:3306/db_proyecto_asistencia";
    // Nombre de usuario para la conexión a la base de datos.
    String usuarioDB = "root";
    // Contraseña para la conexión a la base de datos.
    String contraDB = "";

    // Intenta establecer una conexión con la base de datos.
    try (Connection conexion = DriverManager.getConnection(url, usuarioDB, contraDB)) {
        // Obtiene el nombre de usuario y la contraseña ingresados por el usuario.
        String usuario = UserAdmin;
        String contra = PassAdmin;

        // Consulta SQL para verificar las credenciales del usuario.
        String consulta = "SELECT * FROM administrador WHERE AdminUser = ? AND AdminPass = ?";

        // Prepara la consulta SQL.
        try (PreparedStatement ps = conexion.prepareStatement(consulta)) {
            // Establece los parámetros de la consulta.
            ps.setString(1, usuario);
            ps.setString(2, contra);

            // Ejecuta la consulta y obtiene los resultados.
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    // Si hay un resultado, las credenciales son válidas.
                    JOptionPane.showMessageDialog(null, "Usuario válido. Bienvenido.");
                    return true;
                } else {
                    // Si no hay resultados, las credenciales no son válidas.
                    JOptionPane.showMessageDialog(null, "Usuario o contraseña incorrectos.");
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


