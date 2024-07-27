package main.java.ar.edu.utn.frbb.tup.service.operaciones.ManejoClientes;

import main.java.ar.edu.utn.frbb.tup.exception.ClienteAlreadyExistsException;
import main.java.ar.edu.utn.frbb.tup.model.Cliente;
import main.java.ar.edu.utn.frbb.tup.persistence.ClienteDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.File;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

@Service
public class CrearCliente {

    @Autowired
    private ClienteDao clienteDao;

    private static final String NOMBRE_ARCHIVO = "C:\\Users\\Uriel\\Desktop\\banco\\src\\main\\java\\main\\java\\ar\\edu\\utn\\frbb\\tup\\persistence\\database\\Clientes.txt";

    public void validarClienteIfExist(Cliente cliente) throws ClienteAlreadyExistsException {
        Cliente clienteExistente = clienteDao.findByDni(cliente.getDni());
        if (clienteExistente != null) {
            throw new ClienteAlreadyExistsException("Ya existe un cliente con DNI " + cliente.getDni());
        }
    }
    
    
    public void crearCliente(Cliente cliente) {
        boolean archivoNuevo = !(new File(NOMBRE_ARCHIVO).exists());
        try (BufferedWriter escritor = new BufferedWriter(new FileWriter(NOMBRE_ARCHIVO, true))) {
            if (archivoNuevo) {
                escritor.write("dni,nombre,apellido,direccion,fechaNacimiento,tipoPersona,banco,fechaAlta");
                escritor.newLine();
            }
            escritor.write(clienteToCsv(cliente));
            escritor.newLine();
        } catch (IOException e) {
            System.err.println("Error al escribir en el archivo: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private String clienteToCsv(Cliente cliente) {
        return  cliente.getDni() + "," +
                cliente.getNombre() + "," +
                cliente.getApellido() + "," +
                cliente.getDireccion() + "," +
                cliente.getFechaNacimiento() + "," +
                cliente.getTipoPersona() + "," +
                cliente.getBanco() + "," +
                cliente.getFechaAlta();
    }
}


