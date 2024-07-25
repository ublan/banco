package main.java.ar.edu.utn.frbb.tup.presentation.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import main.java.ar.edu.utn.frbb.tup.service.control.ClienteService;
import main.java.ar.edu.utn.frbb.tup.exception.ClienteAlreadyExistsException;
import main.java.ar.edu.utn.frbb.tup.exception.ClienteNoEncontradoException;
import main.java.ar.edu.utn.frbb.tup.model.Cliente;
import org.springframework.beans.factory.annotation.Autowired;
import main.java.ar.edu.utn.frbb.tup.presentation.validator.ClienteValidator;
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
    public ResponseEntity<String> darDeAltaCliente(@RequestBody ClienteDto clientedto) {
        try {
            clienteValidator.validarCliente(clientedto);
            clienteService.darDeAltaCliente(clientedto);
            return new ResponseEntity<>("Cliente creado exitosamente.", HttpStatus.CREATED);
        } catch (ClienteAlreadyExistsException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        } catch (Exception e) {
            return new ResponseEntity<>("Error interno del servidor.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{dni}")
    public ResponseEntity<Cliente> borrarCliente(@PathVariable String dni) throws ClienteNoEncontradoException {   
        return new ResponseEntity<>(clienteService.borrarCliente(dni), HttpStatus.OK);
    }

    @PutMapping("/{dni}")
    public ResponseEntity<String> modificarCliente(@PathVariable String dni, @RequestBody ClienteDto clientedto) throws ClienteNoEncontradoException {
        clienteValidator.validarCliente(clientedto);
        clienteService.modificarCliente(clientedto);
        return new ResponseEntity<>("Cliente modificado exitosamente.", HttpStatus.OK);
    }

    @GetMapping("/{dni}")
    public ResponseEntity<Cliente> mostrarCliente(@PathVariable String dni) throws ClienteNoEncontradoException {
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
