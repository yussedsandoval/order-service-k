package com.logitrack.order.dto;

import java.util.UUID;

public record OrderCreatedItem(
        UUID productId,
        Long quantity) {
}
