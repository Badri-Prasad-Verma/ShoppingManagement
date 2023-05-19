package com.shoppingManagement.service.impl;


import com.shoppingManagement.entity.Address;
import com.shoppingManagement.entity.Customer;
import com.shoppingManagement.exception.ResourceNotFoundException;
import com.shoppingManagement.payload.AddressDto;
import com.shoppingManagement.repository.AddressRepository;
import com.shoppingManagement.repository.CustomerRepository;
import com.shoppingManagement.service.AddressService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;
    private final CustomerRepository customerRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public AddressServiceImpl(AddressRepository addressRepository, CustomerRepository customerRepository, ModelMapper modelMapper) {
        this.addressRepository = addressRepository;
        this.customerRepository = customerRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public AddressDto saveAddress(Long customerId, AddressDto addressDto) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer", "customerId", customerId));

        Address address = modelMapper.map(addressDto, Address.class);
        address.setCustomer(customer); // Set the customer for the address

        Address savedAddress = addressRepository.save(address);
        return modelMapper.map(savedAddress, AddressDto.class);
    }

    @Override
    public List<AddressDto> getAllAddresses() {
        List<Address> addresses = addressRepository.findAll();
        return addresses.stream()
                .map(address -> modelMapper.map(address, AddressDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public AddressDto getAddressById(Long addressId) {
        Optional<Address> optionalAddress = addressRepository.findById(addressId);
        return optionalAddress.map(address -> modelMapper.map(address, AddressDto.class)).orElse(null);
    }

    @Override
    public void deleteAddress(Long addressId) {
        Optional<Address> optionalAddress = addressRepository.findById(addressId);
        if (optionalAddress.isPresent()) {
            addressRepository.delete(optionalAddress.get());

        }else{
            throw new ResourceNotFoundException("Address","addressId",addressId);
        }

    }

    @Override
    public AddressDto updateAddress(Long addressId, AddressDto addressDto) {
        Address address = addressRepository.findById(addressId)
                .orElseThrow(() -> new ResourceNotFoundException("Address", "addressId", addressId));

        address.setStreetNo(addressDto.getStreetNo());
        address.setBuildingName(addressDto.getBuildingName());
        address.setLocality(addressDto.getLocality());
        address.setCity(addressDto.getCity());
        address.setState(addressDto.getState());
        address.setPinCode(addressDto.getPinCode());

        Address updatedAddress = addressRepository.save(address);
        return modelMapper.map(updatedAddress, AddressDto.class);
    }
}

