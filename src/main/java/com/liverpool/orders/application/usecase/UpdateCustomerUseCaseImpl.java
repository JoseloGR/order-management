package com.liverpool.orders.application.usecase;

import com.liverpool.orders.domain.exception.CustomerAlreadyExistsException;
import com.liverpool.orders.domain.exception.CustomerNotFoundException;
import com.liverpool.orders.domain.model.Customer;
import com.liverpool.orders.domain.model.Order;
import com.liverpool.orders.domain.port.in.UpdateCustomerUseCase;
import com.liverpool.orders.domain.port.out.CustomerRepository;
import com.liverpool.orders.domain.port.out.OrdersExternalService;
import com.liverpool.orders.domain.valueobject.DeliveryAddress;
import com.liverpool.orders.domain.valueobject.Email;
import com.liverpool.orders.domain.valueobject.OrderRef;
import com.liverpool.orders.domain.valueobject.UserId;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class UpdateCustomerUseCaseImpl implements UpdateCustomerUseCase {
    private final CustomerRepository customerRepository;
    private final OrdersExternalService ordersExternalService;

    public UpdateCustomerUseCaseImpl(
            final CustomerRepository customerRepository,
            final OrdersExternalService ordersExternalService) {
        this.customerRepository = Objects.requireNonNull(customerRepository,
                "customerRepository no puede ser nulo");
        this.ordersExternalService = Objects.requireNonNull(ordersExternalService,
                "ordersExternalService no puede ser nulo");
    }

    @Override
    public Customer updateCustomerData(UpdateCustomerDataCommand command) {
        Objects.requireNonNull(command, "El comando de actualización no puede ser nulo");

        UserId userId = new UserId(command.getUserId());
        Email newEmail = new Email(command.getEmail());

        Customer existing = customerRepository.findByUserId(userId)
                .orElseThrow(() -> new CustomerNotFoundException(userId));

        boolean emailChanged = !existing.getEmail().equals(newEmail);
        if (emailChanged && customerRepository.findByEmail(newEmail).isPresent()) {
            throw new CustomerAlreadyExistsException("email", command.getEmail());
        }

        Customer updated = new Customer.Builder()
                .userId(userId)
                .firstName(command.getFirstName())
                .lastNamePaternal(command.getLastNamePaternal())
                .lastNameMaternal(command.getLastNameMaternal())
                .email(newEmail)
                .deliveryAddress(new DeliveryAddress(command.getDeliveryAddress()))
                .orders(existing.getOrders())
                .build();

        return customerRepository.save(updated);
    }

    @Override
    public Customer syncCustomerOrders(String userId) {
        Objects.requireNonNull(userId, "userId no puede ser nulo");
        UserId userIdVO = new UserId(userId);

        Customer existing = customerRepository.findByUserId(userIdVO)
                .orElseThrow(() -> new CustomerNotFoundException(userIdVO));

        List<Order> externalOrders = ordersExternalService.findOrdersByUserId(userIdVO);

        List<OrderRef> freshOrderRefs = externalOrders.stream()
                .map(Order::getOrderRef)
                .collect(Collectors.toList());

        Customer synced = existing.withSyncedOrders(freshOrderRefs);

        return customerRepository.save(synced);
    }
}
