package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.dto.EmployeeRequest;
import com.thoughtworks.springbootemployee.dto.EmployeeResponseDto;
import com.thoughtworks.springbootemployee.exception.NotExistEmployeeException;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.service.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/employees")
public class EmployeeController {
    @Resource
    private EmployeeService employeeService;

    @GetMapping
    public List<Employee> getEmployees(@RequestParam(required = false) Integer page,
                                       @RequestParam(required = false) Integer pageSize,
                                       @RequestParam(required = false) String gender) {
        if (Objects.nonNull(gender)) {
            return employeeService.findAllByGender(gender);
        }
        if (Objects.nonNull(page) && Objects.nonNull(pageSize)) {
            return employeeService.findAll(page, pageSize);
        }
        return employeeService.findAll();
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public Employee createEmployee(@RequestBody EmployeeRequest employeeRequest) {
        return employeeService.addEmployee(employeeRequest);
    }

    @GetMapping("/{id}")
    public EmployeeResponseDto getEmployee(@PathVariable(name = "id") int id) throws NotExistEmployeeException {
        return employeeService.findEmployeeById(id);
    }


    @PutMapping("/{id}")
    public Employee updateEmployee(@PathVariable(name = "id") Integer id, @RequestBody EmployeeRequest employeeRequest) throws NotExistEmployeeException {
        return employeeService.updateEmployeeById(id, employeeRequest);

    }

    @DeleteMapping("/{id}")
    public void deleteEmployee(@PathVariable(name = "id") int id) throws NotExistEmployeeException {
        employeeService.deleteById(id);
    }

}
