package com.thoughtworks.springbootemployee.mapper;

import com.thoughtworks.springbootemployee.dto.EmployeeResponseDto;
import com.thoughtworks.springbootemployee.model.Employee;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EmployeeResponseMapperTest {
    @Test
    void should_return_response_employee_when_to_response_employee_given_employee() {
        Employee employee = new Employee(1, "a", 12, "male", 111111);
        EmployeeResponseMapper employeeResponseMapper = new EmployeeResponseMapper();
        EmployeeResponseDto employeeResponseDto = employeeResponseMapper.toResponseEmployee(employee);
        assertEquals(employeeResponseDto.getName(), employee.getName());
        assertEquals(employeeResponseDto.getAge(), employee.getAge());
        assertEquals(employeeResponseDto.getGender(), employee.getGender());
        assertEquals(employeeResponseDto.getSalary(), employee.getSalary());
    }
}
