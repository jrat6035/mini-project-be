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
public class CartDTO {
    @NotEmpty(message = "User id cannot be empty")
    private String userId;

    private List<ProductOrder> productList;

    private int totalPrice;

    @NotNull
    private Date lastUpdatedDateTime;
}
