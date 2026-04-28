package com.riwi.talent.view;

import com.riwi.talent.controller.EmployeeController;
import com.riwi.talent.model.Employee;
import com.riwi.talent.model.EmployeeReport;

import java.util.List;
import java.util.Scanner;

/**
 * View layer for Employee Management System.
 * Handles all user interaction through console interface.
 * 
 * This class follows MVC pattern by:
 * - ONLY handling user input/output (Scanner usage)
 * - Delegating business logic to Controller
 * - Displaying data returned by Controller
 * - NO direct database operations
 */
public class EmployeeView {
    
    private final EmployeeController controller;
    private final Scanner scanner;
    
    /**
     * Constructor initializes controller and scanner
     */
    public EmployeeView() {
        this.controller = new EmployeeController();
        this.scanner = new Scanner(System.in);
    }
    
    /**
     * Main application loop - displays menu and handles user choices
     */
    public void start() {
        System.out.println("=== EMPLOYEE MANAGEMENT SYSTEM (MVC Pattern) ===");
        System.out.println("Welcome to the Employee Management System!");
        
        boolean running = true;
        while (running) {
            displayMenu();
            int choice = getIntInput("Enter your choice (0-12): ", 0, 12);
            
            switch (choice) {
                case 0:
                    running = false;
                    System.out.println("Thank you for using Employee Management System. Goodbye!");
                    break;
                case 1:
                    createEmployee();
                    break;
                case 2:
                    viewEmployeeById();
                    break;
                case 3:
                    viewAllEmployees();
                    break;
                case 4:
                    viewActiveEmployees();
                    break;
                case 5:
                    updateEmployee();
                    break;
                case 6:
                    deactivateEmployee();
                    break;
                case 7:
                    deleteEmployee();
                    break;
                case 8:
                    searchByDepartment();
                    break;
                case 9:
                    searchByName();
                    break;
                case 10:
                    viewStatistics();
                    break;
                case 11:
                    demonstrateCRUD();
                    break;
                case 12:
                    generateConsolidatedReport();
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
            
            if (running) {
                System.out.println("\nPress Enter to continue...");
                scanner.nextLine();
            }
        }
        
        scanner.close();
    }
    
    /**
     * Displays the main menu options
     */
    private void displayMenu() {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("EMPLOYEE MANAGEMENT MENU");
        System.out.println("=".repeat(50));
        System.out.println("1. Create New Employee");
        System.out.println("2. View Employee by ID");
        System.out.println("3. View All Employees");
        System.out.println("4. View Active Employees Only");
        System.out.println("5. Update Employee");
        System.out.println("6. Deactivate Employee (Soft Delete)");
        System.out.println("7. Delete Employee (Hard Delete)");
        System.out.println("8. Search by Department");
        System.out.println("9. Search by Name");
        System.out.println("10. View Statistics");
        System.out.println("11. Demonstrate CRUD Operations");
        System.out.println("12. Generate Consolidated Report (Records + Text Blocks)");
        System.out.println("0. Exit");
        System.out.println("=".repeat(50));
    }
    
    /**
     * Handles creating a new employee
     */
    private void createEmployee() {
        System.out.println("\n--- CREATE NEW EMPLOYEE ---");
        
        String name = getStringInput("Enter employee name: ");
        String email = getEmailInput("Enter employee email: ");
        String department = getStringInput("Enter department: ");
        Double salary = getDoubleInput("Enter salary: ", 0.0, Double.MAX_VALUE);
        
        Employee created = controller.createEmployee(name, email, department, salary);
        
        if (created != null) {
            System.out.println("✅ Employee created successfully!");
            displayEmployee(created);
        } else {
            System.out.println("❌ Failed to create employee.");
        }
    }
    
    /**
     * Handles viewing employee by ID
     */
    private void viewEmployeeById() {
        System.out.println("\n--- VIEW EMPLOYEE BY ID ---");
        
        Long id = getLongInput("Enter employee ID: ");
        Employee employee = controller.getEmployeeById(id);
        
        if (employee != null) {
            System.out.println("✅ Employee found:");
            displayEmployee(employee);
        } else {
            System.out.println("❌ Employee not found.");
        }
    }
    
    /**
     * Handles viewing all employees
     */
    private void viewAllEmployees() {
        System.out.println("\n--- ALL EMPLOYEES ---");
        
        List<Employee> employees = controller.getAllEmployees();
        
        if (employees.isEmpty()) {
            System.out.println("No employees found in the database.");
        } else {
            System.out.println("Total employees: " + employees.size());
            displayEmployeeList(employees);
        }
    }
    
    /**
     * Handles viewing active employees only
     */
    private void viewActiveEmployees() {
        System.out.println("\n--- ACTIVE EMPLOYEES ---");
        
        List<Employee> employees = controller.getActiveEmployees();
        
        if (employees.isEmpty()) {
            System.out.println("No active employees found.");
        } else {
            System.out.println("Active employees: " + employees.size());
            displayEmployeeList(employees);
        }
    }
    
    /**
     * Handles updating an employee
     */
    private void updateEmployee() {
        System.out.println("\n--- UPDATE EMPLOYEE ---");
        
        Long id = getLongInput("Enter employee ID to update: ");
        Employee existing = controller.getEmployeeById(id);
        
        if (existing == null) {
            System.out.println("❌ Employee not found.");
            return;
        }
        
        System.out.println("Current employee details:");
        displayEmployee(existing);
        
        System.out.println("\nEnter new details (press Enter to keep current value):");
        
        String name = getStringInputWithDefault("Name [" + existing.name() + "]: ", existing.name());
        String email = getEmailInputWithDefault("Email [" + existing.email() + "]: ", existing.email());
        String department = getStringInputWithDefault("Department [" + existing.department() + "]: ", existing.department());
        Double salary = getDoubleInputWithDefault("Salary [" + existing.salary() + "]: ", existing.salary());
        Boolean active = getBooleanInput("Active [" + existing.active() + "]: ", existing.active());
        
        boolean success = controller.updateEmployee(id, name, email, department, salary, active);
        
        if (success) {
            System.out.println("✅ Employee updated successfully!");
            Employee updated = controller.getEmployeeById(id);
            displayEmployee(updated);
        } else {
            System.out.println("❌ Failed to update employee.");
        }
    }
    
    /**
     * Handles deactivating an employee
     */
    private void deactivateEmployee() {
        System.out.println("\n--- DEACTIVATE EMPLOYEE ---");
        
        Long id = getLongInput("Enter employee ID to deactivate: ");
        Employee employee = controller.getEmployeeById(id);
        
        if (employee == null) {
            System.out.println("❌ Employee not found.");
            return;
        }
        
        if (!employee.active()) {
            System.out.println("Employee is already inactive.");
            return;
        }
        
        displayEmployee(employee);
        boolean confirm = getBooleanInput("Are you sure you want to deactivate this employee? ", false);
        
        if (confirm) {
            boolean success = controller.deactivateEmployee(id);
            if (success) {
                System.out.println("✅ Employee deactivated successfully!");
            } else {
                System.out.println("❌ Failed to deactivate employee.");
            }
        } else {
            System.out.println("Operation cancelled.");
        }
    }
    
    /**
     * Handles deleting an employee
     */
    private void deleteEmployee() {
        System.out.println("\n--- DELETE EMPLOYEE ---");
        
        Long id = getLongInput("Enter employee ID to delete: ");
        Employee employee = controller.getEmployeeById(id);
        
        if (employee == null) {
            System.out.println("❌ Employee not found.");
            return;
        }
        
        displayEmployee(employee);
        boolean confirm = getBooleanInput("⚠️  Are you sure you want to PERMANENTLY delete this employee? ", false);
        
        if (confirm) {
            boolean success = controller.deleteEmployee(id);
            if (success) {
                System.out.println("✅ Employee deleted successfully!");
            } else {
                System.out.println("❌ Failed to delete employee.");
            }
        } else {
            System.out.println("Operation cancelled.");
        }
    }
    
    /**
     * Handles searching by department
     */
    private void searchByDepartment() {
        System.out.println("\n--- SEARCH BY DEPARTMENT ---");
        
        String department = getStringInput("Enter department name: ");
        List<Employee> employees = controller.getEmployeesByDepartment(department);
        
        if (employees.isEmpty()) {
            System.out.println("No employees found in department: " + department);
        } else {
            System.out.println("Found " + employees.size() + " employees in " + department + ":");
            displayEmployeeList(employees);
        }
    }
    
    /**
     * Handles searching by name
     */
    private void searchByName() {
        System.out.println("\n--- SEARCH BY NAME ---");
        
        String name = getStringInput("Enter name (or partial name): ");
        List<Employee> employees = controller.getEmployeesByName(name);
        
        if (employees.isEmpty()) {
            System.out.println("No employees found with name containing: " + name);
        } else {
            System.out.println("Found " + employees.size() + " employees:");
            displayEmployeeList(employees);
        }
    }
    
    /**
     * Displays employee statistics
     */
    private void viewStatistics() {
        System.out.println("\n--- EMPLOYEE STATISTICS ---");
        
        int[] stats = controller.getEmployeeStatistics();
        int total = stats[0];
        int active = stats[1];
        int inactive = total - active;
        
        System.out.println("Total Employees: " + total);
        System.out.println("Active Employees: " + active);
        System.out.println("Inactive Employees: " + inactive);
        
        if (total > 0) {
            double activePercentage = (double) active / total * 100;
            System.out.printf("Active Percentage: %.1f%%\n", activePercentage);
        }
    }
    
    /**
     * Demonstrates CRUD operations
     */
    private void demonstrateCRUD() {
        System.out.println("\n--- CRUD DEMONSTRATION ---");
        System.out.println("This demonstrates the MVC pattern in action:");
        System.out.println("1. View receives user input");
        System.out.println("2. Controller processes business logic");
        System.out.println("3. Model handles data persistence");
        System.out.println("4. View displays results");
        
        // Create a sample employee
        Employee created = controller.createEmployee(
            "Demo Employee", 
            "demo@company.com", 
            "Engineering", 
            75000.0
        );
        
        if (created != null) {
            System.out.println("\n✅ Created demo employee:");
            displayEmployee(created);
            
            // Clean up
            controller.deleteEmployee(created.id());
            System.out.println("✅ Demo employee cleaned up");
        }
    }
    
    /**
     * Generates a consolidated report using EmployeeReport records and Text Blocks.
     * 
     * TASK 4 - Integration of Records in persistence (Java 17+):
     * This method demonstrates how Records combined with JDBC modern syntax
     * facilitate code maintenance vs traditional POJO classes:
     * 
     * - Records provide automatic immutability, preventing accidental data modification
     * - Factory methods (fromDatabaseRow) encapsulate complex ResultSet mapping
     * - Text Blocks allow readable, multi-line formatted output
     * - Computed properties (getFormattedSalary, getServiceLevel) are part of the Record
     * - No need for manual equals/hashCode/toString implementation
     * 
     * With POJOs, this same report would require:
     * - Manual getter/setter methods for each field
     * - External utility classes for formatting
     * - Defensive copies to ensure immutability
     * - ~3x more code for the same functionality
     */
    private void generateConsolidatedReport() {
        System.out.println("\n--- CONSOLIDATED EMPLOYEE REPORT (Records + Text Blocks) ---");
        
        List<EmployeeReport> reports = controller.getComprehensiveEmployeeReport();
        
        if (reports.isEmpty()) {
            System.out.println("No active employees found for the report.");
            return;
        }
        
        // Report header using Text Block (Java 17+)
        String header = """
            ╔══════════════════════════════════════════════════════════════════════════╗
            ║              CONSOLIDATED EMPLOYEE REPORT                               ║
            ║              Generated with Java 17+ Records & Text Blocks              ║
            ╠══════════════════════════════════════════════════════════════════════════╣
            ║  This report uses EmployeeReport records mapped from a complex          ║
            ║  SELECT query with CASE, DATEDIFF, and subqueries.                      ║
            ║  Records guarantee data immutability during transfer.                   ║
            ╚══════════════════════════════════════════════════════════════════════════╝
            """;
        System.out.println(header);
        
        // Display each employee report using Text Blocks
        for (EmployeeReport report : reports) {
            String employeeCard = """
                ┌──────────────────────────────────────────────────────────┐
                │  Employee #%d: %s
                │  Email:           %s
                │  Department:      %s (Team size: %d members)
                │  Salary:          %s [%s]
                │  Hire Date:       %s
                │  Years of Service: %d years [%s]
                │  Status:          %s
                └──────────────────────────────────────────────────────────┘
                """.formatted(
                    report.employeeId(),
                    report.employeeName(),
                    report.email(),
                    report.department(),
                    report.departmentSize(),
                    report.getFormattedSalary(),
                    report.salaryCategory(),
                    report.getFormattedHireDate(),
                    report.yearsOfService(),
                    report.getServiceLevel(),
                    report.getStatusIndicator()
                );
            System.out.println(employeeCard);
        }
        
        // Summary section using Text Block
        long totalEmployees = reports.size();
        double totalSalary = reports.stream().mapToDouble(EmployeeReport::salary).sum();
        double avgSalary = totalSalary / totalEmployees;
        long seniorCount = reports.stream().filter(r -> r.yearsOfService() >= 3).count();
        
        String summary = """
            ╔══════════════════════════════════════════════════════════════════════════╗
            ║  SUMMARY                                                               ║
            ╠══════════════════════════════════════════════════════════════════════════╣
            ║  Total Active Employees: %-5d                                          ║
            ║  Total Salary Budget:    $%,.2f                                  ║
            ║  Average Salary:         $%,.2f                                  ║
            ║  Senior Employees (3+y): %-5d                                          ║
            ╠══════════════════════════════════════════════════════════════════════════╣
            ║  Report generated using Java 17+ Records (immutable data transfer)     ║
            ║  and Text Blocks (readable multi-line formatting)                      ║
            ╚══════════════════════════════════════════════════════════════════════════╝
            """.formatted(totalEmployees, totalSalary, avgSalary, seniorCount);
        System.out.println(summary);
    }
    
    /**
     * Displays a single employee's details
     */
    private void displayEmployee(Employee employee) {
        System.out.println("-".repeat(40));
        System.out.println("ID: " + employee.id());
        System.out.println("Name: " + employee.name());
        System.out.println("Email: " + employee.email());
        System.out.println("Department: " + employee.department());
        System.out.printf("Salary: $%.2f\n", employee.salary());
        System.out.println("Hire Date: " + employee.hireDate());
        System.out.println("Status: " + (employee.active() ? "Active" : "Inactive"));
        System.out.println("-".repeat(40));
    }
    
    /**
     * Displays a list of employees
     */
    private void displayEmployeeList(List<Employee> employees) {
        System.out.println("-".repeat(80));
        System.out.printf("%-5s %-20s %-25s %-15s %-10s %-10s\n", 
            "ID", "Name", "Email", "Department", "Salary", "Status");
        System.out.println("-".repeat(80));
        
        for (Employee emp : employees) {
            System.out.printf("%-5d %-20s %-25s %-15s $%-9.0f %-10s\n",
                emp.id(),
                truncate(emp.name(), 20),
                truncate(emp.email(), 25),
                truncate(emp.department(), 15),
                emp.salary(),
                emp.active() ? "Active" : "Inactive"
            );
        }
        System.out.println("-".repeat(80));
    }
    
    // Input helper methods
    
    private String getStringInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }
    
