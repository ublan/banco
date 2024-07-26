package main.java.ar.edu.utn.frbb.tup.service.operaciones.ManejoCuentas;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import main.java.ar.edu.utn.frbb.tup.model.Cuenta;
import main.java.ar.edu.utn.frbb.tup.persistence.CuentaDao;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@Component
public class MostrarCuentas {

    private static final String NOMBRE_ARCHIVO = "C:\\Users\\Uriel\\Desktop\\banco\\src\\main\\java\\main\\java\\ar\\edu\\utn\\frbb\\tup\\persistence\\database\\Cuentas.txt";

    public List<Cuenta> mostrarCuentas(long dni) {
        List<Cuenta> cuentasEncontradas = new ArrayList<>();
        CuentaDao cuentaDao = new CuentaDao();

        try (BufferedReader lector = new BufferedReader(new FileReader(NOMBRE_ARCHIVO))) {
            String linea;
            while ((linea = lector.readLine()) != null) {
                String[] campos = linea.split(",");
                if (Long.parseLong(campos[6]) == dni) {
                    cuentasEncontradas.add(cuentaDao.parseCuentaToObjet(campos));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return cuentasEncontradas;
    }

    public List<Cuenta> mostrarTodasLasCuentas() {
        List<Cuenta> cuentas = new ArrayList<>();
        CuentaDao cuentaDao = new CuentaDao();

        try (BufferedReader lector = new BufferedReader(new FileReader(NOMBRE_ARCHIVO))) {
            String linea;
            while ((linea = lector.readLine()) != null) {
                String[] campos = linea.split(",");
                cuentas.add(cuentaDao.parseCuentaToObjet(campos));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return cuentas;
    }
}

