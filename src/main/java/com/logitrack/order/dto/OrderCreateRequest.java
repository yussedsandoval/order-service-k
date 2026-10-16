package com.logitrack.order.dto;

import jakarta.validation.Valid;

import java.util.List;

public record OrderCreateRequest(
        @Valid List<OrderItemRequest> items) {
}
