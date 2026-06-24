package domain.port.in;

import domain.model.Customer;
import domain.model.Order;

import java.util.List;

public interface GetCustomerUseCase {

    Customer getCustomer(String userId);
    CustomerWithOrdersResult getCustomerWithOrders(String userId);

    final class CustomerWithOrdersResult {

        private final Customer customer;
        private final List<Order> orders;

        public CustomerWithOrdersResult(final Customer customer, final List<Order> orders) {
            this.customer = customer;
            this.orders = java.util.Collections.unmodifiableList(
                    new java.util.ArrayList<>(orders));
        }

        public Customer getCustomer() { return customer; }

        public List<Order> getOrders() { return orders; }
    }
}
