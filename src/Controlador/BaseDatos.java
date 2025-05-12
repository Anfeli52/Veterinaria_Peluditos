/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelo.Profesional;
import java.awt.HeadlessException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.JOptionPane;



/**
 *
 * @author anfel
 */
public class BaseDatos {
    
    private static Connection dbConnection;
    private static Statement stSQL;
    private static String nameDB;
    private static String user;
    private static String pwd;
    private static PreparedStatement pstm;
    private static ResultSet rs;
    private static Date date;
    
    public BaseDatos(){
        
    }
    
    public boolean crearConexion(){
        nameDB = "veterinaria_corp";
        user = "root";
        pwd = "root";
        
        
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String sourceURL = "jdbc:mysql://localhost:3307/" + nameDB; //El significado del :3307 es que yo no tengo la conexión de la base de datos que trabajos en el Puerto 3306, sino que en el 3307
            
            dbConnection = DriverManager.getConnection(sourceURL, user, pwd);
            System.out.println("!! Conexion con la base de datos " + nameDB + " establecida exitosamente !!");
        } catch (ClassNotFoundException | SQLException evt) {
            System.err.println(evt);
            System.out.println("!! Conexión con la base de datos " + nameDB + " fallida !!");
            return false;
        }
        return true;
    }
    
    public boolean insertarProfesional(Profesional profesional) throws SQLException{
        
        
        String nameTable = "";
        try {
            nameTable = "profesionales";
            String sqlString = "INSERT INTO " +nameTable+ " (numero_documento, tipo_documento, nombre_completo, direccion_residencia, correo_electronico, telefono, fecha_inicio_cuidado, contrasena) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            pstm = dbConnection.prepareStatement(sqlString);
            pstm.setInt(1, profesional.getPnumero_documento());
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
