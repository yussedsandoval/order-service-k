package com.logitrack.order.controller;

import com.logitrack.order.dto.OrderCreateRequest;
import com.logitrack.order.dto.OrderItemRequest;
import com.logitrack.order.dto.OrderResponse;
import com.logitrack.order.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/order")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> createOrder(
            @RequestBody @Valid List<OrderItemRequest> request,
            @AuthenticationPrincipal Jwt jwt) {
        UUID userId = UUID.fromString(jwt.getSubject());
        OrderResponse response = orderService.createOrder(request, userId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/list")
    public ResponseEntity<List<OrderResponse>> createOrder() {
        List<OrderResponse> response = orderService.getOrders();
        return ResponseEntity.ok(response);
    }
}
