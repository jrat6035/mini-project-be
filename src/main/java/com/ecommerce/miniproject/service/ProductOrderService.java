package com.ecommerce.miniproject.service;

import com.ecommerce.miniproject.model.ProductOrder;
import org.springframework.stereotype.Service;

import java.util.List;

public interface ProductOrderService {
    List<ProductOrder> getOrders();
    List<ProductOrder> getOrdersByEmail(String userEmail);
    ProductOrder getOrderById(String id);
    ProductOrder placeOrder(ProductOrder productOrder);
    ProductOrder approveOrder(String id);
}
