package com.liverpool.orders.domain.port.out;

import com.liverpool.orders.domain.model.Customer;
import com.liverpool.orders.domain.valueobject.Email;
import com.liverpool.orders.domain.valueobject.UserId;

import java.util.Optional;

public interface CustomerRepository {
    Customer save(Customer customer);

    Optional<Customer> findByUserId(UserId userId);

    Optional<Customer> findByEmail(Email email);

    boolean existsByUserId(UserId userId);
}
