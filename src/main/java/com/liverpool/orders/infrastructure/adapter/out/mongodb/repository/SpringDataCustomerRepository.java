package com.liverpool.orders.infrastructure.adapter.out.mongodb.repository;

import com.liverpool.orders.infrastructure.adapter.out.mongodb.document.CustomerDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SpringDataCustomerRepository
        extends MongoRepository<CustomerDocument, String> {

    Optional<CustomerDocument> findByEmail(String email);

    boolean existsByUserId(String userId);

}
