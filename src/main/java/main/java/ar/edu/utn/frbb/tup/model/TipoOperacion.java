package main.java.ar.edu.utn.frbb.tup.model;

public enum TipoOperacion {
    TRANSFERENCIA("Transferencia"),
    DEPOSITO("Depósito"),
    RETIRO("Retiro");

    private final String descripcion;

    TipoOperacion(String descripcion){
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public static TipoOperacion fromString(String text) throws IllegalArgumentException {
        for (TipoOperacion tipo : TipoOperacion.values()) {
            if (tipo.descripcion.equalsIgnoreCase(text)) {
                return tipo;
            }
        }
        throw new IllegalArgumentException("No se pudo encontrar un TipoOperacion con la descripción: " + text);
    }
}

