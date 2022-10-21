package com.javaguides.tutorial.EmployeeManagementSystem.repository;

import com.javaguides.tutorial.EmployeeManagementSystem.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {}
