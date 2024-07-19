package main.java.ar.edu.utn.frbb.tup.service.operaciones.ManejoClientes;

import main.java.ar.edu.utn.frbb.tup.model.Cliente;
import org.springframework.stereotype.Service;
import main.java.ar.edu.utn.frbb.tup.model.TipoPersona;
import java.time.LocalDate;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class MostrarCliente {

    private static final String NOMBRE_ARCHIVO = "C:\\Users\\Uriel\\Desktop\\banco\\src\\main\\java\\main\\java\\ar\\edu\\utn\\frbb\\tup\\persistence\\database\\Clientes.txt";

    public Cliente mostrarCliente(String dni) {
        try (BufferedReader lector = new BufferedReader(new FileReader(NOMBRE_ARCHIVO))) {
            String linea;
            while ((linea = lector.readLine()) != null) {
                String[] campos = linea.split(",");
                if (campos.length > 1 && campos[3].trim().equals(dni.trim())) {
                    Cliente cliente = new Cliente();
                    cliente.setDni(campos[3]);
                    cliente.setNombre(campos[1]);
                    cliente.setApellido(campos[2]);
                    cliente.setDireccion(campos[4]);
                    cliente.setFechaNacimiento(LocalDate.parse(campos[5]));
                    cliente.setTipoPersona(TipoPersona.fromString(campos[6]));
                    cliente.setBanco(campos[7]);
                    cliente.setFechaAlta(LocalDate.parse(campos[8].replace(".", "")));
                    return cliente;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
