package com.ecommerce.miniproject.service.implementation;

import com.ecommerce.miniproject.constant.Constants;
import com.ecommerce.miniproject.constant.ErrorMessages;
import com.ecommerce.miniproject.exceptions.Exceptions;
import com.ecommerce.miniproject.model.Cart;
import com.ecommerce.miniproject.model.Product;
import com.ecommerce.miniproject.repository.ProductRepository;
import com.ecommerce.miniproject.service.ProductService;
import com.ecommerce.miniproject.util.DateUtil;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    private static final Date CURRENT_DATE = new Date();
    private static final Logger LOGGER = LoggerFactory.getLogger(ProductServiceImpl.class);

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<Product> getProducts(String getOnlyActiveProducts) {
        try {
            List<Product> activeProducts = new ArrayList<>();
            List<Product> allProducts = productRepository.findAll();

            if (getOnlyActiveProducts.equalsIgnoreCase(Constants.ACTIVE)) {
                for (Product product: allProducts) {
                    if (product.getProductStatus().equalsIgnoreCase(Constants.ACTIVE)) {
                        activeProducts.add(product);
                    }
                }
                return activeProducts;
            }
            return allProducts;
        } catch (Exception exception) {
            LOGGER.error("Error occurred when retrieving Products");
            throw new Exceptions("Error occurred when retrieving Products",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public Product getProductById(String id) {
         try {
            Product foundProduct = productRepository.findById(id).orElseThrow(() ->
                    new Exceptions(ErrorMessages.ERROR_PRODUCT_NOT_FOUND, HttpStatus.NOT_FOUND));

            if (!foundProduct.getProductStatus().equalsIgnoreCase(Constants.ACTIVE)) {
                throw new Exceptions(ErrorMessages.ERROR_PRODUCT_NOT_ACTIVE, HttpStatus.FOUND);
            }
            return foundProduct;
        } catch (Exception exception) {
            LOGGER.error("Error occurred when retrieving Product");
            throw new Exceptions("",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public Product createProduct(Product product){
        try {
            product.setProductUpdatedDate(CURRENT_DATE);
            product.setProductStatus(Constants.ACTIVE);
            return productRepository.save(product);
        } catch (Exception exception) {
            LOGGER.error("Error occurred when adding Product");
            throw new Exceptions("",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public Product updateProduct(String id, Product updatedProduct) {
        try {
            Product foundProduct = productRepository.findById(id).orElseThrow(() ->
                    new Exceptions(ErrorMessages.ERROR_PRODUCT_NOT_FOUND, HttpStatus.NOT_FOUND));

            if (!foundProduct.getProductStatus().equalsIgnoreCase(Constants.ACTIVE)) {
                throw new Exceptions(ErrorMessages.ERROR_PRODUCT_NOT_ACTIVE, HttpStatus.FOUND);
            }
            foundProduct.setProductName(updatedProduct.getProductName());
            foundProduct.setProductPrice(updatedProduct.getProductPrice());
            foundProduct.setProductDescription(updatedProduct.getProductDescription());
            foundProduct.setProductQuantity(updatedProduct.getProductQuantity());
            foundProduct.setProductUpdatedDate(CURRENT_DATE);

            return productRepository.save(foundProduct);
        } catch (Exception exception) {
            LOGGER.error("Error occurred when updating Product");
            throw new Exceptions(exception.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public Product deactivateProduct(String id) {
        try {
            Product foundProduct = productRepository.findById(id).orElseThrow(() ->
                    new Exceptions(ErrorMessages.ERROR_PRODUCT_NOT_FOUND, HttpStatus.NOT_FOUND));

            if (!foundProduct.getProductStatus().equalsIgnoreCase(Constants.ACTIVE)) {
                throw new Exceptions(ErrorMessages.ERROR_PRODUCT_NOT_ACTIVE, HttpStatus.FOUND);
            }
            foundProduct.setProductStatus(Constants.INACTIVE);
            foundProduct.setProductUpdatedDate(CURRENT_DATE);
            return productRepository.save(foundProduct);
        } catch (Exception exception) {
            LOGGER.error("Error occurred when removing Product");
            throw new Exceptions("",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public float getCartAmount(List<Cart> cartItems) {
        float totalCartAmount = 0;

        for (Cart cart : cartItems) {
            float cartAmount;
            int availableProductQuantity = 0;
            String productId = cart.getProductId();
            Product product = getProductById(productId);

            if (product.getProductQuantity() < cart.getProductQuantity()) {
                cartAmount = product.getProductPrice() * product.getProductQuantity();
                cart.setProductQuantity(product.getProductQuantity());
            } else {
                cartAmount = cart.getProductQuantity() * product.getProductPrice();
                availableProductQuantity = product.getProductQuantity() - cart.getProductQuantity();
            }
            totalCartAmount += cartAmount;
            product.setProductQuantity(availableProductQuantity);
            cart.setProductName(product.getProductName());
            cart.setTotalAmount(cartAmount);
            cart.setLastUpdatedDateTime(DateUtil.getCurrentDateTime());
            productRepository.save(product);
        }
        return totalCartAmount;
    }

    public float getProductAmount(String productId, int quantity) {
        float orderAmount;
        int availableProductQuantity = 0;
        Product product = getProductById(productId);

        if (product.getProductQuantity() < quantity) {
            orderAmount = product.getProductPrice() * product.getProductQuantity();
        } else {
            orderAmount = quantity * product.getProductPrice();
            availableProductQuantity = product.getProductQuantity() - quantity;
        }
        product.setProductQuantity(availableProductQuantity);
        productRepository.save(product);

        return orderAmount;
    }
}
