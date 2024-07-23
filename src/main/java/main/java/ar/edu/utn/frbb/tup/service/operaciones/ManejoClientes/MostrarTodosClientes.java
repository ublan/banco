package main.java.ar.edu.utn.frbb.tup.service.operaciones.ManejoClientes;

import main.java.ar.edu.utn.frbb.tup.model.Cliente;
import main.java.ar.edu.utn.frbb.tup.model.TipoPersona;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

@Service
public class MostrarTodosClientes {

    private static final String NOMBRE_ARCHIVO = "C:\\Users\\Uriel\\Desktop\\banco\\src\\main\\java\\main\\java\\ar\\edu\\utn\\frbb\\tup\\persistence\\database\\Clientes.txt";

    public List<Cliente> obtenerTodosLosClientes() {
        List<Cliente> clientes = new ArrayList<>();

        try (BufferedReader lector = new BufferedReader(new FileReader(NOMBRE_ARCHIVO))) {
            String linea;
            while ((linea = lector.readLine()) != null) {
                // Ignorar la primera línea si es el encabezado
                if (linea.startsWith("id,")) {
                    continue;
                }
                
                String[] datos = linea.split(",");
                if (datos.length < 9) {
                    System.err.println("Línea mal formada: " + linea);
                    continue;
                }

                try {
                    Cliente cliente = new Cliente();
                    cliente.setDni(datos[3]);
                    cliente.setNombre(datos[1]);
                    cliente.setApellido(datos[2]);
                    cliente.setDireccion(datos[4]);
                    cliente.setFechaNacimiento(LocalDate.parse(datos[5]));
                    cliente.setTipoPersona(TipoPersona.fromString(datos[6]));
                    cliente.setBanco(datos[7]);
                    cliente.setFechaAlta(LocalDate.parse(datos[8].replace(".", "")));

                    clientes.add(cliente);
                } catch (DateTimeParseException e) {
                    System.err.println("Error al parsear la fecha en la línea: " + linea);
                }
            }
        } catch (IOException ex) {
            System.err.println("Error al leer el archivo: " + ex.getMessage());
        }

        return clientes;
    }
}

