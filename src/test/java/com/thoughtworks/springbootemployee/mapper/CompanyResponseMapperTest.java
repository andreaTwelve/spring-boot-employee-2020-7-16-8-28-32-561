package com.thoughtworks.springbootemployee.mapper;

import com.thoughtworks.springbootemployee.dto.CompanyResponseDto;
import com.thoughtworks.springbootemployee.model.Company;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;

public class CompanyResponseMapperTest {
    @Test
    void should_return_response_company_when_to_response_company_given_company() {
        Company company = new Company(1, "oocl", 1000);
        CompanyResponseMapper companyResponseMapper = new CompanyResponseMapper();
        CompanyResponseDto companyResponseDto = companyResponseMapper.toResponseCompany(company);
        assertEquals(companyResponseDto.getCompanyName(), company.getCompanyName());
        assertEquals(companyResponseDto.getEmployeesNumber(), company.getEmployeesNumber());
    }
}
