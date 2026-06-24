package com.liverpool.orders.infrastructure.adapter.out.rest.client;

import com.liverpool.orders.domain.exception.ExternalServiceException;
import com.liverpool.orders.domain.model.Order;
import com.liverpool.orders.domain.model.OrderItem;
import com.liverpool.orders.domain.port.out.OrdersExternalService;
import com.liverpool.orders.domain.valueobject.OrderRef;
import com.liverpool.orders.domain.valueobject.OrderStatus;
import com.liverpool.orders.domain.valueobject.SalesChannel;
import com.liverpool.orders.domain.valueobject.UserId;
import com.liverpool.orders.infrastructure.adapter.out.rest.dto.PedidoDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class PedidosRestAdapter implements OrdersExternalService {

    private static final String SERVICE_NAME = "pedidos";
    private final RestTemplate restTemplate;
    private final String pedidosUrl;

    public PedidosRestAdapter(
            final RestTemplate restTemplate,
            @Value("${external.services.pedidos.url}") final String pedidosUrl) {
        this.restTemplate = restTemplate;
        this.pedidosUrl = pedidosUrl;
    }

    @Override
    public List<Order> findAllOrders() {
        try {
            PedidoDto[] response = restTemplate.getForObject(pedidosUrl, PedidoDto[].class);
            if (response == null) {
                return Collections.emptyList();
            }
            return Arrays.stream(response)
                    .filter(dto -> dto.getOrderRef() != null && dto.getUserId() != null)
                    .map(this::toDomain)
                    .collect(Collectors.toList());
        } catch (RestClientException e) {
            throw new ExternalServiceException(SERVICE_NAME, e);
        }
    }

    @Override
    public List<Order> findOrdersByUserId(final UserId userId) {
        return findAllOrders().stream()
                .filter(order -> order.getUserId().equals(userId))
                .collect(Collectors.toList());
    }

    private Order toDomain(final PedidoDto dto) {
        List<OrderItem> stubItems = dto.getItems() == null
                ? Collections.emptyList()
                : dto.getItems().stream()
                .map(this::toStubItem)
                .collect(Collectors.toList());

        Order.Builder builder = new Order.Builder()
                .orderRef(new OrderRef(dto.getOrderRef()))
                .userId(new UserId(dto.getUserId()))
                .orderStatus(OrderStatus.fromString(dto.getOrderStatus()))
                .salesChannel(SalesChannel.fromString(dto.getCanal()))
                .storeName(dto.getStoreName())
                .marketPlace(dto.isMarketPlace())
                .giftRegistry(dto.isGiftRegistry())
                .items(stubItems);

        return builder.build();
    }

    private OrderItem toStubItem(final String compositeItemId) {
        String skuId = compositeItemId.contains("-")
                ? compositeItemId.substring(compositeItemId.lastIndexOf('-') + 1)
                : compositeItemId;

        return new OrderItem(
                compositeItemId,
                skuId,
                "PENDING_ENRICHMENT",
                1,
                "PENDING_ENRICHMENT"
        );
    }
}
