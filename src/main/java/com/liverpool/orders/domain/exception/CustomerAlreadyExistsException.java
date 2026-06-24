package com.liverpool.orders.domain.exception;

public class CustomerAlreadyExistsException extends RuntimeException {
    private final String field;
    private final String value;

    public CustomerAlreadyExistsException(final String field, final String value) {
        super("Ya existe un cliente con " + field + ": " + value);
        this.field = field;
        this.value = value;
    }

    public String getField() {
        return field;
    }

    public String getValue() {
        return value;
    }
}
