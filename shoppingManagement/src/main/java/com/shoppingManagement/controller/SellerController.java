package com.shoppingManagement.controller;

import com.shoppingManagement.payload.SellerDto;
import com.shoppingManagement.service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/sellers")
public class SellerController {

    @Autowired
    private SellerService sellerService;

    @GetMapping("/find")
    public ResponseEntity<Page<SellerDto>> getAllSellers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Page<SellerDto> sellers = sellerService.getAllSellers(page, size);
        return ResponseEntity.ok(sellers);
    }

    @GetMapping("/search/{id}")
    public ResponseEntity<SellerDto> getSellerById(@PathVariable("id") Long sellerId) {
        SellerDto seller = sellerService.getSellerById(sellerId);
        if (seller != null) {
            return ResponseEntity.ok(seller);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/save")
    public ResponseEntity<SellerDto> createSeller(@RequestBody SellerDto sellerDto) {
        SellerDto savedSeller = sellerService.createSeller(sellerDto);
        return new ResponseEntity<>(savedSeller, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteSeller(@PathVariable("id") Long sellerId) {
        sellerService.deleteSeller(sellerId);
        return ResponseEntity.noContent().build();
    }
}
