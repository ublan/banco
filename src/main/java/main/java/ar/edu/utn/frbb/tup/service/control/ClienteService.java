package main.java.ar.edu.utn.frbb.tup.service.control;

import main.java.ar.edu.utn.frbb.tup.exception.ClienteAlreadyExistsException;
import main.java.ar.edu.utn.frbb.tup.exception.ModificarClienteException;
import main.java.ar.edu.utn.frbb.tup.model.Cliente;
import main.java.ar.edu.utn.frbb.tup.presentation.validator.ClienteValidator;
import main.java.ar.edu.utn.frbb.tup.service.operaciones.ManejoClientes.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteService {

    @Autowired
    private ClienteValidator clienteValidator;

    @Autowired
    private CrearCliente crearCliente;

    @Autowired
    private BorrarCliente borrarCliente;

    @Autowired
    private ModificarCliente modificarCliente;

    @Autowired
    private MostrarCliente mostrarCliente;

    @Autowired
    private MostrarTodosClientes mostrarTodosClientes;

    public void darDeAltaCliente(Cliente cliente) throws ClienteAlreadyExistsException {
        clienteValidator.validarCliente(cliente.toDto());
        crearCliente.validarClienteIfExist(cliente.toDto());
        crearCliente.crearCliente(cliente);
    }

    public void borrarCliente(String dni) {
        borrarCliente.borrarCliente(dni);
    }

    public void modificarCliente(Cliente cliente) {
        clienteValidator.validarCliente(cliente.toDto());
        try {
            modificarCliente.modificarCliente(cliente);
        } catch (Exception e) {
            throw new ModificarClienteException("Error al modificar el cliente: " + cliente.getDni(), e);
        }
    }

    public Cliente mostrarCliente(String dni) {
        return mostrarCliente.mostrarCliente(dni);
    }

    public List<Cliente> obtenerTodosLosClientes() {
        return mostrarTodosClientes.obtenerTodosLosClientes();
    }
}



