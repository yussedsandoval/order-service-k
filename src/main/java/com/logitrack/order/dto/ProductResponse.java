package com.logitrack.order.dto;

import com.logitrack.order.entity.Product;
import jakarta.validation.constraints.NotBlank;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public record ProductResponse(
        UUID id,
        String name,
        String description,
        BigDecimal price,
        Long stock) {

    public static ProductResponse from(Product product) {
        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getStock()
        );
    }

    public static List<ProductResponse> fromAll(List<Product> products) {
        List<ProductResponse> productResponses = new ArrayList<>();
        for (Product product : products) {
            productResponses.add(from(product));
        }
        return productResponses;
    }
}
