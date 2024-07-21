package main.java.ar.edu.utn.frbb.tup.service.operaciones.ManejoMovimientos;

import main.java.ar.edu.utn.frbb.tup.model.Cuenta;
import main.java.ar.edu.utn.frbb.tup.model.Movimiento;
import main.java.ar.edu.utn.frbb.tup.model.TipoOperacion;
import main.java.ar.edu.utn.frbb.tup.persistence.CuentaDao;
import main.java.ar.edu.utn.frbb.tup.persistence.MovimientosDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class Deposito {

    @Autowired
    private CuentaDao cuentaDao;

    @Autowired
    private MovimientosDao movimientosDao;

    public void realizarDeposito(long cbu, double monto, String moneda) {
        if (!cuentaDao.verificarExistenciaCuenta(cbu)) {
            throw new IllegalArgumentException("El CBU no existe");
        }

        Movimiento movimiento = new Movimiento();
        movimiento.setCBU(cbu);
        movimiento.setFechaOperacion(LocalDate.now());
        movimiento.setTipoOperacion(TipoOperacion.DEPOSITO);
        movimiento.setMonto(monto);

        movimientosDao.guardarMovimiento(movimiento);

        Cuenta cuenta = cuentaDao.obtenerCuentaPorCBU(cbu);
        if (cuenta != null) {
            double nuevoBalance = cuenta.getBalance() + monto;
            cuentaDao.actualizarBalanceCuenta(cbu, nuevoBalance);
        }
    }
}

