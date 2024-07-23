package main.java.ar.edu.utn.frbb.tup.service.operaciones.ManejoClientes;

import main.java.ar.edu.utn.frbb.tup.exception.ClienteNoEncontradoException;
import main.java.ar.edu.utn.frbb.tup.model.Cliente;
import org.springframework.stereotype.Service;

import java.io.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class ModificarCliente {

    private static final String NOMBRE_ARCHIVO = "C:\\Users\\Uriel\\Desktop\\banco\\src\\main\\java\\main\\java\\ar\\edu\\utn\\frbb\\tup\\persistence\\database\\Clientes.txt";

    public void modificarCliente(Cliente cliente) throws ClienteNoEncontradoException {
        List<String> nuevosDatos = new ArrayList<>();
        boolean clienteEncontrado = false;

        try (BufferedReader lector = new BufferedReader(new FileReader(NOMBRE_ARCHIVO))) {
            String linea;
            while ((linea = lector.readLine()) != null) {
                String[] campos = linea.split(",");
                if (campos[0].trim().equals(cliente.getDni().trim())) {
                    clienteEncontrado = true;
                    campos[1] = cliente.getNombre();
                    campos[2] = cliente.getApellido();
                    campos[3] = cliente.getDireccion();
                    campos[4] = cliente.getFechaNacimiento().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                    campos[5] = cliente.getTipoPersona().toString();
                    campos[6] = cliente.getBanco();
                    campos[7] = cliente.getFechaAlta().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                }
                nuevosDatos.add(String.join(",", campos));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (!clienteEncontrado) {
            throw new ClienteNoEncontradoException("Cliente no encontrado con DNI: " + cliente.getDni());
        }

        try (BufferedWriter escritor = new BufferedWriter(new FileWriter(NOMBRE_ARCHIVO))) {
            for (String datos : nuevosDatos) {
                escritor.write(datos);
                escritor.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


