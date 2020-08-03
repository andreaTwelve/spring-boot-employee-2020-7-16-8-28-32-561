package com.thoughtworks.springbootemployee.config;

import com.thoughtworks.springbootemployee.exception.NotExistCompanyException;
import com.thoughtworks.springbootemployee.exception.NotExistEmployeeException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    @ExceptionHandler(value = NotExistEmployeeException.class)
    //// TODO: 8/3/2020 exception
    public String notExistEmployeeHandler(NotExistEmployeeException notExistEmployeeException) {
        return notExistEmployeeException.getMessage();
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    @ExceptionHandler(value = NotExistCompanyException.class)
    public String notExistCompanyHandler(NotExistCompanyException notExistCompanyException) {
        return notExistCompanyException.getMessage();
    }
}
