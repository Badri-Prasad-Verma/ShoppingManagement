package com.shoppingManagement.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "customers")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long customerId;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email",unique = true)
    private String email;

    @Column(name = "mobile")
    private String mobile;

    @Column(name = "password",unique = true)
    private String password;

    private LocalDateTime createdDate;

    private String photo;

    @OneToMany(mappedBy = "customer")
    private List<Address> addresses=new ArrayList<>();


}
