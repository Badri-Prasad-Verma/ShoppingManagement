package com.shoppingManagement.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_Name")
    private String lastName;

    @Column(name = "email",unique = true)
    private String email;

    @Column(name = "password",unique = true)
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "users_roles",
    joinColumns = @JoinColumn(name = "users_id",referencedColumnName = "id"),
    inverseJoinColumns = @JoinColumn(name = "roles_id",referencedColumnName = "id"))
    private Set<Role> roles;


}

