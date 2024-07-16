package main.java.ar.edu.utn.frbb.tup.service.control;

import main.java.ar.edu.utn.frbb.tup.model.Movimiento;
import main.java.ar.edu.utn.frbb.tup.model.TipoOperacion;
import main.java.ar.edu.utn.frbb.tup.persistence.MovimientosDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class MovimientoService {

    @Autowired
    private MovimientosDao movimientosDao;

    public void realizarDeposito(long cbu, double monto) {
        Movimiento movimiento = new Movimiento();
        movimiento.setCBU(cbu);
        movimiento.setFechaOperacion(LocalDate.now());
        movimiento.setTipoOperacion(TipoOperacion.DEPOSITO);
        movimiento.setMonto(monto);

        movimientosDao.guardarMovimiento(movimiento);
    }

    public void realizarRetiro(long cbu, double monto) {
        Movimiento movimiento = new Movimiento();
        movimiento.setCBU(cbu);
        movimiento.setFechaOperacion(LocalDate.now());
        movimiento.setTipoOperacion(TipoOperacion.RETIRO);
        movimiento.setMonto(monto);

        movimientosDao.guardarMovimiento(movimiento);
    }

    public void realizarTransferencia(long cuentaOrigen, long cuentaDestino, double monto) {
        Movimiento movimiento = new Movimiento();
        movimiento.setCBU(cuentaOrigen);
        movimiento.setCBUDestino(cuentaDestino);
        movimiento.setFechaOperacion(LocalDate.now());
        movimiento.setTipoOperacion(TipoOperacion.TRANSFERENCIA);
        movimiento.setMonto(monto);

        movimientosDao.guardarMovimiento(movimiento);
    }

    public List<Movimiento> obtenerOperacionesPorCBU(long cbu) {
        return movimientosDao.obtenerOperacionesPorCBU(cbu);
    }

    public Movimiento cuentaPorCBU(long cbu) {
        return movimientosDao.cuentaPorCBU(cbu);
    }
}

