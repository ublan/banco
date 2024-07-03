package main.java.ar.edu.utn.frbb.tup.presentation.controllers;

import org.springframework.web.bind.annotation.RestController;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import main.java.ar.edu.utn.frbb.tup.service.control.CuentaService;
import main.java.ar.edu.utn.frbb.tup.service.operaciones.ManejoCuentas.BorrarCuenta;
//import main.java.ar.edu.utn.frbb.tup.service.operaciones.ManejoCuentas.ModificarCliente;
import main.java.ar.edu.utn.frbb.tup.service.operaciones.ManejoCuentas.MostrarCuentas;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import main.java.ar.edu.utn.frbb.tup.exception.CuentaAlreadyExistsException;
import main.java.ar.edu.utn.frbb.tup.exception.ModificarClienteException;
import main.java.ar.edu.utn.frbb.tup.model.Cuenta;
import main.java.ar.edu.utn.frbb.tup.persistence.SummitCuenta;

import java.util.List;

@RestController
@RequestMapping("/cuentas")
public class CuentaController {
    
}
