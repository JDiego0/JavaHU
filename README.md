# JavaHU - Employee Management System

A comprehensive Java application demonstrating modern Java 17+ features with JDBC, implementing a complete CRUD system using the Model-View-Controller (MVC) architectural pattern.

## 📋 Table of Contents

- [Features](#-features)
- [Architecture](#-architecture)
- [Technologies Used](#-technologies-used)
- [Prerequisites](#-prerequisites)
- [Installation](#-installation)
- [Database Setup](#-database-setup)
- [Configuration](#-configuration)
- [Running the Application](#-running-the-application)
- [Usage Guide](#-usage-guide)
- [Project Structure](#-project-structure)
- [Modern Java Features](#-modern-java-features)
- [API Documentation](#-api-documentation)
- [Testing](#-testing)
- [Troubleshooting](#-troubleshooting)
- [Contributing](#-contributing)
- [License](#-license)

## ✨ Features

### Core Functionality
- **Complete CRUD Operations**: Create, Read, Update, Delete employees
- **Advanced Search**: Search by department, name (partial matching)
- **Soft Delete**: Deactivate employees without permanent deletion
- **Data Validation**: Input validation for email, salary, and required fields
- **Statistics Dashboard**: View employee counts and active/inactive ratios
- **Comprehensive Reporting**: Generate detailed employee reports with calculated fields

### Technical Features
- **MVC Architecture**: Clean separation of concerns
- **Modern Java 17+**: Records, Text Blocks, Pattern Matching
- **Secure Database Access**: PreparedStatement for SQL injection prevention
- **Connection Management**: try-with-resources for automatic resource cleanup
- **Properties-based Configuration**: External database configuration
- **Immutable Data Transfer**: Records for type-safe data handling

## 🏗️ Architecture

```
com.riwi.talent
├── model/                 # Data layer
│   ├── Employee.java      # Record entity
│   ├── EmployeeReport.java # Complex reporting record
│   └── dao/              # Data Access Objects
│       ├── EmpleadoDAO.java # DAO interface
│       └── impl/
│           └── EmpleadoDAOImpl.java # JDBC implementation
├── controller/            # Business logic layer
│   └── EmployeeController.java # MVC controller
├── view/                  # Presentation layer
│   └── EmployeeView.java # Console interface
└── util/                  # Utilities
    └── DatabaseConnection.java # JDBC connection utility
```

## 🛠️ Technologies Used

- **Java 17 LTS** - Modern Java features and performance
- **Maven** - Dependency management and build automation
- **MySQL 8.0** - Relational database (compatible with cloud providers)
- **JDBC** - Database connectivity with modern practices
- **Records** - Immutable data carriers (Java 16+)
- **Text Blocks** - Multi-line string literals (Java 15+)

## 📋 Prerequisites

### Required Software
- **Java Development Kit (JDK) 17** or higher
- **Apache Maven 3.6** or higher
- **MySQL 8.0** or compatible cloud MySQL service

### Optional
- **IDE** (IntelliJ IDEA, Eclipse, VS Code)
- **MySQL Workbench** or **DBeaver** for database management

## 🚀 Installation

### 1. Clone the Repository
```bash
git clone <repository-url>
cd JavaHU
```

### 2. Verify Java Version
```bash
java -version
# Should show Java 17 or higher
```

### 3. Build the Project
```bash
mvn clean compile
```

### 4. Verify Dependencies
```bash
mvn dependency:resolve
```

## 🗄️ Database Setup

### Option 1: Local MySQL

1. **Install MySQL** on your system
2. **Create Database**:
   ```sql
   CREATE DATABASE java_hu_db;
   ```

3. **Execute Setup Script**:
   ```bash
   mysql -u root -p java_hu_db < database_setup.sql
   ```

### Option 2: Cloud MySQL (Aiven, AWS RDS, etc.)

1. **Create MySQL Instance** on your preferred cloud provider
2. **Get Connection Details**:
   - Host URL
   - Port
   - Username
   - Password
   - SSL requirements

3. **Create Employees Table**:
   ```sql
   USE your_database_name;
   
   CREATE TABLE employees (
       id BIGINT AUTO_INCREMENT PRIMARY KEY,
       name VARCHAR(100) NOT NULL,
       email VARCHAR(100) NOT NULL UNIQUE,
       department VARCHAR(50) NOT NULL,
       salary DECIMAL(10,2) NOT NULL,
       hire_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
       active BOOLEAN NOT NULL DEFAULT TRUE,
       
       INDEX idx_department (department),
       INDEX idx_active (active),
       INDEX idx_email (email),
       INDEX idx_name (name)
   );
   ```

## ⚙️ Configuration

### Database Configuration

1. **Locate the properties file**:
   ```
   src/main/resources/database.properties
   ```

2. **Update with your database details**:
   ```properties
   # Database URL (include SSL mode for cloud connections)
   db.url=jdbc:mysql://localhost:3306/java_hu_db
   
   # For cloud MySQL with SSL:
   # db.url=jdbc:mysql://your-host:3306/your-db?sslMode=REQUIRED
   
   # Database credentials
   db.username=your_username
   db.password=your_password
   
   # Connection settings (optional)
   db.connectionTimeout=30000
   db.maxPoolSize=10
   ```

### Security Note
The `database.properties` file contains sensitive credentials. It's excluded from version control via `.gitignore` for security reasons.

## 🏃 Running the Application

### Method 1: Using Maven Exec Plugin
```bash
mvn exec:java
```

### Method 2: Using JAR File
```bash
# Build the JAR
mvn clean package

# Run the application
java -cp target/JavaHU-1.0-SNAPSHOT.jar:target/lib/* com.riwi.talent.Main
```

### Method 3: Direct from IDE
- Run the `Main.java` class from your IDE
- Ensure the working directory is the project root

## 📖 Usage Guide

### Main Menu Options

When you run the application, you'll see a menu with the following options:

1. **Create New Employee** - Add a new employee to the system
2. **View Employee by ID** - Search for a specific employee
3. **View All Employees** - List all employees in the system
4. **View Active Employees Only** - Show only active employees
5. **Update Employee** - Modify existing employee information
6. **Deactivate Employee** - Soft delete (mark as inactive)
7. **Delete Employee** - Permanent deletion
8. **Search by Department** - Find employees in a specific department
9. **Search by Name** - Find employees by name (partial matching)
10. **View Statistics** - See employee counts and ratios
11. **Demonstrate CRUD Operations** - Quick demo of all operations
12. **Generate Consolidated Report** - Advanced reporting with Records and Text Blocks
0. **Exit** - Close the application

### Example Workflow

1. **Start the Application**:
   ```bash
   mvn exec:java
   ```

2. **Create an Employee**:
   - Select option 1
   - Enter: `John Doe`, `john.doe@company.com`, `Engineering`, `75000`

3. **View All Employees**:
   - Select option 3 to see the complete list

4. **Generate Report**:
   - Select option 12 for a comprehensive report with formatting

## 📁 Project Structure

```
JavaHU/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/riwi/talent/
│   │   │       ├── Main.java                 # Application entry point
│   │   │       ├── model/                    # Data models and DAO
│   │   │       ├── controller/               # Business logic
│   │   │       ├── view/                     # User interface
│   │   │       └── util/                     # Utilities
│   │   └── resources/
│   │       └── database.properties           # Database configuration
│   └── test/                                 # Test directory (currently empty)
├── pom.xml                                   # Maven configuration
├── database_setup.sql                        # Database setup script
├── README.md                                 # This file
└── .gitignore                               # Git ignore rules
```

## 🔥 Modern Java Features

### Records (Java 16+)
```java
public record Employee(
    Long id,
    String name,
    String email,
    String department,
    Double salary,
    LocalDateTime hireDate,
    Boolean active
) {
    // Compact constructor for validation
    public Employee {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Employee name cannot be null or empty");
        }
        // Additional validations...
    }
}
```

### Text Blocks (Java 15+)
```java
String report = """
    ╔══════════════════════════════════════════════════════════════════════════╗
    ║              CONSOLIDATED EMPLOYEE REPORT                               ║
    ║              Generated with Java 17+ Records & Text Blocks              ║
    ╚══════════════════════════════════════════════════════════════════════════╝
    """;
```

### try-with-resources (Modern Connection Management)
```java
try (Connection conn = DatabaseConnection.getConnection();
     PreparedStatement stmt = conn.prepareStatement(sql);
     ResultSet rs = stmt.executeQuery()) {
    
    // Process results
    // Resources automatically closed
}
```

## 📚 API Documentation

### EmployeeDAO Interface

```java
public interface EmpleadoDAO {
    Optional<Employee> create(Employee employee);
    Optional<Employee> findById(Long id);
    List<Employee> findAll();
    List<Employee> findActive();
    boolean update(Employee employee);
    boolean deactivate(Long id);
    boolean delete(Long id);
    List<Employee> findByDepartment(String department);
    List<Employee> findByName(String name);
    int count();
    int countActive();
}
```

### EmployeeController Methods

```java
public class EmployeeController {
    public Employee createEmployee(String name, String email, String department, Double salary);
    public Employee getEmployeeById(Long id);
    public List<Employee> getAllEmployees();
    public List<Employee> getActiveEmployees();
    public boolean updateEmployee(Long id, String name, String email, String department, Double salary, Boolean active);
    public boolean deactivateEmployee(Long id);
    public boolean deleteEmployee(Long id);
    public List<Employee> getEmployeesByDepartment(String department);
    public List<Employee> getEmployeesByName(String name);
    public int[] getEmployeeStatistics();
    public List<EmployeeReport> getComprehensiveEmployeeReport();
}
```

## 🧪 Testing

### Database Connection Test
To verify your database connection programmatically:

```java
// Simple connection test
public class TestConnection {
    public static void main(String[] args) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            System.out.println("Connection successful!");
        } catch (SQLException e) {
            System.out.println("Connection failed: " + e.getMessage());
        }
    }
}
```

### Manual Testing
1. **Test CRUD Operations**: Use menu option 11
2. **Generate Reports**: Use menu option 12

## 🔧 Troubleshooting

### Common Issues and Solutions

#### 1. "No suitable driver found" Error
**Problem**: MySQL JDBC driver not in classpath
**Solution**: 
```bash
mvn dependency:resolve
mvn clean compile
```

#### 2. "Access denied" Error
**Problem**: Incorrect database credentials
**Solution**: 
- Verify `database.properties` values
- Check MySQL user permissions
- Ensure database exists

#### 3. "Communications link failure" Error
**Problem**: Cannot connect to database server
**Solution**:
- Check if MySQL service is running
- Verify host and port in connection URL
- Check firewall settings
- For cloud: verify SSL configuration

#### 4. "Table doesn't exist" Error
**Problem**: Employees table not created
**Solution**:
```sql
-- Execute this SQL in your database
CREATE TABLE employees (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    department VARCHAR(50) NOT NULL,
    salary DECIMAL(10,2) NOT NULL,
    hire_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    active BOOLEAN NOT NULL DEFAULT TRUE
);
```

#### 5. "SSL connection error" (Cloud MySQL)
**Problem**: SSL not configured properly
**Solution**: Add SSL parameters to URL:
```properties
db.url=jdbc:mysql://your-host:3306/your-db?sslMode=REQUIRED&useSSL=true
```

### Debug Mode
For detailed error information, run with debug logging:
```bash
mvn exec:java -Dexec.args="--debug"
```

## 🤝 Contributing

1. **Fork** the repository
2. **Create** a feature branch: `git checkout -b feature-name`
3. **Make** your changes
4. **Test** thoroughly
5. **Commit** your changes: `git commit -m 'Add feature description'`
6. **Push** to the branch: `git push origin feature-name`
7. **Submit** a pull request

### Code Style Guidelines
- Use Java 17+ features appropriately
- Follow MVC pattern strictly
- Use PreparedStatement for all SQL operations
- Include meaningful comments
- Validate all user inputs

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 📞 Support

If you encounter any issues:

1. **Check** the troubleshooting section above
2. **Verify** your database configuration
3. **Test** with the provided test utilities
4. **Create** an issue with detailed error information

## 🔄 Version History

- **v1.0.0** - Initial release with complete CRUD functionality
  - MVC architecture implementation
  - Java 17+ features integration
  - Database connection management
  - Comprehensive reporting system

---

**Built with ❤️ using Java 17 LTS and modern development practices**
