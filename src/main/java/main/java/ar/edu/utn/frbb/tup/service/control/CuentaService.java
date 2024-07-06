package main.java.ar.edu.utn.frbb.tup.service.control;

import main.java.ar.edu.utn.frbb.tup.model.Cuenta;
import main.java.ar.edu.utn.frbb.tup.persistence.SummitCuenta;
import main.java.ar.edu.utn.frbb.tup.persistence.SummitCliente;
import main.java.ar.edu.utn.frbb.tup.exception.ClienteAlreadyExistsException;
import main.java.ar.edu.utn.frbb.tup.exception.InvalidCurrencyException;
import main.java.ar.edu.utn.frbb.tup.model.Cliente;
import java.time.LocalDateTime;
import org.springframework.stereotype.Service;

@Service
public class CuentaService {

    public void darDeAltaCuenta(Cuenta cuenta, String dni) throws ClienteAlreadyExistsException, InvalidCurrencyException {
        Cliente clienteExistente = SummitCliente.findByDni(dni);
        if (clienteExistente == null) {
            throw new IllegalArgumentException("El titular de la cuenta no existe");
        }
        cuenta.setTitular(clienteExistente);

        // Validaciones de cuenta

        if (cuenta.getNombre() == null || cuenta.getNombre().isEmpty()) {
            throw new IllegalArgumentException("El nombre de la cuenta es obligatorio");
        }

        if (cuenta.getTipoCuenta() == null || (!cuenta.getTipoCuenta().equals("CORRIENTE") || !cuenta.getTipoCuenta().equals("AHORRO"))) {
            throw new IllegalArgumentException("El tipo de cuenta debe ser CORRIENTE o AHORRO");
        }

        if (cuenta.getBalance() < 0) {
            throw new IllegalArgumentException("El balance no puede ser negativo");
        }

        if (cuenta.getMoneda() == null || cuenta.getMoneda().isEmpty()) {
            throw new IllegalArgumentException("La moneda de la cuenta es obligatoria");
        }

        if (!cuenta.getMoneda().equals("ARS") || !cuenta.getMoneda().equals("USD")) {
            throw new InvalidCurrencyException("La moneda " + cuenta.getMoneda() + " no es aceptada. Solo se aceptan ARS y USD.");
        }

        // Asignar CBU y fecha de creación
        cuenta.setCBU();
        cuenta.setFechaCreacion(LocalDateTime.now());

        // Guardar cuenta en la "base de datos" (archivo)
        SummitCuenta.escribirEnArchivo(cuenta);
    }
}


