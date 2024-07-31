package main.java.ar.edu.utn.frbb.tup.presentation;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import main.java.ar.edu.utn.frbb.tup.presentation.modelDto.ClienteDto;
import main.java.ar.edu.utn.frbb.tup.presentation.validator.ClienteValidator;

public class ClienteValidatorTest {

    @Test
    public void test_validarCliente() {
        ClienteDto clienteDto = new ClienteDto();
        clienteDto.setDni(12345678);
        clienteDto.setNombre("Juan");
        clienteDto.setApellido("Perez");
        clienteDto.setDireccion("Calle Falsa 123");
        clienteDto.setBanco("Banco Nacion");
        clienteDto.setFechaNacimiento("01/01/1980");
        clienteDto.setTipoPersona("Fisica");

        ClienteValidator validator = new ClienteValidator();
        assertDoesNotThrow(() -> validator.validarCliente(clienteDto));
    }

    @Test
    public void test_dni_zero() {
        ClienteDto clienteDto = new ClienteDto();
        clienteDto.setDni(0);
        clienteDto.setNombre("Juan");
        clienteDto.setApellido("Perez");
        clienteDto.setDireccion("Calle Falsa 123");
        clienteDto.setBanco("Banco Nacion");
        clienteDto.setFechaNacimiento("01/01/1980");
        clienteDto.setTipoPersona("Fisica");

        ClienteValidator validator = new ClienteValidator();
        Exception exception = assertThrows(IllegalArgumentException.class, () -> validator.validarCliente(clienteDto));
        assertEquals("El dni del titular de la cuenta es obligatorio", exception.getMessage());
    }
}
