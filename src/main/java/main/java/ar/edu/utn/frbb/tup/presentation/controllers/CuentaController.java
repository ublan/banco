package main.java.ar.edu.utn.frbb.tup.presentation.controllers;

import main.java.ar.edu.utn.frbb.tup.exception.ClienteAlreadyExistsException;
import main.java.ar.edu.utn.frbb.tup.exception.ClienteNotFoundException;
import main.java.ar.edu.utn.frbb.tup.model.Cuenta;
import main.java.ar.edu.utn.frbb.tup.service.control.CuentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cuentas")
public class CuentaController {

    @Autowired
    private CuentaService cuentaService;

    @PostMapping("/crearCuenta/{dni}")
    public ResponseEntity<String> crearCuenta(@RequestBody Cuenta cuenta, @PathVariable String dni) {
        try {
            cuentaService.darDeAltaCuenta(cuenta, dni);
            return new ResponseEntity<>("Cuenta creada para el titular con DNI: " + dni, HttpStatus.CREATED);
        } catch (ClienteNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("Error interno del servidor", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/mostrar/{dni}")
    public ResponseEntity<List<String>> mostrarCuentasPorDni(@PathVariable String dni) {
        try {
            List<String> cuentas = cuentaService.mostrarCuenta(dni);
            return new ResponseEntity<>(cuentas, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/mostrar")
    public ResponseEntity<List<String>> mostrarTodasLasCuentas() {
        try {
            List<String> cuentas = cuentaService.obtenerTodasLasCuentas();
            return new ResponseEntity<>(cuentas, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/eliminar/{cbu}")
    public ResponseEntity<String> eliminarCuentaPorCBU(@PathVariable("cbu") String cbu) {
        try {
            cuentaService.borrarCuenta(cbu);
            return new ResponseEntity<>("Cuenta eliminada correctamente", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

