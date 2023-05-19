package com.shoppingManagement.service;

import com.shoppingManagement.payload.AddressDto;

import java.util.List;

public interface AddressService {
    AddressDto saveAddress(Long customerId, AddressDto addressDto);

    List<AddressDto> getAllAddresses();

    AddressDto getAddressById(Long addressId);

    public void deleteAddress(Long addressId);

    AddressDto updateAddress(Long addressId, AddressDto addressDto);
}
