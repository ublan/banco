package main.java.ar.edu.utn.frbb.tup.presentation.validator;

import main.java.ar.edu.utn.frbb.tup.model.Cuenta;
import main.java.ar.edu.utn.frbb.tup.model.Movimiento;
import main.java.ar.edu.utn.frbb.tup.model.TipoMoneda;
import main.java.ar.edu.utn.frbb.tup.persistence.MovimientosDao;
import main.java.ar.edu.utn.frbb.tup.presentation.modelDto.TransferenciaDto;

public class TransferenciaValidator {

    public void validarTransferencia(TransferenciaDto transferenciaDto) {
        validarExistenciaCuenta(transferenciaDto.getCuentaOrigen());
        validarExistenciaCuenta(transferenciaDto.getCuentaDestino());
        validarMonto(transferenciaDto.getMonto());
        validarCuentasDiferentes(transferenciaDto.getCuentaOrigen(), transferenciaDto.getCuentaDestino());
        validarTipoMoneda(transferenciaDto.getMoneda());
    }

    private void validarExistenciaCuenta(long cbu) throws IllegalArgumentException {
        Movimiento cuenta = MovimientosDao.cuentaPorCBU(cbu);
        if (cuenta == null) {
            throw new IllegalArgumentException("El CBU ingresado no existe.");
        }
    }

    private void validarMonto(double monto) throws IllegalArgumentException {
        if (monto <= 0) {
            throw new IllegalArgumentException("El monto debe ser mayor a cero.");
        }
    }

    private void validarTipoMoneda(String moneda) throws IllegalArgumentException {
        if (!isValidMoneda(moneda)) {
            throw new IllegalArgumentException("El tipo de moneda ingresado no es válido.");
        }
    }

    public static boolean isValidMoneda(String text) {
        for (TipoMoneda tipo : TipoMoneda.values()) {
            if (tipo.getDescripcion().equalsIgnoreCase(text)) {
                return true;
            }
        }
        return false;
    }

    private void validarCuentasDiferentes(long cuentaOrigen, long cuentaDestino) throws IllegalArgumentException {
        if (cuentaOrigen == cuentaDestino) {
            throw new IllegalArgumentException("La cuenta de origen y la cuenta de destino deben ser diferentes.");
        }
    }
}





