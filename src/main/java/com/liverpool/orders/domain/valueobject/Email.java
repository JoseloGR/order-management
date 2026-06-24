package com.liverpool.orders.domain.valueobject;

import java.util.regex.Pattern;

public class Email {
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
    private final String value;

    public Email(final String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("El correo electrónico no puede ser nulo o vacío");
        }
        if (!EMAIL_PATTERN.matcher(value.trim()).matches()) {
            throw new IllegalArgumentException(
                    "El correo electrónico no tiene un formato válido: " + value);
        }
        this.value = value.trim().toLowerCase();
    }

    public String getValue() {
        return value;
    }
}
