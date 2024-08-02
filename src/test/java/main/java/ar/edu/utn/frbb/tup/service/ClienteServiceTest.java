package main.java.ar.edu.utn.frbb.tup.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import main.java.ar.edu.utn.frbb.tup.exception.ClienteAlreadyExistsException;
import main.java.ar.edu.utn.frbb.tup.exception.ClienteNoEncontradoException;
import main.java.ar.edu.utn.frbb.tup.model.Cliente;
import main.java.ar.edu.utn.frbb.tup.model.Cuenta;
import main.java.ar.edu.utn.frbb.tup.persistence.ClienteDao;
import main.java.ar.edu.utn.frbb.tup.persistence.CuentaDao;
import main.java.ar.edu.utn.frbb.tup.persistence.MovimientosDao;
import main.java.ar.edu.utn.frbb.tup.persistence.TransferenciaDao;
import main.java.ar.edu.utn.frbb.tup.presentation.modelDto.ClienteDto;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ClienteServiceTest {
    
    @Mock
    CuentaDao cuentaDao;

    @Mock
    ClienteDao clienteDao;

    @Mock
    MovimientosDao movimientoDao;

    @Mock
    TransferenciaDao transferenciaDao;

    @InjectMocks
    ClienteService ClienteService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    public void testDarDeAltaClienteSuccess() throws ClienteAlreadyExistsException {
        ClienteDto clienteDto = getClienteDto();
        Cliente cliente = new Cliente(clienteDto);
        
        when(clienteDao.findByDni(cliente.getDni())).thenReturn(null);

        Cliente clienteCreado =  ClienteService.darDeAltaCliente(clienteDto);

        verify(clienteDao, times(1)).findByDni(cliente.getDni());
        verify(clienteDao, times(1)).crearCliente(any(Cliente.class));

        assertNotNull(clienteCreado);
    }


    @Test
    public void testDarDeAltaClienteExistente() throws ClienteAlreadyExistsException {
        ClienteDto clienteDto = getClienteDto();
        Cliente cliente = new Cliente(clienteDto);
        
        when(clienteDao.findByDni(cliente.getDni())).thenReturn(cliente);

        assertThrows(ClienteAlreadyExistsException.class, () -> ClienteService.darDeAltaCliente(clienteDto));

        verify(clienteDao, times(1)).findByDni(cliente.getDni());
        verify(clienteDao, times(0)).crearCliente(any(Cliente.class));
    }


    @Test
    public void testBorrarClienteSuccess() throws ClienteNoEncontradoException {
        ClienteDto clienteDto = getClienteDto();
        Cliente cliente = new Cliente(clienteDto);
        List<Cuenta> cuentas = List.of(new Cuenta());

        when(clienteDao.borrarCliente(cliente.getDni())).thenReturn(cliente);
        when(cuentaDao.obtonerCuentasDelCliente(cliente.getDni())).thenReturn(cuentas);

        Cliente borrado =  ClienteService.borrarCliente(cliente.getDni());

        verify(movimientoDao, times(1)).borrarMovimiento(cliente.getDni());
        verify(transferenciaDao , times(1)).borrarTransferencia(cliente.getDni());

        verify(clienteDao, times(1)).borrarCliente(cliente.getDni());
        verify(cuentaDao, times(1)).obtonerCuentasDelCliente(cliente.getDni());

        assertNotNull(borrado);
    }    
    public ClienteDto getClienteDto() {
        ClienteDto clientedto = new ClienteDto();
        clientedto.setDni("12345678");
        clientedto.setNombre("Juan");
        clientedto.setApellido("Perez");
        clientedto.setDireccion("Calle Falsa 123");
        clientedto.setBanco("Banco Nacion");
        clientedto.setFechaNacimiento("2001-01-01");
        clientedto.setTipoPersona("PERSONA_FISICA");
        return clientedto;
    }
}
