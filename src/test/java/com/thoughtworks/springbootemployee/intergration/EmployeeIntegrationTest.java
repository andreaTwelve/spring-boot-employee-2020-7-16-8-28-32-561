package com.thoughtworks.springbootemployee.intergration;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.CompanyRepository;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class EmployeeIntegrationTest {
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
    void should_return_employee_when_add_employee_given_employee() throws Exception {
        //given
        Company company = new Company(1, "test", 100);
        Company newCompany = companyRepository.save(company);
        String jsonEmployee = "{\n" +
                "            \"name\": \"Change\",\n" +
                "            \"age\": 18,\n" +
                "            \"gender\": \"male\",\n" +
                "            \"salary\": 55,\n" +
                "            \"companyId\": " + newCompany.getId() + "\n" +
                "        }";
        mockMvc.perform(post("/employees").contentType(MediaType.APPLICATION_JSON).content(jsonEmployee))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Change"))
                .andExpect(jsonPath("$.age").value(18))
                .andExpect(jsonPath("$.gender").value("male"))
                .andExpect(jsonPath("$.salary").value(55))
                .andExpect(jsonPath("$.companyId").value(newCompany.getId()));

        //when//then
    }

    @Test
    void should_return_employees_when_get_employees_given_none() throws Exception {
        //given
        Company company = new Company(1, "test", 100);
        companyRepository.save(company);
        Employee employee = new Employee(1, "tom", 12, "male", 1111, company.getId());
        employeeRepository.save(employee);

        mockMvc.perform(get("/employees"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").isNumber())
                .andExpect(jsonPath("$[0].name").value("tom"))
                .andExpect(jsonPath("$[0].age").value(12))
                .andExpect(jsonPath("$[0].gender").value("male"))
                .andExpect(jsonPath("$[0].salary").value(1111))
                .andExpect(jsonPath("$[0].companyId").value(1));

        //when//then
    }

    @Test
    void should_return_employee_when_get_employee_by_id_given_employee_id() throws Exception {
        //given
        Company company = new Company(1, "test", 100);
        companyRepository.save(company);
        Employee employee = new Employee(1, "tom", 12, "male", 1111, company.getId());
        employeeRepository.save(employee);

        mockMvc.perform(get("/employees/{id}", 2).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("tom"))
                .andExpect(jsonPath("$.age").value(12))
                .andExpect(jsonPath("$.gender").value("male"))
                .andExpect(jsonPath("$.salary").value(1111))
                .andExpect(jsonPath("$.companyId").value(1));

        //when//then
    }

    @Test
    void should_return_employee_when_get_employee_by_gender_given_employee_gender() throws Exception {
        //given
        Company company = new Company(1, "test", 100);
        companyRepository.save(company);
        Employee employee = new Employee(1, "tom", 12, "male", 1111, company.getId());
        employeeRepository.save(employee);

        mockMvc.perform(get("/employees").contentType(MediaType.APPLICATION_JSON).param("gender", "male"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("tom"))
                .andExpect(jsonPath("$[0].age").value(12))
                .andExpect(jsonPath("$[0].gender").value("male"))
                .andExpect(jsonPath("$[0].salary").value(1111))
                .andExpect(jsonPath("$[0].companyId").value(1));

        //when//then
    }

    @Test
    void should_return_employees_when_get_employees_by_page_given_page_and_page_size() throws Exception {
        //given
        Company company = new Company(1, "test", 100);
        Company newCompany = companyRepository.save(company);
        employeeRepository.save(new Employee(1, "tom", 12, "male", 1111, newCompany.getId()));
        employeeRepository.save(new Employee(1, "tom", 12, "male", 1111, newCompany.getId()));
        employeeRepository.save(new Employee(1, "tom", 12, "male", 1111, newCompany.getId()));
        employeeRepository.save(new Employee(1, "tom", 12, "male", 1111, newCompany.getId()));
        employeeRepository.save(new Employee(1, "tom", 12, "male", 1111, newCompany.getId()));
        employeeRepository.save(new Employee(1, "tom", 12, "male", 1111, newCompany.getId()));

        mockMvc.perform(get("/employees").contentType(MediaType.APPLICATION_JSON)
                .param("page", "1").param("pageSize", "5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(5)))
                .andExpect(jsonPath("$[0].name").value("tom"))
                .andExpect(jsonPath("$[0].age").value(12))
                .andExpect(jsonPath("$[0].gender").value("male"))
                .andExpect(jsonPath("$[0].salary").value(1111))
                .andExpect(jsonPath("$[0].companyId").value(newCompany.getId()));

        //when//then
    }

    @Test
    void should_return_none_when_delete_employee_given_employee_id() throws Exception {
        //given
        Company company = new Company(1, "test", 100);
        Company newCompany = companyRepository.save(company);
        Employee employee = new Employee(1, "tom", 12, "male", 1111, newCompany.getId());
        employeeRepository.save(employee);


        mockMvc.perform(delete("/employees/2").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        //when//then
    }

    @Test
    void should_return_updated_employee_when_update_employee_by_gender_given_employee_and_id() throws Exception {
        //given
        Company company = new Company(1, "test", 100);
        companyRepository.save(company);
        Employee employee = new Employee(1, "tom", 12, "male", 1111, company.getId());
        employeeRepository.save(employee);
        String jsonEmployee = "{\n" +
                "            \"name\": \"Change\",\n" +
                "            \"age\": 18,\n" +
                "            \"gender\": \"male\",\n" +
                "            \"salary\": 55,\n" +
                "            \"companyId\": " + company.getId() + "\n" +
                "        }";

        mockMvc.perform(put("/employees/2").contentType(MediaType.APPLICATION_JSON).content(jsonEmployee))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Change"))
                .andExpect(jsonPath("$.age").value(18))
                .andExpect(jsonPath("$.gender").value("male"))
                .andExpect(jsonPath("$.salary").value(55))
                .andExpect(jsonPath("$.companyId").value(1));

        //when//then
    }
}
