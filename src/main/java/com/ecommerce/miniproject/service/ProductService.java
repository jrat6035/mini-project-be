package com.ecommerce.miniproject.service;

import com.ecommerce.miniproject.model.Product;

import java.util.List;

public interface ProductService {
    List<Product> getProducts(boolean getOnlyActiveProducts);
    Product createProduct(Product product);
    Product getProduct(String productId);
    Product updateProduct(String productId, Product product);
    void deactivateProduct(String productId);
}
