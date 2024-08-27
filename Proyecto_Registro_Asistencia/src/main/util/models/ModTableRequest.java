/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.util.models;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Propietario
 */


public class ModTableRequest {
    private Map<String, Object> TablaAnterior;
    private Map<String, Object> TablaEntrante;

    public ModTableRequest() {
    }

    public ModTableRequest(Map<String, Object> tablaAnterior, Map<String, Object> tablaEntrante) {
        this.TablaAnterior = tablaAnterior;
        this.TablaEntrante = tablaEntrante;
    }

    public Map<String, Object> getTablaAnterior() {
        return TablaAnterior;
    }

    public void setTablaAnterior(Map<String, Object> tablaAnterior) {
        this.TablaAnterior = tablaAnterior;
    }

    public Map<String, Object> getTablaEntrante() {
        return TablaEntrante;
    }

    public void setTablaEntrante(Map<String, Object> tablaEntrante) {
        this.TablaEntrante = tablaEntrante;
    }

    // Método para obtener el estado anterior de la tabla
    public void inicializarTablaAnterior(DefaultTableModel previousModel) {
        this.TablaAnterior = new HashMap<>();
        for (int i = 0; i < previousModel.getRowCount(); i++) {
            String key = previousModel.getValueAt(i, 0).toString();  // Asumiendo que la primera columna es la clave
            Object value = null;
            if (previousModel.getColumnCount() > 1) { // Verificar si hay al menos dos columnas
                value = previousModel.getValueAt(i, 1);  // Asumiendo que la segunda columna es el valor
            }
            this.TablaAnterior.put(key, value);
        }
    }

    public void inicializarTablaEntrante(JTable table) {
        this.TablaEntrante = new HashMap<>();
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        for (int i = 0; i < model.getRowCount(); i++) {
            String key = model.getValueAt(i, 0).toString();  // Asumiendo que la primera columna es la clave
            Object value = null;
            if (model.getColumnCount() > 1) { // Verificar si hay al menos dos columnas
                value = model.getValueAt(i, 1);  // Asumiendo que la segunda columna es el valor
            }
            this.TablaEntrante.put(key, value);
        }
    }

    // Método estático para crear una instancia de ModTableRequest desde una JTable y su modelo anterior
    public static ModTableRequest fromTable(DefaultTableModel previousModel, JTable table) {
        ModTableRequest request = new ModTableRequest();
        request.inicializarTablaAnterior(previousModel);
        request.inicializarTablaEntrante(table);
        return request;
    }
}
