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
    private String TipoDoc;
    private String Documento;
    private String Nombres;
    private String Rol;
    private String ClaseFormacion;


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

    public String getTipoDoc() {
        return TipoDoc;
    }

    public void setTipoDoc(String TipoDoc) {
        this.TipoDoc = TipoDoc;
    }

    public String getDocumento() {
        return Documento;
    }

    public void setDocumento(String documento) {
        Documento = documento;
    }

    public String getNombres() {
        return Nombres;
    }

    public void setNombres(String nombres) {
        Nombres = nombres;
    }

    public String getRole() {
        return Rol;
    }

    public void setRole(String role) {
        Rol = role;
    }

    public String getClaseFormacion() {
        return ClaseFormacion;
    }

    public void setClaseFormacion(String claseFormacion) {
        ClaseFormacion = claseFormacion;
    }

    // Método para limpiar la sesión
    public void clearSession() {
        TipoDoc = null;
        Documento = null;
        Nombres = null;
        Rol = null;
        ClaseFormacion = null;
    }
}
