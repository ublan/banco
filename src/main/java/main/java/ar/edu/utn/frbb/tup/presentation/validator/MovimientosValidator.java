package main.java.ar.edu.utn.frbb.tup.presentation.validator;
import java.util.Objects;

import main.java.ar.edu.utn.frbb.tup.model.TipoOperacion;
import main.java.ar.edu.utn.frbb.tup.presentation.modelDto.MovimientosDto;

public class MovimientosValidator {
    public void validar(MovimientosDto movimientosDto) {

        if (Objects.isNull(movimientosDto.getCBU())) {
            throw new IllegalArgumentException("El CBU no puede ser nulo");
        }

        if (Objects.isNull(movimientosDto.getCBUDestino())) {
            throw new IllegalArgumentException("El CBU destino no puede ser nulo");
        }

        if (movimientosDto.getCBU() == movimientosDto.getCBUDestino()) {
            throw new IllegalArgumentException("El CBU destino no puede ser el mismo que el CBU origen");
        }

        if (movimientosDto.getMonto() <= 0) {
            throw new IllegalArgumentException("El monto no puede ser negativo");
        }

        if (movimientosDto.getTipoOperacion() == null) {
            throw new IllegalArgumentException("El tipo de operacion no puede ser nulo");
        }

        if (!movimientosDto.getTipoOperacion().equals(TipoOperacion.DEPOSITO) && !movimientosDto.getTipoOperacion().equals(TipoOperacion.RETIRO)) {
            throw new IllegalArgumentException("El tipo de operacion no puede ser nulo");
        }

    }
}
