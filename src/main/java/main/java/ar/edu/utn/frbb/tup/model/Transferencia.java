package main.java.ar.edu.utn.frbb.tup.model;

import java.time.LocalDate;

import main.java.ar.edu.utn.frbb.tup.presentation.modelDto.TransferenciaDto;

public class Transferencia {

    private LocalDate fecha;
    private long cuentaOrigen;
    private long cuentaDestino;
    private double monto;
    private String descripcionBreve;
    private TipoMoneda moneda;
    
    public Transferencia(TransferenciaDto transferenciaDto) {
        this.fecha = transferenciaDto.getFecha();
        this.cuentaOrigen = transferenciaDto.getCuentaOrigen();
        this.cuentaDestino = transferenciaDto.getCuentaDestino();
        this.monto = transferenciaDto.getMonto();
        this.descripcionBreve = transferenciaDto.getDescripcionBreve();
        this.moneda = TipoMoneda.fromString(transferenciaDto.getMoneda().toString());
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public long getCuentaOrigen() {
        return cuentaOrigen;
    }

    public void setCuentaOrigen(long cuentaOrigen) {
        this.cuentaOrigen = cuentaOrigen;
    }

    public long getCuentaDestino() {
        return cuentaDestino;
    }

    public void setCuentaDestino(long cuentaDestino) {
        this.cuentaDestino = cuentaDestino;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public String getDescripcionBreve() {
        return descripcionBreve;
    }

    public void setDescripcionBreve(String descripcionBreve) {

        this.descripcionBreve = descripcionBreve;
    }

    public TipoMoneda getMoneda() {
        return moneda;
    }

    public void setMoneda(TipoMoneda moneda) {
        this.moneda = moneda;
    }
}

