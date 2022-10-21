package com.javaguides.tutorial.EmployeeManagementSystem.service;

import com.javaguides.tutorial.EmployeeManagementSystem.model.Employee;

import java.util.List;

public interface EmployeeService {
    List<Employee> getAllEmployees();
    void saveEmployee(Employee employee);
    Employee getEmployeeById(Long id);
    void deleteEmployeeById(Long id);
}
