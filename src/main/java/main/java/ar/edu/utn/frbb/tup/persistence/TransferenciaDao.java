package main.java.ar.edu.utn.frbb.tup.persistence;

import org.springframework.stereotype.Repository;
import main.java.ar.edu.utn.frbb.tup.presentation.modelDto.TransferenciaDto;
import main.java.ar.edu.utn.frbb.tup.model.TipoMoneda;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Repository
public class TransferenciaDao {

    private static final String NOMBRE_ARCHIVO_TRANSFERENCIAS = "C:\\Users\\Uriel\\Desktop\\banco\\src\\main\\java\\main\\java\\ar\\edu\\utn\\frbb\\tup\\persistence\\database\\Transferencias.txt";

    public List<TransferenciaDto> obtenerTransferenciasPorCbu(long cbu) {
        List<TransferenciaDto> transacciones = new ArrayList<>();
        try (BufferedReader lector = new BufferedReader(new FileReader(NOMBRE_ARCHIVO_TRANSFERENCIAS))) {
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
                    TransferenciaDto transaccion = new TransferenciaDto();
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
}

