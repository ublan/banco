package main.java.ar.edu.utn.frbb.tup.model;

import main.java.ar.edu.utn.frbb.tup.exception.CantidadNegativaException;
import main.java.ar.edu.utn.frbb.tup.exception.NoAlcanzaException;

import java.time.LocalDateTime;
import java.util.Random;

public class Cuenta {
    private long numeroCuenta;
    private String nombre;
    private LocalDateTime fechaCreacion;
    private int balance;
    private TipoCuenta tipoCuenta;
    private Cliente titular;
    private long CBU; 
    private String moneda;

    // Getters y setters

    public Cliente getTitular() {
        return titular;
    }

    public void setTitular(Cliente titular) {
        this.titular = titular;
    }

    public TipoCuenta getTipoCuenta() {
        return tipoCuenta;
    }

    public Cuenta setTipoCuenta(TipoCuenta tipoCuenta) {
        this.tipoCuenta = tipoCuenta;
        return this;
    }

    public String getNombre() {
        return nombre;
    }

    public Cuenta setNombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public Cuenta setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
        return this;
    }

    public int getBalance() {
        return balance;
    }

    public Cuenta setBalance(int balance) {
        this.balance = balance;
        return this;
    }

    public long getCBU() {
        return CBU;
    }

    public void setCBU() {
        this.CBU = generarCBU();
    }

    private long generarCBU() {
        Random random = new Random();
        // Generar un número aleatorio de 9 dígitos como string
        StringBuilder cbuBuilder = new StringBuilder();
        for (int i = 0; i < 9; i++) {
            cbuBuilder.append(random.nextInt(10)); // Agregar un dígito aleatorio (0-9)
        }
        return Long.parseLong(cbuBuilder.toString()); // Convertir el string a long
    }

    public void debitarDeCuenta(int cantidadADebitar) throws NoAlcanzaException, CantidadNegativaException {
        if (cantidadADebitar < 0) {
            throw new CantidadNegativaException();
        }

        if (balance < cantidadADebitar) {
            throw new NoAlcanzaException();
        }
        this.balance = this.balance - cantidadADebitar;
    }

    public void forzaDebitoDeCuenta(int i) {
        this.balance = this.balance - i;
    }

    public long getNumeroCuenta() {
        return numeroCuenta;
    }

    public void setNumeroCuenta(long numeroCuenta) {
        this.numeroCuenta = numeroCuenta;
    }

    public String getMoneda() {
        return moneda;
    }

    public Cuenta setMoneda(String moneda) {
        this.moneda = moneda;
        return this;
    }
}

