package domain.valueobject;

public class DeliveryAddress {
    private final String street;

    public DeliveryAddress(final String street) {
        if (street == null || street.isBlank()) {
            throw new IllegalArgumentException("La dirección de envío no puede ser nula ni vacía");
        }
        this.street = street.trim();
    }

    public String getStreet() {
        return street;
    }
}
