package com.logitrack.order.service;

import com.logitrack.order.dto.ProductCreateRequest;
import com.logitrack.order.dto.ProductResponse;
import com.logitrack.order.entity.Product;
import com.logitrack.order.repository.IProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final IProductRepository productRepository;

    public ProductService(IProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public ProductResponse createProduct(ProductCreateRequest product) {
        Product newProduct = productRepository.save(
                new Product(
                        product.name(),
                        product.description(),
                        product.price(),
                        product.stock()
                )
        );

        return ProductResponse.from(newProduct);

    }

    public List<ProductResponse> listProducts() {
        List<Product>  products = productRepository.findAll();
        return ProductResponse.fromAll(products);
    }
}
