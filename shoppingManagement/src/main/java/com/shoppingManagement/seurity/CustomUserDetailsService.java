package com.shoppingManagement.seurity;

import com.shoppingManagement.entity.Role;
import com.shoppingManagement.entity.User;
import com.shoppingManagement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String firstNameOrEmail) throws UsernameNotFoundException {
        User user = userRepository.findByFirstNameOrEmail(firstNameOrEmail, firstNameOrEmail)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found"));
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),
                mapToAuthority(user.getRoles()));
    }
    private Collection<? extends GrantedAuthority> mapToAuthority(Set<Role> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }
}
