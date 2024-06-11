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
import java.sql.*;

import java.util.Map;

@RestController
public class PerfilUsuarioController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private DataSource dataSource;

    @Autowired
    private PlatformTransactionManager transactionManager;

    @RequestMapping(value = "ObtenerUsuario/{IDInstructor}")
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
                    instructor.setTelefono(rs.getString("Telefono"));
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

    @RequestMapping("AgregarUsuario")
    public ResponseEntity<String> agregarUsuario(@RequestBody Map<String, Object> usuarioModel) {
        return upsertUsuario(usuarioModel, true, null);
    }

    @RequestMapping("ModificarUsuario/{IDComp}")
    public ResponseEntity<String> modificarUsuario(@PathVariable Integer IDComp, @RequestBody Map<String, Object> usuarioModel) {
        return upsertUsuario(usuarioModel, false, IDComp);
    }

    private ResponseEntity<String> upsertUsuario(Map<String, Object> campos, boolean isNewUser, Integer IDComp) {
        System.out.println(campos);
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        TransactionStatus status = transactionManager.getTransaction(def);

        try (Connection conexion = dataSource.getConnection()) {
            if (isNewUser) {
                // Inserción de nuevo usuario
                String consultaUsuario = "INSERT INTO usuario (Usuario, Contraseña) VALUES (?, ?)";
                try (PreparedStatement psUsuario = conexion.prepareStatement(consultaUsuario, Statement.RETURN_GENERATED_KEYS)) {
                    psUsuario.setString(1, (String) campos.get("Usuario"));
                    psUsuario.setString(2, (String) campos.get("Contraseña"));
                    int rowsAffectedUsuario = psUsuario.executeUpdate();

                    if (rowsAffectedUsuario <= 0) {
                        return new ResponseEntity<>("No se pudo agregar el usuario", HttpStatus.BAD_REQUEST);
                    }

                    ResultSet rs = psUsuario.getGeneratedKeys();
                    if (rs.next()) {
                        int idUsuarioGenerado = rs.getInt(1);
                        setPerfilUsuarioParams(conexion, campos, true, idUsuarioGenerado, null);
                    }
                }
            } else {
                // Actualización de usuario existente
                for (Map.Entry<String, Object> entry : campos.entrySet()) {
                    if (entry.getValue() != null) {
                        String consulta;
                        if (entry.getKey().equals("Usuario") || entry.getKey().equals("Contraseña")) {
                            consulta = "UPDATE usuario SET " + entry.getKey() + " = ? WHERE ID = (SELECT IDUsuario FROM perfilusuario WHERE Documento = ?)";
                        } else {
                            consulta = "UPDATE perfilusuario SET " + entry.getKey() + " = ? WHERE Documento = ?";
                        }

                        try (PreparedStatement ps = conexion.prepareStatement(consulta)) {
                            if (entry.getValue() instanceof Integer) {
                                ps.setInt(1, (Integer) entry.getValue());
                            } else if (entry.getValue() instanceof String) {
                                ps.setString(1, (String) entry.getValue());
                            }
                            ps.setInt(2, IDComp);
                            int rowsAffected = ps.executeUpdate();
                            if (rowsAffected <= 0) {
                                return new ResponseEntity<>("No se pudo actualizar " + entry.getKey(), HttpStatus.BAD_REQUEST);
                            }
                        }
                    }
                }
            }

            transactionManager.commit(status);
            return new ResponseEntity<>("Usuario " + (isNewUser ? "agregado" : "modificado") + " exitosamente", HttpStatus.OK);
        } catch (SQLException e) {
            transactionManager.rollback(status);
            return new ResponseEntity<>("Error durante la " + (isNewUser ? "inserción" : "modificación") + ": " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private void setPerfilUsuarioParams(Connection conexion, Map<String, Object> campos, boolean isNewUser, int idUsuarioGenerado, Integer IDComp) throws SQLException {
        String consultaPerfil = "INSERT INTO perfilusuario (ID, IDUsuario, Documento, IDTipoDocumento, Nombres, Apellidos, IDGenero, Telefono, IDProgramaFormacion, NumeroFicha, IDJornadaFormacion, IDNivelFormacion, Area, IDSede, Correo, IDRol) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement psPerfil = conexion.prepareStatement(consultaPerfil)) {
            psPerfil.setInt(1, (Integer) campos.get("ID"));
            psPerfil.setInt(2, idUsuarioGenerado);
            psPerfil.setInt(3, (Integer) campos.get("Documento"));
            psPerfil.setInt(4, (Integer) campos.get("IDTipoDocumento"));
            psPerfil.setString(5, (String) campos.get("Nombres"));
            psPerfil.setString(6, (String) campos.get("Apellidos"));
            psPerfil.setInt(7, (Integer) campos.get("IDGenero"));
            psPerfil.setString(8, (String) campos.get("Telefono"));
            psPerfil.setInt(9, (Integer) campos.get("IDProgramaFormacion"));
            psPerfil.setInt(10, (Integer) campos.get("NumeroFicha"));
            psPerfil.setInt(11, (Integer) campos.get("IDJornadaFormacion"));
            psPerfil.setInt(12, (Integer) campos.get("IDNivelFormacion"));
            psPerfil.setString(13, (String) campos.get("Area"));
            psPerfil.setInt(14, (Integer) campos.get("IDSede"));
            psPerfil.setString(15, (String) campos.get("Correo"));
            psPerfil.setInt(16, (Integer) campos.get("IDRol"));

            int rowsAffectedPerfil = psPerfil.executeUpdate();

            if (rowsAffectedPerfil <= 0) {
                throw new SQLException("No se pudo agregar el perfil del usuario");
            }
        }
    }

    @RequestMapping(value = "EliminarUsuario/{DocumentoUsuario}", method = RequestMethod.DELETE)
    public ResponseEntity<String> EliminarUsuario(@PathVariable int DocumentoUsuario) {
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        TransactionStatus status = transactionManager.getTransaction(def);

        try (Connection conexion = dataSource.getConnection()) {
            // Eliminar el perfil del usuario de la tabla 'perfilusuario'
            String consultaPerfilUsuario = "DELETE FROM perfilusuario WHERE Documento = ?";
            try (PreparedStatement psPerfilUsuario = conexion.prepareStatement(consultaPerfilUsuario)) {
                psPerfilUsuario.setInt(1, DocumentoUsuario);
                int rowsAffectedPerfilUsuario = psPerfilUsuario.executeUpdate();
                if (rowsAffectedPerfilUsuario <= 0) {
                    transactionManager.rollback(status);
                    return new ResponseEntity<>("No se pudo eliminar el perfil del usuario", HttpStatus.BAD_REQUEST);
                }
            }

            // Eliminar el usuario de la tabla 'usuario' usando una subconsulta para obtener el IDUsuario
            String consultaUsuario = "DELETE FROM usuario WHERE ID = (SELECT IDUsuario FROM perfilusuario WHERE Documento = ?)";
            try (PreparedStatement psUsuario = conexion.prepareStatement(consultaUsuario)) {
                psUsuario.setInt(1, DocumentoUsuario);
                int rowsAffectedUsuario = psUsuario.executeUpdate();
                if (rowsAffectedUsuario <= 0) {
                    transactionManager.rollback(status);
                    return new ResponseEntity<>("No se pudo eliminar el usuario", HttpStatus.BAD_REQUEST);
                }
            }

            transactionManager.commit(status);
            return new ResponseEntity<>("Usuario eliminado exitosamente", HttpStatus.OK);
        } catch (SQLException e) {
            transactionManager.rollback(status);
            e.printStackTrace(); // Imprime el stack trace para más detalles
            return new ResponseEntity<>("Error al eliminar el usuario: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public void validarDatosUsuario(Map<String, Object> datos) {
        String[] camposRequeridos = {
                "IDUsuario", "IDTipoDocumento", "Documento", "Nombres", "Apellidos", "IDGenero",
                "Telefono", "IDProgramaFormacion", "NumeroFicha", "IDJornadaFormacion",
                "IDNivelFormacion", "Area", "IDSede", "Correo", "IDRol"
        };

        for (String campo : camposRequeridos) {
            if (!datos.containsKey(campo) || datos.get(campo) == null) {
                throw new IllegalArgumentException("Falta el campo requerido: " + campo);
            }
        }
    }

    public void upsertUsuario(Map<String, Object> datos) {
        try {
            // Validar que todos los datos requeridos están presentes
            validarDatosUsuario(datos);

            // Ahora puedes proceder con la lógica de inserción o actualización en la base de datos
            // tu lógica de base de datos aquí
        } catch (IllegalArgumentException e) {
            // Manejar la excepción de validación
            System.err.println("Error de validación: " + e.getMessage());
            // Aquí puedes lanzar una excepción, retornar un error o manejarlo según tu lógica de negocio
        } catch (Exception e) {
            // Manejar otras excepciones
            System.err.println("Error al insertar o actualizar usuario: " + e.getMessage());
        }
    }



}

