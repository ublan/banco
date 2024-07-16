package main.java.ar.edu.utn.frbb.tup.service.operaciones.modulos;

import main.java.ar.edu.utn.frbb.tup.service.control.MovimientoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Deposito {

    @Autowired
    private MovimientoService movimientoService;

    public void realizarDeposito(long cbu, double monto, String moneda) {
        movimientoService.realizarDeposito(cbu, monto);
    }
}

