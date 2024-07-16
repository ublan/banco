package main.java.ar.edu.utn.frbb.tup.persistence;

import main.java.ar.edu.utn.frbb.tup.model.Movimiento;
import main.java.ar.edu.utn.frbb.tup.model.TipoOperacion;
import org.springframework.stereotype.Repository;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;

@Repository
public class MovimientosDao {

    private static final String NOMBRE_ARCHIVO = "C:\\Users\\Uriel\\Desktop\\banco\\src\\main\\java\\main\\java\\ar\\edu\\utn\\frbb\\tup\\persistence\\database\\Movimientos.txt";

    public static void guardarEnArchivo(Movimiento movimiento) {
        boolean archivoNuevo = !(new File(NOMBRE_ARCHIVO).exists());

        try (BufferedWriter escritor = new BufferedWriter(new FileWriter(NOMBRE_ARCHIVO, true))) {
            // Si el archivo es nuevo, escribir la cabecera
            if (archivoNuevo) {
                escritor.write("CBU,CBUDestino,fechaOperacion,tipoOperacion,monto");
                escritor.newLine();
            }

            // Escribir los datos del movimiento
            escritor.write(movimiento.getCBU() + ",");
            escritor.write(movimiento.getCBUDestino() + ",");
            escritor.write(movimiento.getFechaOperacion().toString() + ",");
            escritor.write(movimiento.getTipoOperacion().name() + ",");
            escritor.write(Double.toString(movimiento.getMonto()));
            escritor.newLine();

            System.out.println("Datos del movimiento guardados en " + NOMBRE_ARCHIVO + " correctamente.");
        } catch (IOException ex) {
            System.err.println("Error al escribir en el archivo: " + ex.getMessage());
        }
    }

    public static Movimiento cuentaPorCBU(long cbu) {
        try (BufferedReader lector = new BufferedReader(new FileReader(NOMBRE_ARCHIVO))) {
            String linea;
            // Leer la cabecera
            lector.readLine();

            // Leer cada línea del archivo
            while ((linea = lector.readLine()) != null) {
                String[] datos = linea.split(",");

                // Validar que la línea tiene la cantidad esperada de datos
                if (datos.length < 5) {
                    System.err.println("Línea mal formada: " + linea);
                    continue;
                }

                if (Long.parseLong(datos[0]) == cbu) { // Comparar el CBU en el archivo con el CBU proporcionado
                    Movimiento movimiento = new Movimiento();
                    movimiento.setCBU(Long.parseLong(datos[0]));
                    movimiento.setCBUDestino(Long.parseLong(datos[1]));
                    movimiento.setFechaOperacion(LocalDate.parse(datos[2]));
                    movimiento.setTipoOperacion(TipoOperacion.fromString(datos[3]));
                    movimiento.setMonto(Double.parseDouble(datos[4]));
                    return movimiento;
                }
            }
        } catch (IOException ex) {
            System.err.println("Error al leer el archivo: " + ex.getMessage());
        }
        return null;
    }
}


