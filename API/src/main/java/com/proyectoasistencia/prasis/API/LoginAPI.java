package com.proyectoasistencia.prasis.API;

import com.proyectoasistencia.prasis.models.PerfilUsuarioModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@RestController
public class LoginAPI {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @RequestMapping(value = "Registro/Instructor/{User}/{Pass}")
    public ResponseEntity<Map<String, Object>> LoginInstructor(@PathVariable String User, @PathVariable String Pass) {
        String consulta1 = "SELECT Documento FROM perfilusuario INNER JOIN usuario ON perfilusuario.IDUsuario = usuario.ID WHERE usuario.Usuario = ? AND usuario.Contraseña = ? AND perfilusuario.IDRol = 3";
        Map<String, Object> response = new HashMap<>();

        try {
            String decodedUser = URLDecoder.decode(User, StandardCharsets.UTF_8.toString());
            String decodedPass = URLDecoder.decode(Pass, StandardCharsets.UTF_8.toString());

            Integer documento = jdbcTemplate.queryForObject(consulta1, new Object[]{decodedUser, decodedPass}, Integer.class);

            if (documento != null) {
                String consulta2 = """
                        SELECT perfilusuario.ID, perfilusuario.Documento, perfilusuario.Nombres, perfilusuario.Apellidos 
                        FROM perfilusuario
                        WHERE perfilusuario.Documento = ?""";

                jdbcTemplate.queryForObject(consulta2, new Object[]{documento}, (rs, rowNum) -> {
                    response.put("ID", rs.getInt("ID"));
                    response.put("documento", rs.getInt("Documento"));
                    response.put("nombres", rs.getString("Nombres"));
                    response.put("apellidos", rs.getString("Apellidos"));
                    return null; // Este valor no se utiliza
                });
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
            }
        } catch (EmptyResultDataAccessException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @RequestMapping(value = "Registro/Aprendiz/{User}/{Pass}")
    public ResponseEntity<Map<String, Object>> LoginAprendiz(@PathVariable String User, @PathVariable String Pass) {
        String consulta1 = "SELECT Documento FROM perfilusuario INNER JOIN usuario ON perfilusuario.IDUsuario = usuario.ID WHERE usuario.Usuario = ? AND usuario.Contraseña = ? AND perfilusuario.IDRol = 4";
        Map<String, Object> response = new HashMap<>();

        try {
            String decodedUser = URLDecoder.decode(User, StandardCharsets.UTF_8.toString());
            String decodedPass = URLDecoder.decode(Pass, StandardCharsets.UTF_8.toString());

            Integer documento = jdbcTemplate.queryForObject(consulta1, new Object[]{decodedUser, decodedPass}, Integer.class);

            if (documento != null) {
                String consulta2 = """
                        SELECT perfilusuario.ID, perfilusuario.Documento, perfilusuario.Nombres, perfilusuario.Apellidos 
                        FROM perfilusuario
                        WHERE perfilusuario.Documento = ?""";

                jdbcTemplate.queryForObject(consulta2, new Object[]{documento}, (rs, rowNum) -> {
                    response.put("ID", rs.getInt("ID"));
                    response.put("documento", rs.getInt("Documento"));
                    response.put("nombres", rs.getString("Nombres"));
                    response.put("apellidos", rs.getString("Apellidos"));
                    return null; // Este valor no se utiliza
                });
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
            }
        } catch (EmptyResultDataAccessException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @RequestMapping(value = "Registro/Administrador/{User}/{Pass}")
    public boolean LoginAdministrador(@PathVariable String User, @PathVariable String Pass) {
        String consulta = """
                    SELECT COUNT(*) from administrador
                        WHERE AdminUser = ? AND AdminPass = ?""";
        boolean confirmacion = false;

        try {
            // Primero, verifica las credenciales del usuario.
            Integer cont = jdbcTemplate.queryForObject(consulta, new Object[]{User, Pass}, Integer.class);

            if (cont != null && cont > 0) {
                confirmacion = true;
                return confirmacion;
            }
        } catch (Exception e) {
            // Imprime la traza de la pila de la excepción en caso de que ocurra un error.
            e.printStackTrace();
        }
        return confirmacion;
    }
}
