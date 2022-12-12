package com.udemy.sbforswe.springbootforsoftwareengineers.config;

public class ErrorMessage {
    private String errorMessage;

    public ErrorMessage(String message) {
        this.errorMessage = message;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
