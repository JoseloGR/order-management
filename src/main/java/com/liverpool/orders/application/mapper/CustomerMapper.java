package com.liverpool.orders.application.mapper;

import com.liverpool.orders.domain.model.Customer;
import com.liverpool.orders.domain.model.Order;
import com.liverpool.orders.domain.model.OrderItem;
import com.liverpool.orders.domain.valueobject.OrderRef;

import java.util.List;
import java.util.stream.Collectors;

public final class CustomerMapper {

    private CustomerMapper() {}

    public static final class CustomerResponse {

        private final String userId;
        private final String firstName;
        private final String lastNamePaternal;
        private final String lastNameMaternal;
        private final String email;
        private final String deliveryAddress;
        private final List<String> orders;

        CustomerResponse(
                final String userId,
                final String firstName,
                final String lastNamePaternal,
                final String lastNameMaternal,
                final String email,
                final String deliveryAddress,
                final List<String> orders) {
            this.userId = userId;
            this.firstName = firstName;
            this.lastNamePaternal = lastNamePaternal;
            this.lastNameMaternal = lastNameMaternal;
            this.email = email;
            this.deliveryAddress = deliveryAddress;
            this.orders = orders;
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

        public List<String> getOrders() {
            return orders;
        }
    }

    public static final class CustomerWithOrdersResponse {
        private final CustomerResponse customer;
        private final List<OrderResponse> orders;

        CustomerWithOrdersResponse(
                final CustomerResponse customer,
                final List<OrderResponse> orders) {
            this.customer = customer;
            this.orders = orders;
        }
    }

    public static final class OrderResponse {

        private final String orderRef;
        private final String userId;
        private final String orderStatus;
        private final String salesChannel;
        private final String storeName;
        private final boolean marketPlace;
        private final boolean giftRegistry;
        private final List<OrderItemResponse> items;

        OrderResponse(final String orderRef,
                      final String userId,
                      final String orderStatus,
                      final String salesChannel,
                      final String storeName,
                      final boolean marketPlace,
                      final boolean giftRegistry,
                      final List<OrderItemResponse> items) {
            this.orderRef = orderRef;
            this.userId = userId;
            this.orderStatus = orderStatus;
            this.salesChannel = salesChannel;
            this.storeName = storeName;
            this.marketPlace = marketPlace;
            this.giftRegistry = giftRegistry;
            this.items = items;
        }

        public String getOrderRef() {
            return orderRef;
        }

        public String getUserId() {
            return userId;
        }

        public String getOrderStatus() {
            return orderStatus;
        }

        public String getSalesChannel() {
            return salesChannel;
        }

        public String getStoreName() {
            return storeName;
        }

        public boolean isMarketPlace() {
            return marketPlace;
        }

        public boolean isGiftRegistry() {
            return giftRegistry;
        }

        public List<OrderItemResponse> getItems() {
            return items;
        }
    }

    public static final class OrderItemResponse {
        private final String compositeItemId;
        private final String skuId;
        private final String displayName;
        private final int quantity;
        private final String deliveryStatus;

        OrderItemResponse(
                final String compositeItemId,
                final String skuId,
                final String displayName,
                final int quantity,
                final String deliveryStatus
        ) {
            this.compositeItemId = compositeItemId;
            this.skuId = skuId;
            this.displayName = displayName;
            this.quantity = quantity;
            this.deliveryStatus = deliveryStatus;
        }

        public String getCompositeItemId() {
            return compositeItemId;
        }

        public String getSkuId() {
            return skuId;
        }

        public String getDisplayName() {
            return displayName;
        }

        public int getQuantity() {
            return quantity;
        }

        public String getDeliveryStatus() {
            return deliveryStatus;
        }
    }

    public static CustomerResponse toResponse(final Customer customer) {
        return new CustomerResponse(
                customer.getUserId().getValue(),
                customer.getFirstName(),
                customer.getLastNamePaternal(),
                customer.getLastNameMaternal(),
                customer.getEmail().getValue(),
                customer.getDeliveryAddress() != null
                        ? customer.getDeliveryAddress().getStreet()
                        : null,
                customer.getOrders().stream()
                        .map(OrderRef::getValue)
                        .collect(Collectors.toList())
        );
    }

    public static CustomerWithOrdersResponse toResponseWithOrders(
            final Customer customer,
            final List<Order> orders) {
        return new CustomerWithOrdersResponse(
                toResponse(customer),
                orders.stream()
                        .map(CustomerMapper::toOrderResponse)
                        .collect(Collectors.toList())
        );
    }

    public static OrderResponse toOrderResponse(final Order order) {
        return new OrderResponse(
                order.getOrderRef().getValue(),
                order.getUserId().getValue(),
                order.getOrderStatus().toFormattedString(),
                order.getSalesChannel().getValue().name().toLowerCase(),
                order.getStoreName(),
                order.isMarketPlace(),
                order.isGiftRegistry(),
                order.getItems().stream()
                        .map(CustomerMapper::toItemResponse)
                        .collect(Collectors.toList())
        );
    }

    public static OrderItemResponse toItemResponse(final OrderItem item) {
        return new OrderItemResponse(
                item.getItemId(),
                item.getSkuId(),
                item.getDisplayName(),
                item.getQuantity(),
                item.getDeliveryStatus()
        );
    }
}
