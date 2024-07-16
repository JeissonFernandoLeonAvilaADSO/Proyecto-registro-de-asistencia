/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.util.models;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JButton;
import javax.swing.border.AbstractBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicButtonUI;

/**
 *
 * @author Propietario
 */
public class ButtonStyler {
    
    public static void applyPrimaryStyle(JButton button) {
        button.setUI(new BasicButtonUI());
        button.setFocusPainted(false);
        button.setContentAreaFilled(true);
        button.setOpaque(true);
        button.setBackground(Color.decode("#39A900"));
        button.setForeground(Color.WHITE);
        button.setBorder(new EmptyBorder(10, 20, 10, 10));

        // Configurar eventos de mouse
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(Color.decode("#008550"));
                button.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(Color.decode("#39A900"));
                button.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }

            public void mousePressed(java.awt.event.MouseEvent evt) {
                button.setBackground(Color.decode("#87CA66"));
            }

            public void mouseReleased(java.awt.event.MouseEvent evt) {
                button.setBackground(Color.decode("#39A900"));
            }
        });
    }

    public static void applySecondaryStyle(JButton button) {
        button.setUI(new BasicButtonUI());
        button.setFocusPainted(false);
        button.setContentAreaFilled(true);
        button.setOpaque(true);
        button.setBackground(Color.decode("#002240"));
        button.setForeground(Color.WHITE);
        button.setBorder(new EmptyBorder(10, 20, 10, 10));

        // Configurar eventos de mouse
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(Color.decode("#001833"));
                button.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(Color.decode("#002240"));
                button.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }

            public void mousePressed(java.awt.event.MouseEvent evt) {
                button.setBackground(Color.decode("#004080"));
            }

            public void mouseReleased(java.awt.event.MouseEvent evt) {
                button.setBackground(Color.decode("#002240"));
            }
        });
    }
}
