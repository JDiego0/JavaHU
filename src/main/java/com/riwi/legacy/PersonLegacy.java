package com.riwi.legacy;

/**
 * Traditional abstract class with open inheritance (Legacy Style - Java 8/11)
 * Any class can extend this without restrictions
 */
public abstract class PersonLegacy {
    protected int id;
    protected String name;
    
    public PersonLegacy(int id, String name) {
        this.id = id;
        this.name = name;
    }
    
    // Getters
    public int getId() { return id; }
    public String getName() { return name; }
    
    // Abstract method to be implemented by subclasses
    public abstract String getRole();
    
    // Concrete method
    public String getDisplayName() {
        return name + " (ID: " + id + ")";
    }
}
