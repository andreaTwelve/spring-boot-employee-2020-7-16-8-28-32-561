package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.dto.CompanyRequest;
import com.thoughtworks.springbootemployee.dto.CompanyResponseDto;
import com.thoughtworks.springbootemployee.exception.ExceptionMessage;
import com.thoughtworks.springbootemployee.exception.NotExistCompanyException;
import com.thoughtworks.springbootemployee.mapper.CompanyRequestMapper;
import com.thoughtworks.springbootemployee.mapper.CompanyResponseMapper;
import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;


@Service
public class CompanyService {
    private CompanyRequestMapper companyRequestMapper;

    private CompanyResponseMapper companyResponseMapper = new CompanyResponseMapper();

    @Autowired
    CompanyRepository companyRepository;

    public CompanyService(CompanyRepository companyRepository,CompanyRequestMapper companyRequestMapper) {
        this.companyRepository = companyRepository;
        this.companyRequestMapper = companyRequestMapper;
    }


    public List<Company> findAll() {
        return companyRepository.findAll();
    }

    public Company addCompany(CompanyRequest companyRequest) {
        Company company = companyRequestMapper.toCompany(companyRequest);
        return companyRepository.save(company);
    }

    public CompanyResponseDto findById(int companyId) throws NotExistCompanyException {
        Company company = companyRepository.findById(companyId).orElse(null);
        if (Objects.isNull(company)) {
            throw new NotExistCompanyException(ExceptionMessage.NOT_EXISTS_COMPANY);
        }
        return companyResponseMapper.toResponseCompany(company);
    }

    public List<Company> findAll(int page, int pageSize) {
        if (page < 1 || pageSize < 0) {
            return null;
        }
        return companyRepository.findAll(PageRequest.of(page - 1, pageSize)).getContent();
    }

    public boolean deleteById(Integer companyId) {
        if (Objects.nonNull(companyId) && Objects.nonNull(companyRepository.findById(companyId).orElse(null))) {
            companyRepository.deleteById(companyId);
            return true;
        }
        return false;
    }

    public Company updateCompanyById(int companyId, Company updateCompany) {
        Company company = companyRepository.findById(companyId).orElse(null);
        if (Objects.nonNull(company)) {
            updateCompany.setId(companyId);
            return companyRepository.save(updateCompany);
        }
        return null;
    }

    public List<Employee> getEmployeesByCompanyId(int companyId) {
        Company company = companyRepository.findById(companyId).orElse(null);
        if (Objects.nonNull(company)) {
            return company.getEmployees();
        }
        return null;
    }
}
