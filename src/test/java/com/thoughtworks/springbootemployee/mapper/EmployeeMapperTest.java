package com.thoughtworks.springbootemployee.mapper;

import com.thoughtworks.springbootemployee.dto.EmployeeRequest;
import com.thoughtworks.springbootemployee.model.Employee;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EmployeeMapperTest {
    @Autowired
    private EmployeeRequest employeeRequest;

    @Autowired
    private EmployeeMapper employeeMapper;

    @Test
    void should_return_employee_dto_when_to_employee_given_request_employee() {
        Employee employeeDto = employeeMapper.toEmployee(employeeRequest);
        assertEquals(employeeDto.getId(), employeeRequest.getId());
        assertEquals(employeeDto.getName(), employeeRequest.getName());
        assertEquals(employeeDto.getAge(), employeeRequest.getAge());
        assertEquals(employeeDto.getGender(), employeeRequest.getGender());
        assertEquals(employeeDto.getId(), employeeRequest.getSalary());
    }

}
