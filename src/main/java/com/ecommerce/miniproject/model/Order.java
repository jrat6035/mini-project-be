package com.ecommerce.miniproject.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private String userId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, targetEntity = ProductOrder.class)
    @JoinColumn(name = "cart_id", referencedColumnName = "id")
    private Cart cartId;

    @OneToOne
    @JoinColumn(name = "cart_id")
    private Cart cart;

    private int totalPrice;

    private Date createdDate;
}
