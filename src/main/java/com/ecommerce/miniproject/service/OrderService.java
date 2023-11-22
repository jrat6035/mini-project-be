package com.ecommerce.miniproject.service;

import com.ecommerce.miniproject.dto.OrderDTO;
import com.ecommerce.miniproject.model.Order;

import java.util.List;

public interface OrderService {
    List<Order> getOrders();
    Order getOrderById(String id);
    Order placeOrder(OrderDTO orderDTO);
    void cancelOrder(String id);
    Object getOrderByEmail(String userEmail);
}
