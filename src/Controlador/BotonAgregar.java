/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Vista.AgregarPaciente;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.AbstractCellEditor;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;

/**
 *
 * @author anfel
 */
public class BotonAgregar extends AbstractCellEditor implements TableCellEditor {

    private JButton button;
    private AgregarPaciente agregarPaciente;
    
    
    public BotonAgregar(JTable table) {
        button = new JButton("Agregar");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int fila = table.getSelectedRow();
                String valor = (String) table.getValueAt(fila, 0);
                long idPropietario = Long.parseLong(valor);

                agregarPaciente = new AgregarPaciente(idPropietario);
                agregarPaciente.setVisible(true);
            }
        });
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value,
            boolean isSelected, int row, int column) {
        return button;
    }

    @Override
    public Object getCellEditorValue() {
        return button;
    }
}