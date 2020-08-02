package com.thoughtworks.springbootemployee.mapper;

import com.thoughtworks.springbootemployee.dto.EmployeeResponseDto;
import com.thoughtworks.springbootemployee.model.Employee;
import org.springframework.beans.BeanUtils;

public class EmployeeResponseMapper {
    public EmployeeResponseDto toResponseEmployee(Employee employee) {
        EmployeeResponseDto employeeResponseDto = new EmployeeResponseDto();
        BeanUtils.copyProperties(employee, employeeResponseDto);
        return employeeResponseDto;
    }
}
