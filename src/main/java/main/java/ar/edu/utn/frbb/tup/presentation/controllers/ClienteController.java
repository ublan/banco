package main.java.ar.edu.utn.frbb.tup.presentation.controllers;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import main.java.ar.edu.utn.frbb.tup.service.control.ClienteService;
import main.java.ar.edu.utn.frbb.tup.exception.ClienteAlreadyExistsException;
import main.java.ar.edu.utn.frbb.tup.exception.ModificarClienteException;
import main.java.ar.edu.utn.frbb.tup.model.Cliente;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    private ClienteService clienteService = new ClienteService();

    @PostMapping
    public ResponseEntity<String> darDeAltaCliente(@RequestBody Cliente cliente) {
        try {
            clienteService.darDeAltaCliente(cliente);
            return new ResponseEntity<>("Cliente creado exitosamente.", HttpStatus.CREATED);
        } catch (ClienteAlreadyExistsException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        } catch (Exception e) {
            return new ResponseEntity<>("Error interno del servidor.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{dni}")
    public ResponseEntity<String> borrarCliente(@PathVariable String dni) {
        try {
            clienteService.borrarCliente(dni);
            return new ResponseEntity<>("Cliente borrado exitosamente.", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error interno del servidor.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping
    public ResponseEntity<String> modificarCliente(@RequestBody Cliente cliente) {
        try {
            clienteService.modificarCliente(cliente);
            return new ResponseEntity<>("Cliente modificado exitosamente.", HttpStatus.OK);
        } catch (ModificarClienteException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("Error interno del servidor.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{dni}")
    public ResponseEntity<Cliente> mostrarCliente(@PathVariable String dni) {
        Cliente cliente = clienteService.mostrarCliente(dni);
        if (cliente != null) {
            return new ResponseEntity<>(cliente, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public ResponseEntity<List<Cliente>> obtenerTodosLosClientes() {
        List<Cliente> clientes = clienteService.obtenerTodosLosClientes();
        return new ResponseEntity<>(clientes, HttpStatus.OK);
    }
}

