package com.liverpool.orders.domain.model;

import com.liverpool.orders.domain.valueobject.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Order {
    private final OrderRef orderRef;
    private final UserId userId;
    private final OrderStatus orderStatus;
    private final SalesChannel salesChannel;
    private final String storeName;
    private final boolean marketPlace;
    private final boolean giftRegistry;
    private final DeliveryAddress deliveryAddress;
    private final List<OrderItem> items;

    private Order(final Builder builder) {
        this.orderRef = builder.orderRef;
        this.userId = builder.userId;
        this.orderStatus = builder.orderStatus;
        this.salesChannel = builder.salesChannel;
        this.storeName = builder.storeName;
        this.marketPlace = builder.marketPlace;
        this.giftRegistry = builder.giftRegistry;
        this.deliveryAddress = builder.deliveryAddress;
        this.items = Collections.unmodifiableList(new ArrayList<>(builder.items));
    }

    public boolean containsItemWithDisplayName(final String searchTerm) {
        if (searchTerm == null || searchTerm.isBlank()) {
            return false;
        }
        String normalizedTerm = normalize(searchTerm);
        return items.stream()
                .anyMatch(item -> normalize(item.getDisplayName()).contains(normalizedTerm));
    }

    public boolean storeNameContains(final String searchTerm) {
        if (storeName == null || searchTerm == null || searchTerm.isBlank()) {
            return false;
        }
        return normalize(storeName).contains(normalize(searchTerm));
    }

    private String normalize(final String text) {
        return java.text.Normalizer
                .normalize(text, java.text.Normalizer.Form.NFD)
                .replaceAll("[\\p{InCombiningDiacriticalMarks}]", "")
                .toLowerCase()
                .replaceAll("[,.]", "");
    }

    public OrderRef getOrderRef() {
        return orderRef;
    }

    public UserId getUserId() {
        return userId;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public SalesChannel getSalesChannel() {
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

    public DeliveryAddress getDeliveryAddress() {
        return deliveryAddress;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public static final class Builder {

        private OrderRef orderRef;
        private UserId userId;
        private OrderStatus orderStatus;
        private SalesChannel salesChannel;
        private String storeName;
        private boolean marketPlace;
        private boolean giftRegistry;
        private DeliveryAddress deliveryAddress;
        private final List<OrderItem> items = new ArrayList<>();

        public Builder orderRef(final OrderRef orderRef) {
            this.orderRef = orderRef;
            return this;
        }

        public Builder userId(final UserId userId) {
            this.userId = userId;
            return this;
        }

        public Builder orderStatus(final OrderStatus orderStatus) {
            this.orderStatus = orderStatus;
            return this;
        }

        public Builder salesChannel(final SalesChannel salesChannel) {
            this.salesChannel = salesChannel;
            return this;
        }

        public Builder storeName(final String storeName) {
            this.storeName = storeName;
            return this;
        }

        public Builder marketPlace(final boolean marketPlace) {
            this.marketPlace = marketPlace;
            return this;
        }

        public Builder giftRegistry(final boolean giftRegistry) {
            this.giftRegistry = giftRegistry;
            return this;
        }

        public Builder deliveryAddress(final DeliveryAddress deliveryAddress) {
            this.deliveryAddress = deliveryAddress;
            return this;
        }

        public Builder addItem(final OrderItem item) {
            this.items.add(item);
            return this;
        }

        public Builder items(final List<OrderItem> items) {
            this.items.clear();
            this.items.addAll(items);
            return this;
        }

        public Order build() {
            Objects.requireNonNull(orderRef, "orderRef es obligatorio para construir un Order");
            Objects.requireNonNull(userId, "userId es obligatorio para construir un Order");
            Objects.requireNonNull(orderStatus, "orderStatus es obligatorio para construir un Order");
            Objects.requireNonNull(salesChannel, "salesChannel es obligatorio para construir un Order");
            if (items.isEmpty()) {
                throw new IllegalStateException(
                        "Un pedido debe contener al menos un ítem: " + orderRef);
            }
            return new Order(this);
        }
    }
}
