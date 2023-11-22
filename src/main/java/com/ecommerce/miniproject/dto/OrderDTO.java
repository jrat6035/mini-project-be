package com.ecommerce.miniproject.dto;

import com.ecommerce.miniproject.model.Cart;
import com.ecommerce.miniproject.model.User;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {
    @NotEmpty(message = "User email cannot be empty")
    private String userEmail;

    @NotEmpty(message = "Cart items cannot be empty")
    private List<Cart> cartItems;

    private String description;
}
