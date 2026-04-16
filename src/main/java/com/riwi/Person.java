package com.riwi;

/**
 * Sealed classes offer greater API design security compared to open inheritance because:
 * 1. They explicit restrict which classes can extend the base class, preventing uncontrolled extensions.
 * 2. The allow the compiler to throughly check all possible subtypes in pattern matching
 * 3. they facilitate the maintenance and evolution of the code by knowing  all possible cases.
 * 4. It prevents violations of Liskov substitution principle by controlling the hierarchy
 */
public sealed abstract class Person permits Employee, ExternConsultant, Developer, Manager
{
    private String id;
    private String name;

    protected  String getId(){
        return id;
    }

    protected void setId(String id){
        this.id = id;
    }

    protected String getName(){
        return name;
    }

    protected  void setName(String name){
        this.name = name;
    }

}
