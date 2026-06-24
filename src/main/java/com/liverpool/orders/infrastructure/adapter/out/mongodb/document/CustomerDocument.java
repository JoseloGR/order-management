package com.liverpool.orders.infrastructure.adapter.out.mongodb.document;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Document(collection = "customers")
public class CustomerDocument {

    @Id
    private String userId;

    @Field("firstName")
    private String firstName;

    @Field("lastNamePaternal")
    private String lastNamePaternal;

    @Field("lastNameMaternal")
    private String lastNameMaternal;

    @Indexed(unique = true)
    @Field("email")
    private String email;

    @Field("deliveryAddress")
    private String deliveryAddress;

    @Field("orders")
    private List<String> orders;

    public CustomerDocument() {}

    public CustomerDocument(
            final String userId,
            final String firstName,
            final String lastNamePaternal,
            final String lastNameMaternal,
            final String email,
            final String deliveryAddress,
            final List<String> orders) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastNamePaternal = lastNamePaternal;
        this.lastNameMaternal = lastNameMaternal;
        this.email = email;
        this.deliveryAddress = deliveryAddress;
        this.orders = orders;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastNamePaternal() {
        return lastNamePaternal;
    }

    public void setLastNamePaternal(String lastNamePaternal) {
        this.lastNamePaternal = lastNamePaternal;
    }

    public String getLastNameMaternal() {
        return lastNameMaternal;
    }

    public void setLastNameMaternal(String lastNameMaternal) {
        this.lastNameMaternal = lastNameMaternal;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public List<String> getOrders() {
        return orders;
    }

    public void setOrders(List<String> orders) {
        this.orders = orders;
    }
}
