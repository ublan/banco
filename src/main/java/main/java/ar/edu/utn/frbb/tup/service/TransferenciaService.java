package main.java.ar.edu.utn.frbb.tup.service;

import main.java.ar.edu.utn.frbb.tup.exception.CuentaNoEncontradaException;
import main.java.ar.edu.utn.frbb.tup.exception.CuentaSinSaldoException;
import main.java.ar.edu.utn.frbb.tup.exception.TipoMonedasInvalidasException;
import main.java.ar.edu.utn.frbb.tup.model.Cliente;
import main.java.ar.edu.utn.frbb.tup.model.Cuenta;
import main.java.ar.edu.utn.frbb.tup.model.TipoMoneda;
import main.java.ar.edu.utn.frbb.tup.model.Transferencia;
import main.java.ar.edu.utn.frbb.tup.persistence.ClienteDao;
import main.java.ar.edu.utn.frbb.tup.persistence.CuentaDao;
import main.java.ar.edu.utn.frbb.tup.persistence.TransferenciaDao;
import main.java.ar.edu.utn.frbb.tup.presentation.modelDto.TransferenciaDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class TransferenciaService {

    @Autowired
    private BanelcoService banelcoService;

    @Autowired
    private CuentaDao cuentaDao;

    @Autowired
    private TransferenciaDao transferenciaDao;

    @Autowired
    private ClienteDao clienteDao;


    public Transferencia realizarTransferencia(TransferenciaDto transferenciaDto) throws CuentaNoEncontradaException, CuentaSinSaldoException, TipoMonedasInvalidasException{
        Transferencia transferencia = new Transferencia(transferenciaDto);

        Cuenta cuentaOrigen = cuentaDao.obtenerCuentaPorCBU(Long.parseLong(transferenciaDto.getCuentaOrigen()));
        Cuenta cuentaDestino = cuentaDao.obtenerCuentaPorCBU(Long.parseLong(transferenciaDto.getCuentaDestino()));

        if (cuentaOrigen == null) {
            throw new CuentaNoEncontradaException("La cuenta origen no existe");
        }

        if (cuentaDestino == null) {
            throw new CuentaNoEncontradaException("La cuenta destino no existe");
        }

        Cliente clienteOrigen = clienteDao.findByDni(cuentaOrigen.getDniTitular());
        Cliente clienteDestino = clienteDao.findByDni(cuentaDestino.getDniTitular());

        if (cuentaOrigen.getBalance() < transferencia.getMonto()) {
            throw new CuentaSinSaldoException("Fondos insuficientes en la cuenta origen");
        }

        if (!cuentaOrigen.getMoneda().equals(cuentaDestino.getMoneda())) {
            throw new TipoMonedasInvalidasException("La moneda de ambas cuentas no es la misma");
        }

        if (!clienteOrigen.getBanco().equals(clienteDestino.getBanco())) {
            if (!banelcoService.transferirEntreBancos(transferenciaDto)) {
                throw new IllegalArgumentException("La transferencia entre bancos fue rechazada");
            }
        }
        
        actualizarSaldos(cuentaOrigen, cuentaDestino, transferencia);

        transferenciaDao.guardarTransferencia(transferencia);

        return transferencia;
    }

    private void actualizarSaldos(Cuenta cuentaOrigen, Cuenta cuentaDestino, Transferencia transferencia) {

        double montoTotal = aplicarCargoPorTransferencia(transferencia);

        cuentaDao.borrarCuenta(cuentaOrigen.getCBU());
        cuentaDao.borrarCuenta(cuentaDestino.getCBU());

        cuentaOrigen.setBalance(cuentaOrigen.getBalance() - montoTotal);
        cuentaDestino.setBalance(cuentaDestino.getBalance() + transferencia.getMonto());

        cuentaDao.escribirEnArchivo(cuentaOrigen);
        cuentaDao.escribirEnArchivo(cuentaDestino);
    }


    private double aplicarCargoPorTransferencia(Transferencia transferencia) {
        double monto = transferencia.getMonto();
        double cargo = 0;

        if (transferencia.getMoneda().equals(TipoMoneda.ARS) && monto > 1000000) {
            cargo = monto * 0.02;
        } else if (transferencia.getMoneda().equals(TipoMoneda.USD) && monto > 5000) {
            cargo = monto * 0.005;
        }

        return monto - cargo;
    }

    public List<Transferencia> obtenerTransferenciasPorCbu(long cbu) {
        return transferenciaDao.obtenerTransferenciasPorCbu(cbu);
    }
}
