package com.shoppingManagement.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "sellers")
@Entity
public class Seller {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long sellerId;

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

    @OneToMany(mappedBy = "seller",cascade = CascadeType.ALL)
    private List<Product> products=new ArrayList<>();
}
