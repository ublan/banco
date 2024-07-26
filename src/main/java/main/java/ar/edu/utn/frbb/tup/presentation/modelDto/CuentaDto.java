package main.java.ar.edu.utn.frbb.tup.presentation.modelDto;




public class CuentaDto {
    private String nombre;
    private long dniTitular;
    private String tipoCuenta;
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

    public String getTipoCuenta() {
        return tipoCuenta;
    }

    public CuentaDto setTipoCuenta(String tipoCuenta) {
        this.tipoCuenta = tipoCuenta;
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