    private String getStringInputWithDefault(String prompt, String defaultValue) {
        System.out.print(prompt);
        String input = scanner.nextLine().trim();
        return input.isEmpty() ? defaultValue : input;
    }
    
    private String getEmailInput(String prompt) {
        while (true) {
            String email = getStringInput(prompt);
            if (controller.isValidEmail(email)) {
                return email;
            }
            System.out.println("❌ Invalid email format. Please try again.");
        }
    }
    
    private String getEmailInputWithDefault(String prompt, String defaultValue) {
        while (true) {
            String email = getStringInputWithDefault(prompt, defaultValue);
            if (controller.isValidEmail(email)) {
                return email;
            }
            System.out.println("❌ Invalid email format. Please try again.");
        }
    }
    
    private int getIntInput(String prompt, int min, int max) {
        while (true) {
            try {
                System.out.print(prompt);
                int value = Integer.parseInt(scanner.nextLine().trim());
                if (value >= min && value <= max) {
                    return value;
                }
                System.out.println("❌ Please enter a value between " + min + " and " + max);
            } catch (NumberFormatException e) {
                System.out.println("❌ Please enter a valid number.");
            }
        }
    }
    
    private long getLongInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Long.parseLong(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("❌ Please enter a valid number.");
            }
        }
    }
    
    private double getDoubleInput(String prompt, double min, double max) {
        while (true) {
            try {
                System.out.print(prompt);
                double value = Double.parseDouble(scanner.nextLine().trim());
                if (controller.isValidSalary(value) && value >= min && value <= max) {
                    return value;
                }
                System.out.println("❌ Please enter a valid salary between " + min + " and " + max);
            } catch (NumberFormatException e) {
                System.out.println("❌ Please enter a valid number.");
            }
        }
    }
    
    private double getDoubleInputWithDefault(String prompt, double defaultValue) {
        while (true) {
            try {
                String input = getStringInputWithDefault(prompt, String.valueOf(defaultValue));
                double value = Double.parseDouble(input);
                if (controller.isValidSalary(value)) {
                    return value;
                }
                System.out.println("❌ Please enter a valid positive salary.");
            } catch (NumberFormatException e) {
                System.out.println("❌ Please enter a valid number.");
            }
        }
    }
    
    private boolean getBooleanInput(String prompt, boolean defaultValue) {
        System.out.print(prompt + " (Y/N): ");
        String input = scanner.nextLine().trim().toLowerCase();
        
        if (input.isEmpty()) {
            return defaultValue;
        }
        
        return input.equals("y") || input.equals("yes");
    }
    
    private String truncate(String str, int length) {
        if (str.length() <= length) {
            return str;
        }
        return str.substring(0, length - 3) + "...";
    }
}
