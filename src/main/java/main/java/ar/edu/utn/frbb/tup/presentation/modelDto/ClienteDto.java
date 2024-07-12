package main.java.ar.edu.utn.frbb.tup.presentation.modelDto;

import main.java.ar.edu.utn.frbb.tup.model.TipoPersona;

public class ClienteDto extends PersonaDto {
    private String banco;
    private TipoPersona tipoPersona;

    public String getBanco() {
        return banco;
    }

    public void setBanco(String banco) {
        this.banco = banco;
    }

    public TipoPersona getTipoPersona() {
        return tipoPersona;
    }

    public void setTipoPersona(TipoPersona tipoPersona) {
        this.tipoPersona = tipoPersona;
    }
}


