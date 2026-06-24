package com.liverpool.orders.application.usecase;

import com.liverpool.orders.domain.exception.CustomerAlreadyExistsException;
import com.liverpool.orders.domain.model.Customer;
import com.liverpool.orders.domain.port.in.CreateCustomerUseCase;
import com.liverpool.orders.domain.port.out.CustomerRepository;
import com.liverpool.orders.domain.valueobject.DeliveryAddress;
import com.liverpool.orders.domain.valueobject.Email;
import com.liverpool.orders.domain.valueobject.UserId;

import java.util.Objects;

public class CreateCustomerUseCaseImpl implements CreateCustomerUseCase {

    private final CustomerRepository customerRepository;

    public CreateCustomerUseCaseImpl(final CustomerRepository customerRepository) {
        this.customerRepository = Objects.requireNonNull(customerRepository, "customerRepository no puede ser nulo");
    }

    @Override
    public Customer createCustomer(final CreateCustomerCommand command) {
        Objects.requireNonNull(command, "El comando de creación no puede ser nulo");
        UserId userId = new UserId(command.getUserId());
        Email email = new Email(command.getEmail());

        if (customerRepository.existsByUserId(userId)) {
            throw new CustomerAlreadyExistsException("userId", command.getUserId());
        }

        if (customerRepository.findByEmail(email).isPresent()) {
            throw new CustomerAlreadyExistsException("email", command.getEmail());
        }

        Customer customer = new Customer.Builder()
                .userId(userId)
                .firstName(command.getFirstName())
                .lastNamePaternal(command.getLastNamePaternal())
                .lastNameMaternal(command.getLastNameMaternal())
                .email(email)
                .deliveryAddress(new DeliveryAddress(command.getDeliveryAddress()))
                .build();

        return customerRepository.save(customer);
    }
}
