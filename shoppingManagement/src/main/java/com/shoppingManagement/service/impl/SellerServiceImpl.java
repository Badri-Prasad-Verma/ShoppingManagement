package com.shoppingManagement.service.impl;

import com.shoppingManagement.entity.Seller;
import com.shoppingManagement.payload.SellerDto;
import com.shoppingManagement.repository.SellerRepository;
import com.shoppingManagement.service.SellerService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
public class SellerServiceImpl implements SellerService {

    @Autowired
    public PasswordEncoder passwordEncoder;
    private SellerRepository sellerRepository;
    private ModelMapper modelMapper;

    @Autowired
    public SellerServiceImpl(SellerRepository sellerRepository, ModelMapper modelMapper) {
        this.sellerRepository = sellerRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Page<SellerDto> getAllSellers(int page, int size) {
        Pageable pageable= PageRequest.of(page,size);
        Page<Seller> all = sellerRepository.findAll(pageable);
        return all.map(seller -> modelMapper.map(seller,SellerDto.class));
    }

    @Override
    public SellerDto getSellerById(Long sellerId) {
        Optional<Seller> seller = sellerRepository.findById(sellerId);
        if (seller.isPresent()) {
            return modelMapper.map(seller.get(),SellerDto.class);
        } else {
            return null;
        }
    }

    @Override
    public SellerDto createSeller(SellerDto sellerDto) {
        Seller seller = modelMapper.map(sellerDto,Seller.class);
        seller.setPassword(passwordEncoder.encode(sellerDto.getPassword()));
        Seller savedSeller = sellerRepository.save(seller);
        return modelMapper.map(savedSeller,SellerDto.class);
    }

    @Override
    public void deleteSeller(Long sellerId) {
        sellerRepository.deleteById(sellerId);
    }


}

