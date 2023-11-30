package com.ecommerce.miniproject.service;

import com.ecommerce.miniproject.model.Product;
import com.ecommerce.miniproject.model.ProductOrder;
import com.ecommerce.miniproject.model.User;
import com.ecommerce.miniproject.repository.ProductOrderRepository;
import com.ecommerce.miniproject.service.implementation.ProductOrderServiceImpl;
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
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = ProductOrderService.class)
public class ProductOrderServiceImplTests {
    @Mock
    private ProductOrderRepository productOrderRepository;

    @Mock
    private ProductServiceImpl productServiceImpl;

    @Mock
    private UserServicesImpl userServiceImpl;

    @InjectMocks
    private ProductOrderServiceImpl productOrderService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetOrders() {
        List<ProductOrder> mockOrders = Collections.singletonList(new ProductOrder());
        when(productOrderRepository.findAll()).thenReturn(mockOrders);

        List<ProductOrder> orders = productOrderService.getOrders();

        assertEquals(mockOrders, orders);
    }

    @Test
    public void testGetOrdersByEmail() {
        String userEmail = "test@example.com";
        List<ProductOrder> mockOrders = Collections.singletonList(new ProductOrder());
        when(productOrderRepository.findAllByUserEmail(userEmail)).thenReturn(mockOrders);

        List<ProductOrder> orders = productOrderService.getOrdersByEmail(userEmail);

        assertEquals(mockOrders, orders);
    }

    @Test
    public void testGetOrderById() {
        String orderId = "1";
        ProductOrder mockOrder = new ProductOrder();
        mockOrder.setId(Long.parseLong(orderId));

        when(productOrderRepository.findById(orderId)).thenReturn(Optional.of(mockOrder));

        ProductOrder order = productOrderService.getOrderById(orderId);

        assertNotNull(order);
        assertEquals(Long.parseLong(orderId), order.getId());
    }

    @Test
    public void testPlaceOrder() {
        ProductOrder productOrder = new ProductOrder();
        productOrder.setUserEmail("test@example.com");
        productOrder.setProductId("1");
        productOrder.setProductQuantity(2);

        User mockUser = new User(productOrder.getUserEmail());
        when(userServiceImpl.getUserByEmail(mockUser.getUserEmail())).thenReturn(mockUser);

        when(productServiceImpl.getProductAmount(productOrder.getProductId(), productOrder.getProductQuantity())).thenReturn(100.0f);
        when(productServiceImpl.getProductById(productOrder.getProductId())).thenReturn(createMockProduct());
        when(productOrderRepository.save(productOrder)).thenReturn(productOrder);

        ProductOrder savedOrder = productOrderService.placeOrder(productOrder);

        assertNotNull(savedOrder);
        assertEquals("MockProduct", savedOrder.getProductName());
        assertEquals(100.0f, savedOrder.getTotalPrice());
        assertNotNull(savedOrder.getCreatedDate());
        assertFalse(savedOrder.isApproved());
    }

    @Test
    public void testPlaceOrderUserNotFound() {
        ProductOrder productOrder = new ProductOrder();
        productOrder.setUserEmail("nonexistent@example.com");

        when(userServiceImpl.getUserByEmail(anyString())).thenReturn(null);

        ProductOrder order = productOrderService.placeOrder(productOrder);

        assertNull(order);
    }

    @Test
    public void testApproveOrder() {
        String orderId = "1";
        ProductOrder mockOrder = new ProductOrder();
        mockOrder.setId(Long.valueOf(orderId));

        when(productOrderRepository.findById(orderId)).thenReturn(Optional.of(mockOrder));
        when(productOrderRepository.save(mockOrder)).thenReturn(mockOrder);


        ProductOrder approvedOrder = productOrderService.approveOrder(orderId);

        assertNotNull(approvedOrder);
        assertTrue(approvedOrder.isApproved());
        assertNotNull(approvedOrder.getApprovedDate());
    }

    private Product createMockProduct() {
        Product product = new Product();
        product.setProductName("MockProduct");
        return product;
    }
}
