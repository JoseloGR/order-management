package com.liverpool.orders.application.usecase;

import com.liverpool.orders.application.helper.OrderEnrichmentHelper;
import com.liverpool.orders.domain.exception.CustomerNotFoundException;
import com.liverpool.orders.domain.model.Customer;
import com.liverpool.orders.domain.model.Order;
import com.liverpool.orders.domain.model.OrderItem;
import com.liverpool.orders.domain.port.in.GetCustomerUseCase;
import com.liverpool.orders.domain.port.out.CustomerRepository;
import com.liverpool.orders.domain.port.out.ItemsExternalService;
import com.liverpool.orders.domain.port.out.OrdersExternalService;
import com.liverpool.orders.domain.valueobject.UserId;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class GetCustomerUseCaseImpl implements GetCustomerUseCase {
    private final CustomerRepository customerRepository;
    private final OrdersExternalService ordersExternalService;
    private final ItemsExternalService itemsExternalService;

    public GetCustomerUseCaseImpl(
            final CustomerRepository customerRepository,
            final OrdersExternalService ordersExternalService,
            final ItemsExternalService itemsExternalService) {
        this.customerRepository = Objects.requireNonNull(customerRepository,
                "customerRepository no puede ser nulo");
        this.ordersExternalService = Objects.requireNonNull(ordersExternalService,
                "ordersExternalService no puede ser nulo");
        this.itemsExternalService = Objects.requireNonNull(itemsExternalService,
                "itemsExternalService no puede ser nulo");
    }

    @Override
    public Customer getCustomer(String userId) {
        Objects.requireNonNull(userId, "userId no puede ser nulo");
        UserId userIdVO = new UserId(userId);
        return customerRepository.findByUserId(userIdVO)
                .orElseThrow(() -> new CustomerNotFoundException(userIdVO));
    }

    @Override
    public CustomerWithOrdersResult getCustomerWithOrders(String userId) {
        Objects.requireNonNull(userId, "userId no puede ser nulo");
        UserId userIdVO = new UserId(userId);

        Customer customer = customerRepository.findByUserId(userIdVO)
                .orElseThrow(() -> new CustomerNotFoundException(userIdVO));

        List<Order> rawOrders = ordersExternalService.findOrdersByUserId(userIdVO);

        if (rawOrders.isEmpty()) {
            return new CustomerWithOrdersResult(customer, List.of());
        }

        List<String> allCompositeItemIds = rawOrders.stream()
                .flatMap(order -> order.getItems().stream())
                .map(OrderItem::getItemId)
                .distinct()
                .collect(Collectors.toList());

        List<OrderItem> enrichedItems = itemsExternalService
                .findByItemIds(allCompositeItemIds);

        List<Order> enrichedOrders = OrderEnrichmentHelper.enrichAll(rawOrders, enrichedItems);

        return new CustomerWithOrdersResult(customer, enrichedOrders);
    }
}
