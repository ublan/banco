package main.java.ar.edu.utn.frbb.tup.service.operaciones.ManejoClientes;

import main.java.ar.edu.utn.frbb.tup.exception.ClienteAlreadyExistsException;
import main.java.ar.edu.utn.frbb.tup.model.Cliente;
import main.java.ar.edu.utn.frbb.tup.persistence.ClienteDao;
import main.java.ar.edu.utn.frbb.tup.presentation.modelDto.ClienteDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

@Service
public class CrearCliente {

    @Autowired
    private ClienteDao clienteDao;

    private static final String NOMBRE_ARCHIVO = "C:\\Users\\Uriel\\Desktop\\banco\\src\\main\\java\\main\\java\\ar\\edu\\utn\\frbb\\tup\\persistence\\database\\Clientes.txt";

    public void validarClienteIfExist(ClienteDto clienteDto) throws ClienteAlreadyExistsException {
        Cliente clienteExistente = clienteDao.findByDni(clienteDto.getDni());
        if (clienteExistente != null) {
            System.err.println("Cliente ya existe con DNI: " + clienteDto.getDni());
            throw new ClienteAlreadyExistsException("Ya existe un cliente con DNI " + clienteDto.getDni());
        }
    }
    
    
    
    
    public void crearCliente(Cliente cliente) {
        try (BufferedWriter escritor = new BufferedWriter(new FileWriter(NOMBRE_ARCHIVO, true))) {
            System.out.println("Escribiendo cliente en archivo: " + cliente);
            escritor.write(clienteToCsv(cliente));
            escritor.newLine();
        } catch (IOException e) {
            System.err.println("Error al escribir en el archivo: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private String clienteToCsv(Cliente cliente) {
        return cliente.getDni() + "," +
                cliente.getNombre() + "," +
                cliente.getApellido() + "," +
                cliente.getDireccion() + "," +
                cliente.getFechaNacimiento() + "," +
                cliente.getTipoPersona() + "," +
                cliente.getBanco() + "," +
                cliente.getFechaAlta();
    }
}


