package main.java.ar.edu.utn.frbb.tup.service.operaciones.ManejoClientes;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class MostrarCliente {

    private static final String NOMBRE_ARCHIVO = "C:\\Users\\Uriel\\Desktop\\banco\\src\\main\\java\\main\\java\\ar\\edu\\utn\\frbb\\tup\\persistence\\database\\Clientes.txt";

    public static void mostrarCliente(String dni) {
        List<String> clientes = new ArrayList<>();

        try (BufferedReader lector = new BufferedReader(new FileReader(NOMBRE_ARCHIVO))) {
            String linea;
            while ((linea = lector.readLine()) != null) {
                String[] campos = linea.split(",");
                if (campos.length > 1 && campos[3].trim().equals(dni.trim())) {
                    clientes.add(linea);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
// codigo inecesario
        
    }
}