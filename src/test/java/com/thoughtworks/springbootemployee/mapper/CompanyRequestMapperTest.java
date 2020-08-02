package com.thoughtworks.springbootemployee.mapper;

import com.thoughtworks.springbootemployee.dto.CompanyRequest;
import com.thoughtworks.springbootemployee.dto.EmployeeRequest;
import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CompanyRequestMapperTest {
    //@Autowired
    private final CompanyRequestMapper companyRequestMapper = new CompanyRequestMapper();

    @Test
    void should_return_employee_dto_when_to_employee_given_request_employee() {
        CompanyRequest companyRequest = new CompanyRequest(1, "oocl", 1000);
        Company companyDto = companyRequestMapper.toCompany(companyRequest);
        assertEquals(companyDto.getCompanyName(), companyRequest.getCompanyName());
        assertEquals(companyDto.getEmployeesNumber(), companyRequest.getEmployeesNumber());
    }
}
