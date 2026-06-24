package com.liverpool.orders.domain.port.in;

import com.liverpool.orders.domain.model.Customer;

public interface CreateCustomerUseCase {

    final class CreateCustomerCommand {
        private final String userId;
        private final String firstName;
        private final String lastNamePaternal;
        private final String lastNameMaternal;
        private final String email;
        private final String deliveryAddress;

        public CreateCustomerCommand(
                final String userId,
                final String firstName,
                final String lastNamePaternal,
                final String lastNameMaternal,
                final String email,
                final String deliveryAddress
        ) {
            this.userId = userId;
            this.firstName = firstName;
            this.lastNamePaternal = lastNamePaternal;
            this.lastNameMaternal = lastNameMaternal;
            this.email = email;
            this.deliveryAddress = deliveryAddress;
        }

        public String getUserId() {
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

        public String getEmail() {
            return email;
        }

        public String getDeliveryAddress() {
            return deliveryAddress;
        }
    }

    Customer createCustomer(CreateCustomerCommand command);
}
