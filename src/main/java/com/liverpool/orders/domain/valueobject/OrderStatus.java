package com.liverpool.orders.domain.valueobject;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class OrderStatus {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private final LocalDate estimatedDeliveryDate;

    public OrderStatus(final LocalDate estimatedDeliveryDate) {
        if (estimatedDeliveryDate == null) {
            throw new IllegalArgumentException("La fecha estimada de entrega no puede ser nula");
        }
        this.estimatedDeliveryDate = estimatedDeliveryDate;
    }

    public static OrderStatus fromString(final String rawDate) {
        if (rawDate == null || rawDate.isBlank()) {
            throw new IllegalArgumentException(
                    "El valor de orderStatus no puede ser nulo o vacío");
        }
        try {
            LocalDate date = LocalDate.parse(rawDate.trim(), FORMATTER);
            return new OrderStatus(date);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException(
                    "El valor de orderStatus no tiene el formato esperado (yyyy-MM-dd): '"
                            + rawDate + "'", e);
        }
    }

    public LocalDate getEstimatedDeliveryDate() {
        return estimatedDeliveryDate;
    }

    public String toFormattedString() {
        return estimatedDeliveryDate.format(FORMATTER);
    }

    public boolean isOverdue() {
        return estimatedDeliveryDate.isBefore(LocalDate.now());
    }

}
