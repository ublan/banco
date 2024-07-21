package main.java.ar.edu.utn.frbb.tup.service.operaciones.ManejoCuentas;

import main.java.ar.edu.utn.frbb.tup.model.Cuenta;
import main.java.ar.edu.utn.frbb.tup.persistence.CuentaDao;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class MostrarCuentas {
    private static final String NOMBRE_ARCHIVO = "C:\\Users\\Uriel\\Desktop\\banco\\src\\main\\java\\main\\java\\ar\\edu\\utn\\frbb\\tup\\persistence\\database\\Cuentas.txt";

    public List<String> mostrarCuentas(String dni) {
        List<String> cuentasEncontradas = new ArrayList<>();

        try (BufferedReader lector = new BufferedReader(new FileReader(NOMBRE_ARCHIVO))) {
            String linea;
            while ((linea = lector.readLine()) != null) {
                String[] campos = linea.split(",");
                if (campos.length > 6 && campos[6].trim().equals(dni.trim())) {
                    cuentasEncontradas.add(linea);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return cuentasEncontradas;
    }

    public List<String> mostrarTodasLasCuentas() {
        List<String> cuentas = new ArrayList<>();

        try (BufferedReader lector = new BufferedReader(new FileReader(NOMBRE_ARCHIVO))) {
            String linea;
            while ((linea = lector.readLine()) != null) {
                cuentas.add(linea);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return cuentas;
    }
}

