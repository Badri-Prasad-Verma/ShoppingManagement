package com.shoppingManagement.service;

import com.shoppingManagement.payload.CustomerDto;

import java.io.IOException;

public interface CustomerService {
    CustomerDto saveCustomer(CustomerDto customerDto);
}
