package main.java.ar.edu.utn.frbb.tup.presentation.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import main.java.ar.edu.utn.frbb.tup.exception.CuentaNoEncontradaException;
import main.java.ar.edu.utn.frbb.tup.exception.CuentaSinSaldoException;
import main.java.ar.edu.utn.frbb.tup.exception.MomivientosVaciosException;
import main.java.ar.edu.utn.frbb.tup.model.Movimiento;
import main.java.ar.edu.utn.frbb.tup.service.MovimientoService;

import java.util.List;

@RestController
@RequestMapping("/movimientos")
public class MovimientosController {

    @Autowired
    private MovimientoService movimientoService;

    @PostMapping("/deposito/{cbu}")
    public ResponseEntity<String> realizarDeposito(@PathVariable long cbu, @RequestParam double monto) throws CuentaNoEncontradaException {
        movimientoService.realizarDeposito(cbu, monto);
        return new ResponseEntity<>("Depósito realizado exitosamente", HttpStatus.OK);

    }

    @PostMapping("/retiro")
    public ResponseEntity<String> realizarRetiro(@RequestBody long cbu, @RequestBody double monto) throws CuentaNoEncontradaException, CuentaSinSaldoException {
        movimientoService.realizarRetiro(cbu, monto);
        return new ResponseEntity<>("Retiro realizado exitosamente", HttpStatus.OK);

    }

    @GetMapping("/operaciones/{cbu}")
    public ResponseEntity<List<Movimiento>> obtenerOperacionesPorCBU(@PathVariable long cbu) throws MomivientosVaciosException {
        List<Movimiento> movimientos = movimientoService.obtenerOperacionesPorCBU(cbu);
        return new ResponseEntity<>(movimientos, HttpStatus.OK);
    }
}
