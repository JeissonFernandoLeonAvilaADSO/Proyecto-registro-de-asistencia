/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.util.models;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.plaf.basic.BasicButtonUI;

/**
 *
 * @author Propietario
 */
public class ToggleButtonStyler {
public static void applyPrimaryStyle(JToggleButton button) {
        button.setUI(new BasicButtonUI());
        button.setFocusPainted(false);
        button.setContentAreaFilled(true);
        button.setOpaque(true);
        button.setBackground(Color.WHITE);
        button.setForeground(Color.BLACK);

        RoundedBorder defaultBorder = new RoundedBorder(Color.decode("#39A900"), 10);
        button.setBorder(defaultBorder);

        // Configurar eventos de cambio de estado
        button.addItemListener(evt -> {
            if (evt.getStateChange() == ItemEvent.SELECTED) {
                button.setBackground(Color.decode("#39A900"));
                button.setForeground(Color.WHITE);
                button.setBorder(new RoundedBorder(Color.WHITE, 10));
            } else {
                button.setBackground(Color.WHITE);
                button.setForeground(Color.BLACK);
                button.setBorder(defaultBorder);
            }
        });

        // Configurar eventos de mouse
        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                button.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }

            public void mouseExited(MouseEvent evt) {
                button.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
        });
    }

    public static void applySecondaryStyle(JToggleButton button) {
        button.setUI(new BasicButtonUI());
        button.setFocusPainted(false);
        button.setContentAreaFilled(true);
        button.setOpaque(true);
        button.setBackground(Color.WHITE);
        button.setForeground(Color.BLACK);

        RoundedBorder defaultBorder = new RoundedBorder(Color.decode("#002240"), 10);
        button.setBorder(defaultBorder);

        // Configurar eventos de cambio de estado
        button.addItemListener(evt -> {
            if (evt.getStateChange() == ItemEvent.SELECTED) {
                button.setBackground(Color.decode("#002240"));
                button.setForeground(Color.WHITE);
                button.setBorder(new RoundedBorder(Color.WHITE, 10));
            } else {
                button.setBackground(Color.WHITE);
                button.setForeground(Color.BLACK);
                button.setBorder(defaultBorder);
            }
        });

        // Configurar eventos de mouse
        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                button.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }

            public void mouseExited(MouseEvent evt) {
                button.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
        });
    }

    // Clase interna para crear un borde redondeado
    static class RoundedBorder extends EmptyBorder {
        private final Color color;
        private final int radius;

        public RoundedBorder(Color color, int radius) {
            super(5, 15, 5, 15);
            this.color = color;
            this.radius = radius;
        }

        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(color);
            g2.drawRoundRect(x, y, width - 1, height - 1, radius, radius);
        }
    }
}
