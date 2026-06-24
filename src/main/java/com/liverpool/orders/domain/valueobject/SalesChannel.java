package com.liverpool.orders.domain.valueobject;

public class SalesChannel {
    public enum Channel {
        ONLINE,
        PHYSICAL
    }
    private final Channel value;

    public SalesChannel(final Channel value) {
        if (value == null) {
            throw new IllegalArgumentException("SalesChannel no puede ser nulo");
        }
        this.value = value;
    }

    public static SalesChannel fromString(final String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("El canal de venta no puede ser nulo o vacío");
        }
        try {
            return new SalesChannel(Channel.valueOf(value.toUpperCase().trim()));
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Canal de venta no reconocido: '" + value + "'. Valores válidos: online, physical");
        }
    }

    public Channel getValue() {
        return value;
    }
}
