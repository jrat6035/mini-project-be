package com.ecommerce.miniproject.model;

import jakarta.persistence.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToMany
    @JoinTable(
            name = "cart_product",
            joinColumns = @JoinColumn(name = "cart_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private List<Product> products;

    @ElementCollection
    @CollectionTable(name = "cart_product_quantity", joinColumns = @JoinColumn(name = "cart_id"))
    @MapKeyJoinColumn(name = "product_id")
    private Map<Product, Integer> productQuantity;

    private Date lastUpdatedDateTime;
}
