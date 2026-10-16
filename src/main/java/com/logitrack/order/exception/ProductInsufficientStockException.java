package com.logitrack.order.exception;

public class ProductInsufficientStockException extends RuntimeException {
    public ProductInsufficientStockException() {
        super("The product is insufficient to sell this order.");
    }
}
