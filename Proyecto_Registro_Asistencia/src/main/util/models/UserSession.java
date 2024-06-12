/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.util.models;

/**
 *
 * @author Propietario
 */
public class UserSession {
    private static UserSession instance;
    private Integer ID;
    private Integer Documento;
    private String Nombres;
    private String Apellidos;


    private UserSession() {
        // Constructor privado para evitar instanciación directa
    }

    // Método para obtener la instancia única de UserSession
    public static UserSession getInstance() {
        if (instance == null) {
            instance = new UserSession();
        }
        return instance;
    }

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public Integer getDocumento() {
        return Documento;
    }

    public void setDocumento(Integer documento) {
        Documento = documento;
    }

    public String getNombres() {
        return Nombres;
    }

    public void setNombres(String nombres) {
        Nombres = nombres;
    }

    public String getApellidos() {
        return Apellidos;
    }

    public void setApellidos(String apellidos) {
        Apellidos = apellidos;
    }

    // Método para limpiar la sesión
    public void clearSession() {
        ID = 0;
        Documento = 0;
        Nombres = null;
        Apellidos = null;
    }
}
