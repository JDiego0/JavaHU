-- Script to create employees table for JavaHU project in Aiven cloud MySQL
-- Execute this script in your defaultdb database (Aiven only allows defaultdb)

-- Use the defaultdb database (Aiven only allows this database)
USE defaultdb;

-- 3. Create employees table
CREATE TABLE IF NOT EXISTS employees (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    department VARCHAR(50) NOT NULL,
    salary DECIMAL(10,2) NOT NULL,
    hire_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    active BOOLEAN NOT NULL DEFAULT TRUE,
    
    -- Indexes for performance
    INDEX idx_department (department),
    INDEX idx_active (active),
    INDEX idx_email (email),
    INDEX idx_name (name)
);

-- 4. Insert sample data (optional)
INSERT IGNORE INTO employees (name, email, department, salary, hire_date, active) VALUES
('John Smith', 'john.smith@company.com', 'Engineering', 75000.00, '2023-01-15 09:00:00', TRUE),
('Maria Garcia', 'maria.garcia@company.com', 'Marketing', 65000.00, '2023-02-20 10:00:00', TRUE),
('Carlos Lopez', 'carlos.lopez@company.com', 'Engineering', 85000.00, '2022-11-10 08:30:00', TRUE),
('Ana Martinez', 'ana.martinez@company.com', 'HR', 55000.00, '2023-03-05 14:00:00', TRUE),
('Luis Rodriguez', 'luis.rodriguez@company.com', 'Engineering', 95000.00, '2022-08-12 09:15:00', FALSE),
('Sofia Hernandez', 'sofia.hernandez@company.com', 'Finance', 70000.00, '2023-04-18 11:30:00', TRUE);

-- 5. Show table structure
DESCRIBE employees;

-- 6. Verify inserted data
SELECT * FROM employees ORDER BY hire_date DESC;
