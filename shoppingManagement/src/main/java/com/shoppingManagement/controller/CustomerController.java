package com.shoppingManagement.controller;

import com.shoppingManagement.payload.CustomerDto;
import com.shoppingManagement.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    private CustomerService customerService;

    //@PreAuthorize("hasAnyRole('CUSTOMER','ADMIN')")
    @PostMapping(value = "/register", consumes = {"multipart/form-data"})
    public ResponseEntity<CustomerDto> saveCustomer(@RequestParam("firstName") String firstName,
                                                   @RequestParam("lastName") String lastName,
                                                   @RequestParam("email") String email,
                                                   @RequestParam("mobile") String mobile,
                                                   @RequestParam("password") String password,
                                                   @RequestParam(value = "customerPhoto", required = false) MultipartFile customerPhoto) throws IOException {

        CustomerDto customerDto=new CustomerDto();
        customerDto.setFirstName(firstName);
        customerDto.setLastName(lastName);
        customerDto.setEmail(email);
        customerDto.setMobile(mobile);
        customerDto.setPassword(password);
        customerDto.setCustomerPhoto(customerPhoto);
        CustomerDto savedCustomer = customerService.saveCustomer(customerDto);
        return new ResponseEntity<>(savedCustomer, HttpStatus.CREATED);
    }
}
