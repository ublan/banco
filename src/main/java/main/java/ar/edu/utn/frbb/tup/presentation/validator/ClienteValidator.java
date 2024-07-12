package main.java.ar.edu.utn.frbb.tup.presentation.validator;

import java.time.LocalDate;

import main.java.ar.edu.utn.frbb.tup.model.TipoPersona;
import main.java.ar.edu.utn.frbb.tup.presentation.modelDto.ClienteDto;

public class ClienteValidator {

        public void validarCliente(ClienteDto clientedto) {

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

        if (clientedto.getEdad() < 18) {
            throw new IllegalArgumentException("El cliente debe ser mayor a 18 años");
        }

        if (clientedto.getFechaNacimiento().isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("La fecha de nacimiento no puede ser en el futuro");
        }

        if (clientedto.getTipoPersona() == null) {
            throw new IllegalArgumentException("El tipo de persona no puede ser nulo");
            
        }
        if (!clientedto.getTipoPersona().equals(TipoPersona.PERSONA_FISICA) && !clientedto.getTipoPersona().equals(TipoPersona.PERSONA_JURIDICA)) {
            throw new IllegalArgumentException("El tipo de persona no puede ser nulo");
            }

    }

}
