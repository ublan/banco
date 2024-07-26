package main.java.ar.edu.utn.frbb.tup.service.operaciones.ManejoCuentas;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import main.java.ar.edu.utn.frbb.tup.model.Cuenta;
import main.java.ar.edu.utn.frbb.tup.persistence.CuentaDao;

public class BorrarCuenta {
    private static final String NOMBRE_ARCHIVO = "C:\\Users\\Uriel\\Desktop\\banco\\src\\main\\java\\main\\java\\ar\\edu\\utn\\frbb\\tup\\persistence\\database\\Cuentas.txt";

    @Autowired
    CuentaDao cuentaDao;
    public Cuenta borrarCuenta(long CBU) {
        List<Cuenta> cuentas = new ArrayList<>();
        List<String> cuentasStr = new ArrayList<>();
        Cuenta cuenta = null;
        CuentaDao cuentaDao = new CuentaDao();
        try (BufferedReader lector = new BufferedReader(new FileReader(NOMBRE_ARCHIVO))) {
            String linea;
            while ((linea = lector.readLine()) != null) {
                String[] campos = linea.split(",");
                if (Long.parseLong(campos[0]) != CBU) {
                    cuentas.add(cuentaDao.parseCuentaToObjet(campos));
                    cuentasStr.add(linea);
                } else {
                    cuenta = cuentaDao.parseCuentaToObjet(campos);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("No se pudo acceder a la base de datos");
        }

        if (cuenta != null) {
            try (BufferedWriter escritor = new BufferedWriter(new FileWriter(NOMBRE_ARCHIVO))) {

                for (String cuentaStr : cuentasStr) {
                    escritor.write(cuentaStr);
                    escritor.newLine();
                }
                return cuenta;
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException("No se pudo escribir en el archivo");
            }
        } else {
            return cuenta;
        }
    }
}