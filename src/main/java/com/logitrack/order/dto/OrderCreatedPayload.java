package com.logitrack.order.dto;

import com.logitrack.order.entity.OrderItem;
import com.logitrack.order.entity.Orders;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public record OrderCreatedPayload(
        UUID orderId,
        UUID customerId,
        String deliveryAddress,
        List<OrderCreatedItem> items,
        Instant createdAt) {

    public static OrderCreatedPayload from(Orders order) {
        return new OrderCreatedPayload(
                order.getId(),
                order.getUserId(),
                order.getDeliveryAddress(),
                itemsList(order.getItems()),
                order.getCreatedAt()
        );
    }

    public static List<OrderCreatedItem> itemsList(List<OrderItem> items) {
        List<OrderCreatedItem> response =  new ArrayList<>();
        for (OrderItem item : items) {
            response.add(new OrderCreatedItem(
                    item.getProduct().getId(),
                    item.getQuantity()
            ));
        }
        return response;
    }
}
