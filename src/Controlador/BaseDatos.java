/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelo.Profesional;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;



/**
 *
 * @author anfel
 */
public class BaseDatos {
    
    static Connection dbConnection;
    static Statement stSQL;
    static String nameDB;
    static String user;
    static String pwd;
    static PreparedStatement pstm;
    
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
            String sqlString = "INSERT INTO " +nameTable+ " (pnumero_documento, ptipo_documento, pnombre_completo, pdireccion_residencia, pcorreo_electronico, ptelefono) VALUES (?, ?, ?, ?, ?, ?)";
            pstm = dbConnection.prepareStatement(sqlString);
            pstm.setInt(1, profesional.getPnumero_documento());
            pstm.setString(2, String.valueOf(profesional.getPtipo_documento()));
            pstm.setString(3, profesional.getPnombre_completo());
            pstm.setString(4, profesional.getPdireccion_residencia());
            pstm.setString(5, profesional.getPcorreo_electronico());
            pstm.setString(6, profesional.getPtelefono());
            pstm.executeUpdate();
            
        } catch (SQLException evt) {
            System.out.println("!!Operación de inserción en la tabla " + nameTable + " fallida.!!");
            System.err.println(evt);
            return false;
        }
        
        return true;
    }
    
}
