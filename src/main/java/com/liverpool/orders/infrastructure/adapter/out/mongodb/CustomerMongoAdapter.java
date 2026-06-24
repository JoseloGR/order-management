package com.liverpool.orders.infrastructure.adapter.out.mongodb;

import com.liverpool.orders.domain.model.Customer;
import com.liverpool.orders.domain.port.out.CustomerRepository;
import com.liverpool.orders.domain.valueobject.DeliveryAddress;
import com.liverpool.orders.domain.valueobject.Email;
import com.liverpool.orders.domain.valueobject.OrderRef;
import com.liverpool.orders.domain.valueobject.UserId;
import com.liverpool.orders.infrastructure.adapter.out.mongodb.document.CustomerDocument;
import com.liverpool.orders.infrastructure.adapter.out.mongodb.repository.SpringDataCustomerRepository;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class CustomerMongoAdapter implements CustomerRepository {
    private final SpringDataCustomerRepository springDataCustomerRepository;

    public CustomerMongoAdapter(final SpringDataCustomerRepository springDataCustomerRepository) {
        this.springDataCustomerRepository = springDataCustomerRepository;
    }

    @Override
    public Customer save(final Customer customer) {
        CustomerDocument doc = toDocument(customer);
        CustomerDocument saved = springDataCustomerRepository.save(doc);
        return toDomain(saved);
    }

    @Override
    public Optional<Customer> findByUserId(final UserId userId) {
        return springDataCustomerRepository.findById(userId.getValue())
                .map(this::toDomain);
    }

    @Override
    public Optional<Customer> findByEmail(final Email email) {
        return springDataCustomerRepository.findByEmail(email.getValue())
                .map(this::toDomain);
    }

    @Override
    public boolean existsByUserId(final UserId userId) {
        return springDataCustomerRepository.existsById(userId.getValue());
    }

    private CustomerDocument toDocument(final Customer customer) {
        List<String> orderRefs = customer.getOrders().stream()
                .map(OrderRef::getValue)
                .collect(Collectors.toList());

        return new CustomerDocument(
                customer.getUserId().getValue(),
                customer.getFirstName(),
                customer.getLastNamePaternal(),
                customer.getLastNameMaternal(),
                customer.getEmail().getValue(),
                customer.getDeliveryAddress() != null
                        ? customer.getDeliveryAddress().getStreet()
                        : null,
                orderRefs
        );
    }

    private Customer toDomain(final CustomerDocument doc) {
        List<OrderRef> orderRefs = doc.getOrders() == null
                ? new ArrayList<>()
                : doc.getOrders().stream()
                .map(OrderRef::new)
                .collect(Collectors.toList());

        return new Customer.Builder()
                .userId(new UserId(doc.getUserId()))
                .firstName(doc.getFirstName())
                .lastNamePaternal(doc.getLastNamePaternal())
                .lastNameMaternal(doc.getLastNameMaternal())
                .email(new Email(doc.getEmail()))
                .deliveryAddress(doc.getDeliveryAddress() != null
                        ? new DeliveryAddress(doc.getDeliveryAddress())
                        : null)
                .orders(orderRefs)
                .build();
    }
}
