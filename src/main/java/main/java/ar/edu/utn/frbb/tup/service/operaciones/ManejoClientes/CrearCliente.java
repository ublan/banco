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
        Cliente clienteExistente = clienteDao.findByDni(String.valueOf(clienteDto.getDni()));
        if (clienteExistente != null) {
            throw new IllegalArgumentException("Ya existe un cliente con DNI " + clienteDto.getDni());
        }
    }

    public void crearCliente(Cliente cliente) {
        try (BufferedWriter escritor = new BufferedWriter(new FileWriter(NOMBRE_ARCHIVO, true))) {
            escritor.write(clienteToCsv(cliente));
            escritor.newLine();
        } catch (IOException e) {
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


