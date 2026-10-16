package com.logitrack.order.dto;

import com.logitrack.order.entity.Orders;
import com.logitrack.order.util.OrderStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public record OrderResponse(
        UUID id,
        String deliveryAddress,
        List<OrderItemResponse> products,
        OrderStatus status) {

    public static OrderResponse from(Orders order) {
        return new OrderResponse(
                order.getId(),
                order.getDeliveryAddress(),
                OrderItemResponse.fromAll(order.getItems()),
                order.getStatus()
        );
    }

    public static List<OrderResponse> from(List<Orders> orders) {
        List<OrderResponse> orderResponses = new ArrayList<>();
        for (Orders order : orders) {
            orderResponses.add(from(order));
        }
        return orderResponses;
    }
}
