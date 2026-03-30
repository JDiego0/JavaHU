package com.corporate.talenthub;

import java.math.BigDecimal;

public class EmpleadoService {

    public BigDecimal calcularSalarioFinal(Empleado emp){
        /*
        Orden de ejecución:
        1. Paréntesis
        2. Multiplicación
        3. Suma / Resta
        */

        BigDecimal salarioBase = BigDecimal.valueOf(emp.getSalarioBase());

        BigDecimal bonoConIncremento = emp.getBonoMensual()
                .multiply(BigDecimal.valueOf(1.10));

        BigDecimal retencion = salarioBase
                .multiply(BigDecimal.valueOf(0.05));

        return salarioBase
                .add(bonoConIncremento)
                .subtract(retencion);
    }

    public boolean esIdPar(Empleado emp){
        return emp.getIdEmpleado() % 2 == 0;
    }

    public boolean validarElegibilidad(int puntajeTest, int edad, int idSede, boolean esActivo){
        /*
        Precedencia:
        1. !
        2. &&
        3. ||
        */
        return (puntajeTest > 85 && edad < 30) || (idSede == 1 && !esActivo);
    }

    public void actualizarBono(Empleado emp, BigDecimal incremento){
        emp.setBonoMensual(
                emp.getBonoMensual().add(incremento)
        );
    }

}