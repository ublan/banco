package main.java.ar.edu.utn.frbb.tup.service.control;

import main.java.ar.edu.utn.frbb.tup.exception.ClienteNoEncontradoException;
import main.java.ar.edu.utn.frbb.tup.exception.CuentaNoEncontradaException;
import main.java.ar.edu.utn.frbb.tup.model.Cuenta;
import main.java.ar.edu.utn.frbb.tup.model.Cliente;
import main.java.ar.edu.utn.frbb.tup.service.operaciones.ManejoCuentas.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import main.java.ar.edu.utn.frbb.tup.persistence.ClienteDao;
import main.java.ar.edu.utn.frbb.tup.persistence.CuentaDao;
import main.java.ar.edu.utn.frbb.tup.presentation.modelDto.CuentaDto;
import main.java.ar.edu.utn.frbb.tup.service.operaciones.ManejoCuentas.BorrarCuenta;

import java.util.List;

@Service
public class CuentaService {

    @Autowired
    private MostrarCuentas mostrarCuentas;

    @Autowired
    private ClienteDao clienteDao;

    @Autowired
    private BorrarCuenta borrarCuenta;

    public Cuenta darDeAltaCuenta(CuentaDto cuentaDto) throws ClienteNoEncontradoException {
        Cuenta cuenta = new Cuenta(cuentaDto);
        Cliente clienteExistente = clienteDao.findByDni(cuenta.getDniTitular());
        if (clienteExistente == null) {
            throw new ClienteNoEncontradoException("El titular de la cuenta no existe");
        }
        CuentaDao.escribirEnArchivo(cuenta);
        return cuenta;
    }

    public Cuenta borrarCuenta(long cbu) throws CuentaNoEncontradaException {
        Cuenta cuenta = borrarCuenta.borrarCuenta(cbu);
        if (cuenta == null) {
            throw new  CuentaNoEncontradaException("No se encontro la cuenta con cbu: " + cbu); 
        }
        return cuenta; 
    }

    public List<Cuenta> mostrarCuenta(long dni)throws CuentaNoEncontradaException, ClienteNoEncontradoException {
        Cliente clienteExistente = clienteDao.findByDni(dni);
        if (clienteExistente == null) {
            throw new ClienteNoEncontradoException("El titular de la cuenta no existe");
        }
        List<Cuenta> cuentas = mostrarCuentas.mostrarCuentas(dni);
        if (cuentas.isEmpty()) {
            throw new  CuentaNoEncontradaException("No se encontro el cliente con dni: " + dni); 
        }
        return cuentas;
    }

    public List<Cuenta> obtenerTodasLasCuentas() throws CuentaNoEncontradaException {
        List<Cuenta> cuentas = mostrarCuentas.mostrarTodasLasCuentas();
        if (cuentas.isEmpty()) {
            throw new  CuentaNoEncontradaException("No se encontraron cuentas"); 
        }
        return cuentas;
    }
}





