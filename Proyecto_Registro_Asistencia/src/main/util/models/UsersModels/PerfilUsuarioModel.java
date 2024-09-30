package main.util.models.UsersModels;

import java.sql.Date;

public class PerfilUsuarioModel {

    protected String user;
    protected String password;
    protected String documento;
    protected String tipoDocumento;
    protected String nombres;
    protected String apellidos;
    protected Date fechaNacimiento;
    protected String telefono;
    protected String correo;
    protected String genero;
    protected String residencia;

    public PerfilUsuarioModel(String user,
                              String password,
                              String documento,
                              String tipoDocumento,
                              String nombres,
                              String apellidos,
                              Date fechaNacimiento,
                              String telefono,
                              String correo,
                              String genero,
                              String residencia) {
        this.user = user;
        this.password = password;
        this.documento = documento;
        this.tipoDocumento = tipoDocumento;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.fechaNacimiento = fechaNacimiento;
        this.telefono = telefono;
        this.correo = correo;
        this.genero = genero;
        this.residencia = residencia;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getResidencia() {
        return residencia;
    }

    public void setResidencia(String residencia) {
        this.residencia = residencia;
    }

    public String getFullName(){
        return nombres + " " + apellidos;
    }
}
