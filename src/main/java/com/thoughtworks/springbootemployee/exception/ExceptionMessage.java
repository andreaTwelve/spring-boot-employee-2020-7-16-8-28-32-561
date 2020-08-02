package com.thoughtworks.springbootemployee.exception;

public enum ExceptionMessage {
    NOT_EXISTS_EMPLOYEE("Not exists employee"),
    NOT_EXISTS_COMPANY("Not exists company");

    private final String value;

    ExceptionMessage(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

    public static ExceptionMessage fromValue(String value) {
        for (ExceptionMessage i : values()) {
            if (i.getValue().equals(value)) {
                return i;
            }
        }
        return null;
    }
}
