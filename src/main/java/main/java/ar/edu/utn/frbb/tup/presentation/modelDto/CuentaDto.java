package main.java.ar.edu.utn.frbb.tup.presentation.modelDto;

import main.java.ar.edu.utn.frbb.tup.model.TipoCuenta;


public class CuentaDto {
    private String nombre;
    private long dniTitular;
    TipoCuenta tipoCuenta;
    private int balance;
    private String tipoMoneda;


    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public long getDniTitular() {
        return dniTitular;
    }

    public void setDniTitular(long dniTitular) {
        this.dniTitular = dniTitular;
    }

    public TipoCuenta getTipoCuenta() {
        return tipoCuenta;
    }

    public CuentaDto setTipoCuenta(TipoCuenta tipoCuenta) {
        this.tipoCuenta = tipoCuenta;
        return this;
    }

    public int getBalance() {
        return balance;
    }

    public CuentaDto setBalance(int balance) {
        this.balance = balance;
        return this;
    }
    public String getMoneda() {
        return tipoMoneda;
    }

    public CuentaDto setMoneda(String moneda) {
        this.tipoMoneda = moneda;
        return this;
    }

    public void setTipoMoneda(String tipoMoneda) {
        this.tipoMoneda = tipoMoneda;
    }
}
