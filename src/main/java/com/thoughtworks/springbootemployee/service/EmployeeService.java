package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class EmployeeService {
    private EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public List<Employee> findAll() {
        return this.employeeRepository.findAll();
    }

    public Employee addEmployee(Employee employee) {
        if (Objects.nonNull(employee)) {
            return employeeRepository.save(employee);
        }
        return null;
    }

    public Employee findEmployeeById(Integer employeeId) {
        return employeeRepository.findById(employeeId).orElse(null);
    }

    public boolean deleteById(Integer employeeId) {
        if (Objects.nonNull(employeeId) && Objects.nonNull(findEmployeeById(employeeId))) {
            employeeRepository.deleteById(employeeId);
            return true;
        }
        return false;
    }

    public List<Employee> findAllByGender(String gender) {
        return employeeRepository.findAllByGender(gender);
    }

    public Employee updateEmployeeById(int employeeId, Employee updateEmployee) {
        Employee employee = findEmployeeById(employeeId);
        if (Objects.nonNull(employee)) {
            updateEmployee.setId(employeeId);
            return employeeRepository.save(updateEmployee);
        }
        return null;
    }

    public List<Employee> findAll(int page, int pageSize) {
        if (page < 0 || pageSize < 0) {
            return null;
        }
        return employeeRepository.findAll(PageRequest.of(page + 1, pageSize)).getContent();
    }
}
