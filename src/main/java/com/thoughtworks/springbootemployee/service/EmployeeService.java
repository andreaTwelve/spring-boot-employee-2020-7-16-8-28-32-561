package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.dto.EmployeeRequest;
import com.thoughtworks.springbootemployee.exception.ExceptionMessage;
import com.thoughtworks.springbootemployee.exception.NotExistEmployeeException;
import com.thoughtworks.springbootemployee.exception.NotFoundIDException;
import com.thoughtworks.springbootemployee.mapper.EmployeeRequestMapper;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private EmployeeRequestMapper employeeRequestMapper;

//    public EmployeeService(EmployeeRepository employeeRepository) {
//        this.employeeRepository = employeeRepository;
//    }

    public List<Employee> findAll() {
        return this.employeeRepository.findAll();
    }

    public Employee addEmployee(EmployeeRequest employeeRequest) {
        Employee employee = employeeRequestMapper.toEmployee(employeeRequest);
        if (Objects.nonNull(employee)) {
            return employeeRepository.save(employee);
        }
        return null;
    }

    public Employee findEmployeeById(Integer employeeId) throws NotExistEmployeeException {
        Optional<Employee> employees = employeeRepository.findById(employeeId);
        if (employees.isPresent()) {
            return employees.get();
        }
        throw new NotExistEmployeeException(ExceptionMessage.NOT_EXISTS_EMPLOYEE);
    }

    public boolean deleteById(Integer employeeId) throws NotExistEmployeeException {
        if (Objects.nonNull(employeeId) && Objects.nonNull(findEmployeeById(employeeId))) {
            employeeRepository.deleteById(employeeId);
            return true;
        }
        return false;
    }

    public List<Employee> findAllByGender(String gender) {
        return employeeRepository.findAllByGender(gender);
    }

    public Employee updateEmployeeById(int employeeId, Employee updateEmployee) throws NotExistEmployeeException {
        Employee employee = findEmployeeById(employeeId);
        if (Objects.isNull(employee)) {
            throw new NotExistEmployeeException(ExceptionMessage.NOT_EXISTS_EMPLOYEE);
        }
        updateEmployee.setId(employeeId);
        return employeeRepository.save(updateEmployee);
    }

    public List<Employee> findAll(int page, int pageSize) {
        if (page < 1 || pageSize < 0) {
            return null;
        }
        return employeeRepository.findAll(PageRequest.of(page - 1, pageSize)).getContent();
    }
}
