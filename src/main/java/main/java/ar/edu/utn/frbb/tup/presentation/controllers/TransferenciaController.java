package main.java.ar.edu.utn.frbb.tup.presentation.controllers;

import main.java.ar.edu.utn.frbb.tup.exception.CuentaNoEncontradaException;
import main.java.ar.edu.utn.frbb.tup.exception.CuentaSinSaldoException;
import main.java.ar.edu.utn.frbb.tup.exception.TipoMonedasInvalidasException;
import main.java.ar.edu.utn.frbb.tup.model.Transferencia;
import main.java.ar.edu.utn.frbb.tup.presentation.modelDto.TransferenciaDto;
import main.java.ar.edu.utn.frbb.tup.presentation.validator.TransferenciaValidator;
import main.java.ar.edu.utn.frbb.tup.service.TransferenciaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class TransferenciaController {

    @Autowired
    private TransferenciaValidator transferenciaValidator;

    @Autowired
    private TransferenciaService transferenciaService;

    @GetMapping("/cuenta/{cbu}/transacciones")
    public ResponseEntity<List<Transferencia>> obtenerTransacciones(@PathVariable long cbu) {
        List<Transferencia> transacciones = transferenciaService.obtenerTransferenciasPorCbu(cbu);
        return new ResponseEntity<>(transacciones, HttpStatus.OK);
    }

    @PostMapping("/transferencia")
    public ResponseEntity<Transferencia> realizarTransferencia(@RequestBody TransferenciaDto transferenciaDto) throws CuentaNoEncontradaException, CuentaSinSaldoException, TipoMonedasInvalidasException {
        transferenciaValidator.validarTransferencia(transferenciaDto);
        return new ResponseEntity<>(transferenciaService.realizarTransferencia(transferenciaDto), HttpStatus.OK);
    }

}

