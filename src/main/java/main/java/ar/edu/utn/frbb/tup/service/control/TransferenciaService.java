package main.java.ar.edu.utn.frbb.tup.service.control;

import main.java.ar.edu.utn.frbb.tup.presentation.modelDto.TransferenciaDto;
import main.java.ar.edu.utn.frbb.tup.service.operaciones.ManejoTransferencias.VerTransferencias;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransferenciaService {

    @Autowired
    private VerTransferencias verTransferencias;

    public List<TransferenciaDto> obtenerTransferenciasPorCbu(long cbu) {
        return verTransferencias.obtenerTransferenciasPorCbu(cbu);
    }
}
