package main.java.ar.edu.utn.frbb.tup.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.notNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import main.java.ar.edu.utn.frbb.tup.exception.ClienteNoEncontradoException;
import main.java.ar.edu.utn.frbb.tup.model.Cliente;
import main.java.ar.edu.utn.frbb.tup.model.Cuenta;
import main.java.ar.edu.utn.frbb.tup.persistence.ClienteDao;
import main.java.ar.edu.utn.frbb.tup.persistence.CuentaDao;
import main.java.ar.edu.utn.frbb.tup.persistence.MovimientosDao;
import main.java.ar.edu.utn.frbb.tup.persistence.TransferenciaDao;
import main.java.ar.edu.utn.frbb.tup.presentation.modelDto.CuentaDto;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CuentaServiceTest {

    @Mock
    CuentaDao cuentaDao;

    @Mock
    ClienteDao clienteDao;

    @Mock
    MovimientosDao movimientoDao;

    @Mock
    TransferenciaDao transferenciaDao;

    @InjectMocks
    CuentaService CuentaService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    public void testDarDeAltaCuentaSuccess() throws ClienteNoEncontradoException {
        CuentaDto cuentaDto = getCuentaDto();
        Cuenta cuenta = new Cuenta(cuentaDto);
        Cliente clienteExistente = new Cliente();

        when(clienteDao.findByDni(cuenta.getDniTitular())).thenReturn(clienteExistente);

        Cuenta cuentaGuardada = CuentaService.darDeAltaCuenta(cuentaDto);

        verify(clienteDao, times(1)).findByDni(cuenta.getDniTitular());
        verify(cuentaDao, times(1)).escribirEnArchivo(any(Cuenta.class));

        assertNotNull(cuentaGuardada);
    }

    @Test
    public void testDarDeAltaCuentaFail() throws ClienteNoEncontradoException {
        CuentaDto cuentaDto = getCuentaDto();
        Cuenta cuenta = new Cuenta(cuentaDto);

        when(clienteDao.findByDni(cuenta.getDniTitular())).thenReturn(null);

        assertThrows(ClienteNoEncontradoException.class, () -> CuentaService.darDeAltaCuenta(cuentaDto));

        verify(clienteDao, times(1)).findByDni(cuenta.getDniTitular());
        verify(cuentaDao, times(0)).escribirEnArchivo(any(Cuenta.class));
    }
















    public CuentaDto getCuentaDto() {
        CuentaDto cuentadto = new CuentaDto();
        cuentadto.setNombre("Uriel");
        cuentadto.setDniTitular("12345678");
        cuentadto.setTipoCuenta("AHORRO");
        cuentadto.setTipoMoneda("ARS");
        return cuentadto;
    }
    
}
