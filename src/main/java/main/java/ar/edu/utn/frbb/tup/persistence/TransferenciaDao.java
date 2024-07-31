package main.java.ar.edu.utn.frbb.tup.persistence;

import org.springframework.stereotype.Repository;
import main.java.ar.edu.utn.frbb.tup.presentation.modelDto.TransferenciaDto;
import main.java.ar.edu.utn.frbb.tup.model.TipoMoneda;
import main.java.ar.edu.utn.frbb.tup.model.Transferencia;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Repository
public class TransferenciaDao {

    private static final String ARCHIVO_TRANSFERENCIAS = "src\\main\\java\\main\\java\\ar\\edu\\utn\\frbb\\tup\\persistence\\database\\Transferencias.txt";

    public List<Transferencia> obtenerTransferenciasPorCbu(long cbu) {
        List<Transferencia> transacciones = new ArrayList<>();
        try (BufferedReader lector = new BufferedReader(new FileReader(ARCHIVO_TRANSFERENCIAS))) {
            String linea;
            lector.readLine(); // Leer la línea de encabezado, si la hay
            while ((linea = lector.readLine()) != null) {
                String[] datos = linea.split(",");
                long cuentaOrigen = Long.parseLong(datos[0]);
                long cuentaDestino = Long.parseLong(datos[1]);
                LocalDate fecha = LocalDate.parse(datos[2]);
                double monto = Double.parseDouble(datos[3]);
                String tipo = datos[4];
                TipoMoneda moneda = TipoMoneda.fromString(datos[5]);
                String descripcionBreve = tipo.equals("CREDITO") ? "Transferencia entrante" : "Transferencia saliente";

                if (cuentaOrigen == cbu || cuentaDestino == cbu) {
                    Transferencia transaccion = new Transferencia();
                    transaccion.setFecha(fecha);
                    transaccion.setTipo(tipo);
                    transaccion.setCuentaOrigen(cuentaOrigen);
                    transaccion.setCuentaDestino(cuentaDestino);
                    transaccion.setMonto(monto);
                    transaccion.setMoneda(moneda);
                    transaccion.setDescripcionBreve(descripcionBreve);
                    transacciones.add(transaccion);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return transacciones;
    }

    public void guardarTransferencia(Transferencia transferencia) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ARCHIVO_TRANSFERENCIAS, true))) {
            String transf = transferencia.getCuentaOrigen() + "," +
                            transferencia.getCuentaDestino() + "," +
                            transferencia.getFecha() + "," +
                            transferencia.getMonto() + "," +
                            transferencia.getTipo() + "," +
                            transferencia.getMoneda();
            writer.write(transf);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void borrarTransferencia(long CBU) {
        List<String> transferenciasStr = new ArrayList<>();

        try (BufferedReader lector = new BufferedReader(new FileReader(ARCHIVO_TRANSFERENCIAS))) {
            String linea = lector.readLine();
            transferenciasStr.add(linea);
            while ((linea = lector.readLine()) != null) {
                String[] campos = linea.split(",");
                if (Long.parseLong(campos[0]) != CBU) {
                    transferenciasStr.add(linea);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("No se pudo acceder a la base de datos");
        }

        if (!transferenciasStr.isEmpty()) {
            try (BufferedWriter escritor = new BufferedWriter(new FileWriter(ARCHIVO_TRANSFERENCIAS))) {
                for (String transferenciaStr : transferenciasStr) {
                    escritor.write(transferenciaStr);
                    escritor.newLine();
                }
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException("No se pudo escribir en el archivo");
            }
        } 
    }
}


