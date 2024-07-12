package main.java.ar.edu.utn.frbb.tup.presentation.validator;

import main.java.ar.edu.utn.frbb.tup.model.TipoCuenta;
import main.java.ar.edu.utn.frbb.tup.presentation.modelDto.CuentaDto;

public class CuentaValidator {

    public void validarCuenta(CuentaDto cuentadto) {

    if (cuentadto.getNombre() == null || cuentadto.getNombre().isEmpty()) {
        throw new IllegalArgumentException("El nombre de la cuenta es obligatorio");
    }

    if (cuentadto.getTipoCuenta() == null) {
        throw new IllegalArgumentException("El tipo de cuenta es obligatorio");
    }

    if (!cuentadto.getTipoCuenta().equals(TipoCuenta.CORRIENTE) && !cuentadto.getTipoCuenta().equals(TipoCuenta.AHORRO)) {
        throw new IllegalArgumentException("El tipo de cuenta debe ser CORRIENTE o AHORRO");
    }


    if (cuentadto.getMoneda() == null || cuentadto.getMoneda().isEmpty()) {
        throw new IllegalArgumentException("La moneda de la cuenta es obligatoria");
    }

    if (!cuentadto.getMoneda().equals("ARS") && !cuentadto.getMoneda().equals("USD")) {
        throw new IllegalArgumentException("La moneda " + cuentadto.getMoneda() + " no es aceptada. Solo se aceptan ARS y USD.");
    }
    }
    
}
