package main.java.ar.edu.utn.frbb.tup.model;

import main.java.ar.edu.utn.frbb.tup.presentation.modelDto.TransferenciaDto;

public class Transferencia {

    private long cuentaOrigen;
    private long cuentaDestino;
    private double monto;
    private TipoMoneda moneda;
    
    public Transferencia(TransferenciaDto transferenciaDto) {
        this.cuentaOrigen = transferenciaDto.getCuentaOrigen();
        this.cuentaDestino = transferenciaDto.getCuentaDestino();
        this.monto = transferenciaDto.getMonto();
        this.moneda = TipoMoneda.fromString(transferenciaDto.getMoneda());
    }

    // getters y setters

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

    public TipoMoneda getMoneda() {
        return moneda;
    }

    public void setMoneda(TipoMoneda moneda) {
        this.moneda = moneda;
    }
}

