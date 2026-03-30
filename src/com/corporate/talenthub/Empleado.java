package com.corporate.talenthub;

import java.math.BigDecimal;

public class Empleado {

    // Encapsulamiento: atributos privados
    private byte nivel;
    private short antiguedad;
    private int idEmpleado;
    private long salarioBase;
    private BigDecimal bonoMensual;
    private BigDecimal comision;
    private char genero;
    private boolean esActivo;
    private String nombre;

    // Constructor vacío
    public Empleado() {}

    // Getters y Setters

    public byte getNivel() {
        return nivel;
    }

    public void setNivel(byte nivel) {
        this.nivel = nivel;
    }

    public short getAntiguedad() {
        return antiguedad;
    }

    public void setAntiguedad(short antiguedad) {
        this.antiguedad = antiguedad;
    }

    public int getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(int idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public long getSalarioBase() {
        return salarioBase;
    }

    public void setSalarioBase(long salarioBase) {
        this.salarioBase = salarioBase;
    }

    public BigDecimal getBonoMensual() {
        return bonoMensual;
    }

    public void setBonoMensual(BigDecimal bonoMensual) {
        this.bonoMensual = bonoMensual;
    }

    public BigDecimal getComision() {
        return comision;
    }

    public void setComision(BigDecimal comision) {
        this.comision = comision;
    }

    public char getGenero() {
        return genero;
    }

    public void setGenero(char genero) {
        this.genero = genero;
    }

    public boolean isActivo() {
        return esActivo;
    }

    public void setActivo(boolean activo) {
        boolean isActivo = activo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}