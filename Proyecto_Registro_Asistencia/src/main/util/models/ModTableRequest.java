/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.util.models;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.util.HashMap;
import java.util.Map;
import main.util.API_Actions.ConvertirDatos;

/**
 *
 * @author Propietario
 */


public class ModTableRequest {
    private Map<String, Object> TablaAnterior;
    private Map<String, Object> TablaEntrante;

    public ModTableRequest() {}

    public ModTableRequest(Map<String, Object> tablaAnterior, Map<String, Object> tablaEntrante) {
        this.TablaAnterior = tablaAnterior != null ? tablaAnterior : new HashMap<>();
        this.TablaEntrante = tablaEntrante != null ? tablaEntrante : new HashMap<>();
    }

    public Map<String, Object> getTablaAnterior() {
        return TablaAnterior;
    }

    public void setTablaAnterior(Map<String, Object> tablaAnterior) {
        this.TablaAnterior = tablaAnterior != null ? tablaAnterior : new HashMap<>();
    }

    public Map<String, Object> getTablaEntrante() {
        return TablaEntrante;
    }

    public void setTablaEntrante(Map<String, Object> tablaEntrante) {
        this.TablaEntrante = tablaEntrante != null ? tablaEntrante : new HashMap<>();
    }

    public void inicializarTablaAnterior(DefaultTableModel previousModel) {
        this.TablaAnterior = new HashMap<>();
        if (previousModel != null) {
            for (int i = 0; i < previousModel.getRowCount(); i++) {
                String key = String.valueOf(i + 1); // Usar índice + 1 como clave para emular IDs
                Object value = previousModel.getValueAt(i, 0); // Asumiendo que la primera columna contiene los valores
                if (value != null) {
                    this.TablaAnterior.put(key, value.toString());
                }
            }
        }
    }

    public void inicializarTablaEntrante(JTable table) {
        this.TablaEntrante = new HashMap<>();
        if (table != null && table.getModel() instanceof DefaultTableModel) {
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            for (int i = 0; i < model.getRowCount(); i++) {
                String key = String.valueOf(i + 1); // Usar índice + 1 como clave para emular IDs
                Object value = model.getValueAt(i, 0); // Asumiendo que la primera columna contiene los valores
                if (value != null) {
                    this.TablaEntrante.put(key, value.toString());
                }
            }
        }
    }

public static ModTableRequest fromTable(DefaultTableModel previousModel, JTable table) {
    ModTableRequest request = new ModTableRequest();
    ConvertirDatos convertirDatos = new ConvertirDatos();
    
    if (previousModel != null && table.getModel() instanceof DefaultTableModel) {
        // Inicializar TablaAnterior con el modelo previo
        Map<String, Object> tablaAnterior = new HashMap<>();
        for (int i = 0; i < previousModel.getRowCount(); i++) {
            // Asegurarse de que los índices están dentro de los límites antes de acceder
            if (previousModel.getColumnCount() > 0) {
                String key = String.valueOf(i + 1); // Usar un índice como clave
                Object value = previousModel.getValueAt(i, 0); // Valor es el nombre o descripción
                if (value != null) {
                    tablaAnterior.put(key, value.toString());
                }
            }
        }
        request.setTablaAnterior(tablaAnterior);
        
        // Inicializar TablaEntrante con el modelo actual
        DefaultTableModel currentModel = (DefaultTableModel) table.getModel();
        Map<String, Object> tablaEntrante = new HashMap<>();
        for (int i = 0; i < currentModel.getRowCount(); i++) {
            // Verificar que la columna 0 y 1 existan antes de acceder a ellas
            if (currentModel.getColumnCount() > 0) {
                String key = String.valueOf(i + 1); // Usar el mismo índice
                String numeroFicha = currentModel.getValueAt(i, 0).toString(); // "NumeroFicha" como valor

                if (currentModel.getColumnCount() > 1) {
                    String programaFormacionStr = currentModel.getValueAt(i, 1).toString(); // Programa en texto
                    Integer idPrograma = convertirDatos.ObtenerIDProgramaFormacion(programaFormacionStr); // Convertir a ID
                    
                    if (idPrograma != null) {
                        // Usar el ID como valor en la tabla entrante
                        Map<String, Object> fichaData = new HashMap<>();
                        fichaData.put("NumeroFicha", numeroFicha);
                        fichaData.put("IDProgramaFormacion", idPrograma.toString());
                        tablaEntrante.put(key, fichaData);
                    } else {
                        System.err.println("Error: No se encontró ID para el programa de formación: " + programaFormacionStr);
                    }
                } else {
                    // Manejo de casos donde solo hay una columna
                    tablaEntrante.put(key, currentModel.getValueAt(i, 0));
                }
            }
        }
        
        request.setTablaEntrante(tablaEntrante);
        System.out.println("TablaAnterior: " + request.getTablaAnterior());
        System.out.println("TablaEntrante: " + request.getTablaEntrante());
    } else {
        System.err.println("Error: previousModel o table no son válidos.");
    }
    return request;
}
}
