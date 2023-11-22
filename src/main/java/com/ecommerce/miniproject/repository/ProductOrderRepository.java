package com.ecommerce.miniproject.repository;

import com.ecommerce.miniproject.model.ProductOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductOrderRepository extends JpaRepository<ProductOrder, String> {
    public List<ProductOrder> findAllByUserEmail(String userEmail);
}
