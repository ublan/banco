package main.java.ar.edu.utn.frbb.tup.service.control;

import main.java.ar.edu.utn.frbb.tup.exception.ClienteAlreadyExistsException;
import main.java.ar.edu.utn.frbb.tup.exception.ClienteNoEncontradoException;
import main.java.ar.edu.utn.frbb.tup.model.Cliente;
import main.java.ar.edu.utn.frbb.tup.presentation.validator.ClienteValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import main.java.ar.edu.utn.frbb.tup.presentation.modelDto.ClienteDto;
import main.java.ar.edu.utn.frbb.tup.persistence.ClienteDao;

import java.util.List;

@Service
public class ClienteService {

    @Autowired
    private ClienteDao clienteDao, crearCliente, borrarCliente, modificarCliente, mostrarCliente, mostrarTodosClientes;


    public Cliente darDeAltaCliente(ClienteDto clientedto) throws ClienteAlreadyExistsException {
        Cliente cliente = new Cliente(clientedto);
        clienteDao.validarClienteIfExist(cliente);
        crearCliente.crearCliente(cliente);
        return cliente;
    }
    
    
    public Cliente borrarCliente(long dni) throws ClienteNoEncontradoException {
        Cliente cliente = borrarCliente.borrarCliente(dni);
        System.out.println("Hola se borro");
        System.out.println(cliente);
        if (cliente == null) {
            throw new ClienteNoEncontradoException("No se encontro el cliente con ese dni");   
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

    public List<Cliente> mostrarTodosLosClientes() throws ClienteNoEncontradoException {
        List<Cliente> todosLosClientes = mostrarTodosClientes.mostrarTodosLosClientes();
        if (todosLosClientes.isEmpty()) {
            throw new ClienteNoEncontradoException("No se encontraron clientes");
        }
        return mostrarTodosClientes.mostrarTodosLosClientes();
    }
}



