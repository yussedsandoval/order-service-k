package com.logitrack.order.repository;


import com.logitrack.order.entity.Orders;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface IOrdersRepository extends JpaRepository<Orders, UUID> {

    @EntityGraph(attributePaths = {"items", "items.product"})
    List<Orders> findAll();
}
