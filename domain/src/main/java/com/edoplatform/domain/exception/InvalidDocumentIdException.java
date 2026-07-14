package com.edoplatform.domain.exception;

public class InvalidDocumentIdException extends RuntimeException {

    private final String invalidValue;

    public InvalidDocumentIdException(String invalidValue, Throwable cause) {
        super(String.format("Invalid document ID format: '%s'. Expected valid UUID.", invalidValue), cause);
        this.invalidValue = invalidValue;
    }

    public String getInvalidValue() {
        return invalidValue;
    }
}
