package com.proyectoasistencia.prasis.controller;


import com.proyectoasistencia.prasis.models.PerfilUsuarioModel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.web.bind.annotation.*;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.Map;

@RestController
public class PerfilUsuarioController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private DataSource dataSource;

    @Autowired
    private PlatformTransactionManager transactionManager;

    @RequestMapping(value = "ObtenerInstructor/{IDInstructor}")
    public PerfilUsuarioModel getInstructor(@PathVariable Integer IDInstructor){
        String consulta = """ 
                SELECT perfilusuario.ID,
                                   usuario.Usuario,
                                   usuario.Contraseña,
                                   perfilusuario.Documento,
                                   tipodocumento.TipoDocumento,
                                   perfilusuario.Nombres,
                                   perfilusuario.Apellidos,
                                   genero.TiposGeneros,
                                   perfilusuario.Telefono,
                                   programaformacion.ProgramaFormacion,
                                   perfilusuario.NumeroFicha,
                                   jornadaformacion.JornadasFormacion,
                                   nivelformacion.NivelFormacion,
                                   perfilusuario.Area,
                                   sede.CentroFormacion,
                                   perfilusuario.Correo,
                                   rol.TipoRol
                            FROM perfilusuario
                                     INNER JOIN usuario ON perfilusuario.IDUsuario = usuario.ID
                                     INNER JOIN tipoDocumento ON perfilusuario.IDTipoDocumento = tipoDocumento.ID
                                     INNER JOIN genero ON perfilusuario.IDGenero = genero.ID
                                     INNER JOIN programaformacion ON perfilusuario.IDProgramaFormacion = programaformacion.ID
                                     INNER JOIN jornadaformacion ON perfilusuario.IDJornadaFormacion = jornadaformacion.ID
                                     INNER JOIN nivelformacion ON perfilusuario.IDNivelFormacion = nivelformacion.ID
                                     INNER JOIN sede ON perfilusuario.IDSede = sede.ID
                                     INNER JOIN rol ON perfilusuario.IDRol = rol.ID
                            WHERE perfilusuario.Documento = ?""";
        try {
            return jdbcTemplate.queryForObject(consulta, new Object[]{IDInstructor}, new RowMapper<PerfilUsuarioModel>() {
                @Override
                public PerfilUsuarioModel mapRow(ResultSet rs, int rowNum) throws SQLException {
                    PerfilUsuarioModel instructor = new PerfilUsuarioModel();
                    instructor.setID(rs.getInt("ID"));
                    instructor.setUser(rs.getString("Usuario"));
                    instructor.setPass(rs.getString("Contraseña"));
                    instructor.setDocumento(rs.getInt("Documento"));
                    instructor.setTipoDocumento(rs.getString("TipoDocumento"));
                    instructor.setNombres(rs.getString("nombres"));
                    instructor.setApellidos(rs.getString("apellidos"));
                    instructor.setGenero(rs.getString("TiposGeneros"));
                    instructor.setTelefono(rs.getInt("Telefono"));
                    instructor.setProgramaFormacion(rs.getString("ProgramaFormacion"));
                    instructor.setNumeroFicha(rs.getInt("NumeroFicha"));
                    instructor.setJornadaFormacion(rs.getString("JornadasFormacion"));
                    instructor.setNivelFormacion(rs.getString("NivelFormacion"));
                    instructor.setArea(rs.getString("Area"));
                    instructor.setSede(rs.getString("CentroFormacion"));
                    instructor.setCorreo(rs.getString("Correo"));
                    instructor.setRol(rs.getString("TipoRol"));
                    return instructor;
                }
            });
        } catch (Exception e) {
            // Imprime la traza de la pila de la excepción en caso de que ocurra un error.
            e.printStackTrace();
        }
        return null;
    }

    @PutMapping(value = "ModificarInstructor/{IDInstructor}")
    public ResponseEntity<String> ModInstructor(@PathVariable int IDInstructor, @RequestBody Map<String, Object> usuarioPUTModel){
        Map<String, Object> campos = usuarioPUTModel;
        System.out.println(campos);
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        TransactionStatus status = transactionManager.getTransaction(def);

        try (Connection conexion = dataSource.getConnection()) {
            for (Map.Entry<String, Object> entry : campos.entrySet()) {
                if (entry.getValue() != null) {
                    String consulta;
                    if (entry.getKey().equals("Usuario") || entry.getKey().equals("Contraseña")) {
                        consulta = "UPDATE usuario SET " + entry.getKey() + " = ? WHERE id = (SELECT IDUsuario FROM perfilusuario WHERE Documento = ?)";
                    } else {
                        consulta = "UPDATE perfilusuario SET " + entry.getKey() + " = ? WHERE Documento = ?";
                    }

                    try (PreparedStatement ps = conexion.prepareStatement(consulta)) {
                        if (entry.getValue() instanceof Integer) {
                            ps.setInt(1, (Integer) entry.getValue());
                        } else if (entry.getValue() instanceof String) {
                            ps.setString(1, (String) entry.getValue());
                        }
                        ps.setInt(2, IDInstructor);
                        int rowsAffected = ps.executeUpdate();
                        if (rowsAffected <= 0) {
                            return new ResponseEntity<>("No se pudo actualizar " + entry.getKey(), HttpStatus.BAD_REQUEST);
                        }
                    }
                }
            }
            transactionManager.commit(status);
            return new ResponseEntity<>("Actualización exitosa", HttpStatus.OK);
        } catch (SQLException e) {
            transactionManager.rollback(status);
            return new ResponseEntity<>("Error durante la actualización: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}

