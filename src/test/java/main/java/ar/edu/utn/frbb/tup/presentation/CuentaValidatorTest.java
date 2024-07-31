package main.java.ar.edu.utn.frbb.tup.presentation;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import main.java.ar.edu.utn.frbb.tup.presentation.modelDto.CuentaDto;
import main.java.ar.edu.utn.frbb.tup.presentation.validator.CuentaValidator;

public class CuentaValidatorTest {

    @Test
    public void test_validarCuenta() {
        CuentaValidator validator = new CuentaValidator();
        CuentaDto cuentaDto = new CuentaDto();
        cuentaDto.setDniTitular(12345678);
        cuentaDto.setNombre("John Doe");
        cuentaDto.setTipoCuenta("Ahorro");
        cuentaDto.setMoneda("ARS");
    
        assertDoesNotThrow(() -> validator.validarCuenta(cuentaDto));
    }

    @Test
    public void test_dni_titular_cero_exception() {
        CuentaValidator validator = new CuentaValidator();
        CuentaDto cuentaDto = new CuentaDto();
        cuentaDto.setDniTitular(0);
        cuentaDto.setNombre("John Doe");
        cuentaDto.setTipoCuenta("Ahorro");
        cuentaDto.setMoneda("ARS");
    
        Exception exception = assertThrows(IllegalArgumentException.class, () -> validator.validarCuenta(cuentaDto));
        assertEquals("El dni del titular de la cuenta es obligatorio", exception.getMessage());
    }
    
}
