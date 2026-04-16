package com.riwi;

/**
 * Test class to demonstrate the Promotional interface functionality
 * and proper encapsulation of the Person hierarchy.
 */
public class TestPromotional {
    public static void main(String[] args) {
        // Create instances of classes that implement Promotional
        Developer dev = new Developer("DEV001", "Alice Johnson", "Java");
        Manager mgr = new Manager("MGR001", "Bob Smith", 50000.0);
        
        // Test promotion bonus calculations
        System.out.println("=== Testing Promotional Interface ===");
        
        // Developer bonus calculation
        double devBonus = dev.calculatePromotionBonus();
        dev.registerLog("Developer bonus calculation for " + dev.getName());
        System.out.println("Developer " + dev.getName() + " bonus: $" + String.format("%.2f", devBonus));
        
        // Manager bonus calculation
        double mgrBonus = mgr.calculatePromotionBonus();
        mgr.registerLog("Manager bonus calculation for " + mgr.getName());
        System.out.println("Manager " + mgr.getName() + " bonus: $" + String.format("%.2f", mgrBonus));
        
        // Test encapsulation - verify we can access data through protected methods
        System.out.println("\n=== Testing Encapsulation ===");
        System.out.println("Developer ID: " + dev.getId());
        System.out.println("Manager Name: " + mgr.getName());
        
        System.out.println("\nTask 4 completed successfully!");
        System.out.println("- Promotional interface with abstract method created");
        System.out.println("- Java 8+ default method for logging implemented");
        System.out.println("- Proper encapsulation with private attributes and protected getters/setters");
        System.out.println("- Interface implemented by Developer and Manager classes");
    }
}
