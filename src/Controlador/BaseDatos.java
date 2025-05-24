/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelo.Paciente;
import Modelo.Profesional;
import Modelo.Propietario;
import Modelo.Vacuna;
import java.awt.Color;
import java.awt.HeadlessException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author anfel
 */
public class BaseDatos {

    private static Connection dbConnection;
    private static String nameDB;
    private static String user;
    private static String pwd;
    private static PreparedStatement pstm;
    private static ResultSet rs;
    private static int port;

    public BaseDatos() {

    }

    public boolean crearConexion() {
        nameDB = "veterinaria_corp";
        user = "root";
        pwd = ""; // SI SE TRABAJA DESDE WORKBENCH, DEBE CAMBIAR ESTE VALOR POR "root"
        port = 3306;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String sourceURL = "jdbc:mysql://localhost:" + port + "/" + nameDB; //El significado del :3307 es que yo no tengo la conexión de la base de datos que trabajamos en el Puerto 3306, sino que en el 3307

            dbConnection = DriverManager.getConnection(sourceURL, user, pwd);
            System.out.println("!! Conexion con la base de datos " + nameDB + " establecida exitosamente !!");
        } catch (ClassNotFoundException | SQLException evt) {
            System.err.println(evt);
            System.out.println("!! Conexión con la base de datos " + nameDB + " fallida !!");
            return false;
        }
        return true;
    }

    public void cerrarConexion() {
        try {
            if (dbConnection != null) {
                try {
                    dbConnection.close();
                    System.out.println("!!Cierre exitoso de la conexion con la base de datos " + nameDB + "!!");
                    dbConnection = null;
                } catch (SQLException evt) {
                    System.out.println("!!Cierre fallido de la conexion con la base de datos " + nameDB + "!!");
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }

    }

    public Connection getConnection() {
        return dbConnection;
    }

    public void mostrarProfesionales(JTable tablaProfesionales) {
        String nameTable = "";

        try {
            nameTable = "profesionales";
            String sqlString = "SELECT numero_documento, nombre_completo, correo_electronico, telefono, fecha_inicio_cuidado FROM " + nameTable;
            //String sqlString = "SELECT numero_documento, nombre_completo, correo_electronico, telefono, fecha_inicio_cuidado FROM "+nameTable+" WHERE correo_electronico LIKE ? OR nombre_completo LIKE ?";
            Statement stm;

            //stm = dbConnection.createStatement();
            DefaultTableModel tableModel = new DefaultTableModel();
            String tablaArray[] = {"Número de Documento", "Nombre Completo", "Correo Electrónico", "Teléfono", "Fecha de Inicio"};

            for (String tablaArray3 : tablaArray) {
                tableModel.addColumn(tablaArray3);
            }

            String[] datos = new String[5];
            try {
                stm = dbConnection.createStatement();
                rs = stm.executeQuery(sqlString);
                while (rs.next()) {
                    datos[0] = rs.getString(1);
                    datos[1] = rs.getString(2);
                    datos[2] = rs.getString(3);
                    datos[3] = rs.getString(4);
                    datos[4] = rs.getString(5);
                    tableModel.addRow(datos);
                }
            } catch (SQLException evt) {
                System.out.println("Error al encontrar los datos de la tabla " + nameTable);
                System.err.println(evt);
            }

            tablaProfesionales.setModel(tableModel);

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void mostrarPropietarios(JTable tablaPropietarios) {
        String nameTable = "propietarios";

        String sqlString = "SELECT numero_documento, nombre_completo, direccion_residencia, correo_electronico, telefono, "
                + "(SELECT count(*) FROM pacientes WHERE pacientes.numero_documento_propietario = propietarios.numero_documento) "
                + "AS cantidad_mascotas FROM propietarios";

        DefaultTableModel tableModel = new DefaultTableModel();
        String tablaArray[] = {"Número de Documento", "Nombre Completo", "Dirección de Residencia", "Correo Electrónico", "Teléfono", "Cantidad de Mascotas", "Agregar Mascota", "Editar", "Eliminar"};

        for (String tablaArray2 : tablaArray) {
            tableModel.addColumn(tablaArray2);
        }

        try (Statement stm = dbConnection.createStatement(); ResultSet rs = stm.executeQuery(sqlString)) {

            boolean hayDatos = false;

            while (rs.next()) {
                hayDatos = true;
                Object[] fila = new Object[9];

                JButton btnAgregarMascota = new JButton("Agregar");
                btnAgregarMascota.setBackground(Color.WHITE);
                btnAgregarMascota.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));

                JButton btnEditarPropietario = new JButton("Editar");
                btnEditarPropietario.setBackground(Color.WHITE);
                btnEditarPropietario.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));

                JButton btnEliminarPropietario = new JButton("Eliminar");
                btnEliminarPropietario.setBackground(Color.WHITE);
                btnEliminarPropietario.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));

                fila[0] = rs.getString(1);
                fila[1] = rs.getString(2);
                fila[2] = rs.getString(3);
                fila[3] = rs.getString(4);
                fila[4] = rs.getString(5);
                fila[5] = rs.getString(6);
                fila[6] = btnAgregarMascota;
                fila[7] = btnEditarPropietario;
                fila[8] = btnEliminarPropietario;

                tableModel.addRow(fila);
            }

            if (!hayDatos) {
                Object[] filaVacia = new Object[9];
                filaVacia[0] = "No hay datos registrados";
                for (int i = 1; i < filaVacia.length; i++) {
                    filaVacia[i] = "";
                }
                tableModel.addRow(filaVacia);

                tablaPropietarios.setModel(tableModel);
                tablaPropietarios.setEnabled(false);
                return;
            }

        } catch (SQLException e) {
            System.out.println("Error al obtener los datos de la tabla " + nameTable);
            e.printStackTrace();
        }

        tablaPropietarios.setModel(tableModel);

        tablaPropietarios.getColumnModel().getColumn(6).setCellRenderer(new Boton());
        tablaPropietarios.getColumnModel().getColumn(6).setCellEditor(new BotonAgregar(tablaPropietarios, nameTable));

        tablaPropietarios.getColumnModel().getColumn(7).setCellRenderer(new Boton());
        tablaPropietarios.getColumnModel().getColumn(7).setCellEditor(new BotonEditar(tablaPropietarios));

        tablaPropietarios.getColumnModel().getColumn(8).setCellRenderer(new Boton());
        tablaPropietarios.getColumnModel().getColumn(8).setCellEditor(new BotonEliminar(tablaPropietarios, nameTable));
    }

    public void mostrarPacientes(JTable tablaPacientes) {
        String nameTable = "";

        try {
            nameTable = "pacientes";
            
            String sqlString = "SELECT numero_id, nombre, sexo, especie, caracteristicas_particulares, enfermedades_base, numero_documento_propietario, (SELECT count(*) "
                    + "FROM vacunas WHERE vacunas.numero_id_paciente = pacientes.numero_id) as cantidad_vacunas FROM " + nameTable;
            Statement stm;

            DefaultTableModel tableModel = new DefaultTableModel();
            String tablaArray[] = {"Id del Paciente", "Nombre", "Sexo", "Especie", "Caracterísitcas Particulares", "Enfermedades Base", "Id del Propietario", "Total de Vacunas", "Añadir Vacuna", "Eliminar"};

            for (String tablaArray1 : tablaArray) {
                tableModel.addColumn(tablaArray1);
            }
            
            try {
                stm = dbConnection.createStatement();
                rs = stm.executeQuery(sqlString);

                boolean hayResultados = false;

                while (rs.next()) {
                    hayResultados = true;
                    Object[] fila = new Object[10];

                    JButton btnAgregarMascota = new JButton("Agregar");
                    btnAgregarMascota.setBackground(Color.WHITE);
                    btnAgregarMascota.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));

                    JButton btnEliminarMascota = new JButton("Eliminar");
                    btnEliminarMascota.setBackground(Color.WHITE);
                    btnEliminarMascota.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));

                    fila[0] = rs.getString(1);
                    fila[1] = rs.getString(2);
                    fila[2] = rs.getString(3);
                    fila[3] = rs.getString(4);
                    fila[4] = rs.getString(5);
                    fila[5] = rs.getString(6);
                    fila[6] = rs.getString(7);
                    fila[7] = rs.getString(8);
                    fila[8] = btnAgregarMascota;
                    fila[9] = btnEliminarMascota;

                    tableModel.addRow(fila);
                }

                if (!hayResultados) {
                    Object[] fila = new Object[10];
                    fila[0] = "No hay datos registrados";
                    for (int i = 1; i < fila.length; i++) {
                        fila[i] = null;
                    }
                    tableModel.addRow(fila);
                    
                    tablaPacientes.setModel(tableModel);
                    return;
                }

            } catch (SQLException evt) {
                System.out.println("Error al encontrar los datos de la tabla " + nameTable);
                System.err.println(evt);
            }

            tablaPacientes.setModel(tableModel);

            //Botón para Añadir Pacientes
            tablaPacientes.getColumnModel().getColumn(8).setCellRenderer(new Boton());
            tablaPacientes.getColumnModel().getColumn(8).setCellEditor(new BotonAgregar(tablaPacientes, nameTable));

            //Botón para Editar Propietarios
            tablaPacientes.getColumnModel().getColumn(9).setCellRenderer(new Boton());
            tablaPacientes.getColumnModel().getColumn(9).setCellEditor(new BotonEliminar(tablaPacientes, nameTable));
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public boolean insertarProfesional(Profesional profesional) throws SQLException {
        String nameTable = "";
        try {
            nameTable = "profesionales";
            String sqlString = "INSERT INTO " + nameTable + " (numero_documento, tipo_documento, nombre_completo, direccion_residencia, correo_electronico, telefono, fecha_inicio_cuidado, contrasena) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            pstm = dbConnection.prepareStatement(sqlString);
            pstm.setLong(1, profesional.getPnumero_documento());
            pstm.setString(2, String.valueOf(profesional.getPtipo_documento()));
            pstm.setString(3, profesional.getPnombre_completo());
            pstm.setString(4, profesional.getPdireccion_residencia());
            pstm.setString(5, profesional.getPcorreo_electronico());
            pstm.setString(6, profesional.getPtelefono());
            pstm.setDate(7, profesional.getDate());
            pstm.setString(8, profesional.passwordCreator());
            pstm.executeUpdate();

        } catch (SQLException evt) {
            System.out.println("!!Operación de inserción en la tabla " + nameTable + " fallida.!!");
            System.err.println(evt);
            return false;
        } finally {
            if (pstm != null) {
                try {
                    pstm.close();
                } catch (SQLException e) {
                    System.err.println("Error al cerrar PreparedStatement: " + e);
                }
            }
        }

        return true;
    }

    public boolean insertarPropietario(Propietario propietario) throws SQLException {
        String nameTable = "";
        try {
            nameTable = "propietarios";
            String sqlString = "INSERT INTO " + nameTable + " (numero_documento, tipo_documento, nombre_completo, direccion_residencia, correo_electronico, telefono, fecha_inicio_cuidado) VALUES(?, ?, ?, ?, ?, ?, ?)";
            pstm = dbConnection.prepareStatement(sqlString);
            pstm.setLong(1, propietario.getPnumero_documento());
            pstm.setString(2, String.valueOf(propietario.getPtipo_documento()));
            pstm.setString(3, propietario.getPnombre_completo());
            pstm.setString(4, propietario.getPdireccion_residencia());
            pstm.setString(5, propietario.getPcorreo_electronico());
            pstm.setString(6, propietario.getPtelefono());
            pstm.setDate(7, propietario.getPdate());
            pstm.executeUpdate();

        } catch (SQLException evt) {
            System.out.println("!!Operación de inserción en la tabla " + nameTable + " fallida.!!");
            System.err.println(evt);
            return false;
        } finally {
            if (pstm != null) {
                try {
                    pstm.close();
                } catch (SQLException e) {
                    System.err.println("Error al cerrar PreparedStatement: " + e);
                }
            }
        }

        return true;
    }

    public boolean insertarPaciente(Paciente paciente) throws SQLException {
        String nameTable = "";
        try {
            nameTable = "pacientes";
            String sqlString = "INSERT INTO " + nameTable + " (numero_id, nombre, sexo, especie, peso, color, caracteristicas_particulares, procedencia, fin_zootecnico, esterilizado, enfermedades_base, numero_documento_propietario) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            pstm = dbConnection.prepareStatement(sqlString);
            pstm.setLong(1, paciente.getNumero_id());
            pstm.setString(2, paciente.getNombre());
            pstm.setString(3, paciente.getSexo());
            pstm.setString(4, paciente.getEspecie());
            pstm.setFloat(5, paciente.getPeso());
            pstm.setString(6, paciente.getColor());
            pstm.setString(7, paciente.getCaracteristicas_particulares());
            pstm.setString(8, paciente.getProcedencia());
            pstm.setString(9, paciente.getFin_zootecnico());
            pstm.setBoolean(10, paciente.isEsterilizado());
            pstm.setString(11, paciente.getEnfermedades_base());
            pstm.setLong(12, paciente.getNumero_documento_propietario());
            pstm.executeUpdate();

        } catch (SQLException evt) {
            System.out.println("!!Operación de inserción en la tabla " + nameTable + " fallida.!!");
            System.err.println(evt);
            return false;
        } finally {
            if (pstm != null) {
                try {
                    pstm.close();
                } catch (SQLException e) {
                    System.err.println("Error al cerrar PreparedStatement: " + e);
                }
            }
        }

        return true;
    }

    public boolean buscarProfesional(String correo, String password) throws SQLException {
        String nameTable = "";
        try {
            nameTable = "profesionales";
            String sqlString = "SELECT * FROM " + nameTable + " WHERE correo_electronico = ? AND contrasena = ?";
            pstm = dbConnection.prepareStatement(sqlString);
            pstm.setString(1, correo);
            pstm.setString(2, password);
            rs = pstm.executeQuery();

            if (rs.next()) {
                JOptionPane.showMessageDialog(null, "Inicio de Sesión Éxitoso");
            } else {
                JOptionPane.showMessageDialog(null, "!!!El correo no está registrado!!!");
                return false;
            }

        } catch (HeadlessException | SQLException evt) {
            System.out.println("!!Operación de busqueda en la tabla " + nameTable + " fallida.!!");
            System.err.println(evt);
            return false;
        }

        return true;
    }

    public void buscarEntidad(JTable tabla, String textoBusqueda, String nameTable) {
        DefaultTableModel tableModel = new DefaultTableModel();

        try {
            String sqlString = "";
            switch (nameTable) {
                case "pacientes" -> {
                    
                    String tablaArray [] = {"Id del Paciente", "Nombre", "Sexo", "Especie", "Caracterísitcas Particulares", "Enfermedades Base", "Id del Propietario", "Total de Vacunas", "Añadir Vacuna", "Eliminar"};
                    
                    for (String tablaArray1 : tablaArray) {
                        tableModel.addColumn(tablaArray1);
                    }
                    
                    sqlString = "SELECT numero_id, nombre, sexo, especie, caracteristicas_particulares, enfermedades_base, numero_documento_propietario, (SELECT count(*) "
                    + "FROM vacunas WHERE vacunas.numero_id_paciente = pacientes.numero_id) as cantidad_vacunas FROM pacientes WHERE nombre LIKE '%" + textoBusqueda + "%' OR especie LIKE '%" + textoBusqueda + "%' OR numero_id LIKE '%" + textoBusqueda + "%'";
                }
                case "propietarios" -> {
                    
                    String tablaArray [] = {"Número de Documento", "Nombre Completo", "Dirección de Residencia", "Correo Electrónico", "Teléfono", "Cantidad de Mascotas", "Agregar Mascota", "Editar", "Eliminar"};
                    
                    for (String tablaArray2 : tablaArray) {
                        tableModel.addColumn(tablaArray2);
                    }
                    
                    sqlString = "SELECT numero_documento, nombre_completo, direccion_residencia, correo_electronico, telefono, "
                            + "(SELECT count(*) FROM pacientes WHERE pacientes.numero_documento_propietario = propietarios.numero_documento) "
                            + "AS cantidad_mascotas FROM propietarios WHERE nombre_completo LIKE '%" + textoBusqueda + "%' OR correo_electronico LIKE '%" + textoBusqueda + "%' OR numero_documento LIKE '%" + textoBusqueda + "%'";
                }
                case "profesionales" -> {
                    
                    String tablaArray [] = {"Número de Documento", "Nombre Completo", "Correo Electrónico", "Teléfono", "Fecha de Inicio"};

                    for (String tablaArray3 : tablaArray) {
                        tableModel.addColumn(tablaArray3);
                    }
                    
                    sqlString = "SELECT numero_documento, nombre_completo, correo_electronico, telefono, fecha_inicio_cuidado FROM profesionales WHERE nombre_completo LIKE '%" + textoBusqueda + "%' OR correo_electronico LIKE '%" + textoBusqueda + "%' OR numero_documento LIKE '%" + textoBusqueda + "%'";
                }
                default -> {
                    return; // evita ejecutar SQL si nameTable no coincide
                }
            }

            Statement stm = dbConnection.createStatement();
            ResultSet rs = stm.executeQuery(sqlString);

            while (rs.next()) {
                switch (nameTable) {
                    case "pacientes" -> {
                        Object[] fila = new Object[10];
                        fila[0] = rs.getString(1);
                        fila[1] = rs.getString(2);
                        fila[2] = rs.getString(3);
                        fila[3] = rs.getString(4);
                        fila[4] = rs.getString(5);
                        fila[5] = rs.getString(6);
                        fila[6] = rs.getString(7);
                        fila[7] = rs.getString(8);
                        fila[8] = new JButton("Agregar");
                        fila[9] = new JButton("Eliminar");
                        tableModel.addRow(fila);
                    }
                    case "propietarios" -> {
                        Object[] fila = new Object[9];
                        fila[0] = rs.getString(1);
                        fila[1] = rs.getString(2);
                        fila[2] = rs.getString(3);
                        fila[3] = rs.getString(4);
                        fila[4] = rs.getString(5);
                        fila[5] = rs.getString(6);
                        fila[6] = new JButton("Agregar");
                        fila[7] = new JButton("Editar");
                        fila[8] = new JButton("Eliminar");
                        tableModel.addRow(fila);
                    }
                    case "profesionales" -> {
                        String[] datos = new String[5];
                        datos[0] = rs.getString(1);
                        datos[1] = rs.getString(2);
                        datos[2] = rs.getString(3);
                        datos[3] = rs.getString(4);
                        datos[4] = rs.getString(5);
                        tableModel.addRow(datos);
                    }
                }
            }

            tabla.setModel(tableModel);

            if (nameTable.equals("pacientes")) {
                tabla.getColumnModel().getColumn(8).setCellRenderer(new Boton());
                tabla.getColumnModel().getColumn(8).setCellEditor(new BotonAgregar(tabla, nameTable));
                tabla.getColumnModel().getColumn(9).setCellRenderer(new Boton());
                tabla.getColumnModel().getColumn(9).setCellEditor(new BotonEliminar(tabla, nameTable));
            } else if (nameTable.equals("propietarios")) {
                tabla.getColumnModel().getColumn(6).setCellRenderer(new Boton());
                tabla.getColumnModel().getColumn(6).setCellEditor(new BotonAgregar(tabla, nameTable));
                tabla.getColumnModel().getColumn(7).setCellRenderer(new Boton());
                tabla.getColumnModel().getColumn(7).setCellEditor(new BotonEditar(tabla));
                tabla.getColumnModel().getColumn(8).setCellRenderer(new Boton());
                tabla.getColumnModel().getColumn(8).setCellEditor(new BotonEliminar(tabla, nameTable));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean eliminarEntidad(long idEntidad, String nameTable) throws SQLException {
        String sqlString;

        try {
            if (nameTable.equals("propietarios")) {
                String queryPacientes = "SELECT numero_id FROM pacientes WHERE numero_documento_propietario = ?";
                List<Long> idsPacientes = new ArrayList<>();
                try (PreparedStatement pstmt = dbConnection.prepareStatement(queryPacientes)) {
                    pstmt.setLong(1, idEntidad);
                    ResultSet rs = pstmt.executeQuery();
                    while (rs.next()) {
                        idsPacientes.add(rs.getLong("numero_id"));
                    }
                }
                for (Long idPaciente : idsPacientes) {
                    String deleteVacunas = "DELETE FROM vacunas WHERE numero_id_paciente = ?";
                    try (PreparedStatement pstmtVac = dbConnection.prepareStatement(deleteVacunas)) {
                        pstmtVac.setLong(1, idPaciente);
                        pstmtVac.executeUpdate();
                    }
                    String deletePaciente = "DELETE FROM pacientes WHERE numero_id = ?";
                    try (PreparedStatement pstmtPac = dbConnection.prepareStatement(deletePaciente)) {
                        pstmtPac.setLong(1, idPaciente);
                        pstmtPac.executeUpdate();
                    }
                }
                sqlString = "DELETE FROM propietarios WHERE numero_documento = ?";
                try (PreparedStatement pstmtProp = dbConnection.prepareStatement(sqlString)) {
                    pstmtProp.setLong(1, idEntidad);
                    pstmtProp.executeUpdate();
                }

            } else if (nameTable.equals("pacientes")) {
                String deleteVacunas = "DELETE FROM vacunas WHERE numero_id_paciente = ?";
                try (PreparedStatement pstmtVacunas = dbConnection.prepareStatement(deleteVacunas)) {
                    pstmtVacunas.setLong(1, idEntidad);
                    pstmtVacunas.executeUpdate();
                }
                sqlString = "DELETE FROM pacientes WHERE numero_id = ?";
                try (PreparedStatement pstmt = dbConnection.prepareStatement(sqlString)) {
                    pstmt.setLong(1, idEntidad);
                    pstmt.executeUpdate();
                }

            } else {
                sqlString = "DELETE FROM " + nameTable + " WHERE numero_documento = ?";
                try (PreparedStatement pstmt = dbConnection.prepareStatement(sqlString)) {
                    pstmt.setLong(1, idEntidad);
                    pstmt.executeUpdate();
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public boolean actualizarPropietario(Propietario propietario) throws SQLException {
        String sql = "UPDATE propietarios SET nombre_completo = ?, direccion_residencia = ?, correo_electronico = ?, telefono = ? WHERE numero_documento = ?";
        PreparedStatement pstm = null;

        try {
            if (getConnection() == null || getConnection().isClosed()) {
                crearConexion(); // Asegura que haya conexión activa
            }

            pstm = getConnection().prepareStatement(sql);
            pstm.setString(1, propietario.getPnombre_completo());
            pstm.setString(2, propietario.getPdireccion_residencia());
            pstm.setString(3, propietario.getPcorreo_electronico());
            pstm.setString(4, propietario.getPtelefono());
            pstm.setLong(5, propietario.getPnumero_documento());

            int filasAfectadas = pstm.executeUpdate();
            return filasAfectadas > 0;

        } catch (SQLException e) {
            System.err.println("Error actualizando propietario: " + e.getMessage());
            return false;
        } finally {
            try {
                if (pstm != null) {
                    pstm.close();
                }
            } catch (SQLException e) {
                System.err.println("Error cerrando PreparedStatement: " + e.getMessage());
            }
        }
    }

    public boolean insertarVacuna(Vacuna vacuna) throws SQLException {
        String nameTable = "";
        try {
            nameTable = "vacunas";
            String sqlString = "INSERT INTO " + nameTable + " (numero_id_paciente, numero_serie, cantidad, dosis, tipo, marca, fecha_aplicacion) VALUES(?, ?, ?, ?, ?, ?, ?)";
            pstm = dbConnection.prepareStatement(sqlString);
            pstm.setLong(1, vacuna.getNumero_id_paciente());
            pstm.setString(2, vacuna.getNumero_serie());
            pstm.setInt(3, vacuna.getCantidad());
            pstm.setString(4, vacuna.getDosis());
            pstm.setString(5, vacuna.getTipo());
            pstm.setString(6, vacuna.getMarca());
            pstm.setDate(7, (Date) vacuna.getDate());
            pstm.executeUpdate();
        } catch (SQLException evt) {
            System.out.println("!!Operación de inserción en la tabla " + nameTable + " fallida.!!");
            System.err.println(evt);
            return false;
        } finally {
            if (pstm != null) {
                try {
                    pstm.close();
                } catch (SQLException e) {
                    System.err.println("Error al cerrar PreparedStatement: " + e);
                }
            }
        }

        return true;
    }

}
