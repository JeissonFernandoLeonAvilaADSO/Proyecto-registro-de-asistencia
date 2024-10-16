package main.util.models.UsersModels;

import java.sql.Date;
import java.util.List;

public class InstructorModel extends PerfilUsuarioModel{

    private List<String> claseFormacion;
    private List<Integer> fichas;
    private List<String> programasFormacion;
    private List<String> jornadasFormacion;
    private List<String> nivelesFormacion;
    private List<String> sedes;
    private List<String> areas;

    public InstructorModel(String user,
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
                           List<String> claseFormacion, // Cambiado a List<String>
                           List<Integer> fichas,
                           List<String> programasFormacion,
                           List<String> jornadasFormacion,
                           List<String> nivelesFormacion,
                           List<String> sedes,
                           List<String> areas) {
        super(user, password, documento, tipoDocumento, nombres, apellidos, fechaNacimiento, telefono, correo, genero, residencia);
        this.claseFormacion = claseFormacion;
        this.fichas = fichas;
        this.programasFormacion = programasFormacion;
        this.jornadasFormacion = jornadasFormacion;
        this.nivelesFormacion = nivelesFormacion;
        this.sedes = sedes;
        this.areas = areas;
    }

    public List<String> getClaseFormacion() { // Cambiado a List<String>
        return claseFormacion;
    }

    public void setClaseFormacion(List<String> claseFormacion) { // Cambiado a List<String>
        this.claseFormacion = claseFormacion;
    }

    public List<Integer> getFichas() {
        return fichas;
    }

    public void setFichas(List<Integer> fichas) {
        this.fichas = fichas;
    }

    public List<String> getProgramasFormacion() {
        return programasFormacion;
    }

    public void setProgramasFormacion(List<String> programasFormacion) {
        this.programasFormacion = programasFormacion;
    }

    public List<String> getJornadasFormacion() {
        return jornadasFormacion;
    }

    public void setJornadasFormacion(List<String> jornadasFormacion) {
        this.jornadasFormacion = jornadasFormacion;
    }

    public List<String> getNivelesFormacion() {
        return nivelesFormacion;
    }

    public void setNivelesFormacion(List<String> nivelesFormacion) {
        this.nivelesFormacion = nivelesFormacion;
    }

    public List<String> getSedes() {
        return sedes;
    }

    public void setSedes(List<String> sedes) {
        this.sedes = sedes;
    }

    public List<String> getAreas() {
        return areas;
    }

    public void setAreas(List<String> areas) {
        this.areas = areas;
    }

}
