package com.proyectoasistencia.prasis.controller;


import com.proyectoasistencia.prasis.API.ConversionSubTablasAPI;
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

    // Método para obtener un usuario por ID
    @RequestMapping(value = "ObtenerUsuario/{IDUsuario}")
    public PerfilUsuarioModel getUsuario(@PathVariable String IDUsuario) {
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
                                   fichas.NumeroFicha,
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
                                     INNER JOIN fichas ON perfilusuario.IDFicha = fichas.ID
                                     INNER JOIN programaformacion ON fichas.IDProgramaFormacion = programaformacion.ID
                                     INNER JOIN jornadaformacion ON perfilusuario.IDJornadaFormacion = jornadaformacion.ID
                                     INNER JOIN nivelformacion ON perfilusuario.IDNivelFormacion = nivelformacion.ID
                                     INNER JOIN sede ON perfilusuario.IDSede = sede.ID
                                     INNER JOIN rol ON perfilusuario.IDRol = rol.ID
                            WHERE perfilusuario.Documento = ?""";
        try {
            return jdbcTemplate.queryForObject(consulta, new Object[]{IDUsuario}, new RowMapper<PerfilUsuarioModel>() {
                @Override
                public PerfilUsuarioModel mapRow(ResultSet rs, int rowNum) throws SQLException {
                    PerfilUsuarioModel usuario = new PerfilUsuarioModel();
                    usuario.setID(rs.getInt("ID"));
                    usuario.setUser(rs.getString("Usuario"));
                    usuario.setPass(rs.getString("Contraseña"));
                    usuario.setDocumento(rs.getString("Documento"));
                    usuario.setTipoDocumento(rs.getString("TipoDocumento"));
                    usuario.setNombres(rs.getString("Nombres"));
                    usuario.setApellidos(rs.getString("Apellidos"));
                    usuario.setGenero(rs.getString("TiposGeneros"));
                    usuario.setTelefono(rs.getString("Telefono"));
                    usuario.setProgramaFormacion(rs.getString("ProgramaFormacion"));
                    usuario.setNumeroFicha(rs.getInt("NumeroFicha"));
                    usuario.setJornadaFormacion(rs.getString("JornadasFormacion"));
                    usuario.setNivelFormacion(rs.getString("NivelFormacion"));
                    usuario.setArea(rs.getString("Area"));
                    usuario.setSede(rs.getString("CentroFormacion"));
                    usuario.setCorreo(rs.getString("Correo"));
                    usuario.setRol(rs.getString("TipoRol"));
                    return usuario;
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Método para agregar un usuario
    @RequestMapping("AgregarUsuario")
    public ResponseEntity<String> agregarUsuario(@RequestBody Map<String, Object> usuarioModel) {
        return upsertUsuario(usuarioModel, true, null);
    }

    // Método para modificar un usuario
    @RequestMapping("ModificarUsuario/{IDComp}")
    public ResponseEntity<String> modificarUsuario(@PathVariable String IDComp, @RequestBody Map<String, Object> usuarioModel) {
        return upsertUsuario(usuarioModel, false, IDComp);
    }

    // Método principal para insertar o actualizar un usuario
    private ResponseEntity<String> upsertUsuario(Map<String, Object> campos, boolean isNewUser, String IDComp) {
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        TransactionStatus status = transactionManager.getTransaction(def);
        System.out.println("Datos recibidos: " + campos);

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
                            System.out.println("Actualizando " + entry.getKey() + " para Documento: " + IDComp);
                            try (PreparedStatement ps = conexion.prepareStatement(consulta)) {
                                ps.setString(1, (String) entry.getValue());
                                ps.setString(2, IDComp);
                                int rowsAffected = ps.executeUpdate();
                                System.out.println("Filas afectadas: " + rowsAffected);
                                if (rowsAffected <= 0) {
                                    return new ResponseEntity<>("No se pudo actualizar " + entry.getKey(), HttpStatus.BAD_REQUEST);
                                }
                            }
                        } else if (entry.getKey().equals("NumeroFicha")) {
                            // Obtener el ID de la ficha desde el API
                            ConversionSubTablasAPI conversionSubTablas = new ConversionSubTablasAPI(jdbcTemplate);
                            ResponseEntity<Integer> response = conversionSubTablas.FichaToID((Integer) entry.getValue());
                            Integer fichaID = response.getBody();
                            if (fichaID == null) {
                                return new ResponseEntity<>("No se pudo encontrar el ID para la ficha " + entry.getValue(), HttpStatus.BAD_REQUEST);
                            }
                            consulta = "UPDATE perfilusuario SET IDFicha = ? WHERE Documento = ?";
                            try (PreparedStatement ps = conexion.prepareStatement(consulta)) {
                                ps.setInt(1, fichaID);
                                ps.setString(2, IDComp);
                                int rowsAffected = ps.executeUpdate();
                                if (rowsAffected <= 0) {
                                    return new ResponseEntity<>("No se pudo actualizar el ID de Ficha", HttpStatus.BAD_REQUEST);
                                }
                            }
                        } else {
                            consulta = "UPDATE perfilusuario SET " + entry.getKey() + " = ? WHERE Documento = ?";
                            try (PreparedStatement ps = conexion.prepareStatement(consulta)) {
                                if (entry.getValue() instanceof Integer) {
                                    ps.setInt(1, (Integer) entry.getValue());
                                } else if (entry.getValue() instanceof String) {
                                    ps.setString(1, (String) entry.getValue());
                                }
                                ps.setString(2, IDComp);
                                int rowsAffected = ps.executeUpdate();
                                if (rowsAffected <= 0) {
                                    return new ResponseEntity<>("No se pudo actualizar " + entry.getKey(), HttpStatus.BAD_REQUEST);
                                }
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

    // Método para configurar los parámetros del perfil del usuario
    private void setPerfilUsuarioParams(Connection conexion, Map<String, Object> campos, boolean isNewUser, int idUsuarioGenerado, Integer IDComp) throws SQLException {
        String consultaPerfil = "INSERT INTO perfilusuario (IDUsuario, Documento, IDTipoDocumento, Nombres, Apellidos, IDGenero, Telefono, IDFicha, IDJornadaFormacion, IDNivelFormacion, Area, IDSede, Correo, IDRol) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement psPerfil = conexion.prepareStatement(consultaPerfil)) {
            psPerfil.setInt(1, idUsuarioGenerado);
            psPerfil.setString(2, (String) campos.get("Documento"));
            psPerfil.setInt(3, (Integer) campos.get("IDTipoDocumento"));
            psPerfil.setString(4, (String) campos.get("Nombres"));
            psPerfil.setString(5, (String) campos.get("Apellidos"));
            psPerfil.setInt(6, (Integer) campos.get("IDGenero"));
            psPerfil.setString(7, (String) campos.get("Telefono"));
            psPerfil.setInt(8, (Integer) campos.get("IDFicha"));
            psPerfil.setInt(9, (Integer) campos.get("IDJornadaFormacion"));
            psPerfil.setInt(10, (Integer) campos.get("IDNivelFormacion"));
            psPerfil.setString(11, (String) campos.get("Area"));
            psPerfil.setInt(12, (Integer) campos.get("IDSede"));
            psPerfil.setString(13, (String) campos.get("Correo"));
            psPerfil.setInt(14, (Integer) campos.get("IDRol"));

            int rowsAffectedPerfil = psPerfil.executeUpdate();

            if (rowsAffectedPerfil <= 0) {
                throw new SQLException("No se pudo agregar el perfil del usuario");
            }
        }
    }

    // Método para eliminar un usuario
    @RequestMapping(value = "EliminarUsuario/{DocumentoUsuario}", method = RequestMethod.DELETE)
    public ResponseEntity<String> EliminarUsuario(@PathVariable String DocumentoUsuario) {
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        TransactionStatus status = transactionManager.getTransaction(def);

        try (Connection conexion = dataSource.getConnection()) {
            // Primero elimina el perfil del usuario
            String consultaPerfilUsuario = "DELETE FROM perfilusuario WHERE Documento = ?";
            try (PreparedStatement psPerfilUsuario = conexion.prepareStatement(consultaPerfilUsuario)) {
                psPerfilUsuario.setString(1, DocumentoUsuario);
                int rowsAffectedPerfilUsuario = psPerfilUsuario.executeUpdate();
                if (rowsAffectedPerfilUsuario <= 0) {
                    transactionManager.rollback(status);
                    return new ResponseEntity<>("No se pudo eliminar el perfil del usuario", HttpStatus.BAD_REQUEST);
                }
            }

            // Luego elimina el usuario relacionado
            String consultaUsuario = "DELETE FROM usuario WHERE ID = (SELECT IDUsuario FROM perfilusuario WHERE Documento = ?)";
            try (PreparedStatement psUsuario = conexion.prepareStatement(consultaUsuario)) {
                psUsuario.setString(1, DocumentoUsuario);
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
            e.printStackTrace();
            return new ResponseEntity<>("Error al eliminar el usuario: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Método para obtener el ID de usuario a partir del documento
    public ResponseEntity<Integer> getIDUsuario(@RequestParam String Documento) {
        String consulta = "SELECT IDUsuario FROM perfilusuario WHERE Documento = ?";

        try {
            Integer idUsuario = jdbcTemplate.queryForObject(consulta, new Object[]{Documento}, Integer.class);

            if (idUsuario != null) {
                return ResponseEntity.ok(idUsuario);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

