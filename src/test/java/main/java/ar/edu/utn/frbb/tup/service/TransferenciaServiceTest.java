package main.java.ar.edu.utn.frbb.tup.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import main.java.ar.edu.utn.frbb.tup.exception.CuentaNoEncontradaException;
import main.java.ar.edu.utn.frbb.tup.exception.CuentaSinSaldoException;
import main.java.ar.edu.utn.frbb.tup.exception.TipoMonedasInvalidasException;
import main.java.ar.edu.utn.frbb.tup.model.Cliente;
import main.java.ar.edu.utn.frbb.tup.model.Cuenta;
import main.java.ar.edu.utn.frbb.tup.model.TipoCuenta;
import main.java.ar.edu.utn.frbb.tup.model.TipoMoneda;
import main.java.ar.edu.utn.frbb.tup.model.TipoPersona;
import main.java.ar.edu.utn.frbb.tup.model.Transferencia;
import main.java.ar.edu.utn.frbb.tup.persistence.ClienteDao;
import main.java.ar.edu.utn.frbb.tup.persistence.CuentaDao;
import main.java.ar.edu.utn.frbb.tup.persistence.TransferenciaDao;
import main.java.ar.edu.utn.frbb.tup.presentation.modelDto.ClienteDto;
import main.java.ar.edu.utn.frbb.tup.presentation.modelDto.CuentaDto;
import main.java.ar.edu.utn.frbb.tup.presentation.modelDto.TransferenciaDto;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TransferenciaServiceTest {
    

    @Mock
    BanelcoService banelcoService;

    @Mock
    CuentaDao cuentaDao;

    @Mock
    TransferenciaDao transferenciaDao;

    @Mock
    ClienteDao clienteDao;

    @InjectMocks
    TransferenciaService transferenciaService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    public void testRealizarTransferenciaSuccess() throws CuentaNoEncontradaException, CuentaSinSaldoException, TipoMonedasInvalidasException {
        Cliente clienteOrigen = getCliente();
        Cliente clienteDestino = getCliente();

        Cuenta cuentaOrigen = new Cuenta();
        cuentaOrigen.setCBU(123456789);
        cuentaOrigen.setBalance(2000);
        cuentaOrigen.setMoneda(TipoMoneda.ARS);
        cuentaOrigen.setDniTitular(12345678);

        Cuenta cuentaDestino = new Cuenta();
        cuentaDestino.setCBU(987654321);
        cuentaDestino.setBalance(500);
        cuentaDestino.setMoneda(TipoMoneda.ARS);
        cuentaDestino.setDniTitular(87654321);

        when(cuentaDao.obtenerCuentaPorCBU(cuentaOrigen.getDniTitular())).thenReturn(cuentaOrigen);
        when(cuentaDao.obtenerCuentaPorCBU(cuentaDestino.getDniTitular())).thenReturn(cuentaDestino);

        when(clienteDao.findByDni(cuentaOrigen.getDniTitular())).thenReturn(clienteOrigen);
        when(clienteDao.findByDni(cuentaDestino.getDniTitular())).thenReturn(clienteDestino);

        TransferenciaDto transferenciaDto = getTransferenciaDto();

        Transferencia transferencia = transferenciaService.realizarTransferencia(transferenciaDto);

        verify(cuentaDao,times(2)).obtenerCuentaPorCBU(any(Long.class));
        verify(clienteDao,times(2)).findByDni(any(Long.class));
        verify(cuentaDao,times(2)).borrarCuenta(any(Long.class));
        verify(cuentaDao,times(2)).escribirEnArchivo(any(Cuenta.class));
        verify(transferenciaDao,times(1)).guardarTransferencia(any(Transferencia.class));
        

        assertNotNull(transferencia);
    }

    public Cuenta getCuenta() {
        Cuenta cuenta = new Cuenta();
        cuenta.setNombre("Uriel");
        cuenta.setCBU(123456789);
        cuenta.setBalance(2000);
        cuenta.setDniTitular(12345678);
        cuenta.setTipoCuenta(TipoCuenta.AHORRO);
        cuenta.setMoneda(TipoMoneda.ARS);
        return cuenta;
    }

    public Cliente getCliente() {
        Cliente cliente = new Cliente();
        cliente.setDni(12345678);
        cliente.setNombre("Juan");
        cliente.setApellido("Perez");
        cliente.setDireccion("Calle Falsa 123");
        cliente.setBanco("Banco Nacion");
        cliente.setFechaNacimiento(LocalDate.of(2001, 1, 1));
        cliente.setTipoPersona(TipoPersona.PERSONA_FISICA);
        return cliente;
    }

    public TransferenciaDto getTransferenciaDto() {
        TransferenciaDto transferenciaDto = new TransferenciaDto();
        transferenciaDto.setTipoTransferencia("DEBITO");
        transferenciaDto.setCuentaOrigen("12345678");
        transferenciaDto.setCuentaDestino("87654321");
        transferenciaDto.setMonto(1000.0);
        transferenciaDto.setMoneda("ARS");
        transferenciaDto.setDescripcionBreve("Test de transferencia");
        return transferenciaDto;
    }

}
