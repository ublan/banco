package main.java.ar.edu.utn.frbb.tup.service.operaciones.ManejoTransferencias;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import main.java.ar.edu.utn.frbb.tup.persistence.TransferenciaDao;
import main.java.ar.edu.utn.frbb.tup.presentation.modelDto.TransferenciaDto;
import main.java.ar.edu.utn.frbb.tup.presentation.validator.TransferenciaValidator;
import main.java.ar.edu.utn.frbb.tup.service.control.BanelcoService;
import main.java.ar.edu.utn.frbb.tup.model.TipoMoneda;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class RealizarTransferencia {

    private static final String CUENTAS_FILE = "C:\\Users\\Uriel\\Desktop\\banco\\src\\main\\java\\main\\java\\ar\\edu\\utn\\frbb\\tup\\persistence\\database\\Cuentas.txt";
    private static final String CLIENTES_FILE = "C:\\Users\\Uriel\\Desktop\\banco\\src\\main\\java\\main\\java\\ar\\edu\\utn\\frbb\\tup\\persistence\\database\\Clientes.txt";
    private static final String TRANSFERENCIAS_FILE = "C:\\Users\\Uriel\\Desktop\\banco\\src\\main\\java\\main\\java\\ar\\edu\\utn\\frbb\\tup\\persistence\\database\\Transferencias.txt";

    @Autowired
    private TransferenciaValidator transferenciaValidator;

    @Autowired
    private BanelcoService banelcoService;

    @Autowired
    private TransferenciaDao transferenciaDao;

    public void realizarTransferencia(TransferenciaDto transferenciaDto) throws IOException {
        transferenciaValidator.validarTransferencia(transferenciaDto);

        String bancoOrigen = obtenerBancoDeCuenta(transferenciaDto.getCuentaOrigen());
        String bancoDestino = obtenerBancoDeCuenta(transferenciaDto.getCuentaDestino());

        if (bancoOrigen == null || bancoDestino == null) {
            throw new IllegalArgumentException("Una de las cuentas no existe");
        }

        if (!bancoOrigen.equals(bancoDestino)) {
            if (!banelcoService.transferirEntreBancos(transferenciaDto)) {
                throw new IllegalArgumentException("La transferencia entre bancos fue rechazada");
            }
        }

        if (!validarMoneda(transferenciaDto.getCuentaOrigen(), transferenciaDto.getCuentaDestino())) {
            throw new IllegalArgumentException("La moneda de ambas cuentas no es la misma");
        }

        if (!tieneFondosSuficientes(transferenciaDto)) {
            throw new IllegalArgumentException("Fondos insuficientes en la cuenta origen");
        }

        aplicarCargoPorTransferencia(transferenciaDto);
        actualizarSaldos(transferenciaDto);
        guardarTransferencia(transferenciaDto);
    }

    private String obtenerBancoDeCuenta(long cbu) throws IOException {
        try (BufferedReader lector = new BufferedReader(new FileReader(CUENTAS_FILE))) {
            String linea;
            lector.readLine(); // Leer la línea de encabezado
            while ((linea = lector.readLine()) != null) {
                String[] datos = linea.split(",");
                long cbuCuenta = Long.parseLong(datos[0]);
                if (cbuCuenta == cbu) {
                    long dniTitular = Long.parseLong(datos[6]);
                    return obtenerBancoDeCliente(dniTitular);
                }
            }
        }
        return null;
    }

    private String obtenerBancoDeCliente(long dni) throws IOException {
        try (BufferedReader lector = new BufferedReader(new FileReader(CLIENTES_FILE))) {
            String linea;
            lector.readLine(); // Leer la línea de encabezado
            while ((linea = lector.readLine()) != null) {
                String[] datos = linea.split(",");
                long dniCliente = Long.parseLong(datos[0]);
                if (dniCliente == dni) {
                    return datos[6];
                }
            }
        }
        return null;
    }

    private boolean validarMoneda(long cuentaOrigen, long cuentaDestino) throws IOException {
        String monedaOrigen = obtenerMonedaDeCuenta(cuentaOrigen);
        String monedaDestino = obtenerMonedaDeCuenta(cuentaDestino);

        return monedaOrigen != null && monedaOrigen.equals(monedaDestino);
    }

    private String obtenerMonedaDeCuenta(long cbu) throws IOException {
        try (BufferedReader lector = new BufferedReader(new FileReader(CUENTAS_FILE))) {
            String linea;
            lector.readLine(); // Leer la línea de encabezado
            while ((linea = lector.readLine()) != null) {
                String[] datos = linea.split(",");
                long cbuCuenta = Long.parseLong(datos[0]);
                if (cbuCuenta == cbu) {
                    return datos[4];
                }
            }
        }
        return null;
    }

    private boolean tieneFondosSuficientes(TransferenciaDto transferenciaDto) throws IOException {
        double balanceCuentaOrigen = obtenerBalanceDeCuenta(transferenciaDto.getCuentaOrigen());
        return balanceCuentaOrigen >= transferenciaDto.getMonto();
    }

    private double obtenerBalanceDeCuenta(long cbu) throws IOException {
        try (BufferedReader lector = new BufferedReader(new FileReader(CUENTAS_FILE))) {
            String linea;
            lector.readLine(); // Leer la línea de encabezado
            while ((linea = lector.readLine()) != null) {
                String[] datos = linea.split(",");
                long cbuCuenta = Long.parseLong(datos[0]);
                if (cbuCuenta == cbu) {
                    return Double.parseDouble(datos[3]);
                }
            }
        }
        return 0;
    }

    private void aplicarCargoPorTransferencia(TransferenciaDto transferenciaDto) {
        double monto = transferenciaDto.getMonto();
        double cargo = 0;

        if (transferenciaDto.getMoneda().equals(TipoMoneda.ARS) && monto > 1000000) {
            cargo = monto * 0.02;
        } else if (transferenciaDto.getMoneda().equals(TipoMoneda.USD) && monto > 5000) {
            cargo = monto * 0.005;
        }

        transferenciaDto.setMonto(monto - cargo);
    }

    private void actualizarSaldos(TransferenciaDto transferenciaDto) throws IOException {
        List<String> cuentasActualizadas = new ArrayList<>();
        try (BufferedReader lector = new BufferedReader(new FileReader(CUENTAS_FILE))) {
            String linea;
            lector.readLine(); // Leer la línea de encabezado
            while ((linea = lector.readLine()) != null) {
                String[] datos = linea.split(",");
                long cbuCuenta = Long.parseLong(datos[0]);

                if (cbuCuenta == transferenciaDto.getCuentaOrigen()) {
                    double balance = Double.parseDouble(datos[3]);
                    balance -= transferenciaDto.getMonto();
                    datos[3] = String.valueOf(balance);
                }

                if (cbuCuenta == transferenciaDto.getCuentaDestino()) {
                    double balance = Double.parseDouble(datos[3]);
                    balance += transferenciaDto.getMonto();
                    datos[3] = String.valueOf(balance);
                }

                cuentasActualizadas.add(String.join(",", datos));
            }
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(CUENTAS_FILE))) {
            writer.write("CBU,nombre,tipoCuenta,balance,moneda,fechaCreacion,titularDni\n"); // Escribir encabezado
            for (String cuenta : cuentasActualizadas) {
                writer.write(cuenta);
                writer.newLine();
            }
        }
    }

    private void guardarTransferencia(TransferenciaDto transferenciaDto) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(TRANSFERENCIAS_FILE, true))) {
            String transferencia = transferenciaDto.getCuentaOrigen() + "," +
                                   transferenciaDto.getCuentaDestino() + "," +
                                   transferenciaDto.getFecha() + "," +
                                   transferenciaDto.getMonto() + "," +
                                   transferenciaDto.getTipo() + "," +
                                   transferenciaDto.getMoneda();
            writer.write(transferencia);
            writer.newLine();
        }
    }
}
