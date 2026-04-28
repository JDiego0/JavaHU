package com.riwi.talent.controller;

import com.riwi.talent.model.Employee;
import com.riwi.talent.model.EmployeeReport;
import com.riwi.talent.model.dao.EmpleadoDAO;
import com.riwi.talent.model.dao.impl.EmpleadoDAOImpl;
import com.riwi.talent.util.DatabaseConnection;

import java.util.List;
import java.util.Optional;

/**
 * Controller class that mediates between View and Model layers.
 * Implements business logic and coordinates data operations.
 * 
 * This class follows the MVC pattern by:
 * - Receiving input from the View layer
 * - Processing business logic
 * - Coordinating with the Model layer (DAO)
 * - Returning results to the View layer
 */
public class EmployeeController {
    
    private final EmpleadoDAO employeeDAO;
    
    /**
     * Constructor initializes the DAO implementation
     */
    public EmployeeController() {
        this.employeeDAO = new EmpleadoDAOImpl();
    }
    
    /**
     * Creates a new employee after validation
     * @param name Employee name
     * @param email Employee email
     * @param department Employee department
     * @param salary Employee salary
     * @return Created employee with ID, or null if creation failed
     */
    public Employee createEmployee(String name, String email, String department, Double salary) {
        try {
            // Create new employee object
            Employee newEmployee = Employee.createNew(name, email, department, salary);
            
            // Delegate to DAO for persistence
            Optional<Employee> created = employeeDAO.create(newEmployee);
            
            return created.orElse(null);
            
        } catch (IllegalArgumentException e) {
            System.err.println("Validation error: " + e.getMessage());
            return null;
        }
    }
    
    /**
     * Retrieves an employee by ID
     * @param id Employee ID
     * @return Employee if found, null otherwise
     */
    public Employee getEmployeeById(Long id) {
        Optional<Employee> employee = employeeDAO.findById(id);
        return employee.orElse(null);
    }
    
    /**
     * Retrieves all employees
     * @return List of all employees
     */
    public List<Employee> getAllEmployees() {
        return employeeDAO.findAll();
    }
    
    /**
     * Retrieves only active employees
     * @return List of active employees
     */
    public List<Employee> getActiveEmployees() {
        return employeeDAO.findActive();
    }
    
    /**
     * Updates an existing employee
     * @param id Employee ID
     * @param name New name
     * @param email New email
     * @param department New department
     * @param salary New salary
     * @param active New active status
     * @return true if update successful, false otherwise
     */
    public boolean updateEmployee(Long id, String name, String email, String department, 
                              Double salary, Boolean active) {
        try {
            // First check if employee exists
            Optional<Employee> existing = employeeDAO.findById(id);
            if (existing.isEmpty()) {
                System.err.println("Employee with ID " + id + " not found");
                return false;
            }
            
            // Create updated employee object
            Employee updatedEmployee = Employee.fromDatabase(
                id, name, email, department, salary, 
                existing.get().hireDate(), active
            );
            
            // Delegate to DAO for update
            return employeeDAO.update(updatedEmployee);
            
        } catch (IllegalArgumentException e) {
            System.err.println("Validation error: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Deactivates an employee (soft delete)
     * @param id Employee ID to deactivate
     * @return true if deactivation successful, false otherwise
     */
    public boolean deactivateEmployee(Long id) {
        // Check if employee exists before deactivating
        Optional<Employee> employee = employeeDAO.findById(id);
        if (employee.isEmpty()) {
            System.err.println("Employee with ID " + id + " not found");
            return false;
        }
        
        return employeeDAO.deactivate(id);
    }
    
    /**
     * Permanently deletes an employee
     * @param id Employee ID to delete
     * @return true if deletion successful, false otherwise
     */
    public boolean deleteEmployee(Long id) {
        // Check if employee exists before deleting
        Optional<Employee> employee = employeeDAO.findById(id);
        if (employee.isEmpty()) {
            System.err.println("Employee with ID " + id + " not found");
            return false;
        }
        
        return employeeDAO.delete(id);
    }
    
    /**
     * Searches employees by department
     * @param department Department name
     * @return List of employees in the department
     */
    public List<Employee> getEmployeesByDepartment(String department) {
        return employeeDAO.findByDepartment(department);
    }
    
    /**
     * Searches employees by name (partial match)
     * @param name Name or partial name to search
     * @return List of matching employees
     */
    public List<Employee> getEmployeesByName(String name) {
        return employeeDAO.findByName(name);
    }
    
    /**
     * Gets employee statistics
     * @return Array with [total_count, active_count]
     */
    public int[] getEmployeeStatistics() {
        int total = employeeDAO.count();
        int active = employeeDAO.countActive();
        return new int[]{total, active};
    }
    
    /**
     * Gets comprehensive employee report using Records
     * This demonstrates complex SELECT query mapping with EmployeeReport record
     * @return List of EmployeeReport with calculated fields
     */
    public List<EmployeeReport> getComprehensiveEmployeeReport() {
        // Cast to access complex report method
        if (employeeDAO instanceof EmpleadoDAOImpl impl) {
            return impl.getComplexEmployeeReport();
        }
        return List.of();
    }
    
    /**
     * Validates email format
     * @param email Email to validate
     * @return true if valid, false otherwise
     */
    public boolean isValidEmail(String email) {
        return email != null && email.contains("@") && email.contains(".");
    }
    
    /**
     * Validates salary is positive
     * @param salary Salary to validate
     * @return true if valid, false otherwise
     */
    public boolean isValidSalary(Double salary) {
        return salary != null && salary > 0;
    }
    
    /**
     * Validates name is not empty
     * @param name Name to validate
     * @return true if valid, false otherwise
     */
    public boolean isValidName(String name) {
        return name != null && !name.trim().isEmpty();
    }
    
    }
