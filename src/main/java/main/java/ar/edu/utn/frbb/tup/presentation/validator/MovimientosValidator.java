package main.java.ar.edu.utn.frbb.tup.presentation.validator;

import java.util.Objects;

import org.springframework.stereotype.Component;
import main.java.ar.edu.utn.frbb.tup.model.TipoOperacion;
import main.java.ar.edu.utn.frbb.tup.presentation.modelDto.MovimientosDto;

@Component
public class MovimientosValidator {

    public void validarDeposito(MovimientosDto movimientosDto) {
        if (Objects.isNull(movimientosDto.getCBU())) {
            throw new IllegalArgumentException("El CBU no puede ser nulo");
        }

        if (movimientosDto.getMonto() <= 0) {
            throw new IllegalArgumentException("El monto no puede ser negativo");
        }

        if (movimientosDto.getTipoOperacion() == null || !movimientosDto.getTipoOperacion().equals(TipoOperacion.DEPOSITO)) {
            throw new IllegalArgumentException("El tipo de operación debe ser DEPÓSITO");
        }
    }

    public void validarRetiro(MovimientosDto movimientosDto) {
        if (Objects.isNull(movimientosDto.getCBU())) {
            throw new IllegalArgumentException("El CBU no puede ser nulo");
        }

        if (movimientosDto.getMonto() <= 0) {
            throw new IllegalArgumentException("El monto no puede ser negativo");
        }

        if (movimientosDto.getTipoOperacion() == null || !movimientosDto.getTipoOperacion().equals(TipoOperacion.RETIRO)) {
            throw new IllegalArgumentException("El tipo de operación debe ser RETIRO");
        }
    }

}

