package com.riwi.talent;

import com.riwi.talent.view.EmployeeView;

/**
 * Punto de entrada principal del Sistema de Gestión de Empleados.
 *
 * ARQUITECTURA MVC:
 * ┌─────────────────────────────────────────────────────────────┐
 * │  com.riwi.talent                                            │
 * │  ├── model/                                                 │
 * │  │   ├── Employee.java         (Record - entidad inmutable)  │
 * │  │   ├── EmployeeReport.java   (Record - reporte complejo)   │
 * │  │   └── dao/                                               │
 * │  │       ├── EmpleadoDAO.java  (Interfaz DAO)               │
 * │  │       └── impl/                                          │
 * │  │           └── EmpleadoDAOImpl.java (Implementación JDBC) │
 * │  ├── controller/                                            │
 * │  │   └── EmployeeController.java (Mediador MVC)             │
 * │  ├── view/                                                  │
 * │  │   └── EmployeeView.java    (Interfaz de consola)         │
 * │  └── util/                                                  │
 * │      └── DatabaseConnection.java (Utilidad JDBC)            │
 * └─────────────────────────────────────────────────────────────┘
 *
 * VENTAJAS DE LA ARQUITECTURA MODERNA (Java 17 LTS) vs LEGACY:
 * - Records (Java 16+): Reemplazan POJOs con código inmutable y sin boilerplate.
 * - try-with-resources (Java 7+, estándar en 17): Cierre automático de conexiones JDBC.
 * - Text Blocks (Java 15+): SQL y reportes multilínea legibles.
 * - Sealed classes (Java 17): Control exhaustivo de jerarquías de tipos.
 * - Pattern Matching (Java 16+): instanceof sin cast manual.
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("=== SISTEMA DE GESTIÓN DE EMPLEADOS (Patrón MVC) ===");
        System.out.println("Iniciando aplicación con arquitectura Modelo-Vista-Controlador...\n");

        // Inicializa e inicia la aplicación MVC desde la capa Vista
        EmployeeView application = new EmployeeView();
        application.start();
    }
}
