/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Vista.AgregarPaciente;
import Vista.EditarPropietario;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractCellEditor;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;

/**
 *
 * @author anfel
 */
public class BotonEditar extends AbstractCellEditor implements TableCellEditor{
    
    private JButton button;
    private EditarPropietario editarPropietario;
    
    
    public BotonEditar(JTable table) {
        button = new JButton("Editar");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int fila = table.getSelectedRow();
                String valor = (String) table.getValueAt(fila, 0);
                long idPropietario = Long.parseLong(valor);

                try {
                    editarPropietario = new EditarPropietario(idPropietario);
                } catch (SQLException ex) {
                    Logger.getLogger(BotonEditar.class.getName()).log(Level.SEVERE, null, ex);
                }
                editarPropietario.setVisible(true);
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
