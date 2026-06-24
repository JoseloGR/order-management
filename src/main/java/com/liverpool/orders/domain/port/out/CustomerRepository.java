package domain.port.out;

import domain.model.Customer;
import domain.valueobject.Email;
import domain.valueobject.UserId;

import java.util.Optional;

public interface CustomerRepository {
    Customer save(Customer customer);

    Optional<Customer> findByUserId(UserId userId);

    Optional<Customer> findByEmail(Email email);

    boolean existsByUserId(UserId userId);
}
