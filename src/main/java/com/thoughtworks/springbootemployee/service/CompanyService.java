package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.CompanyRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;


@Service
public class CompanyService {
    private final CompanyRepository companyRepository;

    public CompanyService(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }


    public List<Company> findAll() {
        return companyRepository.findAll();
    }

    public Company addCompany(Company company) {
        return companyRepository.save(company);
    }

    public Company findById(int companyId) {
        return companyRepository.findById(companyId).orElse(null);
    }

    public List<Company> findAll(int page, int pageSize) {
        if (page < 1 || pageSize < 0) {
            return null;
        }
        return companyRepository.findAll(PageRequest.of(page - 1, pageSize)).getContent();
    }

    public boolean deleteById(Integer companyId) {
        if (Objects.nonNull(companyId) && Objects.nonNull(findById(companyId))) {
            companyRepository.deleteById(companyId);
            return true;
        }
        return false;
    }

    public Company updateCompanyById(int companyId, Company updateCompany) {
        Company company = findById(companyId);
        if (Objects.nonNull(company)) {
            updateCompany.setId(companyId);
            return companyRepository.save(updateCompany);
        }
        return null;
    }

    public List<Employee> getEmployeesByCompanyId(int companyId) {
        Company company = findById(companyId);
        if (Objects.nonNull(company)) {
            return company.getEmployees();
        }
        return null;
    }
}
