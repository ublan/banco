package main.java.ar.edu.utn.frbb.tup.presentation.validator;

import java.time.LocalDate;

import org.springframework.stereotype.Component;
import main.java.ar.edu.utn.frbb.tup.model.TipoPersona;
import main.java.ar.edu.utn.frbb.tup.presentation.modelDto.ClienteDto;

@Component
public class ClienteValidator {

    public void validarCliente(ClienteDto clientedto) {

        if (clientedto.getDni() == 0) {
            throw new IllegalArgumentException("El dni del titular de la cuenta es obligatorio");
        }
    
        if (clientedto.getDni() < 10000000 || clientedto.getDni() > 99999999) {
            throw new IllegalArgumentException("El dni del titular de la cuenta debe ser de 8 digitos");
        }

        if (clientedto.getNombre().isEmpty() || clientedto.getNombre() == null) {
            throw new IllegalArgumentException("El nombre del cliente es obligatorio");
        }
    
        if (clientedto.getApellido().isEmpty() || clientedto.getApellido() == null) {
            throw new IllegalArgumentException("El apellido del cliente es obligatorio");
        }

        if (clientedto.getDireccion().isEmpty() || clientedto.getDireccion() == null) {
            throw new IllegalArgumentException("La dirección del cliente es obligatoria");    
        }

        if (clientedto.getBanco().isEmpty() || clientedto.getBanco() == null) {
            throw new IllegalArgumentException("El banco del cliente es obligatorio");     
        }

        if (clientedto.getFechaNacimiento() == null) {
            throw new IllegalArgumentException("La fecha de nacimiento no puede ser nula");
        }

        if (clientedto.getTipoPersona() == null) {
            throw new IllegalArgumentException("El tipo de persona no puede ser nulo");
            
        }

    }

}
