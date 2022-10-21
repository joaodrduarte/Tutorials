package com.javaguides.tutorial.EmployeeManagementSystem.service;

import com.javaguides.tutorial.EmployeeManagementSystem.model.Employee;
import com.javaguides.tutorial.EmployeeManagementSystem.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLOutput;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService{

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    @Override
    public void saveEmployee(Employee employee) {
        this.employeeRepository.save(employee);
    }

    @Override
    public Employee getEmployeeById(Long id) {
        Optional optional = employeeRepository.findById(id);
        Employee employee = null;
        if(optional.isPresent()){
            employee = (Employee)optional.get();
        }else{
            throw new RuntimeException("Empployee not found for id: " + id);
        }
        return employee;
    }

    @Override
    public void deleteEmployeeById(Long id) {
        this.employeeRepository.deleteById(id);
    }
}
