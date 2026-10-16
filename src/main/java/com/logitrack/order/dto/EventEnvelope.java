package com.logitrack.order.dto;


import java.time.Instant;
import java.util.UUID;

public record EventEnvelope<T>(
        UUID eventId,
        String eventType,
        Instant occurredAt,
        UUID aggregateId,
        int version,
        T payload) {

    public static <T> EventEnvelope<T> of(String eventType, UUID aggregateId, T payload) {
        return new EventEnvelope<>(UUID.randomUUID(), eventType, Instant.now(), aggregateId, 1, payload);
    }
}
