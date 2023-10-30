package com.ecommerce.miniproject.dto;

import com.ecommerce.miniproject.model.ProductOrder;
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
    @NotEmpty(message = "User id cannot be empty")
    private String userId;

    @NotEmpty(message = "Cart id cannot be empty")
    private String cartId;

    @Min(value = 0, message = "Total order price must be at least 0")
    private int totalPrice;

    @NotNull
    private Date createdDate;
}
