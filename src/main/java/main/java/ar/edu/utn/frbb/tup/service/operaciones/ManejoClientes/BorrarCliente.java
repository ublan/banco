package main.java.ar.edu.utn.frbb.tup.service.operaciones.ManejoClientes;

import org.springframework.stereotype.Service;
import main.java.ar.edu.utn.frbb.tup.model.Cliente;
import main.java.ar.edu.utn.frbb.tup.persistence.ClienteDao;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class BorrarCliente {

    @Autowired
    private ClienteDao clienteDao;

    private static final String NOMBRE_ARCHIVO = "C:\\Users\\Uriel\\Desktop\\banco\\src\\main\\java\\main\\java\\ar\\edu\\utn\\frbb\\tup\\persistence\\database\\Clientes.txt";

    public Cliente borrarCliente(long dni) {
        List<Cliente> clientes = new ArrayList<>();
        List<String> clientesStr = new ArrayList<>();
        Cliente cliente = null;
        try (BufferedReader lector = new BufferedReader(new FileReader(NOMBRE_ARCHIVO))) {
            String linea;
            linea = lector.readLine();
            clientesStr.add(linea);
            while ((linea = lector.readLine()) != null) {
                String[] campos = linea.split(",");
                if (Long.parseLong(campos[0]) != dni) {
                    clientes.add(clienteDao.parseDatosToObjet(campos));
                    clientesStr.add(linea);
                } else {
                    cliente = clienteDao.parseDatosToObjet(campos);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("No se pudo acceder a la base de datos");
        }

        if (cliente != null) {
            try (BufferedWriter escritor = new BufferedWriter(new FileWriter(NOMBRE_ARCHIVO))) {
                for (String clienteStr : clientesStr) {
                    escritor.write(clienteStr);
                    escritor.newLine();
                }
                return cliente;
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException("No se pudo escribir en el archivo");
            }
        } else {
            return cliente;
        }
    }
}
