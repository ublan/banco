package main.java.ar.edu.utn.frbb.tup.model;

public enum TipoMoneda {
    PESOS("ARS"),
    DOLARES("USD");

    private final String descripcion;

    TipoMoneda(String descripcion){
        this.descripcion = descripcion;
    }

    public static TipoMoneda fromString(String text) throws IllegalArgumentException {
        for (TipoMoneda tipo : TipoMoneda.values()) {
            if (tipo.descripcion.equalsIgnoreCase(text)) {
                return tipo;
            }
        }
        throw new IllegalArgumentException("No se pudo encontrar un TipoMoneda con la descripción: " + text);
    }
}
