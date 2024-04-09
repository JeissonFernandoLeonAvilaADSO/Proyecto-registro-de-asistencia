package main.util.DB_AdminActions;

import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
/**
 * Clase DB_Admin_ModifInstructor que contiene el método para modificar los datos de un instructor en la base de datos.
 * @author Jeisson Leon
 */
public class DB_Admin_ModifInstructor {

    /**
     * Método para modificar los datos de un instructor en la base de datos.
     *
     * @param CambiarCedula   Indica si se debe cambiar la cédula del instructor.
     * @param CambiarNombre   Indica si se debe cambiar el nombre del instructor.
     * @param CambiarApellido Indica si se debe cambiar el apellido del instructor.
     * @param CambiarCorreo   Indica si se debe cambiar el correo del instructor.
     * @param CambiarArea     Indica si se debe cambiar el área del instructor.
     * @param CambiarContra   Indica si se debe cambiar la contraseña del instructor.
     * @param NuevoID         La nueva cédula del instructor.
     * @param NuevoNombre     El nuevo nombre del instructor.
     * @param NuevoApellido   El nuevo apellido del instructor.
     * @param NuevoCorreo     El nuevo correo del instructor.
     * @param NuevoArea       La nueva área del instructor.
     * @param NuevoContra     La nueva contraseña del instructor.
     * @param IDComparativo   La cédula actual del instructor.
     * @return Verdadero si la operación fue exitosa, falso en caso contrario.
     */
    public boolean AdminModifInstructor(boolean CambiarCedula,
                                        boolean CambiarNombre,
                                        boolean CambiarApellido,
                                        boolean CambiarCorreo,
                                        boolean CambiarArea,
                                        boolean CambiarContra,
                                        int NuevoID,
                                        String NuevoNombre,
                                        String NuevoApellido,
                                        String NuevoCorreo,
                                        String NuevoArea,
                                        String NuevoContra,
                                        int IDComparativo) {
        // URL de la base de datos a la que se va a conectar.
        String url = "jdbc:mysql://localhost:3306/db_proyecto_asistencia";
        // Nombre de usuario para la conexión a la base de datos.
        String usuarioDB = "root";
        // Contraseña para la conexión a la base de datos.
        String contraDB = "";

        // Intenta establecer una conexión con la base de datos.
        try (Connection conexion = DriverManager.getConnection(url, usuarioDB, contraDB)) {

            if (CambiarCedula) {
                String consulta = "UPDATE instructor SET id = ? WHERE id = ?";

                try (PreparedStatement ps = conexion.prepareStatement(consulta)) {
                    // Establece los parámetros de la consulta.
                    ps.setInt(1, NuevoID);
                    ps.setInt(2, IDComparativo);

                    // Ejecuta la consulta y obtiene los resultados en base a si se modificaron columnas.
                    int rowsAffected = ps.executeUpdate();
                    if (rowsAffected > 0) {
                        JOptionPane.showMessageDialog(null, "La nueva cedula ha sido registrada con exito");
                    } else {
                        // Si no hay resultados, las credenciales no son válidas.
                        JOptionPane.showMessageDialog(null, "No se pudo registrar la nueva cedula");
                        return false;
                    }
                }
            }

            if (CambiarNombre) {
                String consulta = "UPDATE instructor SET nombre = ? WHERE id = ?";

                try (PreparedStatement ps = conexion.prepareStatement(consulta)) {
                    // Establece los parámetros de la consulta.
                    ps.setString(1, NuevoNombre);
                    ps.setInt(2, IDComparativo);

                    // Ejecuta la consulta y obtiene los resultados en base a si se modificaron columnas.
                    int rowsAffected = ps.executeUpdate();
                    if (rowsAffected > 0) {
                        JOptionPane.showMessageDialog(null, "El nuevo nombre ha sido registrado con exito");
                    } else {
                        JOptionPane.showMessageDialog(null, "No se pudo registrar el nuevo nombre");
                        return false;
                    }
                }
            }

            if (CambiarApellido) {
                String consulta = "UPDATE instructor SET apellido = ? WHERE id = ?";

                try (PreparedStatement ps = conexion.prepareStatement(consulta)) {
                    // Establece los parámetros de la consulta.
                    ps.setString(1, NuevoApellido);
                    ps.setInt(2, IDComparativo);

                    // Ejecuta la consulta y obtiene los resultados en base a si se modificaron columnas.
                    int rowsAffected = ps.executeUpdate();
                    if (rowsAffected > 0) {
                        JOptionPane.showMessageDialog(null, "El nuevo apellido ha sido registrado con exito");
                    } else {
                        JOptionPane.showMessageDialog(null, "No se pudo registrar el nuevo apellido");
                        return false;
                    }
                }
            }

            if (CambiarCorreo) {
                String consulta = "UPDATE instructor SET correo = ? WHERE id = ?";

                try (PreparedStatement ps = conexion.prepareStatement(consulta)) {
                    // Establece los parámetros de la consulta.
                    ps.setString(1, NuevoCorreo);
                    ps.setInt(2, IDComparativo);

                    // Ejecuta la consulta y obtiene los resultados en base a si se modificaron columnas.
                    int rowsAffected = ps.executeUpdate();
                    if (rowsAffected > 0) {
                        JOptionPane.showMessageDialog(null, "El nuevo correo ha sido registrado con exito");
                    } else {
                        JOptionPane.showMessageDialog(null, "No se pudo registrar el nuevo correo");
                        return false;
                    }
                }
            }

            if (CambiarArea) {
                String consulta = "UPDATE instructor SET areaProfe = ? WHERE id = ?";

                try (PreparedStatement ps = conexion.prepareStatement(consulta)) {
                    // Establece los parámetros de la consulta.
                    ps.setString(1, NuevoArea);
                    ps.setInt(2, IDComparativo);

                    // Ejecuta la consulta y obtiene los resultados en base a si se modificaron columnas.
                    int rowsAffected = ps.executeUpdate();
                    if (rowsAffected > 0) {
                        JOptionPane.showMessageDialog(null, "La nueva area del instructor ha sido registrada con exito");
                    } else {
                        JOptionPane.showMessageDialog(null, "No se pudo registrar la nueva area del instructor");
                        return false;
                    }
                }
            }

            if (CambiarContra) {
                String consulta = "UPDATE instructor SET contraseña = ? WHERE id = ?";

                try (PreparedStatement ps = conexion.prepareStatement(consulta)) {
                    // Establece los parámetros de la consulta.
                    ps.setString(1, NuevoContra);
                    ps.setInt(2, IDComparativo);

                    // Ejecuta la consulta y obtiene los resultados en base a si se modificaron columnas.
                    int rowsAffected = ps.executeUpdate();
                    if (rowsAffected > 0) {
                        JOptionPane.showMessageDialog(null, "La nueva contraseña ha sido registrada con exito");
                    } else {
                        JOptionPane.showMessageDialog(null, "No se pudo registrar la nueva contraseña");
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
