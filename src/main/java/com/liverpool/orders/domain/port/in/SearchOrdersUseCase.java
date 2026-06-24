package com.liverpool.orders.domain.port.in;

import com.liverpool.orders.domain.model.Order;

import java.util.List;

public interface SearchOrdersUseCase {

    final class SearchOrdersQuery {
        private final String searchTerm;
        private final String orderStatus;
        private final int limit;

        public SearchOrdersQuery(
                final String searchTerm,
                final String orderStatus,
                final int limit
        ) {
            this.searchTerm = searchTerm;
            this.orderStatus = orderStatus;
            this.limit = limit;
        }

        public String getSearchTerm() {
            return searchTerm;
        }

        public String getOrderStatus() {
            return orderStatus;
        }

        public int getLimit() {
            return limit;
        }

        public boolean hasFilters() {
            return (searchTerm != null && !searchTerm.isBlank()) ||
                    (orderStatus != null && !orderStatus.isBlank());
        }
    }

    List<Order> searchOrders(SearchOrdersQuery query);

}
