package com.liverpool.orders.domain.valueobject;

public class OrderRef {
    private final String value;

    public OrderRef(final String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("OrderRef no puede ser nulo ni vacío");
        }
        this.value = value.trim();
    }

    public String getValue() {
        return value;
    }
}
