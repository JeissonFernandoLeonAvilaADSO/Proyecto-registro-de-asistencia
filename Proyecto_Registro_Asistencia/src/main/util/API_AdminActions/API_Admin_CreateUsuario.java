/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.util.API_AdminActions;

import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

/**
 * Clase para crear usuarios de instructores en la base de datos.
 * @author JeissonLeon
 */
// Esta clase se utiliza para crear un instructor en la base de datos.
public class API_Admin_CreateUsuario {

    // Este método toma los detalles del instructor como parámetros y los inserta en la base de datos.
    public boolean AdminCreateInstructor(int IDInstructor, String NombreInstructor, String ApellidoInstructor, String CorreoInstructor, String AreaInstructor, String Contra){
        // URL de la base de datos a la que se va a conectar.
        String url = "jdbc:mysql://localhost:3306/db_proyecto_asistencia";
        // Nombre de usuario para la conexión a la base de datos.
        String usuarioDB = "root";
        // Contraseña para la conexión a la base de datos.
        String contraDB = "";

        // Intenta establecer una conexión con la base de datos.
        try (Connection conexion = DriverManager.getConnection(url, usuarioDB, contraDB)) {

            // Consulta SQL para verificar las credenciales del usuario.
            String consulta = "INSERT INTO instructor (id, nombre, apellido, correo, areaProfe, contraseña) VALUES (?, ?, ?, ?, ?, ?)";

            // Prepara la consulta SQL.
            try (PreparedStatement ps = conexion.prepareStatement(consulta)) {
                // Establece los parámetros de la consulta.
                ps.setInt(1, IDInstructor);
                ps.setString(2, NombreInstructor);
                ps.setString(3, ApellidoInstructor);
                ps.setString(4, CorreoInstructor);
                ps.setString(5, AreaInstructor);
                ps.setString(6, Contra);

                // Ejecuta la consulta y obtiene los resultados en base a si se modificaron columnas.
                int rowsAffected = ps.executeUpdate();
                if (rowsAffected > 0) {
                    // Si hay un resultado, las credenciales son válidas.
                    JOptionPane.showMessageDialog(null, "Usuario Instructor ingresado correctamente");
                    return true;
                } else {
                    // Si no hay resultados, las credenciales no son válidas.
                    JOptionPane.showMessageDialog(null, "Ha ocurrido un error, no se ha podido crear el nuevo usuario");
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

