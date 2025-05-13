/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelo.Profesional;
import Modelo.Propietario;
import Vista.MenuProfesional;
import java.awt.HeadlessException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import java.sql.Statement;
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
    
    public BaseDatos(){
        
    }
    
    public boolean crearConexion(){
        nameDB = "veterinaria_corp";
        user = "root";
        pwd = "root";
        port = 3307;
        
        
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String sourceURL = "jdbc:mysql://localhost:"+port+"/" + nameDB; //El significado del :3307 es que yo no tengo la conexión de la base de datos que trabajamos en el Puerto 3306, sino que en el 3307
            
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
    
    public void mostrarProfesionales(JTable tablaProfesionales){
        String nameTable = "";
        
        try {
            nameTable = "profesionales";
            String sqlString = "SELECT numero_documento, nombre_completo, correo_electronico, telefono, fecha_inicio_cuidado FROM " + nameTable;
            Statement stm;
            
            //stm = dbConnection.createStatement();
            
            DefaultTableModel tableModel = new DefaultTableModel();
            tableModel.addColumn("Número de Documento");
            tableModel.addColumn("Nombre Completo");
            tableModel.addColumn("Correo Electrónico");
            tableModel.addColumn("Teléfono");
            tableModel.addColumn("Fecha de Inicio");
            
            String[] datos = new String[5];
            try {
                stm = dbConnection.createStatement();
                rs = stm.executeQuery(sqlString);
                while(rs.next()){
                    datos[0] = rs.getString(1);
                    datos[1] = rs.getString(2);
                    datos[2] = rs.getString(3);
                    datos[3] = rs.getString(4);
                    datos[4] = rs.getString(5);
                    tableModel.addRow(datos);
                }
            } catch (SQLException evt) {
                System.out.println("Error al encontrar los datos de la tabla "+nameTable);
                System.err.println(evt);
            }
            
            tablaProfesionales.setModel(tableModel);
            
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
    
    public void mostrarPropietarios(JTable tablaPropietarios){
        String nameTable = "";
        
        try {
            nameTable = "propietarios";
            //Agregar mediante una SUBCONSULTA, el número de mascotas que tiene cada propietario registrado, ya que si es propietario, quiere decir que tiene al menos una mascota.
            String sqlString = "SELECT numero_documento, nombre_completo, direccion_residencia, correo_electronico, telefono FROM propietarios";
            Statement stm;
            
            DefaultTableModel tableModel = new DefaultTableModel();
            tableModel.addColumn("Número de Documento");
            tableModel.addColumn("Nombre Completo");
            tableModel.addColumn("Dirección de Residencia");
            tableModel.addColumn("Correo Electrónico");
            tableModel.addColumn("Teléfono");
            
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
                System.out.println("Error al encontrar los datos de la tabla "+nameTable);
                System.err.println(evt);
            }
            
            tablaPropietarios.setModel(tableModel);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
    
    
    public boolean insertarProfesional(Profesional profesional) throws SQLException{
        
        String nameTable = "";
        try {
            nameTable = "profesionales";
            String sqlString = "INSERT INTO " +nameTable+ " (numero_documento, tipo_documento, nombre_completo, direccion_residencia, correo_electronico, telefono, fecha_inicio_cuidado, contrasena) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
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
    
    public boolean insertarPropietario(Propietario propietario) throws SQLException{
        
        String nameTable = "";
        try {
            nameTable = "propietarios";
            String sqlString = "INSERT INTO "+nameTable+" (numero_documento, tipo_documento, nombre_completo, direccion_residencia, correo_electronico, telefono, fecha_inicio_cuidado) VALUES(?, ?, ?, ?, ?, ?, ?)";
            pstm = dbConnection.prepareCall(sqlString);
            pstm.setInt(1, propietario.getPnumero_documento());
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
    
    public boolean buscarProfesional(String correo, String password) throws SQLException{
        
        String nameTable = "";
        try {
            nameTable = "profesionales";
            String sqlString = "SELECT * FROM "+nameTable+" WHERE correo_electronico = ? AND contrasena = ?";
            pstm = dbConnection.prepareStatement(sqlString);
            pstm.setString(1, correo);
            pstm.setString(2, password);
            rs = pstm.executeQuery();
            
            if(rs.next()){
                JOptionPane.showMessageDialog(null, "Inicio de Sesión Éxitoso");
            }else{
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
    
}
