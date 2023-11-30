package com.ecommerce.miniproject.service;

import com.ecommerce.miniproject.constant.Constants;
import com.ecommerce.miniproject.model.Cart;
import com.ecommerce.miniproject.model.Product;
import com.ecommerce.miniproject.repository.ProductRepository;
import com.ecommerce.miniproject.service.implementation.ProductServiceImpl;
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

@SpringBootTest(classes = ProductService.class)
public class ProductServiceImplTests {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    private Product getProduct() {
        Product product = new Product();
        product.setProductId("id");
        product.setProductStatus(Constants.ACTIVE);
        return product;
    }

    @Test
    public void testGetProducts() {
        String getOnlyActiveProducts = Constants.ACTIVE;
        Product product = getProduct();
        List<Product> mockProducts = Collections.singletonList(product);
        when(productRepository.findAll()).thenReturn(mockProducts);

        List<Product> products = productService.getProducts(getOnlyActiveProducts);

        assertEquals(mockProducts, products);
    }

    @Test
    public void testGetProductById() {
        String productId = "1";
        Product mockProduct = new Product();
        mockProduct.setProductId(productId);
        mockProduct.setProductStatus("active");

        when(productRepository.findById(productId)).thenReturn(Optional.of(mockProduct));

        Product product = productService.getProductById(productId);

        assertNotNull(product);
        assertEquals(productId, product.getProductId());
    }

    @Test
    public void testCreateProduct() {
        Product productToCreate = new Product();
        productToCreate.setProductName("Test Product");
        productToCreate.setProductPrice(50);
        productToCreate.setProductDescription("Description");
        productToCreate.setProductQuantity(10);

        when(productRepository.save(any(Product.class))).thenReturn(productToCreate);

        Product createdProduct = productService.createProduct(productToCreate);

        assertNotNull(createdProduct);
        assertEquals(productToCreate.getProductName(), createdProduct.getProductName());
        assertEquals(productToCreate.getProductPrice(), createdProduct.getProductPrice());
        assertEquals(productToCreate.getProductDescription(), createdProduct.getProductDescription());
        assertEquals(productToCreate.getProductQuantity(), createdProduct.getProductQuantity());
        assertEquals(Constants.ACTIVE, createdProduct.getProductStatus());
        assertNotNull(createdProduct.getProductUpdatedDate());
    }

    @Test
    public void testUpdateProduct() {
        String productId = "1";
        Product existingProduct = new Product();
        existingProduct.setProductId(productId);
        existingProduct.setProductStatus("active");

        Product updatedProduct = new Product();
        updatedProduct.setProductName("Updated Product");
        updatedProduct.setProductPrice(60);
        updatedProduct.setProductDescription("Updated Description");
        updatedProduct.setProductQuantity(15);

        when(productRepository.findById(productId)).thenReturn(Optional.of(existingProduct));
        when(productRepository.save(any(Product.class))).thenReturn(updatedProduct);

        Product result = productService.updateProduct(productId, updatedProduct);

        assertNotNull(result);
        assertEquals(updatedProduct.getProductName(), result.getProductName());
        assertEquals(updatedProduct.getProductPrice(), result.getProductPrice());
        assertEquals(updatedProduct.getProductDescription(), result.getProductDescription());
        assertEquals(updatedProduct.getProductQuantity(), result.getProductQuantity());
    }

    @Test
    public void testDeactivateProduct() {
        String productId = "1";
        Product existingProduct = new Product();
        existingProduct.setProductId(productId);
        existingProduct.setProductStatus("active");

        when(productRepository.findById(productId)).thenReturn(Optional.of(existingProduct));
        when(productRepository.save(any(Product.class))).thenReturn(existingProduct);

        Product deactivatedProduct = productService.deactivateProduct(productId);

        assertNotNull(deactivatedProduct);
        assertEquals(productId, deactivatedProduct.getProductId());
        assertEquals(Constants.INACTIVE, deactivatedProduct.getProductStatus());
        assertNotNull(deactivatedProduct.getProductUpdatedDate());
    }

    @Test
    public void testGetCartAmount() {
        Cart cartItem = new Cart();
        cartItem.setProductId("1");
        cartItem.setProductQuantity(5);

        Product product = new Product();
        product.setProductId("1");
        product.setProductPrice(10);
        product.setProductQuantity(8);
        product.setProductStatus(Constants.ACTIVE);

        when(productRepository.findById("1")).thenReturn(Optional.of(product));
        when(productRepository.save(any(Product.class))).thenReturn(product);

        float totalAmount = productService.getCartAmount(Collections.singletonList(cartItem));

        assertEquals(50.0f, totalAmount);
        assertEquals(3, product.getProductQuantity());
    }

    @Test
    public void testGetProductAmount() {
        String productId = "1";
        int quantity = 3;

        Product product = new Product();
        product.setProductId(productId);
        product.setProductPrice(15);
        product.setProductQuantity(5);
        product.setProductStatus(Constants.ACTIVE);

        when(productRepository.findById(productId)).thenReturn(Optional.of(product));
        when(productRepository.save(product)).thenReturn(product);

        float orderAmount = productService.getProductAmount(productId, quantity);

        assertEquals(45.0f, orderAmount);
        assertEquals(2, product.getProductQuantity());
    }
}
