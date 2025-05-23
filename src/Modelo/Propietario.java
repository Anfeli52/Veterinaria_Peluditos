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
public class Propietario {
    
    private long pnumero_documento;
    private String ptipo_documento;
    private String pnombre_completo;
    private String pdireccion_residencia;
    private String pcorreo_electronico;
    private String ptelefono;
    private Date pdate;
    
    public Propietario(){
        
    }
    
    public Propietario(long unumero_documento, String unombre_completo, String udireccion_residencia, String ucorreo_electronico, String utelefono){
        this.pnumero_documento = unumero_documento;
        this.pnombre_completo = unombre_completo;
        this.pdireccion_residencia = udireccion_residencia;
        this.pcorreo_electronico = ucorreo_electronico;
        this.ptelefono = utelefono;
    }
    
    public Propietario(long pnumero_documento, String ptipo_documento, String pnombre_completo, String pdireccion_residencia, String pcorreo_electronico, String ptelefono, Date pdate) {
        this.pnumero_documento = pnumero_documento;
        this.ptipo_documento = ptipo_documento;
        this.pnombre_completo = pnombre_completo;
        this.pdireccion_residencia = pdireccion_residencia;
        this.pcorreo_electronico = pcorreo_electronico;
        this.ptelefono = ptelefono;
        this.pdate = pdate;
    }

    public long getPnumero_documento() {
        return pnumero_documento;
    }

    public String getPtipo_documento() {
        return ptipo_documento;
    }

    public void setPtipo_documento(String ptipo_documento) {
        this.ptipo_documento = ptipo_documento;
    }

    public String getPnombre_completo() {
        return pnombre_completo;
    }

    public void setPnombre_completo(String pnombre_completo) {
        this.pnombre_completo = pnombre_completo;
    }

    public String getPdireccion_residencia() {
        return pdireccion_residencia;
    }

    public void setPdireccion_residencia(String pdireccion_residencia) {
        this.pdireccion_residencia = pdireccion_residencia;
    }

    public String getPcorreo_electronico() {
        return pcorreo_electronico;
    }

    public void setPcorreo_electronico(String pcorreo_electronico) {
        this.pcorreo_electronico = pcorreo_electronico;
    }

    public String getPtelefono() {
        return ptelefono;
    }

    public void setPtelefono(String ptelefono) {
        this.ptelefono = ptelefono;
    }

    public Date getPdate() {
        return pdate;
    }    
    
}
