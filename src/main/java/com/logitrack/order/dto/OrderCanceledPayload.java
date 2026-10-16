package com.logitrack.order.dto;

import java.time.Instant;
import java.util.UUID;

public record OrderCanceledPayload(
        UUID orderId,
        UUID customerId,
        UUID canceledBy,
        Instant canceledAt) {
}
