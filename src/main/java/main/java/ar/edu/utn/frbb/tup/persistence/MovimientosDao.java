package main.java.ar.edu.utn.frbb.tup.persistence;

import main.java.ar.edu.utn.frbb.tup.model.Movimiento;
import main.java.ar.edu.utn.frbb.tup.model.TipoOperacion;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Repository
public class MovimientosDao {

    private static final String NOMBRE_ARCHIVO_MOVIMIENTOS = "C:\\Users\\Uriel\\Desktop\\banco\\src\\main\\java\\main\\java\\ar\\edu\\utn\\frbb\\tup\\persistence\\database\\Movimientos.txt";
    private static final String NOMBRE_ARCHIVO_TRANSFERENCIAS = "C:\\Users\\Uriel\\Desktop\\banco\\src\\main\\java\\main\\java\\ar\\edu\\utn\\frbb\\tup\\persistence\\database\\Transferencias.txt";

    public void guardarMovimiento(Movimiento movimiento) {
        String rutaArchivo = movimiento.getTipoOperacion() == TipoOperacion.TRANSFERENCIA ? NOMBRE_ARCHIVO_TRANSFERENCIAS : NOMBRE_ARCHIVO_MOVIMIENTOS;
        boolean archivoNuevo = !(new File(rutaArchivo).exists());

        try (BufferedWriter escritor = new BufferedWriter(new FileWriter(rutaArchivo, true))) {
            if (archivoNuevo) {
                escritor.write("CBU,CBUDestino,fechaOperacion,tipoOperacion,monto");
                escritor.newLine();
            }

            escritor.write(movimiento.getCBU() + ",");
            escritor.write(movimiento.getCBUDestino() + ",");
            escritor.write(movimiento.getFechaOperacion().toString() + ",");
            escritor.write(movimiento.getTipoOperacion().name() + ",");
            escritor.write(Double.toString(movimiento.getMonto()));
            escritor.newLine();

            System.out.println("Datos del movimiento guardados en " + rutaArchivo + " correctamente.");
        } catch (IOException ex) {
            System.err.println("Error al escribir en el archivo: " + ex.getMessage());
        }
    }

    public Movimiento cuentaPorCBU(long cbu) {
        List<Movimiento> movimientos = new ArrayList<>();
        movimientos.addAll(leerMovimientosDeArchivo(NOMBRE_ARCHIVO_MOVIMIENTOS, cbu));
        movimientos.addAll(leerMovimientosDeArchivo(NOMBRE_ARCHIVO_TRANSFERENCIAS, cbu));

        for (Movimiento movimiento : movimientos) {
            if (movimiento.getCBU() == cbu) {
                return movimiento;
            }
        }
        return null;
    }

    public List<Movimiento> obtenerOperacionesPorCBU(long cbu) {
        List<Movimiento> movimientos = new ArrayList<>();
        movimientos.addAll(leerMovimientosDeArchivo(NOMBRE_ARCHIVO_MOVIMIENTOS, cbu));
        movimientos.addAll(leerMovimientosDeArchivo(NOMBRE_ARCHIVO_TRANSFERENCIAS, cbu));
        return movimientos;
    }

    private List<Movimiento> leerMovimientosDeArchivo(String rutaArchivo, long cbu) {
        List<Movimiento> movimientos = new ArrayList<>();

        try (BufferedReader lector = new BufferedReader(new FileReader(rutaArchivo))) {
            String linea;
            lector.readLine();

            while ((linea = lector.readLine()) != null) {
                String[] datos = linea.split(",");

                if (datos.length < 5) {
                    System.err.println("Línea mal formada: " + linea);
                    continue;
                }

                long cbuOrigen = Long.parseLong(datos[0]);
                long cbuDestino = Long.parseLong(datos[1]);

                if (cbuOrigen == cbu || cbuDestino == cbu) {
                    Movimiento movimiento = new Movimiento();
                    movimiento.setCBU(cbuOrigen);
                    movimiento.setCBUDestino(cbuDestino);
                    movimiento.setFechaOperacion(LocalDate.parse(datos[2]));
                    movimiento.setTipoOperacion(TipoOperacion.fromString(datos[3]));
                    movimiento.setMonto(Double.parseDouble(datos[4]));
                    movimientos.add(movimiento);
                }
            }
        } catch (IOException ex) {
            System.err.println("Error al leer el archivo: " + ex.getMessage());
        }
        return movimientos;
    }
}



