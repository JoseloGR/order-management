package com.liverpool.orders.application.helper;

import com.liverpool.orders.domain.model.Order;
import com.liverpool.orders.domain.model.OrderItem;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class OrderEnrichmentHelper {
    private OrderEnrichmentHelper(){}

    public static List<Order> enrichAll(
            final List<Order> rawOrders,
            final List<OrderItem> enrichedItems) {

        Map<String, OrderItem> itemMap = buildItemMap(enrichedItems);
        return rawOrders.stream()
                .map(order -> enrich(order, itemMap))
                .collect(Collectors.toList());
    }

    public static Map<String, OrderItem> buildItemMap(final List<OrderItem> items) {
        return items.stream()
                .collect(Collectors.toMap(
                        OrderItem::getItemId,
                        item -> item,
                        (existing, duplicate) -> existing
                ));
    }

    public static Order enrich(final Order raw, final Map<String, OrderItem> itemMap) {
        List<OrderItem> resolved = raw.getItems().stream()
                .map(stub -> itemMap.getOrDefault(stub.getItemId(), stub))
                .collect(Collectors.toList());

        return new Order.Builder()
                .orderRef(raw.getOrderRef())
                .userId(raw.getUserId())
                .orderStatus(raw.getOrderStatus())
                .salesChannel(raw.getSalesChannel())
                .storeName(raw.getStoreName())
                .marketPlace(raw.isMarketPlace())
                .giftRegistry(raw.isGiftRegistry())
                .deliveryAddress(raw.getDeliveryAddress())
                .items(resolved)
                .build();
    }
}
