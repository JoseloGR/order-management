package domain.model;

import domain.valueobject.DeliveryAddress;
import domain.valueobject.Email;
import domain.valueobject.OrderRef;
import domain.valueobject.UserId;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Customer {
    private final UserId userId;
    private final String firstName;
    private final String lastNamePaternal;
    private final String lastNameMaternal;
    private final Email email;
    private final DeliveryAddress deliveryAddress;
    private final List<OrderRef> orders;

    private Customer(final Builder builder) {
        this.userId = builder.userId;
        this.firstName = builder.firstName;
        this.lastNamePaternal = builder.lastNamePaternal;
        this.lastNameMaternal = builder.lastNameMaternal;
        this.email = builder.email;
        this.deliveryAddress = builder.deliveryAddress;
        this.orders = Collections.unmodifiableList(new ArrayList<>(builder.orders));
    }

    public Customer withOrderRef(final OrderRef orderRef) {
        Objects.requireNonNull(orderRef, "orderRef no puede ser nulo");
        if (orders.contains(orderRef)) {
            return this;
        }
        List<OrderRef> newOrders = new ArrayList<>(orders);
        newOrders.add(orderRef);
        return new Builder()
                .userId(this.userId)
                .firstName(this.firstName)
                .lastNamePaternal(this.lastNamePaternal)
                .lastNameMaternal(this.lastNameMaternal)
                .email(this.email)
                .deliveryAddress(this.deliveryAddress)
                .orders(newOrders)
                .build();
    }

    public String getFullName() {
        StringBuilder sb = new StringBuilder(firstName)
                .append(' ').append(lastNamePaternal);
        if (lastNameMaternal != null && !lastNameMaternal.isBlank()) {
            sb.append(' ').append(lastNameMaternal);
        }
        return sb.toString();
    }

    public UserId getUserId() {
        return userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastNamePaternal() {
        return lastNamePaternal;
    }

    public String getLastNameMaternal() {
        return lastNameMaternal;
    }

    public Email getEmail() {
        return email;
    }

    public DeliveryAddress getDeliveryAddress() {
        return deliveryAddress;
    }

    public List<OrderRef> getOrders() {
        return orders;
    }

    public static final class Builder {
        private UserId userId;
        private String firstName;
        private String lastNamePaternal;
        private String lastNameMaternal;
        private Email email;
        private DeliveryAddress deliveryAddress;
        private List<OrderRef> orders = new ArrayList<>();

        public Builder userId(final UserId userId) {
            this.userId = userId;
            return this;
        }

        public Builder firstName(final String firstName) {
            this.firstName = firstName;
            return this;
        }

        public Builder lastNamePaternal(final String lastNamePaternal) {
            this.lastNamePaternal = lastNamePaternal;
            return this;
        }

        public Builder lastNameMaternal(final String lastNameMaternal) {
            this.lastNameMaternal = lastNameMaternal;
            return this;
        }

        public Builder email(final Email email) {
            this.email = email;
            return this;
        }

        public Builder deliveryAddress(final DeliveryAddress deliveryAddress) {
            this.deliveryAddress = deliveryAddress;
            return this;
        }

        public Builder orders(final List<OrderRef> orders) {
            this.orders = new ArrayList<>(orders);
            return this;
        }

        public Customer build() {
            Objects.requireNonNull(userId, "userId es obligatorio para construir un Customer");
            Objects.requireNonNull(email, "email es obligatorio para construir un Customer");
            if (firstName == null || firstName.isBlank()) {
                throw new IllegalArgumentException("firstName es obligatorio para un Customer");
            }
            if (lastNamePaternal == null || lastNamePaternal.isBlank()) {
                throw new IllegalArgumentException("lastNamePaternal es obligatorio para un Customer");
            }
            return new Customer(this);
        }

    }
}
