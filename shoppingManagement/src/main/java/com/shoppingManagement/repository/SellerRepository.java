package com.shoppingManagement.repository;

import com.shoppingManagement.entity.Seller;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SellerRepository extends JpaRepository<Seller,Long> {
}
