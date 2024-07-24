package main.java.ar.edu.utn.frbb.tup.service.operaciones.ManejoTransferencias;

import main.java.ar.edu.utn.frbb.tup.persistence.TransferenciaDao;
import main.java.ar.edu.utn.frbb.tup.presentation.modelDto.TransferenciaDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class VerTransferencias {

    @Autowired
    private TransferenciaDao transferenciaDao;

    public List<TransferenciaDto> obtenerTransferenciasPorCbu(long cbu) {
        return transferenciaDao.obtenerTransferenciasPorCbu(cbu);
    }
}
