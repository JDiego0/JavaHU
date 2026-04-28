-- Database schema for Employee Management System
-- This script creates the necessary tables for the JDBC CRUD operations

-- Create database if it doesn't exist
CREATE DATABASE IF NOT EXISTS java_hu_db 
CHARACTER SET utf8mb4 
COLLATE utf8mb4_unicode_ci;

-- Use the database
USE java_hu_db;

-- Create employees table
CREATE TABLE IF NOT EXISTS employees (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    department VARCHAR(50) NOT NULL,
    salary DECIMAL(10,2) NOT NULL,
    hire_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    active BOOLEAN NOT NULL DEFAULT TRUE,
    
    -- Add indexes for better query performance
    INDEX idx_department (department),
    INDEX idx_active (active),
    INDEX idx_name (name),
    INDEX idx_email (email),
    
    -- Add constraints for data integrity
    CONSTRAINT chk_salary_positive CHECK (salary >= 0),
    CONSTRAINT chk_email_format CHECK (email LIKE '%@%')
);

-- Insert sample data for testing
INSERT INTO employees (name, email, department, salary, hire_date, active) VALUES
('Alice Johnson', 'alice.johnson@company.com', 'Engineering', 75000.00, '2023-01-15 09:00:00', TRUE),
('Bob Smith', 'bob.smith@company.com', 'Engineering', 80000.00, '2023-02-20 10:30:00', TRUE),
('Carol Williams', 'carol.williams@company.com', 'Marketing', 65000.00, '2023-03-10 14:15:00', TRUE),
('David Brown', 'david.brown@company.com', 'Engineering', 85000.00, '2023-04-05 11:45:00', TRUE),
('Eva Davis', 'eva.davis@company.com', 'HR', 60000.00, '2023-05-12 16:20:00', FALSE),
('Frank Miller', 'frank.miller@company.com', 'Engineering', 90000.00, '2023-06-18 13:30:00', TRUE),
('Grace Wilson', 'grace.wilson@company.com', 'Marketing', 62000.00, '2023-07-22 09:45:00', TRUE),
('Henry Moore', 'henry.moore@company.com', 'Engineering', 78000.00, '2023-08-30 15:10:00', TRUE);

-- Create a view for active employees (optional, for convenience)
CREATE OR REPLACE VIEW active_employees AS
SELECT id, name, email, department, salary, hire_date
FROM employees 
WHERE active = TRUE
ORDER BY name;
