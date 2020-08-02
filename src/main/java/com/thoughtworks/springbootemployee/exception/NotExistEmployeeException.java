package com.thoughtworks.springbootemployee.exception;

public class NotExistEmployeeException extends Exception {
    private String errorMessage;

    public NotExistEmployeeException(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public NotExistEmployeeException(ExceptionMessage notExistsEmployee) {
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
