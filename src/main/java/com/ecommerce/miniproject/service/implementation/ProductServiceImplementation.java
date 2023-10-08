package com.ecommerce.miniproject.service.implementation;

import com.ecommerce.miniproject.constant.ErrorMessages;
import com.ecommerce.miniproject.exceptions.Exceptions;
import com.ecommerce.miniproject.model.Product;
import com.ecommerce.miniproject.repository.ProductRepository;
import com.ecommerce.miniproject.service.ProductService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ProductServiceImplementation implements ProductService {
    private static final Date CURRENT_DATE = new Date(String.valueOf(LocalDate.now()));
    private static final Logger LOGGER = LoggerFactory.getLogger(ProductServiceImplementation.class);

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<Product> getProducts(boolean getOnlyActiveProducts) {
        try {
            List<Product> activeProducts = new ArrayList<>();
            List<Product> allProducts = productRepository.findAll();
            if (getOnlyActiveProducts) {
                for (Product product: allProducts) {
                    if (product.isProductActive()) {
                        activeProducts.add(product);
                    }
                }
                return activeProducts;
            }
            return allProducts;
        } catch (Exception exception) {
            LOGGER.error("Error occurred when adding Product");
            throw new Exceptions("",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public Product getProduct(String productId) {
        try {
            Product foundProduct = productRepository.findById(productId).orElseThrow(() ->
                    new Exceptions(ErrorMessages.ERROR_PRODUCT_NOT_FOUND, HttpStatus.NOT_FOUND));

            if (!foundProduct.isProductActive()) {
                throw new Exceptions(ErrorMessages.ERROR_PRODUCT_NOT_ACTIVE, HttpStatus.FOUND);
            }
            return foundProduct;
        } catch (Exception exception) {
            LOGGER.error("Error occurred when adding Product");
            throw new Exceptions("",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public Product createProduct(Product product){
        try {
            product.setProductUpdatedDate(CURRENT_DATE);
            product.setProductActive(true);
            return productRepository.save(product);
        } catch (Exception exception) {
            LOGGER.error("Error occurred when adding Product");
            throw new Exceptions("",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public Product updateProduct(String productId, Product updatedProduct) {
        try {
            Product foundProduct = productRepository.findById(productId).orElseThrow(() ->
                    new Exceptions(ErrorMessages.ERROR_PRODUCT_NOT_FOUND, HttpStatus.NOT_FOUND));

            if (!foundProduct.isProductActive()) {
                throw new Exceptions(ErrorMessages.ERROR_PRODUCT_NOT_ACTIVE, HttpStatus.FOUND);
            }
            modelMapper.map(updatedProduct, foundProduct);
            foundProduct.setProductUpdatedDate(CURRENT_DATE);
            return productRepository.save(foundProduct);
        } catch (Exception exception) {
            LOGGER.error("Error occurred when adding Product");
            throw new Exceptions("",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public void deactivateProduct(String productId) {
        try {
            Product foundProduct = productRepository.findById(productId).orElseThrow(() ->
                    new Exceptions(ErrorMessages.ERROR_PRODUCT_NOT_FOUND, HttpStatus.NOT_FOUND));

            if (!foundProduct.isProductActive()) {
                throw new Exceptions(ErrorMessages.ERROR_PRODUCT_NOT_ACTIVE, HttpStatus.FOUND);
            }
            foundProduct.setProductActive(false);
        } catch (Exception exception) {
            LOGGER.error("Error occurred when adding Product");
            throw new Exceptions("",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
