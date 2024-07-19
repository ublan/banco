package main.java.ar.edu.utn.frbb.tup.presentation.controllers;

public class MovimientoRequest {
    private long cbu;
    private double monto;
    private String moneda;

    // Getters y Setters
    public long getCbu() {
        return cbu;
    }

    public void setCbu(long cbu) {
        this.cbu = cbu;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public String getMoneda() {
        return moneda;
    }

    public void setMoneda(String moneda) {
        this.moneda = moneda;
    }
}
