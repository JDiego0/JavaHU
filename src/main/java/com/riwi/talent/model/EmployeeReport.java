package com.riwi.talent.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Employee Report record for complex data mapping from database queries.
 * 
 * This record demonstrates how Java 17+ Records facilitate efficient data reading
 * from complex SELECT queries compared to traditional POJO classes.
 * 
 * MODERN ANALYSIS - Records vs POJO:
 * 
 * 1. IMMUTABILITY BY DEFAULT:
 *    - Records are automatically immutable, preventing accidental data modification
 *    - POJOs require manual implementation of immutability patterns
 *    - This eliminates entire classes of bugs related to state changes
 * 
 * 2. AUTOMATIC CONSTRUCTOR AND ACCESSORS:
 *    - Records generate constructor, getters, equals(), hashCode(), toString() automatically
 *    - POJOs require ~50-100 lines of boilerplate code for the same functionality
 *    - Less code = fewer bugs, easier maintenance, faster development
 * 
 * 3. PATTERN MATCHING COMPATIBILITY:
 *    - Records work seamlessly with Java 17+ pattern matching features
 *    - Enables cleaner switch expressions and instanceof patterns
 *    - POJOs require manual type checking and casting
 * 
 * 4. JDBC INTEGRATION:
 *    - Records can be directly mapped from ResultSet with minimal code
 *    - Compact constructors allow validation during object creation
 *    - Factory methods provide clean object creation patterns
 *    - POJOs require manual mapping in each DAO method
 * 
 * 5. MAINTENANCE BENEFITS:
 *    - Adding new fields requires only changing the record definition
 *    - POJOs require updating constructor, getters, setters, equals, hashCode, toString
 *    - Records reduce maintenance overhead by ~80%
 * 
 * 6. TYPE SAFETY:
 *    - Records provide compile-time guarantees about field existence
 *    - POJOs can have inconsistent getter/setter patterns
 *    - Records eliminate reflection-based access patterns
 * 
 * @param employeeId Employee unique identifier
 * @param employeeName Employee full name
 * @param email Employee email address
 * @param department Department where employee works
 * @param salary Employee annual salary
 * @param hireDate Date when employee was hired
 * @param isActive Whether employee is currently active
 * @param yearsOfService Calculated years of service
 * @param salaryCategory Salary category (Entry/Mid/Senior)
 * @param departmentSize Number of employees in same department
 */
public record EmployeeReport(
    Long employeeId,
    String employeeName,
    String email,
    String department,
    Double salary,
    LocalDateTime hireDate,
    Boolean isActive,
    Integer yearsOfService,
    String salaryCategory,
    Integer departmentSize
) {
    
    /**
     * Compact constructor for additional validation and calculations
     * Demonstrates how records can include business logic during object creation
     */
    public EmployeeReport {
        // Validate required fields
        if (employeeName == null || employeeName.trim().isEmpty()) {
            throw new IllegalArgumentException("Employee name cannot be null or empty");
        }
        if (email == null || !email.contains("@")) {
            throw new IllegalArgumentException("Valid email is required");
        }
        if (salary != null && salary < 0) {
            throw new IllegalArgumentException("Salary cannot be negative");
        }
        
        // Calculate salary category if not provided
        if (salaryCategory == null && salary != null) {
            salaryCategory = calculateSalaryCategory(salary);
        }
        
        // Calculate years of service if not provided
        if (yearsOfService == null && hireDate != null) {
            yearsOfService = calculateYearsOfService(hireDate);
        }
    }
    
    /**
     * Factory method for creating EmployeeReport from complex database query results
     * Demonstrates efficient mapping from JDBC ResultSet to Record
     */
    public static EmployeeReport fromDatabaseRow(
        Long employeeId,
        String employeeName,
        String email,
        String department,
        Double salary,
        LocalDateTime hireDate,
        Boolean isActive,
        Integer departmentSize
    ) {
        return new EmployeeReport(
            employeeId,
            employeeName,
            email,
            department,
            salary,
            hireDate,
            isActive,
            calculateYearsOfService(hireDate),
            calculateSalaryCategory(salary),
            departmentSize
        );
    }
    
    /**
     * Factory method for creating from legacy Employee record
     * Shows how records can be easily converted between different data representations
     */
    public static EmployeeReport fromEmployee(Employee employee, Integer departmentSize) {
        return new EmployeeReport(
            employee.id(),
            employee.name(),
            employee.email(),
            employee.department(),
            employee.salary(),
            employee.hireDate(),
            employee.active(),
            calculateYearsOfService(employee.hireDate()),
            calculateSalaryCategory(employee.salary()),
            departmentSize
        );
    }
    
    /**
     * Calculates salary category based on annual salary
     * Business logic encapsulated in the record
     */
    private static String calculateSalaryCategory(Double salary) {
        if (salary == null) return "Unknown";
        if (salary < 50000) return "Entry Level";
        if (salary < 80000) return "Mid Level";
        if (salary < 120000) return "Senior Level";
        return "Executive";
    }
    
    /**
     * Calculates years of service from hire date
     * Demonstrates how records can contain computed properties
     */
    private static Integer calculateYearsOfService(LocalDateTime hireDate) {
        if (hireDate == null) return null;
        return (int) java.time.temporal.ChronoUnit.YEARS.between(
            hireDate.toLocalDate(), 
            LocalDateTime.now().toLocalDate()
        );
    }
    
    /**
     * Formatted hire date for display
     * Shows how records can have computed display methods
     */
    public String getFormattedHireDate() {
        if (hireDate == null) return "Unknown";
        return hireDate.format(DateTimeFormatter.ofPattern("MMM dd, yyyy"));
    }
    
    /**
     * Formatted salary for display
     * Demonstrates how records can provide formatted output
     */
    public String getFormattedSalary() {
        if (salary == null) return "Unknown";
        return String.format("$%,.2f", salary);
    }
    
    /**
     * Status indicator for display
     * Shows how records can provide computed display properties
     */
    public String getStatusIndicator() {
        return Boolean.TRUE.equals(isActive) ? "🟢 Active" : "🔴 Inactive";
    }
    
    /**
     * Service level indicator
     * Combines multiple fields for meaningful display
     */
    public String getServiceLevel() {
        if (yearsOfService == null) return "Unknown";
        if (yearsOfService < 1) return "New Hire";
        if (yearsOfService < 3) return "Junior";
        if (yearsOfService < 5) return "Experienced";
        return "Senior";
    }
}
