package com.riwi.talent.model.dao.impl;

import com.riwi.talent.model.Employee;
import com.riwi.talent.model.EmployeeReport;
import com.riwi.talent.model.dao.EmpleadoDAO;
import com.riwi.talent.util.DatabaseConnection;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Implementation of EmpleadoDAO interface using JDBC with PreparedStatement.
 * 
 * SECURITY NOTE: All SQL queries use PreparedStatement to prevent SQL injection attacks.
 * PreparedStatement automatically handles proper escaping of special characters and 
 * parameter binding, making database operations secure against malicious input.
 * 
 * MODERN RECORDS INTEGRATION:
 * This class demonstrates how Java 17+ Records facilitate efficient data reading
 * from complex SELECT queries compared to traditional POJO classes.
 * 
 * BENEFITS OF RECORDS IN JDBC:
 * 1. IMMUTABLE DATA TRANSFER: Records guarantee data integrity during database operations
 * 2. AUTOMATIC MAPPING: ResultSet to Record conversion requires minimal code
 * 3. TYPE SAFETY: Compile-time validation of field existence and types
 * 4. MAINTENANCE: Adding new fields only requires updating record definition
 * 5. PERFORMANCE: Records are optimized for memory usage and access patterns
 */
public class EmpleadoDAOImpl implements EmpleadoDAO {
    
    // SQL queries - using parameterized queries for security
    private static final String INSERT_SQL = 
        "INSERT INTO employees (name, email, department, salary, hire_date, active) VALUES (?, ?, ?, ?, ?, ?)";
    
    private static final String SELECT_BY_ID_SQL = 
        "SELECT id, name, email, department, salary, hire_date, active FROM employees WHERE id = ?";
    
    private static final String SELECT_ALL_SQL = 
        "SELECT id, name, email, department, salary, hire_date, active FROM employees ORDER BY hire_date DESC";
    
    private static final String SELECT_ACTIVE_SQL = 
        "SELECT id, name, email, department, salary, hire_date, active FROM employees WHERE active = true ORDER BY name";
    
    private static final String UPDATE_SQL = 
        "UPDATE employees SET name = ?, email = ?, department = ?, salary = ?, active = ? WHERE id = ?";
    
    private static final String DEACTIVATE_SQL = 
        "UPDATE employees SET active = false WHERE id = ?";
    
    private static final String DELETE_SQL = 
        "DELETE FROM employees WHERE id = ?";
    
    private static final String SELECT_BY_DEPARTMENT_SQL = 
        "SELECT id, name, email, department, salary, hire_date, active FROM employees WHERE department = ? AND active = true ORDER BY name";
    
    private static final String SELECT_BY_NAME_SQL = 
        "SELECT id, name, email, department, salary, hire_date, active FROM employees WHERE name LIKE ? AND active = true ORDER BY name";
    
    private static final String COUNT_SQL = 
        "SELECT COUNT(*) FROM employees";
    
    private static final String COUNT_ACTIVE_SQL = 
        "SELECT COUNT(*) FROM employees WHERE active = true";
    
    /**
     * COMPLEX SELECT QUERY for comprehensive employee reporting
     * This demonstrates how Records efficiently map complex query results
     * with calculated fields and joins - something that would require extensive
     * manual mapping with traditional POJOs
     */
    private static final String SELECT_COMPLEX_REPORT_SQL = """
        SELECT 
            e.id as employee_id,
            e.name as employee_name,
            e.email as email,
            e.department as department,
            e.salary as salary,
            e.hire_date as hire_date,
            e.active as is_active,
            DATEDIFF(CURDATE(), e.hire_date) / 365.25 as years_of_service,
            CASE 
                WHEN e.salary < 50000 THEN 'Entry Level'
                WHEN e.salary < 80000 THEN 'Mid Level'
                WHEN e.salary < 120000 THEN 'Senior Level'
                ELSE 'Executive'
            END as salary_category,
            (SELECT COUNT(*) FROM employees e2 WHERE e2.department = e.department AND e2.active = true) as department_size
        FROM employees e
        WHERE e.active = true
        ORDER BY e.department, e.salary DESC
        """;
    
