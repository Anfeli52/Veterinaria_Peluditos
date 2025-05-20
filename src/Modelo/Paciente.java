/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

/**
 *
 * @author anfel
 */
public class Paciente {
    
    private int numero_id; //PK
    private String nombre;
    private String sexo;
    private String especie;
    private float peso;
    private String color;
    private String caracteristicas_particulares;
    private String procedencia;
    private String fin_zootecnico;
    private boolean esterilizado;
    private String enfermedades_base;
    private final long numero_documento_propietario; //FK

    public Paciente(String nombre, String sexo, String especie, float peso, String color, String caracteristicas_particulares, String procedencia, String fin_zootecnico, boolean esterilizado, String enfermedades_base, long numero_documento_propietario) {
        this.nombre = nombre;
        this.sexo = sexo;
        this.especie = especie;
        this.peso = peso;
        this.color = color;
        this.caracteristicas_particulares = caracteristicas_particulares;
        this.procedencia = procedencia;
        this.fin_zootecnico = fin_zootecnico;
        this.esterilizado = esterilizado;
        this.enfermedades_base = enfermedades_base;
        this.numero_documento_propietario = numero_documento_propietario;
    }

    public int getNumero_id() { //PK DEL PACIENTE/MASCOTA
        return numero_id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getEspecie() {
        return especie;
    }

    public void setEspecie(String especie) {
        this.especie = especie;
    }

    public float getPeso() {
        return peso;
    }

    public void setPeso(float peso) {
        this.peso = peso;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getCaracteristicas_particulares() {
        return caracteristicas_particulares;
    }

    public void setCaracteristicas_particulares(String caracteristicas_particulares) {
        this.caracteristicas_particulares = caracteristicas_particulares;
    }

    public String getProcedencia() {
        return procedencia;
    }

    public void setProcedencia(String procedencia) {
        this.procedencia = procedencia;
    }

    public String getFin_zootecnico() {
        return fin_zootecnico;
    }

    public void setFin_zootecnico(String fin_zootecnico) {
        this.fin_zootecnico = fin_zootecnico;
    }

    public boolean isEsterilizado() {
        return esterilizado;
    }

    public void setEsterilizado(boolean esterilizado) {
        this.esterilizado = esterilizado;
    }

    public String getEnfermedades_base() {
        return enfermedades_base;
    }

    public void setEnfermedades_base(String enfermedades_base) {
        this.enfermedades_base = enfermedades_base;
    }

    public long getNumero_documento_propietario() { //FK DEL PACIENTE/MASCOTA
        return numero_documento_propietario;
    }

    
}
