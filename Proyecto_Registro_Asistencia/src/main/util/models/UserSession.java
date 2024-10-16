/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.util.models;

import java.util.List;
import java.util.Map;

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
    private List<Map<String, Object>> clasesFormacion;


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

    public List<Map<String, Object>> getClasesFormacion() {
        return clasesFormacion;
    }

    public void setClasesFormacion(List<Map<String, Object>> clasesFormacion) {
        this.clasesFormacion = clasesFormacion;
    }


    // Método para limpiar la sesión
    public void clearSession() {
        TipoDoc = null;
        Documento = null;
        Nombres = null;
        Rol = null;
        clasesFormacion = null;
    }
}
