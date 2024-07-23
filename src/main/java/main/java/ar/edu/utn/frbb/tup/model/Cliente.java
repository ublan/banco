package main.java.ar.edu.utn.frbb.tup.model;

import main.java.ar.edu.utn.frbb.tup.presentation.modelDto.ClienteDto;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class Cliente extends Persona {

    private TipoPersona tipoPersona;
    private String banco;
    private LocalDate fechaAlta;
    private Set<Cuenta> cuentas = new HashSet<>();

    // Constructor que acepta un ClienteDto
    public Cliente(ClienteDto clienteDto) {
        super(clienteDto.getDni(), clienteDto.getNombre(), clienteDto.getApellido(), clienteDto.getDireccion(), clienteDto.getFechaNacimiento());
        this.tipoPersona = clienteDto.getTipoPersona();
        this.banco = clienteDto.getBanco();
        this.fechaAlta = LocalDate.now(); // O puedes obtener esta fecha de clienteDto si está disponible
    }

    public Cliente() {
        super(null, null, null, null, null);
    }

    public TipoPersona getTipoPersona() {
        return tipoPersona;
    }

    public void setTipoPersona(TipoPersona tipoPersona) {
        this.tipoPersona = tipoPersona;
    }

    public String getBanco() {
        return banco;
    }

    public void setBanco(String banco) {
        this.banco = banco;
    }

    public LocalDate getFechaAlta() {
        return fechaAlta;
    }

    public void setFechaAlta(LocalDate fechaAlta) {
        this.fechaAlta = fechaAlta;
    }

    public Set<Cuenta> getCuentas() {
        return cuentas;
    }

    public void addCuenta(Cuenta cuenta) {
        this.cuentas.add(cuenta);
        cuenta.setTitular(this);
    }

    public boolean tieneCuenta(TipoCuenta tipoCuenta, String moneda) {
        for (Cuenta cuenta : cuentas) {
            if (tipoCuenta.equals(cuenta.getTipoCuenta()) && moneda.equals(cuenta.getMoneda())) {
                return true;
            }
        }
        return false;
    }

    // Método para convertir Cliente en ClienteDto
    public ClienteDto toDto() {
        ClienteDto dto = new ClienteDto();
        dto.setDni(this.getDni());
        dto.setNombre(this.getNombre());
        dto.setApellido(this.getApellido());
        dto.setDireccion(this.getDireccion());
        dto.setFechaNacimiento(this.getFechaNacimiento());
        dto.setTipoPersona(this.tipoPersona);
        dto.setBanco(this.banco);
        return dto;
    }


}



