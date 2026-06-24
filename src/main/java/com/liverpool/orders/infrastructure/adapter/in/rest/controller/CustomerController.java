package com.liverpool.orders.infrastructure.adapter.in.rest.controller;

import com.liverpool.orders.application.mapper.CustomerMapper;
import com.liverpool.orders.domain.model.Customer;
import com.liverpool.orders.domain.port.in.CreateCustomerUseCase;
import com.liverpool.orders.domain.port.in.GetCustomerUseCase;
import com.liverpool.orders.domain.port.in.UpdateCustomerUseCase;
import com.liverpool.orders.infrastructure.adapter.in.rest.dto.CreateCustomerRequest;
import com.liverpool.orders.infrastructure.adapter.in.rest.dto.UpdateCustomerRequest;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/customers")
@Tag(name = "Customers", description="Gestión de clientes")
public class CustomerController {
    private final CreateCustomerUseCase createCustomerUseCase;
    private final GetCustomerUseCase getCustomerUseCase;
    private final UpdateCustomerUseCase updateCustomerUseCase;

    public CustomerController(
            final CreateCustomerUseCase createCustomerUseCase,
            final GetCustomerUseCase getCustomerUseCase,
            final UpdateCustomerUseCase updateCustomerUseCase
    ) {
        this.createCustomerUseCase = createCustomerUseCase;
        this.getCustomerUseCase = getCustomerUseCase;
        this.updateCustomerUseCase = updateCustomerUseCase;
    }

    @PostMapping
    public ResponseEntity<CustomerMapper.CustomerResponse> createCustomer(
            @Valid @RequestBody final CreateCustomerRequest request
    ) {
        CreateCustomerUseCase.CreateCustomerCommand command = new CreateCustomerUseCase.CreateCustomerCommand(
                request.getUserId(),
                request.getFirstName(),
                request.getLastNamePaternal(),
                request.getLastNameMaternal(),
                request.getEmail(),
                request.getDeliveryAddress()
        );
        Customer created = createCustomerUseCase.createCustomer(command);
        return ResponseEntity.status(HttpStatus.CREATED).body(CustomerMapper.toResponse(created));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<CustomerMapper.CustomerResponse> getCustomer(
            @PathVariable final String userId
    ) {
        Customer customer = getCustomerUseCase.getCustomer(userId);
        return ResponseEntity.ok(CustomerMapper.toResponse(customer));
    }

    @PutMapping("/{userId}")
    public ResponseEntity<CustomerMapper.CustomerResponse> updateCustomer(
            @PathVariable final String userId,
            @Valid @RequestBody final UpdateCustomerRequest request
    ) {
        UpdateCustomerUseCase.UpdateCustomerDataCommand command = new UpdateCustomerUseCase.UpdateCustomerDataCommand(
                userId,
                request.getFirstName(),
                request.getLastNamePaternal(),
                request.getLastNameMaternal(),
                request.getEmail(),
                request.getDeliveryAddress()
        );

        Customer updated = updateCustomerUseCase.updateCustomerData(command);
        return ResponseEntity.ok(CustomerMapper.toResponse(updated));
    }
}
