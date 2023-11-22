package com.ecommerce.miniproject.controller;

import com.ecommerce.miniproject.dto.OrderDTO;
import com.ecommerce.miniproject.dto.ResponseObject;
import com.ecommerce.miniproject.model.Order;
import com.ecommerce.miniproject.service.OrderService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/${api.path.orders}")
public class OrderController extends AbstractController {
    @Autowired
    private OrderService orderService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping
    public ResponseEntity<ResponseObject> getOrders() {
        return sendFoundResponse(orderService.getOrders());
    }

    @PostMapping
    public ResponseEntity<ResponseObject> placeOrder(@RequestBody OrderDTO orderDTO) {
        return sendCreatedResponse(orderService.placeOrder(orderDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseObject> getOrderById(@PathVariable String id) {
        return sendFoundResponse(orderService.getOrderById(id));
    }

    @GetMapping("/{userEmail}")
    public ResponseEntity<ResponseObject> getOrderByUserEmail(@PathVariable String userEmail) {
        return sendFoundResponse(orderService.getOrderByEmail(userEmail));
    }
}
