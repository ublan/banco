package main.java.ar.edu.utn.frbb.tup.service.control;

import main.java.ar.edu.utn.frbb.tup.presentation.modelDto.MovimientosDto;
import main.java.ar.edu.utn.frbb.tup.model.Movimiento;
import main.java.ar.edu.utn.frbb.tup.persistence.MovimientosDao;
import main.java.ar.edu.utn.frbb.tup.service.operaciones.ManejoMovimientos.Deposito;
import main.java.ar.edu.utn.frbb.tup.service.operaciones.ManejoMovimientos.Retiro;
import main.java.ar.edu.utn.frbb.tup.service.operaciones.ManejoMovimientos.Transferencia;
import main.java.ar.edu.utn.frbb.tup.presentation.validator.MovimientosValidator;
import main.java.ar.edu.utn.frbb.tup.model.TipoOperacion;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MovimientoService {

    @Autowired
    private MovimientosDao movimientosDao;

    @Autowired
    private MovimientosValidator movimientosValidator;

    @Autowired
    private Deposito deposito;

    @Autowired
    private Retiro retiro;

    @Autowired
    private Transferencia transferencia;

    public void realizarDeposito(long cbu, double monto, String moneda) {
        MovimientosDto movimientosDto = new MovimientosDto();
        movimientosDto.setCBU(cbu);
        movimientosDto.setMonto(monto);
        movimientosDto.setTipoOperacion(TipoOperacion.DEPOSITO);

        movimientosValidator.validarDeposito(movimientosDto);
        deposito.realizarDeposito(cbu, monto, moneda);
    }

    public void realizarRetiro(long cbu, double monto, String moneda) {
        MovimientosDto movimientosDto = new MovimientosDto();
        movimientosDto.setCBU(cbu);
        movimientosDto.setMonto(monto);
        movimientosDto.setTipoOperacion(TipoOperacion.RETIRO);

        movimientosValidator.validarRetiro(movimientosDto);
        retiro.realizarRetiro(cbu, monto, moneda);
    }

    public void realizarTransferencia(long cuentaOrigen, long cuentaDestino, double monto, String moneda) {
        MovimientosDto movimientosDto = new MovimientosDto();
        movimientosDto.setCBU(cuentaOrigen);
        movimientosDto.setCBUDestino(cuentaDestino);
        movimientosDto.setMonto(monto);
        movimientosDto.setTipoOperacion(TipoOperacion.TRANSFERENCIA);

        movimientosValidator.validarTransferencia(movimientosDto);
        transferencia.realizarTransferencia(cuentaOrigen, cuentaDestino, monto, moneda);
    }

    public List<Movimiento> obtenerOperacionesPorCBU(long cbu) {
        return movimientosDao.obtenerOperacionesPorCBU(cbu);
    }

    public Movimiento cuentaPorCBU(long cbu) {
        return movimientosDao.cuentaPorCBU(cbu);
    }
}

