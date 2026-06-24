package domain.valueobject;

public final class UserId {
    private final String value;

    public UserId(final String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("UserId no puede ser nulo ni vacío");
        }
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
