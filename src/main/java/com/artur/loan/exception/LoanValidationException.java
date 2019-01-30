package com.artur.loan.exception;

import java.util.List;

public class LoanValidationException extends RuntimeException {

    private final List<String> errorMessages;

    public LoanValidationException(List<String> errorMessages) {
        this.errorMessages = errorMessages;
    }

    public List<String> getErrorMessages() {
        return errorMessages;
    }
}
