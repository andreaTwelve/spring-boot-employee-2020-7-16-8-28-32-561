package com.thoughtworks.springbootemployee.mapper;

import com.thoughtworks.springbootemployee.dto.CompanyResponseDto;
import com.thoughtworks.springbootemployee.model.Company;
import org.springframework.beans.BeanUtils;

public class CompanyResponseMapper {
    public CompanyResponseDto toResponseCompany(Company company) {
        CompanyResponseDto companyResponseDto = new CompanyResponseDto();
        BeanUtils.copyProperties(company, companyResponseDto);
        return companyResponseDto;
    }
}
