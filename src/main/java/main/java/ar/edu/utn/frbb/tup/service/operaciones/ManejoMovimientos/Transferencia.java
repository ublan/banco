package main.java.ar.edu.utn.frbb.tup.service.operaciones.ManejoMovimientos;

import main.java.ar.edu.utn.frbb.tup.service.control.MovimientoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Transferencia {

    @Autowired
    private MovimientoService movimientoService;

    public void realizarTransferencia(long cuentaOrigen, long cuentaDestino, double monto, String moneda) {
        movimientoService.realizarTransferencia(cuentaOrigen, cuentaDestino, monto);
    }
}

