package com.riwi;

import java.util.List;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== System report performance ===");
        
        // TASK 1: Sealed vs. Open Inheritance Demonstration
        System.out.println("\n=== TASK 1: SEALED vs OPEN INHERITANCE ===");
        
        // Modern Style (Java 17+) - Sealed Inheritance
        System.out.println("\n--- Sealed Inheritance (Modern Style) ---");
        Employee employee = new Employee();
        employee.setId("E001");
        employee.setName("Juan Pérez");
        System.out.println("Employee: " + employee.getName() + " (ID: " + employee.getId() + ")");
        System.out.println("Only Employee and ExternConsultant can extend Person");
        
        // Legacy Style (Java 8/11) - Open Inheritance
        System.out.println("\n--- Open Inheritance (Legacy Style) ---");
        ManagerLegacy manager = new ManagerLegacy(1001, "Ana García", "TI");
        System.out.println("Manager: " + manager.getDisplayName());
        System.out.println("Any class can extend PersonLegacy");
        
        // Demonstrate sealed classes security
        System.out.println("\n--- Benefits of Sealed Inheritance ---");
        demonstrateSealedInheritanceBenefits();
        
        // TASK 2: Modern syntax demonstration with records (Java 17+)
        System.out.println("\n=== TASK 2: RECORDS vs POJO ===");
        System.out.println("\n--- Reports with Records (Modern Syntax) ---");
        
        // Creating immutable reports using the record
        PerformanceReport report1 = new PerformanceReport(101, 85.5, "Good overall performance");
        PerformanceReport report2 = new PerformanceReport(102, 92.0, "Excellent work on projects");
        PerformanceReport report3 = PerformanceReport.createEndOfMonthReport(103, 78.3, "Meets basic expectations");
        
        // Demonstrate immutability and data access
        System.out.println("Report 1: " + report1);
        System.out.println("Employee ID: " + report1.idEmployee());
        System.out.println("Average: " + report1.average());
        System.out.println("Feedback: " + report1.feedback());
        System.out.println("Level: " + report1.getLevelPerformance());
        
        // Comparison with traditional POJO (Legacy Syntax)
        System.out.println("\n--- Comparison with Traditional POJO ---");
        PerformanceReportPOJO pojoReport = new PerformanceReportPOJO(101, 85.5, "Good overall performance");
        System.out.println("POJO Report: " + pojoReport.toString());
        System.out.println("Employee ID: " + pojoReport.getIdEmployee());
        System.out.println("Average: " + pojoReport.getAverage());
        
        // Application flow for end-of-month reports
        System.out.println("\n--- End-of-Month Report Generation ---");
        generateReportsEndMonth();
        
        // Demonstrate benefits of records
        System.out.println("\n--- Benefits of Records vs POJO ---");
        demostrateBenefitsRecords();
        
        // TASK 3: Polymorphism and Pattern Matching Demonstration
        System.out.println("\n=== TASK 3: POLYMORPHISM & PATTERN MATCHING ===");
        
        // Create instances
        Developer dev = new Developer("D001", "Alice Smith", "Java");
        Manager mgr = new Manager("M001", "Bob Johnson", 50000.0);
        
        // Legacy validation (Java 8/11)
        System.out.println("\n--- Legacy Validation (Manual Casting) ---");
        System.out.println("Developer: " + validateRoleLegacy(dev));
        System.out.println("Manager: " + validateRoleLegacy(mgr));
        
        // Modern validation (Java 17/21)
        System.out.println("\n--- Modern Validation (Pattern Matching) ---");
        System.out.println("Developer: " + validateRoleModern(dev));
        System.out.println("Manager: " + validateRoleModern(mgr));
        
        // Show benefits
        System.out.println("\n--- Benefits of Pattern Matching ---");
        System.out.println("• Less code: No manual casting required");
        System.out.println("• Safer: Eliminates ClassCastException risk");
        System.out.println("• Cleaner: Direct access to methods/fields");
    }
    
    private static void demonstrateSealedInheritanceBenefits() {
        System.out.println("1. Full control: Only permitted classes can extend");
        System.out.println("2. Compiler safety: Thoroughly verifies in pattern matching");
        System.out.println("3. Maintenance: You know all possible subtypes");
        System.out.println("4. Safe evolution: Prevents Liskov principle violations");
        System.out.println("5. Predictable API: No unexpected extensions");
        
        System.out.println("\nPractical comparison:");
        System.out.println("• Person (sealed): Only Employee, ExternConsultant");
        System.out.println("• PersonLegacy (open): ManagerLegacy, any other class");
    }

    private static void generateReportsEndMonth() {
        List<PerformanceReport> reports = new ArrayList<>();
        
        // End-of-month report generation simulation
        reports.add(PerformanceReport.createEndOfMonthReport(201, 88.7, "Consistent and reliable"));
        reports.add(PerformanceReport.createEndOfMonthReport(202, 95.2, "Exceeded all goals"));
        reports.add(PerformanceReport.createEndOfMonthReport(203, 72.1, "Needs additional training"));
        
        System.out.println("Generated reports:");
        for (PerformanceReport report : reports) {
            System.out.printf("Employee %d: %.1f - %s (%s)\n", 
                report.idEmployee(),
                report.average(),
                report.feedback(),
                report.getLevelPerformance());
        }
    }
    
    private static void demostrateBenefitsRecords() {
        System.out.println("1. Guaranteed immutability: Records are immutable by default");
        System.out.println("2. Concise code: Automatic constructor, getters, equals(), hashCode(), toString()");
        System.out.println("3. Type safety: Validation in compact constructor");
        System.out.println("4. Less boilerplate: ~20 lines vs ~60 lines in POJO");
        System.out.println("5. Security: No setters, data cannot be accidentally modified");
        
        // Demonstrate immutability
        PerformanceReport original = new PerformanceReport(301, 85.0, "Original");
        // To "modify" a record, you must create a new instance
        PerformanceReport modificado = new PerformanceReport(original.idEmployee(), original.average(), "Modified");
        
        System.out.println("\nOriginal record: " + original);
        System.out.println("Modified record: " + modificado);
        System.out.println("Records do not allow direct modification - you must create a new instance");
    }

    public static String validateRoleLegacy(Person p){
        if(p instanceof Developer){
            Developer dev = (Developer) p; //manual Casting
            return "Developer: " + dev.getMainlanguage();
        }
        if(p instanceof  Manager){
            Manager mgr = (Manager) p;
            return "Manager: $ " + mgr.getMonthlyBudget();
        }
        return "Unknown role";
    }

    public static String validateRoleModern(Person p){
        if(p instanceof Developer dev){ // Pattern matching
            return "Developer: " + dev.getMainlanguage();
        }
        if(p instanceof  Manager mgr){
            return "Manager: $ " + mgr.getMonthlyBudget();
        }
        return "Unknown role";
    }

}