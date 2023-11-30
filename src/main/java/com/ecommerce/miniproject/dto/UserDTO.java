package com.ecommerce.miniproject.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    @NotEmpty(message = "User email cannot be empty")
    private String userEmail;
    @NotEmpty(message = "User name cannot be empty")
    private String userFullName;
    @NotNull
    private boolean userActive;
    @NotEmpty(message = "User type cannot be empty")
    private boolean userType;
}
