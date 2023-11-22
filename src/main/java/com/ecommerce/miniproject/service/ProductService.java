package com.ecommerce.miniproject.service;

import com.ecommerce.miniproject.model.Product;

import java.util.List;

public interface ProductService {
    List<Product> getProducts(String getOnlyActiveProducts);
    Product createProduct(Product product);
    Product getProductById(String id);
    Product updateProduct(String id, Product product);
    Product deactivateProduct(String id);
}
