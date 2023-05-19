package com.shoppingManagement.repository;

import com.shoppingManagement.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address,Long> {
}
