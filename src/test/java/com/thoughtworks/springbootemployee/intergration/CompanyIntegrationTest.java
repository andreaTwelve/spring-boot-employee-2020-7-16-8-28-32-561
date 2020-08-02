package com.thoughtworks.springbootemployee.intergration;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.CompanyRepository;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CompanyIntegrationTest {
    @Autowired
    CompanyRepository companyRepository;
    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired
    MockMvc mockMvc;

    @AfterEach
    public void after() {
        companyRepository.deleteAll();
        employeeRepository.deleteAll();
    }

    @Test
    void should_return_new_company_when_add_company_given_company() throws Exception {
        //given
        String jsonCompany = "{\n" +
                "    \"companyName\":\"test5\",\n" +
                "    \"employeesNumber\":200\n" +
                "}";
        mockMvc.perform(post("/companies").contentType(MediaType.APPLICATION_JSON).content(jsonCompany))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.companyName").value("test5"))
                .andExpect(jsonPath("$.employeesNumber").value(200));
        //when
        //then
    }

    @Test
    void should_return_companies_when_get_companies_given_none() throws Exception {
        //given
        companyRepository.save(new Company(1, "oocl1", 1001));
        companyRepository.save(new Company(2, "oocl2", 1002));
        companyRepository.save(new Company(3, "oocl3", 1003));

        mockMvc.perform(get("/companies").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").isNumber())
                .andExpect(jsonPath("$[0].companyName").value("oocl1"))
                .andExpect(jsonPath("$[0].employeesNumber").value(1001));

        //when//then
    }

    @Test
    void should_return_employees_when_get_employees_by_id_given_company_id() throws Exception {
        //given
        Company company = new Company(1, "test", 100);
        Company newCompany = companyRepository.save(company);
        Employee newEmployee = employeeRepository.save(new Employee(1, "andrea", 12, "female", 121212, newCompany.getId()));
        Employee newEmployee1 = employeeRepository.save(new Employee(1, "andrea1", 11, "male", 121212, newCompany.getId()));
        newCompany.setEmployees(Arrays.asList(newEmployee, newEmployee1));


        mockMvc.perform(get("/companies/{id}/employees", newCompany.getId()).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name").value("andrea"))
                .andExpect(jsonPath("$[0].age").value(12))
                .andExpect(jsonPath("$[0].gender").value("female"))
                .andExpect(jsonPath("$[0].salary").value(121212))
                .andExpect(jsonPath("$[1].name").value("andrea1"))
                .andExpect(jsonPath("$[1].age").value(11))
                .andExpect(jsonPath("$[1].gender").value("male"))
                .andExpect(jsonPath("$[1].companyId").value(newEmployee1.getCompanyId()));
        //when//then
    }

    @Test
    void should_return_company_when_get_company_by_id_given_company_id() throws Exception {
        //given
        Company company = new Company(1, "test", 100);
        Company newCompany = companyRepository.save(company);

        mockMvc.perform(get("/companies/{id}", newCompany.getId()).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.companyName").value("test"))
                .andExpect(jsonPath("$.employeesNumber").value(100));

        //when//then
    }

    @Test
    void should_return_companies_when_get_companies_by_page_given_page_and_page_size() throws Exception {
        //given
        Company newCompany1 = companyRepository.save(new Company(1, "oocl1", 1001));
        Company newCompany2 = companyRepository.save(new Company(2, "test2", 1002));
        Company newCompany3 = companyRepository.save(new Company(3, "test3", 1003));
        Company newCompany4 = companyRepository.save(new Company(4, "test4", 1004));
        Company newCompany5 = companyRepository.save(new Company(5, "test5", 1005));
        Company newCompany6 = companyRepository.save(new Company(6, "test6", 1006));

        mockMvc.perform(get("/companies").contentType(MediaType.APPLICATION_JSON)
                .param("page", "1").param("pageSize", "5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(5)));

        //when//then
    }

    @Test
    void should_return_none_when_delete_company_by_id_given_company_id() throws Exception {
        //given
        Company company = new Company(1, "test", 100);
        Company newCompany = companyRepository.save(company);
        Employee employee = new Employee(1, "tom", 12, "male", 1111, newCompany.getId());
        employeeRepository.save(employee);


        mockMvc.perform(delete("/companies/{id}", newCompany.getId()).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        //when//then
    }

    @Test
    void should_return_updated_company_when_update_company_given_company_id() throws Exception {
        Company company = companyRepository.save(new Company(1, "oocl", 1000));
        String jsonCompany = "{\n" +
                "    \"companyName\": \"oocl\",\n" +
                "    \"employeesNumber\": 10001\n" +
                "}";
        mockMvc.perform(put("/companies/{id}", company.getId()).contentType(MediaType.APPLICATION_JSON).content(jsonCompany))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.companyName").value("oocl"))
                .andExpect(jsonPath("$.employeesNumber").value(10001));
    }
}
