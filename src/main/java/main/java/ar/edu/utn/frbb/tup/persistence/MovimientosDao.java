package main.java.ar.edu.utn.frbb.tup.persistence;

import main.java.ar.edu.utn.frbb.tup.model.Movimiento;
import org.springframework.stereotype.Repository;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

@Repository
public class MovimientosDao {

    private static final String NOMBRE_ARCHIVO = "C:\\Users\\Uriel\\Desktop\\banco\\src\\main\\java\\main\\java\\ar\\edu\\utn\\frbb\\tup\\persistence\\database\\Movimientos.txt";

    public static void guardarEnArchivo(Movimiento movimiento) {
        boolean archivoNuevo = !(new File(NOMBRE_ARCHIVO).exists());

        try (BufferedWriter escritor = new BufferedWriter(new FileWriter(NOMBRE_ARCHIVO, true))) {
            // Si el archivo es nuevo, escribir la cabecera
            if (archivoNuevo) {
                escritor.write("CBU,fechaOperacion,tipoOperacion,monto");
                escritor.newLine();
            }

            // Escribir los datos del movimiento
            escritor.write(movimiento.getCBU() + ",");
            escritor.write(movimiento.getFechaOperacion().toString() + ",");
            escritor.write(movimiento.getTipoOperacion().name() + ",");
            escritor.write(Double.toString(movimiento.getMonto()));
            escritor.newLine();

            System.out.println("Datos del movimiento guardados en " + NOMBRE_ARCHIVO + " correctamente.");
        } catch (IOException ex) {
            System.err.println("Error al escribir en el archivo: " + ex.getMessage());
        }
    }
}

