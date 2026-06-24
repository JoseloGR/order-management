package com.liverpool.orders.domain.exception;

import com.liverpool.orders.domain.valueobject.UserId;

public class CustomerNotFoundException extends RuntimeException {
    private final String userId;

    public CustomerNotFoundException(final UserId userId) {
        super("No se encontró ningún cliente con userId: " + userId.getValue());
        this.userId = userId.getValue();
    }

    public CustomerNotFoundException(final String userId) {
        super("No se encontró ningún cliente con userId: " + userId);
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }
}
