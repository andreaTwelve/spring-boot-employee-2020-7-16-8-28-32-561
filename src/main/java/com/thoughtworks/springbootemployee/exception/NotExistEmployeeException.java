package com.thoughtworks.springbootemployee.exception;

public class NotExistEmployeeException extends Exception {
    private String errorMessage;

    public NotExistEmployeeException(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public NotExistEmployeeException(ExceptionMessage notExistsEmployee) {
        this.errorMessage = notExistsEmployee.getValue();
    }

    @Override
    public String getMessage() {
        return this.errorMessage;
    }
}
