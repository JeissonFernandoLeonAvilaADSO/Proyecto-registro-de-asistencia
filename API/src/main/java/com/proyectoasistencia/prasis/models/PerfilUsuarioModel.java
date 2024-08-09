package com.proyectoasistencia.prasis.models;

public class PerfilUsuarioModel {

    private int ID;
    private String User;
    private String Pass;
    private String Documento;
    private String TipoDocumento;
    private String Nombres;
    private String  Apellidos;
    private String Genero;
    private String Telefono;
    private String ProgramaFormacion;
    private int NumeroFicha;
    private String JornadaFormacion;
    private String NivelFormacion;
    private String Area;
    private String Sede;
    private String Correo;
    private String Rol;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getUser() {
        return User;
    }

    public void setUser(String user) {
        User = user;
    }

    public String getPass() {
        return Pass;
    }

    public void setPass(String pass) {
        Pass = pass;
    }

    public String getDocumento() {
        return Documento;
    }

    public void setDocumento(String documento) {
        Documento = documento;
    }

    public String getTipoDocumento() {
        return TipoDocumento;
    }

    public void setTipoDocumento(String tipoDocumento) {
        TipoDocumento = tipoDocumento;
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

    public String getGenero() {
        return Genero;
    }

    public void setGenero(String genero) {
        Genero = genero;
    }

    public String getTelefono() {
        return Telefono;
    }

    public void setTelefono(String telefono) {
        Telefono = telefono;
    }

    public String getProgramaFormacion() {
        return ProgramaFormacion;
    }

    public void setProgramaFormacion(String programaFormacion) {
        ProgramaFormacion = programaFormacion;
    }

    public int getNumeroFicha() {
        return NumeroFicha;
    }

    public void setNumeroFicha(int numeroFicha) {
        NumeroFicha = numeroFicha;
    }

    public String getJornadaFormacion() {
        return JornadaFormacion;
    }

    public void setJornadaFormacion(String jornadaFormacion) {
        JornadaFormacion = jornadaFormacion;
    }

    public String getNivelFormacion() {
        return NivelFormacion;
    }

    public void setNivelFormacion(String nivelFormacion) {
        NivelFormacion = nivelFormacion;
    }

    public String getArea() {
        return Area;
    }

    public void setArea(String area) {
        Area = area;
    }

    public String getSede() {
        return Sede;
    }

    public void setSede(String sede) {
        Sede = sede;
    }

    public String getCorreo() {
        return Correo;
    }

    public void setCorreo(String correo) {
        Correo = correo;
    }

    public String getRol() {
        return Rol;
    }

    public void setRol(String rol) {
        Rol = rol;
    }
}
