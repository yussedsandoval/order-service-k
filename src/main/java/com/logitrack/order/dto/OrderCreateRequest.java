package com.logitrack.order.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

public record OrderCreateRequest(
        @Valid List<OrderItemRequest> items,
        @NotBlank String deliveryAddress) {
}
