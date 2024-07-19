package main.java.ar.edu.utn.frbb.tup.exception;

public class ModificarClienteException extends RuntimeException {
    public ModificarClienteException(String message) {
        super(message);
    }

    public ModificarClienteException(String message, Throwable cause) {
        super(message, cause);
    }
}
