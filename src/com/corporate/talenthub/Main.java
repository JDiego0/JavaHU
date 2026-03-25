package com.corporate.talenthub;

public class Main {
    public static void main(String[] args) {

        // En Java moderno, los errores NullPointerException indican exactamente qué variable es null,
        // a diferencia de Java 8 donde el mensaje era más genérico.

        // Text block JAVA moderno
        String encabezado = """
                Corporate talent HUB
                """;

        System.out.println(encabezado);

        //crear objeto empleado
        Empleado emp = new Empleado();
        emp.idEmpleado = 2;
        emp.salarioBase = 2000000L;
        emp.bonoMensual = 200000f;
        emp.nombre = "Juan";
        emp.esActivo = true;

        //crear record
        EmpresaRecord empresa = new EmpresaRecord("TechCorp","123456",2010);
        System.out.println(empresa);

        //servicio
        EmpleadoService service = new EmpleadoService();

        //salario
        double salarioFinal =  service.calcularSalarioFinal(emp);
        System.out.println("Salario final: " + salarioFinal);

        //par o impar
        System.out.println("¿ID es par?: " + service.esIdPar(emp));

        //Elegibilidad
        boolean elegible = service.validarElegibilidad(90,25,1,false);
        System.out.println("Elegible: "+ elegible);

        //Null test
        emp.nombre = null;
        /*
        Java 8:
        NullPointerException genérico

        Java moderno:
        Indica exactamente qué variable es null
        */

        //comparacion de objetos
        Empleado emp2 =  new Empleado();

        System.out.println(emp == emp2);
        //false porque son referencias distintas en memoria
        // == compara referencias en memoria (Heap), no el contenido del objeto
    }
}