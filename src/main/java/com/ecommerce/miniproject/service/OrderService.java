package com.ecommerce.miniproject.service;

import com.ecommerce.miniproject.model.Order;

import java.util.List;

public interface OrderService {
    List<Order> getOrders();
    Order getOrderById(String id);
    Order createOrder(Order order);
    void cancelOrder(String id);
}
