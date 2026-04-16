package com.riwi;

/**
 * Example demonstrating open inheritance - any class can extend PersonLegacy
 */
public class ManagerLegacy extends PersonLegacy {
    private String department;
    
    public ManagerLegacy(int id, String name, String department) {
        super(id, name);
        this.department = department;
    }
    
    @Override
    public String getRole() {
        return "Manager";
    }
    
    public String getDepartment() {
        return department;
    }
    
    @Override
    public String getDisplayName() {
        return super.getDisplayName() + " - " + department;
    }
}
