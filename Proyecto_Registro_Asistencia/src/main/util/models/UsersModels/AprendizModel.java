package main.util.models.UsersModels;


import java.sql.Date;

public class AprendizModel extends PerfilUsuarioModel{

    private Integer ficha;
    private String programaFormacion;
    private String jornadaFormacion;
    private String nivelFormacion;
    private String sede;
    private String area;

    public AprendizModel(String user,
                         String password,
                         String documento,
                         String tipoDocumento,
                         String nombres,
                         String apellidos,
                         Date fechaNacimiento,
                         String telefono,
                         String correo,
                         String genero,
                         String residencia,
                         Integer ficha,
                         String programaFormacion,
                         String jornadaFormacion,
                         String nivelFormacion,
                         String sede,
                         String area) {
        super(user, password, documento, tipoDocumento, nombres, apellidos, fechaNacimiento, telefono, correo, genero, residencia);
        this.ficha = ficha;
        this.programaFormacion = programaFormacion;
        this.jornadaFormacion = jornadaFormacion;
        this.nivelFormacion = nivelFormacion;
        this.sede = sede;
        this.area = area;
    }

    public Integer getFicha() {
        return ficha;
    }

    public void setFicha(Integer ficha) {
        this.ficha = ficha;
    }

    public String getProgramaFormacion() {
        return programaFormacion;
    }

    public void setProgramaFormacion(String programaFormacion) {
        this.programaFormacion = programaFormacion;
    }

    public String getJornadaFormacion() {
        return jornadaFormacion;
    }

    public void setJornadaFormacion(String jornadaFormacion) {
        this.jornadaFormacion = jornadaFormacion;
    }

    public String getNivelFormacion() {
        return nivelFormacion;
    }

    public void setNivelFormacion(String nivelFormacion) {
        this.nivelFormacion = nivelFormacion;
    }

    public String getSede() {
        return sede;
    }

    public void setSede(String sede) {
        this.sede = sede;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }
}
