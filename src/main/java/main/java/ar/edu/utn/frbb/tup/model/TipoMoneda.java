package main.java.ar.edu.utn.frbb.tup.model;

public enum TipoMoneda {
    PESOS("ARS"),
    DOLARES("USD");

    private final String descripcion;

    TipoMoneda(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public static TipoMoneda fromString(String text) throws IllegalArgumentException {
        // Convertir el texto a minúsculas para evitar problemas de mayúsculas/minúsculas
        String lowerCaseText = text.toLowerCase();
        
        for (TipoMoneda tipo : TipoMoneda.values()) {
            if (tipo.descripcion.equalsIgnoreCase(lowerCaseText)) {
                return tipo;
            }
        }
        
        throw new IllegalArgumentException("No se pudo encontrar un TipoMoneda con la descripción: " + text);
    }
}
