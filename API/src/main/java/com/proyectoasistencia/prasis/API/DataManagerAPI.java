package com.proyectoasistencia.prasis.API;

import com.proyectoasistencia.prasis.models.ModTableRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.*;
import java.util.Map;

@RestController
@RequestMapping("/DataMg")
public class DataManagerAPI {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private PlatformTransactionManager transactionManager;

    @PostMapping("/ModTipoDocTable")
    public ResponseEntity<String> ModTipoDocTable(@RequestBody ModTableRequest request) {
        return ModTable(request.getTablaAnterior(), request.getTablaEntrante(), "tipodocumento", "TipoDocumento");
    }

    @PostMapping("/ModFichasTable")
    public ResponseEntity<String> ModFichasTable(@RequestBody ModTableRequest request) {
        return ModTable(request.getTablaAnterior(), request.getTablaEntrante(), "fichas", "NumeroFicha");
    }

    @PostMapping("/ModGeneroTable")
    public ResponseEntity<String> ModGeneroTable(@RequestBody ModTableRequest request) {
        return ModTable(request.getTablaAnterior(), request.getTablaEntrante(), "genero", "TiposGeneros");
    }

    @PostMapping("/ModSedeTable")
    public ResponseEntity<String> ModSedeTable(@RequestBody ModTableRequest request) {
        return ModTable(request.getTablaAnterior(), request.getTablaEntrante(), "sede", "CentroFormacion");
    }

    @PostMapping("/ModAmbientesTable")
    public ResponseEntity<String> ModAmbientesTable(@RequestBody ModTableRequest request) {
        return ModTable(request.getTablaAnterior(), request.getTablaEntrante(), "ambientes", "Ambiente");
    }

    @PostMapping("/ModProgramaFormacionTable")
    public ResponseEntity<String> ModProgramaFormacionTable(@RequestBody ModTableRequest request) {
        return ModTable(request.getTablaAnterior(), request.getTablaEntrante(), "programaformacion", "ProgramaFormacion");
    }

    public ResponseEntity<String> ModTable(Map<String, Object> TablaAnterior, Map<String, Object> TablaEntrante, String tableName, String columnName) {
        System.out.println(TablaAnterior);
        System.out.println(TablaEntrante);
        if (TablaAnterior == null || TablaEntrante == null) {
            if (TablaAnterior == null) {
                return new ResponseEntity<>("TablaAnterior no puede ser null", HttpStatus.BAD_REQUEST);
            } else if (TablaEntrante == null) {
                return new ResponseEntity<>("TablaEntrante no puede ser null", HttpStatus.BAD_REQUEST);
            }
        }

        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        TransactionStatus status = transactionManager.getTransaction(def);

        try (Connection conexion = jdbcTemplate.getDataSource().getConnection()) {
            // Agregar nuevos registros
            for (Map.Entry<String, Object> entry : TablaEntrante.entrySet()) {
                if (!TablaAnterior.containsKey(entry.getKey())) {
                    // Insertar nueva entrada en la base de datos, sin el ID porque es autoincrementable
                    String insertQuery = "INSERT INTO " + tableName + " (" + columnName + ") VALUES (?)";
                    try (PreparedStatement psInsert = conexion.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS)) {
                        psInsert.setObject(1, entry.getValue()); // El valor del map es el valor de la columna
                        psInsert.executeUpdate();

                        // Obtener el ID generado automáticamente
                        try (ResultSet generatedKeys = psInsert.getGeneratedKeys()) {
                            if (generatedKeys.next()) {
                                long generatedId = generatedKeys.getLong(1);
                                // Aquí podrías hacer algo con el ID generado si es necesario
                            }
                        }
                    }
                } else if (!entry.getValue().equals(TablaAnterior.get(entry.getKey()))) {
                    // Modificar la entrada existente si es diferente
                    String updateQuery = "UPDATE " + tableName + " SET " + columnName + " = ? WHERE ID = ?";
                    try (PreparedStatement psUpdate = conexion.prepareStatement(updateQuery)) {
                        psUpdate.setObject(1, entry.getValue()); // El valor del map es el nuevo valor para la columna
                        psUpdate.setObject(2, entry.getKey());   // Suponiendo que la clave del map es el ID
                        psUpdate.executeUpdate();
                    }
                }
            }

            // Eliminar registros que están en TablaAnterior pero no en TablaEntrante
            for (String key : TablaAnterior.keySet()) {
                if (!TablaEntrante.containsKey(key)) {
                    // Paso 1: Obtener todas las tablas que tienen una clave foránea que apunta a la tabla principal
                    String getForeignKeysQuery = "SELECT TABLE_NAME, COLUMN_NAME " +
                            "FROM INFORMATION_SCHEMA.KEY_COLUMN_USAGE " +
                            "WHERE REFERENCED_TABLE_NAME = ? AND REFERENCED_COLUMN_NAME = 'ID'";

                    try (PreparedStatement psGetFK = conexion.prepareStatement(getForeignKeysQuery)) {
                        psGetFK.setString(1, tableName);
                        ResultSet rsFK = psGetFK.executeQuery();

                        // Paso 2: Actualizar dinámicamente todas las tablas dependientes para establecer la columna de clave foránea a NULL
                        while (rsFK.next()) {
                            String dependentTable = rsFK.getString("TABLE_NAME");
                            String dependentColumn = rsFK.getString("COLUMN_NAME");

                            String updateDependentTableQuery = "UPDATE " + dependentTable + " SET " + dependentColumn + " = NULL WHERE " + dependentColumn + " = ?";
                            try (PreparedStatement psUpdateDependents = conexion.prepareStatement(updateDependentTableQuery)) {
                                psUpdateDependents.setObject(1, key);
                                psUpdateDependents.executeUpdate();
                            }
                        }
                    }

                    // Paso 3: Luego, eliminar el registro en la tabla principal
                    String deleteQuery = "DELETE FROM " + tableName + " WHERE ID = ?";
                    try (PreparedStatement psDelete = conexion.prepareStatement(deleteQuery)) {
                        psDelete.setObject(1, key); // Suponiendo que la clave del map es el ID
                        psDelete.executeUpdate();
                    }
                }
            }

            transactionManager.commit(status);
            return new ResponseEntity<>("Tabla modificada exitosamente", HttpStatus.OK);
        } catch (SQLException e) {
            transactionManager.rollback(status);
            e.printStackTrace();
            return new ResponseEntity<>("Error durante la modificación: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}


