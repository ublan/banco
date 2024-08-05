package main.java.ar.edu.utn.frbb.tup.presentation.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import main.java.ar.edu.utn.frbb.tup.exception.ClienteAlreadyExistsException;
import main.java.ar.edu.utn.frbb.tup.exception.ClienteMenorEdadException;
import main.java.ar.edu.utn.frbb.tup.exception.ClienteNoEncontradoException;
import main.java.ar.edu.utn.frbb.tup.model.Cliente;
import org.springframework.beans.factory.annotation.Autowired;
import main.java.ar.edu.utn.frbb.tup.presentation.validator.ClienteValidator;
import main.java.ar.edu.utn.frbb.tup.service.ClienteService;
import main.java.ar.edu.utn.frbb.tup.presentation.modelDto.ClienteDto;


import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private ClienteValidator clienteValidator;
    
    @PostMapping
    public ResponseEntity<Cliente> darDeAltaCliente(@RequestBody ClienteDto clientedto) throws ClienteAlreadyExistsException, ClienteMenorEdadException {
        clienteValidator.validarCliente(clientedto);
        return new ResponseEntity<>(clienteService.darDeAltaCliente(clientedto), HttpStatus.CREATED);
    }

    @DeleteMapping("/{dni}")
    public ResponseEntity<Cliente> borrarCliente(@PathVariable long dni) throws ClienteNoEncontradoException {   
        return new ResponseEntity<>(clienteService.borrarCliente(dni), HttpStatus.OK);
    }

    @PutMapping("/{dni}")
    public ResponseEntity<Cliente> modificarCliente(@PathVariable long dni, @RequestBody ClienteDto clientedto) throws ClienteNoEncontradoException {
        clienteValidator.validarCliente(clientedto);
        return new ResponseEntity<>(clienteService.modificarCliente(clientedto), HttpStatus.OK);
    }

    @GetMapping("/{dni}")
    public ResponseEntity<Cliente> mostrarCliente(@PathVariable long dni) throws ClienteNoEncontradoException {
        Cliente cliente = clienteService.mostrarCliente(dni);
        if (cliente != null) {
            return new ResponseEntity<>(cliente, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public ResponseEntity<List<Cliente>> mostrarTodosLosClientes() throws ClienteNoEncontradoException {
        List<Cliente> clientes = clienteService.mostrarTodosLosClientes();
        return new ResponseEntity<>(clientes, HttpStatus.OK);   
    }


}
