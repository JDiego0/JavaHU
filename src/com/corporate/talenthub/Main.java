package com.corporate.talenthub;

import java.math.BigDecimal;

public class Main {
    public static void main(String[] args) {

        // En Java moderno, los errores NullPointerException indican exactamente qué variable es null,
        // a diferencia de Java 8 donde el mensaje era más genérico.

        // Text block JAVA moderno
        String encabezado = """
                Corporate talent HUB
                """;

        System.out.println(encabezado);

        // crear objeto empleado (usando encapsulamiento)
        Empleado emp = new Empleado();
        emp.setIdEmpleado(2);
        emp.setSalarioBase(2000000L);
        emp.setBonoMensual(new BigDecimal("200000"));
        emp.setNombre("Juan");
        emp.setActivo(true);

        // crear record
        EmpresaRecord empresa = new EmpresaRecord("TechCorp", "123456", 2010);
        System.out.println(empresa);

        // servicio
        EmpleadoService service = new EmpleadoService();

        // salario
        BigDecimal salarioFinal = service.calcularSalarioFinal(emp);
        System.out.println("Salario final: " + salarioFinal);

        // par o impar
        System.out.println("¿ID es par?: " + service.esIdPar(emp));

        // elegibilidad
        boolean elegible = service.validarElegibilidad(90, 25, 1, false);
        System.out.println("Elegible: " + elegible);

        // Null test
        emp.setNombre(null);

        /*
        Java 8:
        NullPointerException genérico

        Java moderno:
        Indica exactamente qué variable es null
        */

        // Forzamos el error para evidenciar el mensaje moderno
        try {
            System.out.println(emp.getNombre().length());
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        // comparación de objetos
        Empleado emp2 = new Empleado();

        System.out.println(emp == emp2);
        // false porque son referencias distintas en memoria
        // == compara referencias en memoria (Heap), no el contenido del objeto
    }
}