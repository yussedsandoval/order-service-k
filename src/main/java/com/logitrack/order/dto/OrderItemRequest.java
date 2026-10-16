package com.logitrack.order.dto;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record OrderItemRequest(
        @NotNull UUID productId,
        @NotNull Long quantity) {
}
