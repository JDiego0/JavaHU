package com.corporate.talenthub;

public class NotasArquitectura {
    /*
    JAVA 8 vs JAVA 17/21

    Java 8:
    - Código más extenso
    - No hay Records
    - Errores de null poco claros

    Java 17/21:
    - Records (menos código)
    - Text Blocks
    - Mejor rendimiento JVM
    - Helpful NullPointerException

    - Virtual Threads (Project Loom):
      Permiten manejar miles de hilos ligeros con bajo costo.
      Mejoran el rendimiento en aplicaciones concurrentes
      (APIs, microservicios, operaciones I/O).

    JVM Y GARBAGE COLLECTOR

    - Los objetos se crean en el Heap
    - Si no tienen referencias → el GC los elimina
    - Libera memoria automáticamente

    Mejoras modernas:
    - Algoritmos como G1, ZGC y Shenandoah
    - Menores pausas (low latency)
    - Mejor rendimiento en grandes volúmenes de memoria

    LABORATORIO: NULL POINTER EXCEPTION

    En Java 8:
    - NullPointerException sin detalle del origen

    En Java 17/21:
    - Mensajes detallados indicando qué variable es null

    Nota:
    - Se provocó intencionalmente un NullPointerException para evidenciar
      el comportamiento mejorado en Java moderno y facilitar el diagnóstico.
    */
}

class User {
    String name;
}

class NullExample {
    public static void main(String[] args) {
        User user = null;

        // Provoca el error para evidenciar Helpful NullPointerException
        System.out.println(user.name);
    }
}

/*
Resultado esperado en Java 17/21:

Exception in thread "main" java.lang.NullPointerException:
Cannot read field "name" because "user" is null
*/