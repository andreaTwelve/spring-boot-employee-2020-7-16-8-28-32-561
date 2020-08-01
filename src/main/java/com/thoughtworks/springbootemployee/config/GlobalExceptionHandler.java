package com.thoughtworks.springbootemployee.config;

import com.thoughtworks.springbootemployee.exception.NotExistEmployeeException;
import com.thoughtworks.springbootemployee.exception.NotFoundIDException;
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
    public String notExistEmployeeHandler(NotExistEmployeeException notExistEmployeeException) {
        return notExistEmployeeException.getMessage();
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(value = NotFoundIDException.class)
    public String notFoundExceptionHandler(NotFoundIDException notFoundExceptionHandler) {
        return notFoundExceptionHandler.getMessage();
    }
}
