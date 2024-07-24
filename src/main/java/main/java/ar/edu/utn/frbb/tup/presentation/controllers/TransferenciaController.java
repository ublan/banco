package main.java.ar.edu.utn.frbb.tup.presentation.controllers;

import main.java.ar.edu.utn.frbb.tup.presentation.modelDto.TransferenciaDto;
import main.java.ar.edu.utn.frbb.tup.service.control.TransferenciaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class TransferenciaController {

    @Autowired
    private TransferenciaService transferenciaService;

    @GetMapping("/cuenta/{cuentaId}/transacciones")
    public ResponseEntity<List<TransferenciaDto>> obtenerTransacciones(@PathVariable long cuentaId) {
        List<TransferenciaDto> transacciones = transferenciaService.obtenerTransferenciasPorCbu(cuentaId);
        return ResponseEntity.ok(transacciones);
    }

    @PostMapping
    public ResponseEntity<String> realizarTransferencia(@RequestBody TransferenciaDto transferenciaDto) {
        try {
            transferenciaService.ejecutarTransferencia(transferenciaDto);
            return ResponseEntity.ok("Transferencia exitosa");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}

