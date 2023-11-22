package com.ecommerce.miniproject.controller;

import com.ecommerce.miniproject.dto.ResponseObject;
import com.ecommerce.miniproject.model.ProductOrder;
import com.ecommerce.miniproject.service.ProductOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/${api.path.product-orders}")
public class ProductOrderController extends AbstractController {
    @Autowired
    private ProductOrderService productOrderService;

    @GetMapping
    public ResponseEntity<ResponseObject> getOrders() {
        return sendFoundResponse(productOrderService.getOrders());
    }

    @PostMapping
    public ResponseEntity<ResponseObject> placeOrder(@RequestBody ProductOrder productOrder) {
        return sendCreatedResponse(productOrderService.placeOrder(productOrder));
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<ResponseObject> getOrderById(@PathVariable String orderId) {
        return sendFoundResponse(productOrderService.getOrderById(orderId));
    }

    @GetMapping("/user/{userEmail}")
    public ResponseEntity<ResponseObject> getOrdersByUserEmail(@PathVariable String userEmail) {
        return sendFoundResponse(productOrderService.getOrdersByEmail(userEmail));
    }

    @PatchMapping("/{orderId}")
    public ResponseEntity<ResponseObject> approveOrder(@PathVariable String orderId) {
        return sendFoundResponse(productOrderService.approveOrder(orderId));
    }
}

