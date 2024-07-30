package main.java.ar.edu.utn.frbb.tup.persistence;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import main.java.ar.edu.utn.frbb.tup.model.*;

@Repository

public class CuentaDao {
    private static final String NOMBRE_ARCHIVO = "src\\main\\java\\main\\java\\ar\\edu\\utn\\frbb\\tup\\persistence\\DataBase\\Cuentas.txt"; //cambiar aca 
    private static final String NOMBRE_ARCHIVO1 = "src\\main\\java\\main\\java\\ar\\edu\\utn\\frbb\\tup\\persistence\\database\\Clientes.txt"; // cambiar


        public boolean verificarExistenciaCuenta(long cbu) {
        List<Cuenta> cuentas = leerCuentasDeArchivo();
        for (Cuenta cuenta : cuentas) {
            if (cuenta.getCBU() == cbu) {
                return true;
            }
        }
        return false;
    }

    public Cuenta obtenerCuentaPorCBU(long cbu) {
        List<Cuenta> cuentas = leerCuentasDeArchivo();
        for (Cuenta cuenta : cuentas) {
            if (cuenta.getCBU() == cbu) {
                return cuenta;
            }
        }
        return null;
    }

    public void actualizarBalanceCuenta(long cbu, double nuevoBalance) {
        List<Cuenta> cuentas = leerCuentasDeArchivo();
        try (BufferedWriter escritor = new BufferedWriter(new FileWriter(NOMBRE_ARCHIVO))) {
            escritor.write("CBU,nombre,tipoCuenta,balance,moneda,fechaCreacion,titularDni");
            escritor.newLine();
            
            for (Cuenta cuenta : cuentas) {
                if (cuenta.getCBU() == cbu) {
                    cuenta.setBalance((int) nuevoBalance); // Convertir de double a int
                }
                escritor.write(cuenta.getCBU() + ",");
                escritor.write(cuenta.getNombre() + ",");
                escritor.write(cuenta.getTipoCuenta() + ",");
                escritor.write(cuenta.getBalance() + ",");
                escritor.write(cuenta.getMoneda() + ",");
                escritor.write(cuenta.getFechaCreacion().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) + ",");
                escritor.write(cuenta.getDniTitular() + "");
                escritor.newLine();
            }
        } catch (IOException ex) {
            System.err.println("Error al actualizar el balance en el archivo: " + ex.getMessage());
        }
    }
    
    public static void escribirEnArchivo(Cuenta cuenta) {
        boolean archivoNuevo = !(new File(NOMBRE_ARCHIVO).exists());

        try (BufferedWriter escritor = new BufferedWriter(new FileWriter(NOMBRE_ARCHIVO, true))) {
            // Si el archivo es nuevo, escribir la cabecera
            if (archivoNuevo) {
                escritor.write("CBU,nombre,tipoCuenta,balance,moneda,fechaCreacion,titularDni");
                escritor.newLine();
            }

            escritor.write(cuenta.getCBU() + ",");
            escritor.write(cuenta.getNombre() + ",");
            escritor.write(cuenta.getTipoCuenta() + ",");
            escritor.write(cuenta.getBalance() + ",");
            escritor.write(cuenta.getMoneda() + ",");
            escritor.write(cuenta.getFechaCreacion().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) + ",");
            escritor.write(cuenta.getDniTitular()+"");
            escritor.newLine();

            System.out.println("Datos de la cuenta guardados en " + NOMBRE_ARCHIVO + " correctamente.");
        } catch (IOException ex) {
            System.err.println("Error al escribir en el archivo: " + ex.getMessage());
        }
    }

    public static Cliente buscarClientePorDni(long dni) {
        try (BufferedReader lector = new BufferedReader(new FileReader(NOMBRE_ARCHIVO1))) {
            String linea;
            while ((linea = lector.readLine()) != null) {
                String[] campos = linea.split(",");
                if (campos.length > 0) {
                    try {
                        long dniCliente = Long.parseLong(campos[0]);
                        if (dniCliente == dni) {
                            Cliente cliente = new Cliente();
                            cliente.setDni(Long.parseLong(campos[0]));
                            cliente.setNombre(campos[1]);
                            cliente.setApellido(campos[2]);
                            return cliente;
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Error al convertir el primer campo a long: " + campos[0]);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null; 
    }

    private List<Cuenta> leerCuentasDeArchivo() {
        List<Cuenta> cuentas = new ArrayList<>();
        try (BufferedReader lector = new BufferedReader(new FileReader(NOMBRE_ARCHIVO))) {
            String linea;
            lector.readLine(); // Leer la cabecera
    
            while ((linea = lector.readLine()) != null) {
                String[] datos = linea.split(",");
                if (datos.length < 7) {
                    System.err.println("Línea mal formada: " + linea);
                    continue;
                }
                
                Cuenta cuenta = new Cuenta();
                cuenta.setCBU(Long.parseLong(datos[0]));
                cuenta.setNombre(datos[1]);
                cuenta.setTipoCuenta(TipoCuenta.valueOf(datos[2]));
                cuenta.setBalance(Double.parseDouble(datos[3])); 
                cuenta.setMoneda(TipoMoneda.valueOf(datos[4]));
                cuenta.setFechaCreacion(LocalDateTime.parse(datos[5], DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                cuenta.setDniTitular(Long.parseLong(datos[6]));
    
                cuentas.add(cuenta);
            }
        } catch (IOException ex) {
            System.err.println("Error al leer el archivo de cuentas: " + ex.getMessage());
        }
        return cuentas;
    }
    
    public Cuenta parseCuentaToObjet(String[] datos){
        Cuenta cuenta = new Cuenta();
        cuenta.setCBU(Long.parseLong(datos[0]));
        cuenta.setNombre(datos[1]);
        cuenta.setTipoCuenta(TipoCuenta.valueOf(datos[2]));
        cuenta.setBalance(Double.parseDouble(datos[3])); 
        cuenta.setMoneda(TipoMoneda.valueOf(datos[4]));
        cuenta.setFechaCreacion(LocalDateTime.parse(datos[5], DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        cuenta.setDniTitular(Long.parseLong(datos[6]));
        return cuenta;
    }

}