    @Override
    public Optional<Employee> create(Employee employee) {
        // Using try-with-resources for automatic resource management
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS)) {
            
            // Secure parameter binding - prevents SQL injection
            stmt.setString(1, employee.name());
            stmt.setString(2, employee.email());
            stmt.setString(3, employee.department());
            stmt.setDouble(4, employee.salary());
            stmt.setTimestamp(5, Timestamp.valueOf(employee.hireDate()));
            stmt.setBoolean(6, employee.active());
            
            int affectedRows = stmt.executeUpdate();
            
            if (affectedRows == 0) {
                return Optional.empty();
            }
            
            // Get the generated ID
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    Long generatedId = generatedKeys.getLong(1);
                    Employee createdEmployee = new Employee(
                        generatedId,
                        employee.name(),
                        employee.email(),
                        employee.department(),
                        employee.salary(),
                        employee.hireDate(),
                        employee.active()
                    );
                    return Optional.of(createdEmployee);
                }
            }
            
        } catch (SQLException e) {
            System.err.println("Error creating employee: " + e.getMessage());
            e.printStackTrace();
        }
        
        return Optional.empty();
    }
    
    @Override
    public Optional<Employee> findById(Long id) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SELECT_BY_ID_SQL)) {
            
            // Secure parameter binding
            stmt.setLong(1, id);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Employee employee = mapResultSetToEmployee(rs);
                    return Optional.of(employee);
                }
            }
            
        } catch (SQLException e) {
            System.err.println("Error finding employee by ID: " + e.getMessage());
            e.printStackTrace();
        }
        
        return Optional.empty();
    }
    
    @Override
    public List<Employee> findAll() {
        List<Employee> employees = new ArrayList<>();
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SELECT_ALL_SQL);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                Employee employee = mapResultSetToEmployee(rs);
                employees.add(employee);
            }
            
        } catch (SQLException e) {
            System.err.println("Error finding all employees: " + e.getMessage());
            e.printStackTrace();
        }
        
        return employees;
    }
    
    @Override
    public List<Employee> findActive() {
        List<Employee> employees = new ArrayList<>();
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SELECT_ACTIVE_SQL);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                Employee employee = mapResultSetToEmployee(rs);
                employees.add(employee);
            }
            
        } catch (SQLException e) {
            System.err.println("Error finding active employees: " + e.getMessage());
            e.printStackTrace();
        }
        
        return employees;
    }
    
    @Override
    public boolean update(Employee employee) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(UPDATE_SQL)) {
            
            // Secure parameter binding for all fields
            stmt.setString(1, employee.name());
            stmt.setString(2, employee.email());
            stmt.setString(3, employee.department());
            stmt.setDouble(4, employee.salary());
            stmt.setBoolean(5, employee.active());
            stmt.setLong(6, employee.id());
            
            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
            
        } catch (SQLException e) {
            System.err.println("Error updating employee: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    @Override
    public boolean deactivate(Long id) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(DEACTIVATE_SQL)) {
            
            // Secure parameter binding
            stmt.setLong(1, id);
            
            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
            
        } catch (SQLException e) {
            System.err.println("Error deactivating employee: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    @Override
    public boolean delete(Long id) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(DELETE_SQL)) {
            
            // Secure parameter binding
            stmt.setLong(1, id);
            
            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
            
        } catch (SQLException e) {
            System.err.println("Error deleting employee: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    @Override
    public List<Employee> findByDepartment(String department) {
        List<Employee> employees = new ArrayList<>();
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SELECT_BY_DEPARTMENT_SQL)) {
            
            // Secure parameter binding for department search
            stmt.setString(1, department);
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Employee employee = mapResultSetToEmployee(rs);
                    employees.add(employee);
                }
            }
            
        } catch (SQLException e) {
            System.err.println("Error finding employees by department: " + e.getMessage());
            e.printStackTrace();
        }
        
        return employees;
    }
    
    @Override
    public List<Employee> findByName(String name) {
        List<Employee> employees = new ArrayList<>();
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SELECT_BY_NAME_SQL)) {
            
            // Secure parameter binding for LIKE query - prevents SQL injection
            stmt.setString(1, "%" + name + "%");
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Employee employee = mapResultSetToEmployee(rs);
                    employees.add(employee);
                }
            }
            
        } catch (SQLException e) {
            System.err.println("Error finding employees by name: " + e.getMessage());
            e.printStackTrace();
        }
        
        return employees;
    }
    
    @Override
    public int count() {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(COUNT_SQL);
             ResultSet rs = stmt.executeQuery()) {
            
            if (rs.next()) {
                return rs.getInt(1);
            }
            
        } catch (SQLException e) {
            System.err.println("Error counting employees: " + e.getMessage());
            e.printStackTrace();
        }
        
        return 0;
    }
    
    @Override
    public int countActive() {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(COUNT_ACTIVE_SQL);
             ResultSet rs = stmt.executeQuery()) {
            
            if (rs.next()) {
                return rs.getInt(1);
            }
            
        } catch (SQLException e) {
            System.err.println("Error counting active employees: " + e.getMessage());
            e.printStackTrace();
        }
        
        return 0;
    }
    
    /**
     * Helper method to map ResultSet to Employee object
     * Centralizes the mapping logic to avoid code duplication
     */
    private Employee mapResultSetToEmployee(ResultSet rs) throws SQLException {
        Long id = rs.getLong("id");
        String name = rs.getString("name");
        String email = rs.getString("email");
        String department = rs.getString("department");
        Double salary = rs.getDouble("salary");
        LocalDateTime hireDate = rs.getTimestamp("hire_date").toLocalDateTime();
        Boolean active = rs.getBoolean("active");
        
        return Employee.fromDatabase(id, name, email, department, salary, hireDate, active);
    }
    
    /**
     * COMPLEX QUERY METHOD using EmployeeReport Record
     * This demonstrates the efficiency of Records in mapping complex SELECT results
     * with calculated fields, CASE statements, and subqueries
     * 
     * COMPARISON WITH POJO APPROACH:
     * POJO would require:
     * - Manual mapping of 10+ fields in each DAO method
     * - Separate calculation methods outside the data class
     * - Extensive boilerplate for getters/setters
     * - Manual implementation of equals/hashCode/toString
     * 
     * RECORD APPROACH:
     * - Automatic field mapping from ResultSet
     * - Built-in validation in compact constructor
     * - Automatic generation of all required methods
     * - Factory methods for clean object creation
     * - Computed properties within the record itself
     */
    public List<EmployeeReport> getComplexEmployeeReport() {
        List<EmployeeReport> reports = new ArrayList<>();
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SELECT_COMPLEX_REPORT_SQL);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                // Efficient mapping from complex ResultSet to Record
                // This is where Records shine - minimal code, maximum type safety
                EmployeeReport report = EmployeeReport.fromDatabaseRow(
                    rs.getLong("employee_id"),
                    rs.getString("employee_name"),
                    rs.getString("email"),
                    rs.getString("department"),
                    rs.getDouble("salary"),
                    rs.getTimestamp("hire_date").toLocalDateTime(),
                    rs.getBoolean("is_active"),
                    rs.getInt("department_size")
                );
                
                reports.add(report);
            }
            
        } catch (SQLException e) {
            System.err.println("Error generating complex employee report: " + e.getMessage());
            e.printStackTrace();
        }
        
        return reports;
    }
}
