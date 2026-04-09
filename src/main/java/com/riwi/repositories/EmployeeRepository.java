package com.riwi.repositories;

import com.riwi.models.Employee;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EmployeeRepository {
    private List<Employee> employees = new ArrayList<>();
    private Map<String, Employee> mapEmployees = new HashMap<>();

    public EmployeeRepository(Map<String, Employee> mapEmployees, List<Employee> employees) {
        this.mapEmployees = mapEmployees;
        this.employees = employees;
    }

    public void addEmployee(Employee employee){
        if (employee == null) {
            System.out.println("Input value is null");
            return;
        } else if (mapEmployees.containsKey(employee.getId())) {
            System.out.println("The ID " + employee.getId()+ " already exists");
        }else {
            employees.add(employee);
            mapEmployees.put(employee.getId(), employee);
        }
    }

    public List<Employee> getAllEmployees(){
        return new ArrayList<>(employees);
    }

    public Employee getEmployeeById(String id){
        return mapEmployees.get(id);
    }

    public boolean deleteEmployee(String id){
        Employee employee = mapEmployees.get(id);
        if(employee != null){
            System.out.println("Employee with id: : "+ employee.getId() + " deleted successfuly!.");
            employees.remove(employee);
            mapEmployees.remove(id);
            return true;
        }else{
            return false;
        }
    }
}
