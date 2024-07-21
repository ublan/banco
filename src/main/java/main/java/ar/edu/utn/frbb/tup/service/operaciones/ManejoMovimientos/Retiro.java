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
public class Retiro {

    @Autowired
    private CuentaDao cuentaDao;

    @Autowired
    private MovimientosDao movimientosDao;

    public void realizarRetiro(long cbu, double monto, String moneda) {
        if (!cuentaDao.verificarExistenciaCuenta(cbu)) {
            throw new IllegalArgumentException("El CBU no existe");
        }

        Cuenta cuenta = cuentaDao.obtenerCuentaPorCBU(cbu);
        if (cuenta == null) {
            throw new IllegalArgumentException("Cuenta no encontrada");
        }

        double balanceActual = cuenta.getBalance();
        if (balanceActual < monto) {
            throw new IllegalArgumentException("Saldo insuficiente");
        }

        Movimiento movimiento = new Movimiento();
        movimiento.setCBU(cbu);
        movimiento.setFechaOperacion(LocalDate.now());
        movimiento.setTipoOperacion(TipoOperacion.RETIRO);
        movimiento.setMonto(monto);

        movimientosDao.guardarMovimiento(movimiento);

        double nuevoBalance = balanceActual - monto;
        cuentaDao.actualizarBalanceCuenta(cbu, nuevoBalance);
    }
}
