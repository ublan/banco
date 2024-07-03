package main.java.ar.edu.utn.frbb.tup.model;
public enum TipoCuenta {

    CORRIENTE("CORRIENTE"),
    AHORRO("AHORRO");

    private final String descripcion;

    TipoCuenta(String descripcion) {
        this.descripcion = descripcion;
    }
}
