package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.dto.EmployeeRequest;
import com.thoughtworks.springbootemployee.exception.NotFoundIDException;
import com.thoughtworks.springbootemployee.mapper.EmployeeRequestMapper;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import com.thoughtworks.springbootemployee.service.EmployeeService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class EmployeeServiceTest {
    private static List<Employee> employees = new ArrayList<>();
    @Mock
    EmployeeRepository employeeRepository;
    @Mock
    EmployeeRequestMapper employeeRequestMapper;

    @InjectMocks
    EmployeeService employeeService;



    @BeforeAll
    static void init() {
        for (int j = 0; j < 10; j++) {
            employees.add(new Employee(j, "Tom" + j, 18, j % 2 == 0 ? "male" : "female", j * 1000));
        }
    }

    @Test
    void should_return_employees_when_find_all_given_no_parameters() {
        //given

        when(employeeRepository.findAll()).thenReturn(employees);
        //when
        List<Employee> actualEmployees = employeeService.findAll();
        //then
        assertEquals(employees.size(), actualEmployees.size());
    }

    @Test
    void should_return_employees_when_add_employee_given_employee() {
        //given
        EmployeeRequest employeeRequest = new EmployeeRequest(1, "test", 18, "female", 900);
        Employee employee = employeeRequestMapper.toEmployee(employeeRequest);
        when(employeeRequestMapper.toEmployee(employeeRequest)).thenReturn(employee);
        when(employeeRepository.save(employee)).thenReturn(employee);
        //when
        Employee actualEmployee = employeeService.addEmployee(employeeRequest);
        //then
        assertEquals(employee, actualEmployee);
    }
//    @Test
//    void should_return_employee_when_find_employees_by_id_given_employee_id() {
//        //given
//        int employeeId = 1;
//        when(employeeRepository.findById(1)).thenReturn(Optional.of(new Employee(1, "test", 18, "female", 900)));
//        //when
//        Employee actualEmployee = employeeService.findEmployeeById(employeeId);
//        //then
//        assertNotNull(actualEmployee);
//        assertEquals(1, actualEmployee.getId());
//    }

//    @Test
//    void should_return_true_when_delete_employee_given_employee_id() {
//        //todo
//        //given
//        int employeeId = 1;
//        when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(new Employee(employeeId, "test", 18, "female", 900)));
//        //when
//        boolean isDelete = employeeService.deleteById(employeeId);
//        //then
//        assertTrue(isDelete);
//    }

    @Test
    void should_return_male_employees_when_find_employee_by_gender_given_male() {
        //given
        String gender = "male";
        when(employeeRepository.findAllByGender(gender)).thenReturn(Arrays.asList(
                new Employee(1, "test", 18, "male", 900),
                new Employee(2, "test", 18, "male", 900)));
        //when
        List<Employee> actualEmployees = employeeService.findAllByGender(gender);
        //then
        assertEquals(2, actualEmployees.size());
    }
//    @Test
//    void should_return_update_employee_when_update_employee_by_id_given_employee_id() throws NotFoundIDException {
//        //given
//        int employeeId = 1;
//        Employee updateEmployee = new Employee(employeeId, "test", 18, "male", 900);
//        when(employeeRepository.save(updateEmployee)).thenReturn(updateEmployee);
//        when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(updateEmployee));
//        //when
//        Employee employee = employeeService.updateEmployeeById(employeeId, updateEmployee);
//        //then
//        assertNotNull(employee);
//        assertEquals(updateEmployee, employee);
//    }

    @Test
    void should_return_employees_when_get_employee_by_paging_given_page_and_page_size() {
        //given
        int page = 1;
        int pageSize = 5;
        when(employeeRepository.findAll(PageRequest.of(page, pageSize).first())).thenReturn(new PageImpl<>(Arrays.asList(
                new Employee(1, "test", 18, "male", 900),
                new Employee(2, "test", 18, "male", 900))));
        //when
        List<Employee> employeeList = employeeService.findAll(page, pageSize);
        //then
        assertNotNull(employeeList);
        assertEquals(2, employeeList.size());
    }

    @Test
    void should_return_not_found_exception_when_update_employee_by_id_given_wrong_employee_id() {
        //given
        int employeeId = 1;
        Employee updateEmployee = new Employee(employeeId, "test", 18, "male", 900);
        when(employeeRepository.save(updateEmployee)).thenReturn(updateEmployee);
        when(employeeRepository.findById(2)).thenReturn(Optional.of(updateEmployee));
        //when
        NotFoundIDException notFoundIDException = assertThrows(NotFoundIDException.class, () -> {
            employeeService.updateEmployeeById(employeeId, updateEmployee);
        });
        //then
        assertEquals(NotFoundIDException.class, notFoundIDException.getClass());
    }
}
