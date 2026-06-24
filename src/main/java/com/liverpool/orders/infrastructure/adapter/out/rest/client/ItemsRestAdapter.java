package com.liverpool.orders.infrastructure.adapter.out.rest.client;

import com.liverpool.orders.domain.exception.ExternalServiceException;
import com.liverpool.orders.domain.model.OrderItem;
import com.liverpool.orders.domain.port.out.ItemsExternalService;
import com.liverpool.orders.infrastructure.adapter.out.rest.dto.ItemDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class ItemsRestAdapter implements ItemsExternalService {

    private static final String SERVICE_NAME = "items";
    private final RestTemplate restTemplate;
    private final String itemsUrl;

    public ItemsRestAdapter(
            final RestTemplate restTemplate,
            @Value("${external.services.items.url}") final String itemsUrl) {
        this.restTemplate = restTemplate;
        this.itemsUrl = itemsUrl;
    }

    @Override
    public List<OrderItem> findAllItems() {
        try {
            ItemDto[] response = restTemplate.getForObject(itemsUrl, ItemDto[].class);
            if (response == null) {
                return Collections.emptyList();
            }
            return Arrays.stream(response)
                    .filter(dto -> dto.getItemId() != null)
                    .map(this::toDomain)
                    .collect(Collectors.toList());
        } catch (RestClientException e) {
            throw new ExternalServiceException(SERVICE_NAME, e);
        }
    }

    @Override
    public Optional<OrderItem> findByItemId(String itemId) {
        return findAllItems().stream()
                .filter(item -> item.getItemId().equals(itemId))
                .findFirst();
    }

    @Override
    public List<OrderItem> findByItemIds(List<String> itemIds) {
        if (itemIds == null || itemIds.isEmpty()) {
            return Collections.emptyList();
        }
        Set<String> idSet = itemIds.stream().collect(Collectors.toSet());
        return findAllItems().stream()
                .filter(item -> idSet.contains(item.getItemId()))
                .collect(Collectors.toList());
    }

    private OrderItem toDomain(final ItemDto dto) {
        return new OrderItem(
                dto.getItemId(),
                dto.getSkuId(),
                dto.getDisplayName(),
                dto.getQuantity(),
                dto.getDeliveryStatus()
        );
    }
}
