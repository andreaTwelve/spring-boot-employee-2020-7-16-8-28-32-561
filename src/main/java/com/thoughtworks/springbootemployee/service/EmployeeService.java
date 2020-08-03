package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.dto.EmployeeRequest;
import com.thoughtworks.springbootemployee.dto.EmployeeResponseDto;
import com.thoughtworks.springbootemployee.exception.ExceptionMessage;
import com.thoughtworks.springbootemployee.exception.NotExistEmployeeException;
import com.thoughtworks.springbootemployee.mapper.EmployeeRequestMapper;
import com.thoughtworks.springbootemployee.mapper.EmployeeResponseMapper;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private EmployeeRequestMapper employeeRequestMapper;

    private EmployeeResponseMapper employeeResponseMapper = new EmployeeResponseMapper();

    public EmployeeService(EmployeeRepository employeeRepository, EmployeeRequestMapper employeeRequestMapper) {
        this.employeeRepository = employeeRepository;
        this.employeeRequestMapper = employeeRequestMapper;
    }

    //    public EmployeeService(EmployeeRepository employeeRepository) {
//        this.employeeRepository = employeeRepository;
//    }

    public List<Employee> findAll() {
        return this.employeeRepository.findAll();
    }

    public Employee addEmployee(EmployeeRequest employeeRequest) {
        Employee employee = employeeRequestMapper.toEmployee(employeeRequest);
        return employeeRepository.save(employee);
    }

    public EmployeeResponseDto findEmployeeById(Integer employeeId) throws NotExistEmployeeException {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new NotExistEmployeeException(ExceptionMessage.NOT_EXISTS_EMPLOYEE));
        return employeeResponseMapper.toResponseEmployee(employee);
    }

    public void deleteById(Integer employeeId) throws NotExistEmployeeException {
        employeeRepository.findById(employeeId).orElseThrow(() -> new NotExistEmployeeException(ExceptionMessage.NOT_EXISTS_EMPLOYEE));
        employeeRepository.deleteById(employeeId);
    }

    public List<Employee> findAllByGender(String gender) {
        return employeeRepository.findAllByGender(gender);
    }

    public Employee updateEmployeeById(int employeeId, EmployeeRequest employeeRequest) throws NotExistEmployeeException {
        if (employeeId != employeeRequest.getId()) {
            return null;
        }
        Employee employeeDto = employeeRequestMapper.toEmployee(employeeRequest);
        Employee employee = employeeRepository.findById(employeeRequest.getId()).orElse(null);
        if (Objects.isNull(employee)) {
            throw new NotExistEmployeeException(ExceptionMessage.NOT_EXISTS_EMPLOYEE);
        }

        //employeeDto.setId(employeeId);
        return employeeRepository.save(employeeDto);
    }

    public List<Employee> findAll(int page, int pageSize) {
        if (page < 1 || pageSize < 0) {
            return null;
        }
        return employeeRepository.findAll(PageRequest.of(page - 1, pageSize)).getContent();
    }
}
