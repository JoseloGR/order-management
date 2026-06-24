package com.liverpool.orders.domain.port.out;

import com.liverpool.orders.domain.model.Order;
import com.liverpool.orders.domain.valueobject.UserId;

import java.util.List;

public interface OrdersExternalService {

    List<Order> findAllOrders();

    List<Order> findOrdersByUserId(UserId userId);

}
