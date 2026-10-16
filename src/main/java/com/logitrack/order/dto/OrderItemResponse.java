package com.logitrack.order.dto;

import com.logitrack.order.entity.OrderItem;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public record OrderItemResponse(
        UUID productId,
        Long quantity) {

    public static List<OrderItemResponse> fromAll(List<OrderItem> orderItems) {
        List<OrderItemResponse> orderItemResponses = new ArrayList<>();
        for (OrderItem orderItem : orderItems) {
            orderItemResponses.add(new OrderItemResponse(
                    orderItem.getProduct().getId(),
                    orderItem.getQuantity()
            ));
        }
        return orderItemResponses;

    }
}
