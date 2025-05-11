/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

/**
 *
 * @author anfel
 */
public class Profesional {
    /*
    
        pnumero_documento int primary key not null,
	ptipo_documento char(2) not null,  -- CC:Cedula Ciudadanía, CE: Cedula de Extranjería, TI:Tarjeta de identidad, RC: Registro civil
	pnombre_completo varchar(30) not null,
	pdireccion_residencia text not null, 
	pcorreo_electronico varchar(60) not null,
	ptelefono varchar(15) not null
    
    */
    
    private int pnumero_documento;
    private char ptipo_documento;
    private String pnombre_completo;
    private String pdireccion_residencia;
    private String pcorreo_electronico;
    private String ptelefono;

    public Profesional(int pnumero_documento, char ptipo_documento, String pnombre_completo, String pdireccion_residencia, String pcorreo_electronico, String ptelefono) {
        this.pnumero_documento = pnumero_documento;
        this.ptipo_documento = ptipo_documento;
        this.pnombre_completo = pnombre_completo;
        this.pdireccion_residencia = pdireccion_residencia;
        this.pcorreo_electronico = pcorreo_electronico;
        this.ptelefono = ptelefono;
    }

    public int getPnumero_documento() {
        return pnumero_documento;
    }

    public char getPtipo_documento() {
        return ptipo_documento;
    }

    public void setPtipo_documento(char ptipo_documento) {
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
    
    
    
    
}
