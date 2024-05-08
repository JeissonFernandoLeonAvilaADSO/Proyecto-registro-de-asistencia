package com.proyectoasistencia.prasis.API;

import com.proyectoasistencia.prasis.models.InstructorModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.ResultSet;
import java.sql.SQLException;

@RestController
public class LoginAPI {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @RequestMapping(value = "Registro/Instructor/{User}/{Pass}")
    public InstructorModel LoginInstructor(@PathVariable String User, @PathVariable String Pass) {
        String consulta1 = """
                            SELECT Documento from perfilusuario 
                                INNER JOIN usuario ON perfilusuario.IDUsuario = usuario.ID 
                                WHERE usuario.Usuario = ? AND usuario.Contraseña = ?""";
        InstructorModel instructor = null;

        try {
            // Primero, verifica las credenciales del usuario.
            Integer documento = jdbcTemplate.queryForObject(consulta1, new Object[]{User, Pass}, Integer.class);

            if (documento != null) {
                String consulta2 = """ 
                                    SELECT perfilusuario.Documento, perfilusuario.Nombres, perfilusuario.Apellidos, perfilusuario.Telefono, perfilusuario.Area, TipoDocumento.TipoDocumento, Genero.TiposGeneros, Rol.TipoRol, Sede.CentroFormacion FROM perfilusuario
                                        INNER JOIN TipoDocumento ON perfilusuario.IDTipoDocumento = TipoDocumento.ID
                                        INNER JOIN Genero ON perfilusuario.IDGenero = Genero.ID
                                        INNER JOIN Rol ON perfilusuario.IDRol = Rol.ID
                                        INNER JOIN Sede ON perfilusuario.IDSede = Sede.ID
                                        WHERE perfilusuario.Documento = ?""";

                // Si las credenciales son correctas, obtén los detalles del instructor.
                instructor = jdbcTemplate.queryForObject(consulta2, new Object[]{documento}, new RowMapper<InstructorModel>() {
                    @Override
                    public InstructorModel mapRow(ResultSet rs, int rowNum) throws SQLException {
                        InstructorModel instructor = new InstructorModel();
                        instructor.setDocumento(rs.getInt("Documento"));
                        instructor.setTipoDocumento(rs.getString("TipoDocumento"));
                        instructor.setNombres(rs.getString("nombres"));
                        instructor.setApellidos(rs.getString("apellidos"));
                        instructor.setGenero(rs.getString("TiposGeneros"));
                        instructor.setTelefono(rs.getInt("Telefono"));
                        instructor.setArea(rs.getString("Area"));
                        instructor.setRol(rs.getString("TipoRol"));
                        instructor.setSede(rs.getString("CentroFormacion"));
                        return instructor;
                    }
                });
            }
        } catch (Exception e) {
            // Imprime la traza de la pila de la excepción en caso de que ocurra un error.
            e.printStackTrace();
        }
        return instructor;
    }
}
