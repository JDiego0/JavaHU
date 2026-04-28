package com.riwi.talent.util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Utility class for managing JDBC connections to relational databases.
 * Demonstrates the difference between Legacy (Java 8-) and Modern (Java 17/21) syntax.
 * 
 * ============================================================================================
 * VENTAJAS DE LA ARQUITECTURA MODERNA (Java 17/21 LTS) vs RIESGOS DE VERSIONES LEGACY
 * ============================================================================================
 * 
 * 1. GESTIÓN DE RECURSOS (try-with-resources vs finally):
 *    - LEGACY (Java 7-): El cierre manual con finally es propenso a errores humanos.
 *      Si el programador olvida cerrar un recurso, se generan fugas de memoria (Memory Leaks).
 *      Además, excepciones dentro del finally pueden enmascarar la excepción original.
 *    - MODERNO (Java 9+): try-with-resources garantiza el cierre automático de cualquier
 *      objeto que implemente AutoCloseable. El compilador genera el código de cierre,
 *      eliminando errores humanos. Las excepciones suprimidas se manejan correctamente.
 * 
 * 2. RECORDS vs POJOs (Java 16+):
 *    - LEGACY: Las clases POJO requieren ~50-100 líneas de boilerplate (constructor,
 *      getters, setters, equals, hashCode, toString) para cada entidad.
 *    - MODERNO: Los Records generan automáticamente todo el boilerplate en una sola línea.
 *      Son inmutables por defecto, lo que previene errores de estado compartido.
 *      Los compact constructors permiten validación inline.
 * 
 * 3. TEXT BLOCKS (Java 13+):
 *    - LEGACY: Las consultas SQL multilínea requerían concatenación de Strings con "+",
 *      escapado manual de comillas y saltos de línea (\n), resultando en código ilegible.
 *    - MODERNO: Los Text Blocks (""" ... """) permiten escribir SQL, JSON y reportes
 *      multilínea de forma legible, manteniendo el formato y la indentación.
 * 
 * 4. PATTERN MATCHING (Java 16+):
 *    - LEGACY: Requería cast manual después de instanceof, propenso a ClassCastException.
 *    - MODERNO: instanceof con binding variable elimina el cast manual,
 *      y switch expressions permiten exhaustive matching con sealed classes.
 * 
 * 5. SEGURIDAD:
 *    - LEGACY: Muchos tutoriales antiguos usaban Statement con concatenación de strings,
 *      exponiendo el sistema a inyección SQL.
 *    - MODERNO: PreparedStatement es el estándar obligatorio. Los parámetros se vinculan
 *      de forma segura, previniendo inyección SQL completamente.
 * 
 * 6. SOPORTE LTS (Long-Term Support):
 *    - Java 8 LTS: Finaliza soporte extendido en 2030. Sin mejoras de rendimiento.
 *    - Java 17 LTS: Soporte hasta 2029+. Incluye mejoras de GC (ZGC, Shenandoah),
 *      sealed classes, records, pattern matching, text blocks.
 *    - Java 21 LTS: Soporte hasta 2031+. Incluye virtual threads, sequenced collections,
 *      string templates (preview), y mejoras significativas de rendimiento.
 * ============================================================================================
 */
public class DatabaseConnection {
    
    // Database configuration from properties file
    private static final Properties properties = loadDatabaseProperties();
    private static final String URL = properties.getProperty("db.url");
    private static final String USER = properties.getProperty("db.username");
    private static final String PASSWORD = properties.getProperty("db.password");
    
    /**
     * Loads database configuration from properties file
     * This approach provides better security and configuration management
     * 
     * BENEFITS OF USING PROPERTIES FILE:
     * 1. Security: Credentials not hardcoded in source code
     * 2. Flexibility: Easy to change configuration without recompiling
     * 3. Environment-specific: Different properties for dev/test/prod
     * 4. Version Control: Properties file can be excluded from git
     */
    private static Properties loadDatabaseProperties() {
        Properties props = new Properties();
        
        try (InputStream input = DatabaseConnection.class.getClassLoader()
                .getResourceAsStream("database.properties")) {
            
            if (input == null) {
                System.err.println("ERROR: database.properties file not found in classpath");
                System.err.println("Please ensure database.properties is in src/main/resources/");
                
                // Fallback to default values for development
                props.setProperty("db.url", "jdbc:mysql://localhost:3306/java_hu_db");
                props.setProperty("db.username", "root");
                props.setProperty("db.password", "password");
                System.err.println("Using default database configuration");
            } else {
                props.load(input);
                System.out.println("Database configuration loaded from properties file");
            }
            
        } catch (IOException e) {
            System.err.println("ERROR loading database properties: " + e.getMessage());
            e.printStackTrace();
            
            // Fallback to default values
            props.setProperty("db.url", "jdbc:mysql://localhost:3306/java_hu_db");
            props.setProperty("db.username", "root");
            props.setProperty("db.password", "password");
        }
        
        return props;
    }
    
