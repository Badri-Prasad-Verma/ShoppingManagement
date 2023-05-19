package com.shoppingManagement.service.impl;

import com.shoppingManagement.entity.Customer;
import com.shoppingManagement.payload.CustomerDto;
import com.shoppingManagement.repository.CustomerRepository;
import com.shoppingManagement.service.CustomerService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
@Service
    public class CustomerServiceImpl implements CustomerService {
    private final String uploadDirectory = "src/main/resources/static/customer_profile_image/";
    @Autowired
    private  CustomerRepository customerRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private ModelMapper modelMapper;
    @Override
    public CustomerDto saveCustomer(CustomerDto customerDto) {
        Customer customer = modelMapper.map(customerDto, Customer.class);
        customer.setPassword(passwordEncoder.encode(customerDto.getPassword()));
        customer.setCreatedDate(LocalDateTime.now());

        MultipartFile profileImage = customerDto.getCustomerPhoto();
        if (profileImage != null && !profileImage.isEmpty()) {
            String fileName = saveProfileImage(profileImage);
            customer.setPhoto(fileName);
        }
        Customer save = customerRepository.save(customer);
        return modelMapper.map(save,CustomerDto.class);
    }

    private String saveProfileImage(MultipartFile file) {
        try {
            byte[] bytes = file.getBytes();
            String originalFileName = file.getOriginalFilename();
            String fileExtension = originalFileName.substring(originalFileName.lastIndexOf('.'));
            String baseFileName = originalFileName.substring(0, originalFileName.lastIndexOf('.'));
            String uniqueFileName = baseFileName + "_" + System.currentTimeMillis() + fileExtension;
            Path path = Paths.get(uploadDirectory + uniqueFileName);
            Files.write(path, bytes);

            return uniqueFileName;
        } catch (IOException e) {
            throw new RuntimeException("Failed to save profile image", e);
        }
    }
}




