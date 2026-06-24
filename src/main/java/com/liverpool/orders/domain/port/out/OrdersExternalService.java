package domain.port.out;

import domain.model.Order;
import domain.valueobject.UserId;

import java.util.List;

public interface OrdersExternalService {

    List<Order> findAllOrders();

    List<Order> findOrdersByUserId(UserId userId);

}
