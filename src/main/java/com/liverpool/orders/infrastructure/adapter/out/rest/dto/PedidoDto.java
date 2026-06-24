package com.liverpool.orders.infrastructure.adapter.out.rest.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PedidoDto {

    @JsonProperty("id")
    private String id;

    @JsonProperty("orderRef")
    private String orderRef;

    @JsonProperty("userId")
    private String userId;

    @JsonProperty("canal")
    private String canal;

    @JsonProperty("orderStatus")
    private String orderStatus;

    @JsonProperty("marketPlace")
    private boolean marketPlace;

    @JsonProperty("giftRegistry")
    private boolean giftRegistry;

    @JsonProperty("storeName")
    private String storeName;

    @JsonProperty("items")
    private List<String> items;

    public PedidoDto() { }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrderRef() {
        return orderRef;
    }

    public void setOrderRef(String orderRef) {
        this.orderRef = orderRef;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCanal() {
        return canal;
    }

    public void setCanal(String canal) {
        this.canal = canal;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public boolean isMarketPlace() {
        return marketPlace;
    }

    public void setMarketPlace(boolean marketPlace) {
        this.marketPlace = marketPlace;
    }

    public boolean isGiftRegistry() {
        return giftRegistry;
    }

    public void setGiftRegistry(boolean giftRegistry) {
        this.giftRegistry = giftRegistry;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public List<String> getItems() {
        return items;
    }

    public void setItems(List<String> items) {
        this.items = items;
    }
}
