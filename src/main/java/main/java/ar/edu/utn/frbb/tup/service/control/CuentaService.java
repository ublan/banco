package main.java.ar.edu.utn.frbb.tup.service.control;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import main.java.ar.edu.utn.frbb.tup.model.*;
import main.java.ar.edu.utn.frbb.tup.persistence.*;
import main.java.ar.edu.utn.frbb.tup.exception.*;

public class CuentaService {
    
    private static final String NOMBRE_ARCHIVO = "C:\\Users\\Uriel\\Desktop\\banco\\tup2024-master\\src\\main\\java\\ar\\edu\\utn\\frbb\\tup\\persistence\\DataBase\\Cuentas.txt"; //cambiar aca 

    public void darDeAltaCuenta(Cuenta cuenta) throws CuentaAlreadyExistsException, InvalidDataException {
        // Validar CBU
        if (cuenta.getCBU() == null || !cuenta.getCBU().matches("\\d{22}")) {
            throw new InvalidDataException("CBU inválido");
        }
        
        // Verificar si el CBU ya existe
        if (isCBUExists(cuenta.getCBU())) {
            throw new CuentaAlreadyExistsException("El CBU ya existe");
        }

        // Validar Titular
        if (cuenta.getTitular() == null || cuenta.getTitular().getDni() == null) {
            throw new InvalidDataException("Titular inválido");
        }
        
        Cliente titular = SummitCuenta.buscarClientePorDni(Long.parseLong(cuenta.getTitular().getDni()));
        if (titular == null) {
            throw new InvalidDataException("El titular no existe");
        }

        // Validar Nombre de la Cuenta
        if (cuenta.getNombre() == null || cuenta.getNombre().isEmpty()) {
            throw new InvalidDataException("Nombre de cuenta inválido");
        }

        // Validar Tipo de Cuenta
        if (cuenta.getTipoCuenta() == null || (!cuenta.getTipoCuenta().equals("CORRIENTE") && !cuenta.getTipoCuenta().equals("AHORRO"))) {
            throw new InvalidDataException("Tipo de cuenta inválido");
        }

        // Validar Moneda
        if (cuenta.getMoneda() == null || (!cuenta.getMoneda().equals("ARS") && !cuenta.getMoneda().equals("USD"))) {
            throw new InvalidDataException("Moneda inválida");
        }

        // Validar Balance
        if (cuenta.getBalance() < 0) {
            throw new InvalidDataException("Balance inválido");
        }

        // Guardar la cuenta en el archivo
        SummitCuenta.escribirEnArchivo(cuenta);
    }

    private boolean isCBUExists(String CBU) {
        try {
            List<String> lines = Files.readAllLines(Paths.get(NOMBRE_ARCHIVO));
            for (String line : lines) {
                if (line.split(",")[0].equals(CBU)) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}

