package com.ecommerce.miniproject.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import org.hibernate.annotations.GenericGenerator;

import java.util.Date;

@Entity
public class Product {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    private String productId;

    private String productName;

    private int productPrice;
    private String productDescription;

    private Date productListedDate;

    private int productQuantity;

    private String productStatus;

    public Product() {
    }
    public Product(String productId, String productName, int productPrice, Date productListedDate, int productQuantity, String productStatus, String productDescription) {
        this.productId = productId;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productListedDate = productListedDate;
        this.productQuantity = productQuantity;
        this.productStatus = productStatus;
        this.productDescription = productDescription;
    }

    public String getProductId() {
        return productId;
    }

    public Date getProductListedDate() {
        return productListedDate;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(int productPrice) {
        this.productPrice = productPrice;
    }

    public int getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(int productQuantity) {
        this.productQuantity = productQuantity;
    }

    public String getProductStatus() {
        return productStatus;
    }

    public void setProductStatus(String productStatus) {
        this.productStatus = productStatus;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }
}
