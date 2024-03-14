package com.example.projection.services;

import com.example.projection.entities.Employee;

import java.util.Optional;

public interface EmployeeService {
    Optional<Employee> findOneById(int id);

    void save(Employee employee);
}
