package main.util.models.UsersModels;


import java.sql.Date;
import java.util.List;
import java.util.Map;

public class AprendizModel extends PerfilUsuarioModel {

    private List<Map<String, Object>> vinculaciones;

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
                         List<Map<String, Object>> vinculaciones) {
        super(user, password, documento, tipoDocumento, nombres, apellidos, fechaNacimiento, telefono, correo, genero, residencia);
        this.vinculaciones = vinculaciones;
    }

    public List<Map<String, Object>> getVinculaciones() {
        return vinculaciones;
    }

    public void setVinculaciones(List<Map<String, Object>> vinculaciones) {
        this.vinculaciones = vinculaciones;
    }
}
