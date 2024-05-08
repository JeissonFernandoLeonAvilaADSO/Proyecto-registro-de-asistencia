package com.proyectoasistencia.prasis.controller;


import com.proyectoasistencia.prasis.models.InstructorModel;
import com.proyectoasistencia.prasis.models.InstructorPUTModel;
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

@RestController
public class InstructorController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private DataSource dataSource;

    @Autowired
    private PlatformTransactionManager transactionManager;

    @RequestMapping(value = "ObtenerInstructor/{IDInstructor}")
    public InstructorModel getInstructor(@PathVariable Integer IDInstructor){
        String consulta = "SELECT perfilinstructor.Documento, perfilinstructor.Nombres, perfilinstructor.Apellidos, perfilinstructor.Telefono, perfilinstructor.Area, TipoDocumento.TipoDocumento, Genero.TiposGeneros, Rol.TipoRol, Sede.CentroFormacion FROM perfilinstructor INNER JOIN TipoDocumento ON perfilinstructor.IDTipoDocumento = TipoDocumento.ID INNER JOIN Genero ON perfilinstructor.IDGenero = Genero.ID INNER JOIN Rol ON perfilinstructor.IDRol = Rol.ID INNER JOIN Sede ON perfilinstructor.IDSede = Sede.ID WHERE perfilinstructor.Documento = ?";

        try {
            return jdbcTemplate.queryForObject(consulta, new Object[]{IDInstructor}, new RowMapper<InstructorModel>() {
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
        } catch (Exception e) {
            // Imprime la traza de la pila de la excepción en caso de que ocurra un error.
            e.printStackTrace();
        }
        return null;
    }

    @PutMapping(value = "ModificarInstructor/{IDInstructor}")
    public ResponseEntity<String> ModInstructor(@PathVariable Integer IDInstructor, @RequestBody InstructorPUTModel instructorPUTModel){
        boolean cambiarDocumento = instructorPUTModel.isCambiarDocumento();
        boolean cambiarTipoDoc = instructorPUTModel.isCambiarTipoDoc();
        boolean cambiarNombres = instructorPUTModel.isCambiarNombres();
        boolean cambiarApellidos = instructorPUTModel.isCambiarApellidos();
        boolean cambiarGenero = instructorPUTModel.isCambiarGenero();
        boolean cambiarTelefono = instructorPUTModel.isCambiarTelefono();
        boolean cambiarArea = instructorPUTModel.isCambiarArea();
        boolean cambiarRol = instructorPUTModel.isCambiarRol();
        boolean cambiarSede = instructorPUTModel.isCambiarSede();

        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        TransactionStatus status = transactionManager.getTransaction(def);

        try (Connection conexion = dataSource.getConnection()) {
            if (cambiarDocumento) {
                String consulta = "UPDATE instructor SET Documento = ? WHERE id = ?";

                try (PreparedStatement ps = conexion.prepareStatement(consulta)) {
                    ps.setInt(1, instructorPUTModel.getNuevoDocumento());
                    ps.setInt(2, IDInstructor);
                    int rowsAffected = ps.executeUpdate();
                    if (rowsAffected <= 0) {
                        return new ResponseEntity<>("No se pudo actualizar el documento", HttpStatus.BAD_REQUEST);
                    }
                }
            }

            if (cambiarTipoDoc) {
                String consulta = "UPDATE instructor SET IDTipoDocumento = ? WHERE Documento = ?";

                try (PreparedStatement ps = conexion.prepareStatement(consulta)) {
                    ps.setInt(1, instructorPUTModel.getNuevoTipoDoc());
                    ps.setInt(2, IDInstructor);
                    int rowsAffected = ps.executeUpdate();
                    if (rowsAffected <= 0) {
                        return new ResponseEntity<>("No se pudo actualizar el tipo de documento", HttpStatus.BAD_REQUEST);
                    }
                }
            }

            if (cambiarNombres) {
                String consulta = "UPDATE instructor SET Nombres = ? WHERE id = ?";

                try (PreparedStatement ps = conexion.prepareStatement(consulta)) {
                    ps.setString(1, instructorPUTModel.getNuevosNombres());
                    ps.setInt(2, IDInstructor);
                    int rowsAffected = ps.executeUpdate();
                    if (rowsAffected <= 0) {
                        return new ResponseEntity<>("No se pudo actualizar los nombres", HttpStatus.BAD_REQUEST);
                    }
                }
            }

            if (cambiarApellidos) {
                String consulta = "UPDATE instructor SET Apellidos = ? WHERE Documento = ?";

                try (PreparedStatement ps = conexion.prepareStatement(consulta)) {
                    ps.setString(1, instructorPUTModel.getNuevosApellidos());
                    ps.setInt(2, IDInstructor);
                    int rowsAffected = ps.executeUpdate();
                    if (rowsAffected <= 0) {
                        return new ResponseEntity<>("No se pudo actualizar los apellidos", HttpStatus.BAD_REQUEST);
                    }
                }
            }

            if (cambiarGenero) {
                String consulta = "UPDATE instructor SET IDGenero = ? WHERE Documento = ?";

                try (PreparedStatement ps = conexion.prepareStatement(consulta)) {
                    ps.setInt(1, instructorPUTModel.getNuevoGenero());
                    ps.setInt(2, IDInstructor);
                    int rowsAffected = ps.executeUpdate();
                    if (rowsAffected <= 0) {
                        return new ResponseEntity<>("No se pudo actualizar el género", HttpStatus.BAD_REQUEST);
                    }
                }
            }

            if (cambiarTelefono) {
                String consulta = "UPDATE instructor SET Telefono = ? WHERE Documento = ?";

                try (PreparedStatement ps = conexion.prepareStatement(consulta)) {
                    ps.setInt(1, instructorPUTModel.getNuevoTelefono());
                    ps.setInt(2, IDInstructor);
                    int rowsAffected = ps.executeUpdate();
                    if (rowsAffected <= 0) {
                        return new ResponseEntity<>("No se pudo actualizar el teléfono", HttpStatus.BAD_REQUEST);
                    }
                }
            }

            if (cambiarArea) {
                String consulta = "UPDATE instructor SET Area = ? WHERE Documento = ?";

                try (PreparedStatement ps = conexion.prepareStatement(consulta)) {
                    ps.setString(1, instructorPUTModel.getNuevoArea());
                    ps.setInt(2, IDInstructor);
                    int rowsAffected = ps.executeUpdate();
                    if (rowsAffected <= 0) {
                        return new ResponseEntity<>("No se pudo actualizar el área", HttpStatus.BAD_REQUEST);
                    }
                }
            }

            if (cambiarRol) {
                String consulta = "UPDATE instructor SET IDRol = ? WHERE Documento = ?";

                try (PreparedStatement ps = conexion.prepareStatement(consulta)) {
                    ps.setInt(1, instructorPUTModel.getNuevoRol());
                    ps.setInt(2, IDInstructor);
                    int rowsAffected = ps.executeUpdate();
                    if (rowsAffected <= 0) {
                        return new ResponseEntity<>("No se pudo actualizar el rol", HttpStatus.BAD_REQUEST);
                    }
                }
            }

            if (cambiarSede) {
                String consulta = "UPDATE instructor SET IDSede = ? WHERE Documento = ?";

                try (PreparedStatement ps = conexion.prepareStatement(consulta)) {
                    ps.setInt(1, instructorPUTModel.getNuevaSede());
                    ps.setInt(2, IDInstructor);
                    int rowsAffected = ps.executeUpdate();
                    if (rowsAffected <= 0) {
                        return new ResponseEntity<>("No se pudo actualizar la sede", HttpStatus.BAD_REQUEST);
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

