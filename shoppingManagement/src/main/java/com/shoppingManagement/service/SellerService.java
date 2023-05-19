package com.shoppingManagement.service;

import com.shoppingManagement.payload.SellerDto;
import org.springframework.data.domain.Page;

public interface SellerService {
    Page<SellerDto> getAllSellers(int page, int size);

    SellerDto getSellerById(Long sellerId);

    SellerDto createSeller(SellerDto sellerDto);


    void deleteSeller(Long sellerId);
}
