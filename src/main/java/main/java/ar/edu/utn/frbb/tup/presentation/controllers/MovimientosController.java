package main.java.ar.edu.utn.frbb.tup.presentation.controllers;

import main.java.ar.edu.utn.frbb.tup.service.control.MovimientoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import main.java.ar.edu.utn.frbb.tup.model.Movimiento;
import java.util.List;

@RestController
@RequestMapping("/movimientos")
public class MovimientosController {

    @Autowired
    private MovimientoService movimientoService;

    @PostMapping("/deposito")
    public ResponseEntity<String> realizarDeposito(@RequestBody MovimientoRequest request) {
        try {
            movimientoService.realizarDeposito(request.getCbu(), request.getMonto(), request.getMoneda());
            return new ResponseEntity<>("Depósito realizado exitosamente", HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al realizar el depósito", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/retiro")
    public ResponseEntity<String> realizarRetiro(@RequestBody MovimientoRequest request) {
        try {
            movimientoService.realizarRetiro(request.getCbu(), request.getMonto(), request.getMoneda());
            return new ResponseEntity<>("Retiro realizado exitosamente", HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al realizar el retiro", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/operaciones/{cbu}")
    public ResponseEntity<List<Movimiento>> obtenerOperacionesPorCBU(@PathVariable long cbu) {
        try {
            List<Movimiento> movimientos = movimientoService.obtenerOperacionesPorCBU(cbu);
            return new ResponseEntity<>(movimientos, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
