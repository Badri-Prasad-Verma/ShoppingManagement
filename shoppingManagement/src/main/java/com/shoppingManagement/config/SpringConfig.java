package com.shoppingManagement.config;

import com.shoppingManagement.seurity.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SpringConfig extends WebSecurityConfigurerAdapter {

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Autowired
    CustomUserDetailsService userDetailsService;

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers( "/api/users/**").permitAll()
                .antMatchers( "/api/addresses/**").permitAll()
                .antMatchers("/api/auth/**").permitAll()
                .antMatchers("/api/admin/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET, "/api/sellers/**").hasAnyRole("SELLER", "ADMIN")
                .antMatchers(HttpMethod.POST, "/api/sellers/**").hasAnyRole("SELLER", "ADMIN")
                .antMatchers(HttpMethod.PUT, "/api/sellers/**").hasAnyRole("SELLER", "ADMIN")
                .antMatchers(HttpMethod.DELETE, "/api/sellers/**").hasAnyRole("SELLER", "ADMIN")
                .antMatchers(HttpMethod.POST, "/api/customers/**").hasAnyRole("CUSTOMER", "ADMIN")
                .antMatchers(HttpMethod.PUT, "/api/customers/**").hasAnyRole("CUSTOMER", "ADMIN")
                .antMatchers(HttpMethod.DELETE, "/api/customers/**").hasAnyRole("CUSTOMER", "ADMIN")
                .antMatchers(HttpMethod.GET, "/api/customers/**").hasAnyRole("CUSTOMER", "ADMIN")
                .antMatchers("/api/common/**").hasAnyRole("CUSTOMER", "SELLER", "ADMIN")
                .antMatchers(HttpMethod.GET,"/api/products/**").hasAnyRole("CUSTOMER","SELLER","ADMIN")
                .antMatchers(HttpMethod.POST,"/api/products/**").hasAnyRole("SELLER","ADMIN")
                .antMatchers(HttpMethod.PUT,"/api/products/**").hasAnyRole("SELLER","ADMIN")
                .antMatchers(HttpMethod.DELETE,"/api/products/**").hasAnyRole("SELLER","ADMIN")
                .anyRequest().authenticated()
                .and()
                .httpBasic();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }
}
