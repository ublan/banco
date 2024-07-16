package main.java.ar.edu.utn.frbb.tup.service.operaciones.ManejoMovimientos;

import main.java.ar.edu.utn.frbb.tup.model.Movimiento;
import main.java.ar.edu.utn.frbb.tup.service.control.MovimientoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VerOperaciones {

    @Autowired
    private MovimientoService movimientoService;

    public List<Movimiento> obtenerOperacionesPorCBU(long cbu) {
        return movimientoService.obtenerOperacionesPorCBU(cbu);
    }
}
