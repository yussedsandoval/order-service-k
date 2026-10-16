package com.logitrack.order.repository;


import com.logitrack.order.entity.Orders;
import com.logitrack.order.util.OrderStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IOrdersRepository extends JpaRepository<Orders, UUID> {

    @EntityGraph(attributePaths = {"items", "items.product"})
    List<Orders> findAll();

    @EntityGraph(attributePaths = {"items", "items.product"})
    Optional<Orders> findById(UUID id);

    Page<Orders> findByStatus(OrderStatus status, Pageable pageable);

    Page<Orders> findByUserId(UUID userId, Pageable pageable);

    Page<Orders> findByUserIdAndStatus(UUID userId, OrderStatus status, Pageable pageable);
}
