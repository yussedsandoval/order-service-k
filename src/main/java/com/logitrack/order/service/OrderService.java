package com.logitrack.order.service;

import com.logitrack.order.dto.OrderItemRequest;
import com.logitrack.order.dto.OrderResponse;
import com.logitrack.order.entity.Orders;
import com.logitrack.order.entity.Product;
import com.logitrack.order.exception.ProductInsufficientStockException;
import com.logitrack.order.exception.ProductNotFoundException;
import com.logitrack.order.repository.IOrdersRepository;
import com.logitrack.order.repository.IProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

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
    public OrderResponse createOrder(List<OrderItemRequest> items, UUID userId) {
        Orders order = new Orders(userId);
        items.forEach(item -> {
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

    public List<OrderResponse> getOrders() {
        List<Orders> orders = ordersRepository.findAll();
        return OrderResponse.from(orders);
    }
}
