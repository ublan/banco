package main.java.ar.edu.utn.frbb.tup.presentation.validator;

import org.springframework.stereotype.Component;

import main.java.ar.edu.utn.frbb.tup.presentation.modelDto.TransferenciaDto;

@Component
public class TransferenciaValidator {

    public void validarTransferencia(TransferenciaDto transferenciaDto) {
        if (transferenciaDto.getCuentaOrigen() == transferenciaDto.getCuentaDestino()) {
            throw new IllegalArgumentException("El CBU destino no puede ser el mismo que el CBU origen");
        }

        if (transferenciaDto.getCuentaDestino() == 0) {
            throw new IllegalArgumentException("El CBU destino no puede ser nulo");
        }

        if (transferenciaDto.getMonto() <= 0) {
            throw new IllegalArgumentException("El monto no puede ser negativo");
        }

        if (transferenciaDto.getMoneda() == null) {
            throw new IllegalArgumentException("La moneda no puede ser nula");
        }

        if (!transferenciaDto.getMoneda().equals("USD") && !transferenciaDto.getMoneda().equals("ARS")) {
            throw new IllegalArgumentException("La moneda no es válida");
        }
    }
}





