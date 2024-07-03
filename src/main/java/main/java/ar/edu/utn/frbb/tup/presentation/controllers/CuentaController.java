package main.java.ar.edu.utn.frbb.tup.presentation.controllers;

import main.java.ar.edu.utn.frbb.tup.model.Cuenta;
import main.java.ar.edu.utn.frbb.tup.service.control.CuentaService;
import main.java.ar.edu.utn.frbb.tup.exception.CuentaAlreadyExistsException;
import main.java.ar.edu.utn.frbb.tup.exception.InvalidDataException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cuentas")
public class CuentaController {

    @Autowired
    private CuentaService cuentaService;

    @PostMapping("/crear")
    public ResponseEntity<String> crearCuenta(@RequestBody Cuenta cuenta) {
        try {
            cuentaService.darDeAltaCuenta(cuenta);
            return ResponseEntity.status(HttpStatus.CREATED).body("Cuenta creada exitosamente.");
        } catch (CuentaAlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (InvalidDataException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}

