package main.java.ar.edu.utn.frbb.tup.service.operaciones.ManejoCuentas;

import main.java.ar.edu.utn.frbb.tup.model.Cuenta;
import main.java.ar.edu.utn.frbb.tup.persistence.CuentaDao;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;

@Component
public class CrearCuenta {

    public void crearCuenta(Cuenta cuenta) {
        // Asignar CBU y fecha de creación
        cuenta.generarCBU();
        cuenta.setFechaCreacion(LocalDateTime.now());
        // Guardar cuenta en la "base de datos" (archivo)
        CuentaDao.escribirEnArchivo(cuenta);
    }
}
