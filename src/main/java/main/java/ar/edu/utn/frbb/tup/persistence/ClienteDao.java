package main.java.ar.edu.utn.frbb.tup.persistence;

import java.io.*;
import java.time.LocalDate;

import main.java.ar.edu.utn.frbb.tup.model.Cliente;
import main.java.ar.edu.utn.frbb.tup.model.TipoPersona;
import org.springframework.stereotype.Repository;

@Repository
public class ClienteDao {

    private static final String NOMBRE_ARCHIVO = "C:\\Users\\Uriel\\Desktop\\banco\\src\\main\\java\\main\\java\\ar\\edu\\utn\\frbb\\tup\\persistence\\database\\Clientes.txt";

    public void escribirEnArchivo(Cliente cliente) {
        boolean archivoNuevo = !(new File(NOMBRE_ARCHIVO).exists());

        try (BufferedWriter escritor = new BufferedWriter(new FileWriter(NOMBRE_ARCHIVO, true))) {
            if (archivoNuevo) {
                escritor.write("id,nombre,apellido,dni,direccion,fechaNacimiento,tipoPersona,banco,fechaAlta");
                escritor.newLine();
            }
            escritor.write(cliente.getDni() + ",");
            escritor.write(cliente.getNombre() + ",");
            escritor.write(cliente.getApellido() + ",");
            escritor.write(cliente.getDni() + ",");
            escritor.write(cliente.getDireccion() + ",");
            escritor.write(cliente.getFechaNacimiento().toString() + ",");
            escritor.write(cliente.getTipoPersona().toString() + ",");
            escritor.write(cliente.getBanco() + ",");
            escritor.write(cliente.getFechaAlta().toString());
            escritor.newLine();
        } catch (IOException ex) {
            System.err.println("Error al escribir en el archivo: " + ex.getMessage());
        }
    }

    public Cliente findByDni(String dni) {
        try (BufferedReader lector = new BufferedReader(new FileReader(NOMBRE_ARCHIVO))) {
            String linea;
            lector.readLine();
            while ((linea = lector.readLine()) != null) {
                String[] datos = linea.split(",");
                if (datos[3].equals(dni)) {
                    Cliente cliente = new Cliente();
                    cliente.setDni(datos[3]);
                    cliente.setNombre(datos[1]);
                    cliente.setApellido(datos[2]);
                    cliente.setDireccion(datos[4]);
                    cliente.setFechaNacimiento(LocalDate.parse(datos[5]));
                    cliente.setTipoPersona(TipoPersona.fromString(datos[6]));
                    cliente.setBanco(datos[7]);
                    cliente.setFechaAlta(LocalDate.parse(datos[8]));
                    return cliente;
                }
            }
        } catch (IOException ex) {
            System.err.println("Error al leer el archivo: " + ex.getMessage());
        }
        return null;
    }

}