    /**
     * LEGACY Method (Java 8 and earlier) - Manual resource management with finally block
     * 
     * PROBLEMS WITH THIS APPROACH:
     * 1. Verbose and repetitive code
     * 2. Easy to forget closing resources in the finally block
     * 3. Possible NullPointerException if any resource is null
     * 4. Memory leaks if connections are not properly closed
     * 5. Code becomes difficult to maintain and read
     */
    public void legacyConnectionExample() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        
        try {
            // 1. Establish connection
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            
            // 2. Create prepared statement
            String sql = "SELECT * FROM users WHERE id = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, 1);
            
            // 3. Execute query
            resultSet = preparedStatement.executeQuery();
            
            // 4. Process results
            while (resultSet.next()) {
                System.out.println("User: " + resultSet.getString("name"));
            }
            
        } catch (SQLException e) {
            System.err.println("Error in query: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // LEGACY FINALLY BLOCK - Manual resource closing
            // This code is CRITICAL to prevent memory leaks but is error-prone
            
            // Close ResultSet - must check for null to avoid NullPointerException
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    System.err.println("Error closing ResultSet: " + e.getMessage());
                }
            }
            
            // Close PreparedStatement
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    System.err.println("Error closing PreparedStatement: " + e.getMessage());
                }
            }
            
            // Close Connection - most important to free DB resources
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    System.err.println("Error closing Connection: " + e.getMessage());
                }
            }
        }
    }
    
    /**
     * MODERN Method (Java 17/21) - Using try-with-resources
     * 
     * ADVANTAGES OF THIS APPROACH:
     * 1. Cleaner and more readable code
     * 2. Automatic and guaranteed resource closing
     * 3. Implements AutoCloseable automatically
     * 4. Effectively prevents memory leaks
     * 5. More elegant exception handling
     * 6. Less prone to human errors
     */
    public void modernConnectionExample() {
        // TRY-WITH-RESOURCES - Resources are automatically closed
        // Connection, PreparedStatement, and ResultSet implement AutoCloseable
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM users WHERE id = ?")) {
            
            // Configure parameters
            preparedStatement.setInt(1, 1);
            
            // Execute and process results (also within try-with-resources)
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    System.out.println("User: " + resultSet.getString("name"));
                }
            } // ResultSet is automatically closed here
            
        } catch (SQLException e) {
            System.err.println("Error in query: " + e.getMessage());
            e.printStackTrace();
        }
        // All resources (Connection, PreparedStatement, ResultSet) 
        // are automatically closed when exiting the try block
        // NO NEED FOR FINALLY BLOCK
    }
    
    /**
     * Utility method to get connection using modern syntax
     * @return valid Connection or null if error occurs
     */
    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            System.err.println("Error establishing connection: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
    
    /**
     * Method demonstrating how try-with-resources prevents memory leaks
     * 
     * MEMORY LEAKS IN JDBC:
     * - Each unclosed Connection consumes connection pool resources
     * - Unclosed PreparedStatement consume memory on the server
     * - Open ResultSets maintain cursors in the database
     * - Over time, this exhausts resources and causes system failures
     * 
     * TRY-WITH-RESOURCES SOLVES THIS BECAUSE:
     * 1. Compiler automatically generates closing code
     * 2. Guarantees closing even if exceptions occur
     * 3. Closes resources in reverse order of creation
     * 4. Handles exceptions during closing safely
     */
    public void demonstrateMemoryLeakPrevention() {
        // This code is 100% safe against memory leaks
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT COUNT(*) FROM users");
             ResultSet rs = stmt.executeQuery()) {
            
            if (rs.next()) {
                int userCount = rs.getInt(1);
                System.out.println("Total users: " + userCount);
            }
            
        } catch (SQLException e) {
            System.err.println("Error in query: " + e.getMessage());
        }
        // Here Java automatically calls:
        // 1. rs.close() (if not null)
        // 2. stmt.close() (if not null)  
        // 3. conn.close() (if not null)
        // Even if exceptions occur during closing
    }
    
    /**
     * Method to test the connection
     * @return true if connection is successful
     */
    public static boolean testConnection() {
        try (Connection connection = getConnection()) {
            return connection != null && !connection.isClosed();
        } catch (SQLException e) {
            System.err.println("Error testing connection: " + e.getMessage());
            return false;
        }
    }
}
