package com.logitrack.order.service;

import com.logitrack.order.dto.OrderCreateRequest;
import com.logitrack.order.dto.OrderItemRequest;
import com.logitrack.order.dto.OrderResponse;
import com.logitrack.order.dto.PageResponse;
import com.logitrack.order.entity.Orders;
import com.logitrack.order.entity.Product;
import com.logitrack.order.exception.OrderInvalidCanceledException;
import com.logitrack.order.exception.OrderNotFoundException;
import com.logitrack.order.exception.ProductInsufficientStockException;
import com.logitrack.order.exception.ProductNotFoundException;
import com.logitrack.order.repository.IOrdersRepository;
import com.logitrack.order.repository.IProductRepository;
import com.logitrack.order.util.OrderStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class OrderService {

    private final IOrdersRepository ordersRepository;
    private final IProductRepository productRepository;

    public OrderService(IOrdersRepository ordersRepository,  IProductRepository productRepository) {
        this.ordersRepository = ordersRepository;
        this.productRepository = productRepository;
    }

    @Transactional
    public OrderResponse createOrder(OrderCreateRequest request, UUID userId) {
        Orders order = new Orders(userId, request.deliveryAddress());
        request.items().forEach(item -> {
            Product product = productRepository.findById(item.productId()).orElseThrow(
                    () -> new ProductNotFoundException(item.productId())
            );
            if(product.getStock() < item.quantity()) {
                throw new ProductInsufficientStockException();
            }
            product.decreaseStock(item.quantity());
            order.addItem(product, item.quantity());
        });

        Orders response = ordersRepository.save(order);
        return OrderResponse.from(response);
    }

    @Transactional(readOnly = true)
    public PageResponse<OrderResponse> getOrders(OrderStatus status, Pageable pageable, UUID userId, boolean isAdmin) {
        Page<Orders> orders;
        if (isAdmin) {
            orders = status == null
                    ? ordersRepository.findAll(pageable)
                    : ordersRepository.findByStatus(status, pageable);
        } else {
            orders = status == null
                    ? ordersRepository.findByUserId(userId, pageable)
                    : ordersRepository.findByUserIdAndStatus(userId, status, pageable);
        }
        return PageResponse.from(orders.map(OrderResponse::from));
    }

    @Transactional(readOnly = true)
    public OrderResponse getOrderById(UUID orderId, UUID userId, boolean isAdmin) {
        Orders order = ordersRepository.findById(orderId).orElseThrow(OrderNotFoundException::new);
        if (!isAdmin && !order.getUserId().equals(userId)) {
            throw new OrderNotFoundException();
        }
        return OrderResponse.from(order);
    }

    @Transactional
    public OrderResponse cancelOrder(UUID orderId, UUID userId,  boolean isAdmin) {
        Orders order = ordersRepository.findById(orderId).orElseThrow(OrderNotFoundException::new);
        if (!isAdmin && !order.getUserId().equals(userId)) {
            throw new OrderNotFoundException();
        }
        if (order.getStatus() == OrderStatus.COMPLETED || order.getStatus() == OrderStatus.CANCELED) {
            throw new OrderInvalidCanceledException();
        }
        order.cancelOrder();
        Orders response = ordersRepository.save(order);
        return OrderResponse.from(response);
    }
}
