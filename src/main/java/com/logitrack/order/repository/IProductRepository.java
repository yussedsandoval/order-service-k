package com.logitrack.order.repository;

import com.logitrack.order.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface IProductRepository extends JpaRepository<Product, UUID> {
}
