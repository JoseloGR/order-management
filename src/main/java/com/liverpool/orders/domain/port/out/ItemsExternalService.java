package domain.port.out;

import domain.model.OrderItem;

import java.util.List;
import java.util.Optional;

public interface ItemsExternalService {

    List<OrderItem> findAllItems();

    Optional<OrderItem> findByItemId(String itemId);

}
