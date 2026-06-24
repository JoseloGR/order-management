package domain.valueobject;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class OrderStatus {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE;
    private final LocalDate estimatedDeliveryDate;

    public OrderStatus(final LocalDate estimatedDeliveryDate) {
        if (estimatedDeliveryDate == null) {
            throw new IllegalArgumentException("La fecha estimada de entrega no puede ser nula");
        }
        this.estimatedDeliveryDate = estimatedDeliveryDate;
    }

    public static OrderStatus fromIsoString(final String isoDateString) {
        if (isoDateString == null || isoDateString.isBlank()) {
            throw new IllegalArgumentException("El valor de orderStatus no puede ser nulo ni vacío");
        }
        try {
            return new OrderStatus(LocalDate.parse(isoDateString.trim(), FORMATTER));
        } catch(DateTimeParseException e) {
            throw new IllegalArgumentException("Formato de fecha inválido en orderStatus. Se esperaba formato yyyy-MM-dd", e);
        }
    }

    public LocalDate getEstimatedDeliveryDate() {
        return estimatedDeliveryDate;
    }

    public String toIsoString() {
        return estimatedDeliveryDate.format(FORMATTER);
    }

    public boolean isOverdue() {
        return estimatedDeliveryDate.isBefore(LocalDate.now());
    }

}
