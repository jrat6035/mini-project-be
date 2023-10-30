package com.ecommerce.miniproject.service;

import com.ecommerce.miniproject.model.Cart;
import com.ecommerce.miniproject.model.Order;

import java.util.List;

public interface CartService {
    Cart getCartById(String id);
    Cart createCart(Cart cart);
    Cart updateCart(String id, Cart cart);
    void clearCart(String id);
}
