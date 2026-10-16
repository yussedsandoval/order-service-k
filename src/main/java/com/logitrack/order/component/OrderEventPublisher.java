package com.logitrack.order.component;

import com.logitrack.order.dto.EventEnvelope;
import com.logitrack.order.dto.OrderCanceledPayload;
import com.logitrack.order.dto.OrderCreatedPayload;
import com.logitrack.order.entity.Orders;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.UUID;

@Component
public class OrderEventPublisher {

    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final String source;

    public  OrderEventPublisher(KafkaTemplate<String, Object> kafkaTemplate,
                                @Value("${spring.application.name}") String source) {
        this.kafkaTemplate = kafkaTemplate;
        this.source = source;
    }

    public void publishOrderCreated(Orders order) {
        OrderCreatedPayload payload = OrderCreatedPayload.from(order);
        EventEnvelope<OrderCreatedPayload> event = EventEnvelope.of("ORDER_CREATED", order.getId(), payload);

        ProducerRecord<String, Object> record =
                new ProducerRecord<>("logistics.events", String.valueOf(order.getId()), event);
        record.headers().add("eventType", "ORDER_CREATED".getBytes(StandardCharsets.UTF_8));
        record.headers().add("eventId", event.eventId().toString().getBytes(StandardCharsets.UTF_8));
        record.headers().add("source", source.getBytes(StandardCharsets.UTF_8));

        kafkaTemplate.send(record);
    }

    public void publishOrderCanceled(UUID orderId, UUID userId, UUID canceledBy) {
        OrderCanceledPayload payload = new OrderCanceledPayload(orderId, userId, canceledBy, Instant.now());
        EventEnvelope<OrderCanceledPayload> event = EventEnvelope.of("ORDER_CANCELLED", orderId, payload);

        ProducerRecord<String, Object> record =
                new ProducerRecord<>("logistics.events", String.valueOf(orderId), event);
        record.headers().add("eventType", "ORDER_CANCELLED".getBytes(StandardCharsets.UTF_8));
        record.headers().add("eventId", event.eventId().toString().getBytes(StandardCharsets.UTF_8));
        record.headers().add("source", source.getBytes(StandardCharsets.UTF_8));

        kafkaTemplate.send(record);
    }
}
