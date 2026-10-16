package com.logitrack.order.controller;

import com.logitrack.order.dto.ProductCreateRequest;
import com.logitrack.order.dto.ProductResponse;
import com.logitrack.order.entity.Product;
import com.logitrack.order.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/create")
    public ResponseEntity<ProductResponse> create(@RequestBody @Valid ProductCreateRequest request) {
        return ResponseEntity.ok(productService.createProduct(request));
    }

    @GetMapping("/list")
    public ResponseEntity<List<ProductResponse>> listProducts() {
        return ResponseEntity.ok(productService.listProducts());
    }
}
