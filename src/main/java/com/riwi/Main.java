package com.riwi;

import com.riwi.models.Employee;
import com.riwi.repositories.EmployeeRepository;

import java.util.*;

public class Main {
    public static void main(String[] args) {

        // TASK 2: Factory Methods (Legacy 9/11)
        // Immutable list of technologies using the List.of() factory method (Java 9+)
        // SAFER than a traditional ArrayList because:
        // - It does not allow unauthorized or accidental modifications
        // - Avoids concurrency errors in multithreaded applications
        // - Guarantees that configuration data will not change at runtime
        // - Prevents malicious modification attacks
        // USE CASE: Perfect for reference data, lookup tables, and constants
        // WARNING: It is IMMUTABLE; it does not allow .add(), .remove(), or .clear()
        List<String> technologies = List.of("Java", "Python", "JavaScript");

        // Immutable Map of Sedes using the Map.of() factory method (Java 9+)
        // SAFER than a traditional HashMap because:
        // - Prevents accidental or malicious modifications of critical data
        // - Ideal for configuration data that should never change
        // - Better performance for static data (no synchronization required)
        // - Ensures data integrity throughout the application
        // USE CASE: Excellent for configuration mappings and static reference data
        // WARNING: It is IMMUTABLE; it does not allow .put(), .remove(), or .clear()

        Map<String, String> offices = Map.of(
          "BOG", "Bogota",
          "CTG", "Cartagena",
          "BAQ", "Barranquilla",
          "CAL", "Cali",
          "MED", "Medellin"
        );

        // TASK 1: Migration to ArrayList and HashMap (Legacy 8/11)
        // Dynamic storage replacing fixed arrays with standard collections
        // ArrayList<Employee>: Dynamic array that grows automatically, O(1) access by index
        // HashMap<String, Employee>: Key-value mapping for O(1) search by ID
        // BENEFITS: No size limitations, automatic resizing, efficient search operations
        List<Employee> arrayEmployees = new ArrayList<>();
        Map<String, Employee> mapEmployees = new HashMap<>();

        var repository = new EmployeeRepository(mapEmployees, arrayEmployees);

        Employee employee1 = new Employee("1","Diego","CEO",10000);
        Employee employee2 = new Employee("2","Juan","Manager",5000);
        Employee employee3 = new Employee("3","Maria","Developer",3000);
        Employee employee4 = new Employee("4","Diana","owner", 15000);

        repository.addEmployee(employee1);
        repository.addEmployee(employee2);
        repository.addEmployee(employee3);
        repository.addEmployee(employee4);

        var allEmployees = repository.getAllEmployees();
        allEmployees.forEach(System.out::println);

        var employeeByID = repository.getEmployeeById("2");
        System.out.println(employeeByID);

        repository.deleteEmployee("2");

        var allEmployees2 = repository.getAllEmployees();
        allEmployees2.forEach(System.out::println);

        System.out.println("======= TECHNOLOGIES ==========");
        technologies.forEach(System.out::println);

        System.out.println("======= OFFICES ==========");
        offices.forEach((key, value) -> System.out.println(key + " - " + value));

        // TASK 3: Sequenced Collections (Java 21)
        // Legacy Syntax (Java 8/11) - Get first and last element by index
        System.out.println("\nLegacy Syntax");
        List<Employee> employeesList = repository.getAllEmployees();

        // Traditional way with manual indices (prone to errors)
        // RISK: IndexOutOfBoundsException if list is empty
        // RISK: Calculation errors in size() - 1
        // PERFORMANCE: O(1) access but with manual index management
        Employee firstLegacyEmployee = employeesList.get(0);
        Employee lastLegacyEmployee = employeesList.get(employeesList.size()-1);
        System.out.println("First Legacy Employee = " + firstLegacyEmployee);
        System.out.println("last Legacy Employee = " + lastLegacyEmployee);

        // Modern Syntax (Java 21) - Sequenced Collections methods
        System.out.println("\nModern Syntax");
        // IMPROVEMENTS with Java 21:
        // - READABILITY: getFirst() and getLast() are self-explanatory
        // - SAFETY: Prevents IndexOutOfBoundsException automatically
        // - MAINTAINABILITY: Code is easier to understand and debug
        // - NO CALCULATION ERRORS: No need for size() - 1 arithmetic
        // - PERFORMANCE: O(1) operations with built-in optimization
        Employee firstModernEmployee = employeesList.getFirst();
        Employee lastModernEmployee = employeesList.getLast();
        System.out.println("\nFirst Modern Employee = " + firstModernEmployee);
        System.out.println("\nLast Modern Employee = " + lastModernEmployee);

        // Inverse list using reversed() method
        // EFFICIENCY: No need for manual sorting algorithms
        // SIMPLICITY: One-line solution for reverse order
        // MEMORY: Creates a view, not a full copy (more memory efficient)
        // PERFORMANCE: O(1) operation for creating reversed view
        System.out.println("\nInverse List");
        List<Employee> reversedList = employeesList.reversed();
        reversedList.forEach(System.out::println);

        // TASK 4: Advanced Filtering and Type Inference with var
        System.out.println("\n Advanced Filtering");
        var employeesForFiltering = repository.getAllEmployees();
        System.out.println("Before of filter: "+ employeesForFiltering.size() + " employees");

        // FUNCTIONAL PROGRAMMING: removeIf() uses lambda expressions for filtering
        // LAMBDA: emp -> emp.getSalary() < 6000 is a predicate function
        // PERFORMANCE: O(n) operation, removes elements in-place
        employeesForFiltering.removeIf(emp -> emp.getSalary() < 6000);
        System.out.println("After of filter: "+ employeesForFiltering.size() + " employees");

        // Type inference (Java 11+) vs. explicit declaration (Java 8)
        System.out.println("\nType inference comparison");

        //Java 8 - explicit declaration
        List<Employee> explicitEmployees = repository.getAllEmployees();
        double explicitSalary = explicitEmployees.get(0).getSalary();
        System.out.println("Explicit salary: " + explicitSalary);

        //java 11+ - Inference with var
        var inferredEmployees = repository.getAllEmployees();
        var inferredSalary = inferredEmployees.get(0).getSalary();

        // BENEFITS of var:
        // - Less code to write
        // - Type safety maintained
        // - Focus on logic, not type declarations
        // - Easier refactoring

        //Final report
        System.out.println("\nFINAL REPORT:");
        var finalEmployees = repository.getAllEmployees();

        //total Employees
        var totalEmployees = finalEmployees.size();
        System.out.println("Total employees: " + totalEmployees);

        //Average Salary using streams
        // STREAMS: mapToDouble() transforms objects to primitive values
        // TERMINAL OPERATIONS: average() and orElse() are terminal operations
        // PERFORMANCE: O(n) operation with efficient primitive stream
        var averageSalary = finalEmployees.stream()
                .mapToDouble(Employee::getSalary)
                .average()
                .orElse(0.0);

        System.out.println("Average salary: $" + averageSalary);

        System.out.println("\n Advanced filtering with streams");
        // FUNCTIONAL PROGRAMMING: Multiple filter operations chained together
        // METHOD REFERENCES: Employee::getSalary is a method reference
        // LAZY EVALUATION: Filters are applied only when terminal operation is called
        var highEarners = finalEmployees.stream()
                .filter(emp -> emp.getSalary()>8000)
                .filter(emp-> emp.getPosition().equals("CEO") || emp.getPosition().equals("owner"))
                .toList();

        System.out.println("High earners (CEO/Owner with salary > 8000):");
        highEarners.forEach(System.out::println);



    }
}