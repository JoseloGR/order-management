package com.liverpool.orders.infrastructure.config;

import com.liverpool.orders.application.usecase.CreateCustomerUseCaseImpl;
import com.liverpool.orders.application.usecase.GetCustomerUseCaseImpl;
import com.liverpool.orders.application.usecase.UpdateCustomerUseCaseImpl;
import com.liverpool.orders.domain.port.in.CreateCustomerUseCase;
import com.liverpool.orders.domain.port.in.GetCustomerUseCase;
import com.liverpool.orders.domain.port.in.UpdateCustomerUseCase;
import com.liverpool.orders.domain.port.out.CustomerRepository;
import com.liverpool.orders.domain.port.out.ItemsExternalService;
import com.liverpool.orders.domain.port.out.OrdersExternalService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class BeanConfig {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public CreateCustomerUseCase createCustomerUseCase(
            final CustomerRepository customerRepository) {
        return new CreateCustomerUseCaseImpl(customerRepository);
    }

    @Bean
    public GetCustomerUseCase getCustomerUseCase(
            final CustomerRepository customerRepository,
            final OrdersExternalService ordersExternalService,
            final ItemsExternalService itemsExternalService) {
        return new GetCustomerUseCaseImpl(
                customerRepository, ordersExternalService, itemsExternalService);
    }

    @Bean
    public UpdateCustomerUseCase updateCustomerUseCase(
            final CustomerRepository customerRepository,
            final OrdersExternalService ordersExternalService) {
        return new UpdateCustomerUseCaseImpl(customerRepository, ordersExternalService);
    }
}
