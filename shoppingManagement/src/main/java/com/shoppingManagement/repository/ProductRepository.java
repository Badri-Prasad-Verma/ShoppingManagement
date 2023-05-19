package com.shoppingManagement.repository;

import com.shoppingManagement.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {
    @Query("FROM Product WHERE productName=:productName")
    List<Product> searchProductsByKeyword(@Param("productName") String productName);


    @Query("FROM Product WHERE productName=:productName and price=:price")
    List<Product> searchProductsByQuery(@Param("productName") String productName, @Param("price") Double price);

}
