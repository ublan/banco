package main.java.ar.edu.utn.frbb.tup.service.operaciones.modulos;

import main.java.ar.edu.utn.frbb.tup.model.TipoOperacion;
import main.java.ar.edu.utn.frbb.tup.model.Movimiento;
import main.java.ar.edu.utn.frbb.tup.service.control.MovimientoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VerSaldo {

    @Autowired
    private MovimientoService movimientoService;

    public double obtenerSaldoPorCBU(long cbu) {
        List<Movimiento> movimientos = movimientoService.obtenerOperacionesPorCBU(cbu);
        double saldo = 0.0;

        for (Movimiento movimiento : movimientos) {
            if (movimiento.getCBU() == cbu) {
                switch (movimiento.getTipoOperacion()) {
                    case DEPOSITO:
                        saldo += movimiento.getMonto();
                        break;
                    case RETIRO:
                        saldo -= movimiento.getMonto();
                        break;
                    case TRANSFERENCIA:
                        saldo -= movimiento.getMonto();
                        break;
                    default:
                        break;
                }
            } else if (movimiento.getCBUDestino() == cbu && movimiento.getTipoOperacion() == TipoOperacion.TRANSFERENCIA) {
                saldo += movimiento.getMonto();
            }
        }

        return saldo;
    }
}
