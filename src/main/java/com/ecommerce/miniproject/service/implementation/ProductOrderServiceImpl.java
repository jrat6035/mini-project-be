package com.ecommerce.miniproject.service.implementation;

import com.ecommerce.miniproject.constant.ErrorMessages;
import com.ecommerce.miniproject.exceptions.Exceptions;
import com.ecommerce.miniproject.model.ProductOrder;
import com.ecommerce.miniproject.model.User;
import com.ecommerce.miniproject.repository.ProductOrderRepository;
import com.ecommerce.miniproject.service.ProductOrderService;
import com.ecommerce.miniproject.util.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductOrderServiceImpl implements ProductOrderService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductOrderServiceImpl.class);

    @Autowired
    private ProductOrderRepository productOrderRepository;

    @Autowired
    private ProductServiceImpl productServiceImpl;

    @Autowired
    private UserServicesImpl userServiceImpl;

    @Override
    public List<ProductOrder> getOrders() {
        try {
            return productOrderRepository.findAll();
        } catch (Exception exception) {
            LOGGER.error("Error while fetching orders", exception);
            throw new Exceptions("Error while fetching orders",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public List<ProductOrder> getOrdersByEmail(String userEmail) {
        try {
            return productOrderRepository.findAllByUserEmail(userEmail);
        } catch (Exception exception) {
            LOGGER.error("Error while fetching orders", exception);
            throw new Exceptions("Error while fetching orders",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ProductOrder getOrderById(String id) {
        try {
            return productOrderRepository.findById(id).orElseThrow(() ->
                    new Exceptions(ErrorMessages.ERROR_PRODUCT_NOT_FOUND, HttpStatus.NOT_FOUND));
        } catch (Exception exception) {
            LOGGER.error("Error while fetching orders", exception);
            throw new Exceptions("Error while fetching orders",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ProductOrder placeOrder(ProductOrder productOrder) {
        float totalAmount = productServiceImpl.getProductAmount(productOrder.getProductId(), productOrder.getProductQuantity());
        User user = userServiceImpl.getUserByEmail(productOrder.getUserEmail());
        if (user != null) {
            String productName = productServiceImpl.getProductById(productOrder.getProductId()).getProductName();
            productOrder.setProductName(productName);
            productOrder.setTotalPrice(totalAmount);
            productOrder.setCreatedDate(DateUtil.getCurrentDateTime());
            productOrder.setApproved(false);
            return productOrderRepository.save(productOrder);
        } else {
            LOGGER.error("Could not find user with email: " + productOrder.getUserEmail());
            return null;
        }
    }

    @Override
    public ProductOrder approveOrder(String id) {
        try {
            ProductOrder productOrder = productOrderRepository.findById(id).orElseThrow(() ->
                    new Exceptions(ErrorMessages.ERROR_PRODUCT_NOT_FOUND, HttpStatus.NOT_FOUND));
            productOrder.setApproved(true);
            productOrder.setApprovedDate(DateUtil.getCurrentDateTime());
            return productOrderRepository.save(productOrder);
        } catch (Exception exception) {
            LOGGER.error("Error while fetching orders", exception);
            throw new Exceptions("Error while fetching orders",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
