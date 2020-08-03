package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.dto.EmployeeRequest;
import com.thoughtworks.springbootemployee.dto.EmployeeResponseDto;
import com.thoughtworks.springbootemployee.exception.ExceptionMessage;
import com.thoughtworks.springbootemployee.exception.NotExistEmployeeException;
import com.thoughtworks.springbootemployee.mapper.EmployeeRequestMapper;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

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
        Employee employee = new Employee(1, "test", 18, "female", 900);
        when(employeeRequestMapper.toEmployee(employeeRequest)).thenReturn(employee);
        when(employeeRepository.save(employee)).thenReturn(employee);
        //when
        Employee actualEmployee = employeeService.addEmployee(employeeRequest);
        //then
        assertNotNull(employee);
        assertEquals(employee, actualEmployee);
    }
    @Test
    void should_return_employee_when_find_employees_by_id_given_employee_id() throws NotExistEmployeeException {
        //given
        int employeeId = 1;
        when(employeeRepository.findById(1)).thenReturn(Optional.of(new Employee(1, "test", 18, "female", 900)));
        //when
        EmployeeResponseDto employeeResponseDto = employeeService.findEmployeeById(employeeId);
        //then
        assertNotNull(employeeResponseDto);
        assertEquals(1, employeeResponseDto.getId());
    }

    @Test
    void should_return_employee_when_find_employees_by_id_given_wrong_employee_id() {
        //given
        Employee employee = new Employee();
        when(employeeRepository.findById(anyInt())).thenReturn(Optional.empty());
        //when
        NotExistEmployeeException notExistEmployeeException = assertThrows(NotExistEmployeeException.class, () -> {
            employeeService.findEmployeeById(anyInt());
        });
        //then
        assertEquals(ExceptionMessage.NOT_EXISTS_EMPLOYEE.getValue(), notExistEmployeeException.getMessage());
    }

    @Test
    void should_return_none_when_delete_employee_given_employee_id() {
        //given
        NotExistEmployeeException notExistEmployeeException = assertThrows(NotExistEmployeeException.class, () -> {
            employeeService.deleteById(anyInt());
        });
        //when
        //then
        assertEquals(ExceptionMessage.NOT_EXISTS_EMPLOYEE.getValue(), notExistEmployeeException.getMessage());
    }

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


    @Test
    void should_return_update_employee_when_update_employee_by_id_given_employee_id() throws NotExistEmployeeException {
        //given
        int employeeId = 1;
        EmployeeRequest employeeRequest = new EmployeeRequest(1, "test", 18, "male", 900, 1);
        when(employeeRepository.findById(anyInt())).thenReturn(Optional.of(new Employee()));
        //when
        Employee updatedEmployee = employeeService.updateEmployeeById(employeeId, employeeRequest);
        //then
        //assertNotNull(updatedEmployee);
    }

//    @Test
//    void should_return_employees_when_get_employee_by_paging_given_page_and_page_size() {
//        //given
//        int page = 1;
//        int pageSize = 5;
//        when(employeeRepository.findAll(PageRequest.of(page, pageSize).first())).thenReturn(new PageImpl<>(Arrays.asList(
//                new Employee(1, "test", 18, "male", 900),
//                new Employee(2, "test", 18, "male", 900))));
//        //when
//        List<Employee> employeeList = employeeService.findAll(page, pageSize);
//        //then
//        assertNotNull(employeeList);
//        assertEquals(2, employeeList.size());
//    }

    @Test
    void should_return_not_exist_exception_when_update_employee_by_id_given_wrong_employee_id() {
        //given
        int employeeId = 1;
        EmployeeRequest employeeRequest = new EmployeeRequest(1, "test", 18, "male", 900);
        Employee updateEmployee = employeeRequestMapper.toEmployee(employeeRequest);
        when(employeeRequestMapper.toEmployee(employeeRequest)).thenReturn(updateEmployee);
        //Employee updateEmployee = new Employee(employeeId, "test", 18, "male", 900);
        when(employeeRepository.save(updateEmployee)).thenReturn(updateEmployee);
        //when(employeeRepository.findById(1)).thenReturn(Optional.of(updateEmployee));
        //when
        NotExistEmployeeException notExistEmployeeException = assertThrows(NotExistEmployeeException.class, () -> {
            employeeService.updateEmployeeById(employeeId, employeeRequest);
        });
        //then
        assertEquals(NotExistEmployeeException.class, notExistEmployeeException.getClass());
    }
}
