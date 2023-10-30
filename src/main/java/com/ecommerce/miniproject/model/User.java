package com.ecommerce.miniproject.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class User {
    @Id
    private String id;

    @Column(unique = true)
    private String userEmail;

    private String userFullName;

    private boolean userActive;
}
