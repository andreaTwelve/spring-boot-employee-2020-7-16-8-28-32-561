package com.thoughtworks.springbootemployee.exception;

public class NotExistCompanyException extends Exception {
    private String errorMessage;
    public NotExistCompanyException(ExceptionMessage notExistsCompany) {
        this.errorMessage = notExistsCompany.getValue();
    }

    @Override
    public String getMessage() {
        return this.errorMessage;
    }
}
