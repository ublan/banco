package main.java.ar.edu.utn.frbb.tup.service.operaciones.ManejoMovimientos;

import main.java.ar.edu.utn.frbb.tup.model.Movimiento;
import main.java.ar.edu.utn.frbb.tup.model.TipoOperacion;
import main.java.ar.edu.utn.frbb.tup.persistence.MovimientosDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class Transferencia {

    @Autowired
    private MovimientosDao movimientosDao;

    public void realizarTransferencia(long cuentaOrigen, long cuentaDestino, double monto, String moneda) {
        Movimiento movimiento = new Movimiento();
        movimiento.setCBU(cuentaOrigen);
        movimiento.setCBUDestino(cuentaDestino);
        movimiento.setFechaOperacion(LocalDate.now());
        movimiento.setTipoOperacion(TipoOperacion.TRANSFERENCIA);
        movimiento.setMonto(monto);

        movimientosDao.guardarMovimiento(movimiento);
    }
}


