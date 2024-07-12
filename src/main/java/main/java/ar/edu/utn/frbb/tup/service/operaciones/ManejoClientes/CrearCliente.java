package main.java.ar.edu.utn.frbb.tup.service.operaciones.ManejoClientes;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import main.java.ar.edu.utn.frbb.tup.exception.*;
import main.java.ar.edu.utn.frbb.tup.model.Cliente;
import main.java.ar.edu.utn.frbb.tup.persistence.ClienteDao;
import main.java.ar.edu.utn.frbb.tup.presentation.modelDto.ClienteDto;

public class CrearCliente {

    private static final String NOMBRE_ARCHIVO = "C:\\Users\\Uriel\\Desktop\\banco\\src\\main\\java\\main\\java\\ar\\edu\\utn\\frbb\\tup\\persistence\\database\\Clientes.txt";
    
    // Validar que el DNI no exista en el archivo
    public void validarClienteIfExist(ClienteDto clienteDto) throws ClienteAlreadyExistsException {
        Cliente cliente = new Cliente(clienteDto);
        Cliente clienteExistente = ClienteDao.findByDni(String.valueOf(cliente.getDni()));
        if (clienteExistente != null) {
            throw new IllegalArgumentException("Ya existe un cliente con DNI " + cliente.getDni());
        }
    }

    public static void crearCliente(long dni, String nombre, String apellido, String direccion, String fechaNacimiento, String tipoPersona, String banco, String fechaAlta) {
        try (BufferedWriter escritor = new BufferedWriter(new FileWriter(NOMBRE_ARCHIVO, true))) {
            escritor.write(dni + "," + nombre + "," + apellido + "," + dni + "," + direccion + "," + fechaNacimiento + "," + tipoPersona + "," + banco + "," + fechaAlta);
            escritor.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

