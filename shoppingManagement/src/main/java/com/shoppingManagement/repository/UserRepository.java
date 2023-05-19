package com.shoppingManagement.repository;

import com.shoppingManagement.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User ,Long> {
    Optional<User> findByFirstNameOrEmail(String firstName,String email);
    Optional<User> findByEmail(String email);
    Optional<User> findByFirstName(String firstName);

    boolean existsByEmail(String email);
    boolean existsByFirstName(String firstName);
}
