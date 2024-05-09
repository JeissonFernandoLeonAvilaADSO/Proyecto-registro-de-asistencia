package main.util.API_AdminActions;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import javax.swing.JOptionPane;

public class API_Admin_DeletInstructor {
    public boolean borrarInstructor(int IDInstructor){
        // URL de la base de datos a la que se va a conectar.
        String url = "jdbc:mysql://localhost:3306/db_proyecto_asistencia";
        // Nombre de usuario para la conexión a la base de datos.
        String usuarioDB = "root";
        // Contraseña para la conexión a la base de datos.
        String contraDB = "";

        // Intenta establecer una conexión con la base de datos.
        try (Connection conexion = DriverManager.getConnection(url, usuarioDB, contraDB)) {

            // Consulta SQL para eliminar al instructor.
            String consulta = "DELETE FROM instructor WHERE id = ?";

            // Prepara la consulta SQL.
            try (PreparedStatement ps = conexion.prepareStatement(consulta)) {
                // Establece los parámetros de la consulta.
                ps.setInt(1, IDInstructor);

                // Ejecuta la consulta y obtiene los resultados en base a si se modificaron columnas.
                int rowsAffected = ps.executeUpdate();
                if (rowsAffected > 0) {
                    // Si hay un resultado, el usuario fue eliminado correctamente.
                    JOptionPane.showMessageDialog(null, "Usuario Instructor eliminado correctamente");
                    return true;
                } else {
                    // Si no hay resultados, el usuario no existe o hubo un error.
                    JOptionPane.showMessageDialog(null, "Ha ocurrido un error, no se ha podido eliminar el usuario");
                    return false;
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
