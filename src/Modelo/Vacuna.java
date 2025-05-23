/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import java.sql.Date;

/**
 *
 * @author anfel
 */
public class Vacuna {
    
    private final long  numero_id_paciente;
    private final String numero_serie;
    private final Date date;
    private int cantidad;
    private String dosis;
    private String tipo;
    private String marca;
    
    public Vacuna(long numero_id_paciente, String numero_serie, int cantidad, String dosis, String tipo, String marca, Date date){
        this.numero_id_paciente = numero_id_paciente;
        this.numero_serie = numero_serie;
        this.cantidad = cantidad;
        this.dosis = dosis;
        this.tipo = tipo;
        this.marca = marca;
        this.date = date;
    }

    public long getNumero_id_paciente() {
        return numero_id_paciente;
    }

    public String getDosis() {
        return dosis;
    }

    public void setDosis(String dosis) {
        this.dosis = dosis;
    }

    public String getNumero_serie() {
        return numero_serie;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public Date getDate() {
        return date;
    }    

}
