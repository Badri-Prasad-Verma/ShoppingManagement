package com.shoppingManagement.controller;

import com.shoppingManagement.payload.AddressDto;
import com.shoppingManagement.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/addresses")
public class AddressController {

    private final AddressService addressService;

    @Autowired
    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @PostMapping("/customer/{customerId}")
    public ResponseEntity<AddressDto> saveAddress(@PathVariable Long customerId, @Valid @RequestBody AddressDto addressDto) {
        AddressDto savedAddress = addressService.saveAddress(customerId, addressDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedAddress);
    }

    @GetMapping
    public ResponseEntity<List<AddressDto>> getAllAddresses() {
        List<AddressDto> addresses = addressService.getAllAddresses();
        return ResponseEntity.ok(addresses);
    }

    @GetMapping("/{addressId}")
    public ResponseEntity<AddressDto> getAddressById(@PathVariable Long addressId) {
        AddressDto address = addressService.getAddressById(addressId);
        if (address != null) {
            return ResponseEntity.ok(address);
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{addressId}")
    public ResponseEntity<AddressDto> updateAddress(@PathVariable Long addressId, @Valid @RequestBody AddressDto addressDto) {
        AddressDto updatedAddress = addressService.updateAddress(addressId, addressDto);
        return ResponseEntity.ok(updatedAddress);
    }

    @DeleteMapping("/{addressId}")
    public ResponseEntity<String> deleteAddress(@PathVariable Long addressId) {
       addressService.deleteAddress(addressId);
        return new ResponseEntity<>("Address is deleted successfully",HttpStatus.OK);
    }
}
