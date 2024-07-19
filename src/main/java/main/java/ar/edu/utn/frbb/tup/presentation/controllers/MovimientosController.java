package main.java.ar.edu.utn.frbb.tup.presentation.controllers;

import main.java.ar.edu.utn.frbb.tup.service.operaciones.ManejoMovimientos.Deposito;
import main.java.ar.edu.utn.frbb.tup.service.operaciones.ManejoMovimientos.Retiro;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/movimientos")
public class MovimientosController {

    @Autowired
    private Deposito depositoService;

    @Autowired
    private Retiro retiroService;

    // Endpoint para realizar un depósito
    @PostMapping("/deposito")
    public ResponseEntity<String> realizarDeposito(@RequestBody MovimientoRequest request) {
        try {
            depositoService.realizarDeposito(request.getCbu(), request.getMonto(), request.getMoneda());
            return new ResponseEntity<>("Depósito realizado exitosamente", HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al realizar el depósito", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Endpoint para realizar un retiro
    @PostMapping("/retiro")
    public ResponseEntity<String> realizarRetiro(@RequestBody MovimientoRequest request) {
        try {
            retiroService.realizarRetiro(request.getCbu(), request.getMonto(), request.getMoneda());
            return new ResponseEntity<>("Retiro realizado exitosamente", HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al realizar el retiro", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
