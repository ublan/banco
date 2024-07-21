package main.java.ar.edu.utn.frbb.tup.service.control;

import main.java.ar.edu.utn.frbb.tup.exception.ClienteNotFoundException;
import main.java.ar.edu.utn.frbb.tup.model.Cuenta;
import main.java.ar.edu.utn.frbb.tup.model.Cliente;
import main.java.ar.edu.utn.frbb.tup.presentation.validator.CuentaValidator;
import main.java.ar.edu.utn.frbb.tup.service.operaciones.ManejoCuentas.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CuentaService {

    @Autowired
    private CuentaValidator cuentaValidator;

    @Autowired
    private CrearCuenta crearCuenta;

    @Autowired
    private MostrarCuentas mostrarCuentas;

    @Autowired
    private ClienteService clienteService;

    public void darDeAltaCuenta(Cuenta cuenta, String dni) throws ClienteNotFoundException {
        Cliente clienteExistente = clienteService.mostrarCliente(dni);
        if (clienteExistente == null) {
            throw new IllegalArgumentException("El titular de la cuenta no existe");
        }
        cuenta.setTitular(clienteExistente);
        cuentaValidator.validarCuenta(cuenta.toDto());
        crearCuenta.crearCuenta(cuenta);
    }

    public void borrarCuenta(String cbu) {
        BorrarCuenta.borrarCuenta(cbu);
    }

    public List<String> mostrarCuenta(String dni) {
        return mostrarCuentas.mostrarCuentas(dni);
    }

    public List<String> obtenerTodasLasCuentas() {
        return mostrarCuentas.mostrarTodasLasCuentas();
    }
}





