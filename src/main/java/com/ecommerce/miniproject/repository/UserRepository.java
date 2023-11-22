package com.ecommerce.miniproject.repository;

import com.ecommerce.miniproject.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    User findByUserEmail(String userEmail);
}
