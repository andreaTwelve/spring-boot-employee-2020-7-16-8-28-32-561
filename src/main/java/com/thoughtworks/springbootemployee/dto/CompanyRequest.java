package com.thoughtworks.springbootemployee.dto;

public class CompanyRequest {
    private String companyName;
    private Integer employeesNumber;

    public CompanyRequest() {
    }

    public CompanyRequest(Integer id, String companyName, Integer employeesNumber) {
        this.companyName = companyName;
        this.employeesNumber = employeesNumber;
    }

    public String getCompanyName() {
        return companyName;
    }

    public Integer getEmployeesNumber() {
        return employeesNumber;
    }
}
