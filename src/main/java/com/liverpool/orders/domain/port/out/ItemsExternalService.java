package com.liverpool.orders.domain.port.out;

import com.liverpool.orders.domain.model.OrderItem;

import java.util.List;
import java.util.Optional;

public interface ItemsExternalService {

    List<OrderItem> findAllItems();

    Optional<OrderItem> findByItemId(String itemId);

    List<OrderItem> findByItemIds(List<String> itemIds);
}
