package com.ecommerce.miniproject.service.implementation;

import com.ecommerce.miniproject.constant.ErrorMessages;
import com.ecommerce.miniproject.dto.OrderDTO;
import com.ecommerce.miniproject.exceptions.Exceptions;
import com.ecommerce.miniproject.model.Order;
import com.ecommerce.miniproject.model.User;
import com.ecommerce.miniproject.repository.OrderRepository;
import com.ecommerce.miniproject.service.OrderService;
import com.ecommerce.miniproject.util.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductServiceImpl productService;

    @Autowired
    private UserServicesImpl userService;

    @Override
    public List<Order> getOrders() {
        try {
            return orderRepository.findAll();
        } catch (Exception exception) {
            LOGGER.error("Error while fetching orders", exception);
            throw new Exceptions("Error while fetching orders",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public Order getOrderById(String id) {
        try {
            return orderRepository.findById(id).orElseThrow(() ->
                    new Exceptions(ErrorMessages.ERROR_PRODUCT_NOT_FOUND, HttpStatus.NOT_FOUND));
        } catch (Exception exception) {
            LOGGER.error("Error while fetching orders", exception);
            throw new Exceptions("Error while fetching orders",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public Order placeOrder(OrderDTO orderDTO) {
        float totalAmount = productService.getCartAmount(orderDTO.getCartItems());
        User user = new User(orderDTO.getUserEmail());

        Long userId = userService.findUser(user);
        if (userId != null) {
            user.setId(userId);
            Order order = new Order();
            order.setUser(user);
            order.setCartItems(order.getCartItems());
            order.setDescription(order.getDescription());
            order.setTotalPrice(totalAmount);
            order.setCreatedDate(DateUtil.getCurrentDateTime());
            orderRepository.save(order);
            LOGGER.info("Order processed successfully");
            return order;
        } else {
            LOGGER.error("Could not find user with email: " + orderDTO.getUserEmail());
            return null;
        }
    }

    @Override
    public void cancelOrder(String id) {
    }

    @Override
    public Object getOrderByEmail(String userEmail) {
        return null;
    }
}
