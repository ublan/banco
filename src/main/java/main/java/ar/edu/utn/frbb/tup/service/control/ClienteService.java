package main.java.ar.edu.utn.frbb.tup.service.control;

import main.java.ar.edu.utn.frbb.tup.exception.ClienteAlreadyExistsException;
import main.java.ar.edu.utn.frbb.tup.exception.ClienteNoEncontradoException;
import main.java.ar.edu.utn.frbb.tup.exception.ModificarClienteException;
import main.java.ar.edu.utn.frbb.tup.model.Cliente;
import main.java.ar.edu.utn.frbb.tup.presentation.validator.ClienteValidator;
import main.java.ar.edu.utn.frbb.tup.service.operaciones.ManejoClientes.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import main.java.ar.edu.utn.frbb.tup.presentation.modelDto.ClienteDto;

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

    public Cliente darDeAltaCliente(ClienteDto clientedto) throws ClienteAlreadyExistsException {
        Cliente cliente = new Cliente(clientedto);
        crearCliente.validarClienteIfExist(cliente);
        crearCliente.crearCliente(cliente);
        return cliente;
    }
    
    
    public Cliente borrarCliente(long dni) throws ClienteNoEncontradoException {
        Cliente cliente = borrarCliente.borrarCliente(dni);

        System.out.println("Hola se borro");
        System.out.println(cliente);
        if (cliente == null) {
            throw new  ClienteNoEncontradoException("No se encontro el cliente con dni: " + dni);   
        }
        return cliente;
    }

    public void modificarCliente(ClienteDto clientedto) throws ClienteNoEncontradoException {
        Cliente cliente = new Cliente(clientedto);
        modificarCliente.modificarCliente(cliente);
    }

    public Cliente mostrarCliente(long dni) throws ClienteNoEncontradoException {
        Cliente cliente = mostrarCliente.mostrarCliente(dni);
        if (cliente == null) {
            throw new ClienteNoEncontradoException("No se encontro el cliente con dni: " + dni);
        }
        return cliente;
    }

    public List<Cliente> obtenerTodosLosClientes() {
        List<Cliente> todosLosClientes = mostrarTodosClientes.obtenerTodosLosClientes();
        if (todosLosClientes.isEmpty()) {
            throw new IllegalArgumentException("No se encontraron clientes");
        }
        return mostrarTodosClientes.obtenerTodosLosClientes();
    }
}



