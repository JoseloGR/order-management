package com.liverpool.orders.domain.model;

public class OrderItem {
    private final String itemId;
    private final String skuId;
    private final String displayName;
    private final int quantity;
    private final String deliveryStatus;

    public OrderItem(
            final String itemId,
            final String skuId,
            final String displayName,
            final int quantity,
            final String deliveryStatus) {
        if (itemId == null || itemId.isBlank()) {
            throw new IllegalArgumentException("El itemId no puede ser nulo o vacío");
        }
        if (skuId == null || skuId.isBlank()) {
            throw new IllegalArgumentException("El skuId no puede ser nulo o vacío");
        }
        if (displayName == null || displayName.isBlank()) {
            throw new IllegalArgumentException("El displayName no puede ser nulo o vacío");
        }
        if (quantity <= 0) {
            throw new IllegalArgumentException(
                    "La cantidad debe ser mayor a cero, se recibió: " + quantity);
        }
        if (deliveryStatus == null || deliveryStatus.isBlank()) {
            throw new IllegalArgumentException("El deliveryStatus no puede ser nulo o vacío");
        }
        this.itemId = itemId.trim();
        this.skuId = skuId.trim();
        this.displayName = displayName.trim();
        this.quantity = quantity;
        this.deliveryStatus = deliveryStatus.trim();
    }

    public String getItemId() {
        return itemId;
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
