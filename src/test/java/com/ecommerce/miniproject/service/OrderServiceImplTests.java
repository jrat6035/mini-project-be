package com.ecommerce.miniproject.service;

import com.ecommerce.miniproject.dto.OrderDTO;
import com.ecommerce.miniproject.model.Order;
import com.ecommerce.miniproject.model.User;
import com.ecommerce.miniproject.repository.OrderRepository;
import com.ecommerce.miniproject.service.implementation.OrderServiceImpl;
import com.ecommerce.miniproject.service.implementation.ProductServiceImpl;
import com.ecommerce.miniproject.service.implementation.UserServicesImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = OrderService.class)
public class OrderServiceImplTests {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private ProductServiceImpl productService;

    @Mock
    private UserServicesImpl userService;

    @InjectMocks
    private OrderServiceImpl orderService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetOrders() {
        List<Order> mockOrders = Collections.singletonList(new Order());
        when(orderRepository.findAll()).thenReturn(mockOrders);

        List<Order> orders = orderService.getOrders();

        assertEquals(mockOrders, orders);
    }

    @Test
    public void testGetOrderById() {
        String orderId = "1";
        Order mockOrder = new Order();
        mockOrder.setId(Long.parseLong(orderId));

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(mockOrder));

        Order order = orderService.getOrderById(orderId);

        assertNotNull(order);
        assertEquals(Long.parseLong(orderId), order.getId());
    }

    @Test
    public void testPlaceOrder() {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setUserEmail("test@example.com");
        orderDTO.setCartItems(Collections.emptyList());

        User mockUser = new User(orderDTO.getUserEmail());
        when(userService.findUser(mockUser)).thenReturn(1L);

        when(productService.getCartAmount(orderDTO.getCartItems())).thenReturn(100.0f);

        Order order = orderService.placeOrder(orderDTO);

        assertNotNull(order);
        assertEquals(1L, order.getUser().getId());
        assertEquals(100.0f, order.getTotalPrice());
        assertNotNull(order.getCreatedDate());
    }

    @Test
    public void testPlaceOrderUserNotFound() {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setUserEmail("nonexistent@example.com");
        orderDTO.setCartItems(Collections.emptyList());

        when(userService.findUser(any(User.class))).thenReturn(null);

        Order order = orderService.placeOrder(orderDTO);

        assertNull(order);
    }
}