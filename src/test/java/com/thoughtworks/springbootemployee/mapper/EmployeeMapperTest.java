package com.thoughtworks.springbootemployee.mapper;

import com.thoughtworks.springbootemployee.dto.EmployeeRequest;
import com.thoughtworks.springbootemployee.model.Employee;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EmployeeMapperTest {
    //@Autowired
    private final EmployeeRequestMapper employeeRequestMapper = new EmployeeRequestMapper();

    @Test
    void should_return_employee_dto_when_to_employee_given_request_employee() {
        EmployeeRequest employeeRequest = new EmployeeRequest(1, "test", 18, "female", 900);
        Employee employeeDto = employeeRequestMapper.toEmployee(employeeRequest);
        //assertEquals(employeeDto.getId(), employeeRequest.getId());
        assertEquals(employeeDto.getName(), employeeRequest.getName());
        assertEquals(employeeDto.getAge(), employeeRequest.getAge());
        assertEquals(employeeDto.getGender(), employeeRequest.getGender());
        assertEquals(employeeDto.getSalary(), employeeRequest.getSalary());
    }

}
