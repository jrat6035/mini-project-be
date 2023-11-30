package com.ecommerce.miniproject.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {
    @NotEmpty(message = "Product name cannot be empty")
    private String productName;

    @Min(value = 1, message = "Product price must be at least 1")
    @Max(value = 10000, message = "Product price cannot exceed 1000")
    private int productPrice;

    private String productDescription;

    @NotNull
    private Date productUpdatedDate;

    @Min(value = 0, message = "Product quantity must be at least 0")
    @Max(value = 10000, message = "Product quantity cannot exceed 20")
    private int productQuantity;

    @NotEmpty
    @Pattern(regexp = "^(Active|Inactive)$", message = "Product status is not a valid input")
    private String productStatus;

    private String productImageUrl;
}