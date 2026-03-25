package com.corporate.talenthub;

public class EmpleadoService {

    public double calcularSalarioFinal(Empleado emp){
        /*
        Orden de ejecución:
        1. Paréntesis
        2. Multiplicación
        3. Suma / Resta
        */

        return (emp.salarioBase + (emp.bonoMensual * 1.10)) - (emp.salarioBase * 0.05);

    }

    public boolean esIdPar(Empleado emp){
        return emp.idEmpleado % 2 == 0;
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

    public void actualizarBono(Empleado emp, float incremento){
        emp.bonoMensual += incremento;
    }

}
