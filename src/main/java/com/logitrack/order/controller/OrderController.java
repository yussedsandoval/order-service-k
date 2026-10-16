package com.logitrack.order.controller;

import com.logitrack.order.dto.OrderCreateRequest;
import com.logitrack.order.dto.OrderItemRequest;
import com.logitrack.order.dto.OrderResponse;
import com.logitrack.order.dto.PageResponse;
import com.logitrack.order.service.OrderService;
import com.logitrack.order.util.OrderStatus;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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
            @RequestBody @Valid OrderCreateRequest request,
            @AuthenticationPrincipal Jwt jwt) {
        UUID userId = UUID.fromString(jwt.getSubject());
        OrderResponse response = orderService.createOrder(request, userId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/list")
    public ResponseEntity<PageResponse<OrderResponse>> getOrders(
            @RequestParam(required = false) OrderStatus status,
            @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable,
            @AuthenticationPrincipal Jwt jwt
    ) {
        UUID userId = UUID.fromString(jwt.getSubject());
        boolean isAdmin = jwt.getClaimAsStringList("roles").contains("ADMIN");
        PageResponse<OrderResponse> response = orderService.getOrders(status, pageable, userId, isAdmin);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponse> getOrderById(@PathVariable UUID id, @AuthenticationPrincipal Jwt jwt) {
        UUID userId = UUID.fromString(jwt.getSubject());
        boolean isAdmin = jwt.getClaimAsStringList("roles").contains("ADMIN");
        OrderResponse response = orderService.getOrderById(id, userId, isAdmin);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}/cancel")
    public ResponseEntity<OrderResponse> cancelOrderById(@PathVariable UUID id, @AuthenticationPrincipal Jwt jwt) {
        UUID userId = UUID.fromString(jwt.getSubject());
        boolean isAdmin = jwt.getClaimAsStringList("roles").contains("ADMIN");
        OrderResponse response = orderService.cancelOrder(id, userId, isAdmin);
        return ResponseEntity.ok(response);
    }
}
