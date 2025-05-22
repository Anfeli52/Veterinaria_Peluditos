/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import javax.swing.table.TableCellRenderer;
import java.awt.Component;
import javax.swing.JButton;
import javax.swing.JTable;

/**
 *
 * @author anfel
 */
public class Boton implements TableCellRenderer {
    
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {
        return (Component) value;
    }

}
