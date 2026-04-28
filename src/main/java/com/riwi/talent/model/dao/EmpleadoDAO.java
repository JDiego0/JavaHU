package com.riwi.talent.model.dao;

import com.riwi.talent.model.Employee;
import java.util.List;
import java.util.Optional;

/**
 * Data Access Object interface for Employee entity.
 * Defines CRUD operations with secure database access using PreparedStatement.
 * 
 * This interface follows the DAO pattern to separate business logic from data access logic.
 * All implementations must use PreparedStatement to prevent SQL injection attacks.
 */
public interface EmpleadoDAO {
    
    /**
     * Creates a new employee in the database.
     * Uses PreparedStatement to prevent SQL injection.
     * 
     * @param employee Employee object to be created (without ID)
     * @return Employee object with generated ID, or empty if creation failed
     */
    Optional<Employee> create(Employee employee);
    
    /**
     * Retrieves an employee by their unique ID.
     * Uses PreparedStatement for secure parameter binding.
     * 
     * @param id Employee ID to search for
     * @return Optional containing Employee if found, empty otherwise
     */
    Optional<Employee> findById(Long id);
    
    /**
     * Retrieves all employees from the database.
     * Returns a list of all active and inactive employees.
     * 
     * @return List of all employees, empty list if no employees found
     */
    List<Employee> findAll();
    
    /**
     * Retrieves only active employees.
     * Uses PreparedStatement for the active status filter.
     * 
     * @return List of active employees, empty list if none found
     */
    List<Employee> findActive();
    
    /**
     * Updates an existing employee's information.
     * All parameters are safely bound using PreparedStatement.
     * 
     * @param employee Employee object with updated information (must include ID)
     * @return true if update was successful, false otherwise
     */
    boolean update(Employee employee);
    
    /**
     * Soft deletes an employee by setting active status to false.
     * Uses PreparedStatement to safely bind the ID parameter.
     * This approach preserves data integrity while logically removing the employee.
     * 
     * @param id Employee ID to deactivate
     * @return true if deactivation was successful, false otherwise
     */
    boolean deactivate(Long id);
    
    /**
     * Hard deletes an employee from the database.
     * Uses PreparedStatement for secure parameter binding.
     * WARNING: This permanently removes the employee record.
     * 
     * @param id Employee ID to permanently delete
     * @return true if deletion was successful, false otherwise
     */
    boolean delete(Long id);
    
    /**
     * Searches employees by department.
     * Uses PreparedStatement to safely bind the department parameter.
     * 
     * @param department Department name to search for
     * @return List of employees in the specified department, empty list if none found
     */
    List<Employee> findByDepartment(String department);
    
    /**
     * Searches employees by name (partial match).
     * Uses PreparedStatement with LIKE clause for safe pattern matching.
     * 
     * @param name Name or partial name to search for
     * @return List of employees matching the name criteria, empty list if none found
     */
    List<Employee> findByName(String name);
    
    /**
     * Counts total number of employees.
     * Uses PreparedStatement for secure query execution.
     * 
     * @return Total count of employees in the database
     */
    int count();
    
    /**
     * Counts only active employees.
     * Uses PreparedStatement for secure filtering.
     * 
     * @return Count of active employees
     */
    int countActive();
}
