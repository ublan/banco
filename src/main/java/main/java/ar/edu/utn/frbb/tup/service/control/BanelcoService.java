package main.java.ar.edu.utn.frbb.tup.service.control;

import main.java.ar.edu.utn.frbb.tup.presentation.modelDto.TransferenciaDto;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class BanelcoService {

    public boolean transferirEntreBancos(TransferenciaDto transferenciaDto) {
        Random random = new Random();
        int numeroAleatorio = random.nextInt();
        return numeroAleatorio % 2 == 0;
    }
    
}
