package com.logitrack.order.exception;

public class OrderInvalidCanceledException extends RuntimeException {
    public OrderInvalidCanceledException() {
        super("Order has not been canceled because the order status is invalid");
    }
}
