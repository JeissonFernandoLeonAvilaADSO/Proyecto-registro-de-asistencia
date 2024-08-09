/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.util.models;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import java.util.HashMap;
import java.util.Map;


public class CustomCellRenderer extends DefaultTableCellRenderer {
    private final Map<Integer, Color> rowColors = new HashMap<>();

    public void highlightRow(int row, String estado) {
        switch (estado) {
            case "A tiempo":
                rowColors.put(row, Color.GREEN);
                break;
            case "Tarde":
                rowColors.put(row, Color.ORANGE);
                break;
            case "Inasistencia":
                rowColors.put(row, Color.RED);
                break;
            default:
                rowColors.put(row, Color.WHITE);
                break;
        }
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        if (rowColors.containsKey(row)) {
            c.setBackground(rowColors.get(row));
        } else {
            c.setBackground(Color.RED);
        }
        return c;
    }
}








