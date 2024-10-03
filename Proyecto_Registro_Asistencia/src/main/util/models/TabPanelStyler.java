package main.util.models;

import javax.swing.*;
import javax.swing.plaf.basic.BasicTabbedPaneUI;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class TabPanelStyler {

    // Primary Style
    public static void applyPrimaryStyle(JTabbedPane tabbedPane) {
        tabbedPane.setUI(new BasicTabbedPaneUI() {
            @Override
            protected void installDefaults() {
                super.installDefaults();
                tabbedPane.setForeground(Color.WHITE);  // Color de texto de las tabs
                tabbedPane.setBackground(Color.decode("#39A900")); // Color de fondo
                tabbedPane.setOpaque(true);
            }

            @Override
            protected void paintTabBackground(Graphics g, int tabPlacement, int tabIndex, int x, int y, int w, int h, boolean isSelected) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); // Suavizar bordes

                if (isSelected) {
                    g2.setColor(Color.decode("#008550")); // Color del tab seleccionado
                } else {
                    g2.setColor(Color.decode("#39A900")); // Color del tab no seleccionado
                }

                g2.fillRoundRect(x, y, w, h, 15, 15); // Bordes redondeados

                // Borde más oscuro
                g2.setColor(Color.decode("#006837"));
                g2.drawRoundRect(x, y, w - 1, h - 1, 15, 15); // Borde redondeado
            }

            @Override
            protected void paintText(Graphics g, int tabPlacement, Font font, FontMetrics metrics, int tabIndex, String title, Rectangle textRect, boolean isSelected) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON); // Antialiasing para suavizar letras
                g2.setFont(font);

                if (isSelected) {
                    g2.setColor(Color.WHITE); // Color del texto del tab seleccionado
                } else {
                    g2.setColor(Color.WHITE); // Color del texto del tab no seleccionado
                }

                g2.drawString(title, textRect.x, textRect.y + metrics.getAscent());
            }
        });

        // Agregar efecto hover a las tabs
        tabbedPane.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                JTabbedPane source = (JTabbedPane) e.getSource();
                int index = source.indexAtLocation(e.getX(), e.getY());
                if (index >= 0) {
                    source.setForegroundAt(index, Color.decode("#87CA66")); // Color de texto al pasar el mouse
                    source.setBackgroundAt(index, Color.decode("#008550")); // Color de fondo al pasar el mouse
                    source.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                JTabbedPane source = (JTabbedPane) e.getSource();
                int index = source.indexAtLocation(e.getX(), e.getY());
                if (index >= 0) {
                    source.setForegroundAt(index, Color.WHITE); // Restablecer color de texto
                    source.setBackgroundAt(index, Color.decode("#39A900")); // Restablecer color de fondo
                    source.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                }
            }
        });
    }
}