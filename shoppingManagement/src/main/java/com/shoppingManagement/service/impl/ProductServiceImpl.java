package com.shoppingManagement.service.impl;

import com.shoppingManagement.entity.Product;
import com.shoppingManagement.entity.Seller;
import com.shoppingManagement.exception.ResourceNotFoundException;
import com.shoppingManagement.payload.ProductDto;
import com.shoppingManagement.repository.ProductRepository;
import com.shoppingManagement.repository.SellerRepository;
import com.shoppingManagement.service.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private final String uploadDirectory = "src/main/resources/static/customer_profile_image/";

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private SellerRepository sellerRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public Page<ProductDto> getAllProducts(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Product> products = productRepository.findAll(pageable);
        return products.map(product -> modelMapper.map(product, ProductDto.class));
    }

    @Override
    public List<ProductDto> searchProducts(String productName) {
        List<Product> products = productRepository.searchProductsByKeyword(productName);
        return products.stream().map(product -> modelMapper.map(product, ProductDto.class)).collect(Collectors.toList());
    }

    @Override
    public List<ProductDto> getProductsByQuery(String productName,Double price) {
        List<Product> products = productRepository.searchProductsByQuery(productName, Double.valueOf(String.valueOf(price)));
        return products.stream().map(product -> modelMapper.map(product, ProductDto.class)).collect(Collectors.toList());
    }

    @Override
    public ProductDto getProductById(Long productId) {
        Optional<Product> optionalProduct = productRepository.findById(productId);
        if (optionalProduct.isPresent()) {
            Product product = optionalProduct.get();
            return modelMapper.map(product, ProductDto.class);
        }
        throw new ResourceNotFoundException("Product not found with id: ","PostId" , productId);
    }

    @Override
    public ProductDto addProduct(Long sellerId,ProductDto productDto) throws IOException {
        Seller seller = sellerRepository.findById(sellerId).orElseThrow(() -> new ResourceNotFoundException("id", "id", sellerId));
        Product product = new Product();
        product.setSeller(seller);
        product.setProductName(productDto.getProductName());
        product.setDescription(productDto.getDescription());
        product.setQuantity(productDto.getQuantity());
        product.setPrice(productDto.getPrice());
        product.setManufacturer(productDto.getManufacturer());


        if (productDto.getProductPhoto() != null && !productDto.getProductPhoto().isEmpty()) {
            product.setPhoto(productDto.getProductPhoto().getBytes());
          //  String fileName = saveProductImage(productPhoto);
        }

        Product savedProduct = productRepository.save(product);
        return modelMapper.map(savedProduct, ProductDto.class);
    }

    @Override
    public void deleteProduct(Long productId) {
        Optional<Product> optionalProduct = productRepository.findById(productId);
        if (optionalProduct.isPresent()) {
            Product product = optionalProduct.get();
            productRepository.delete(product);
        } else {
            throw new ResourceNotFoundException("Product not found with id","productId", productId);
        }
    }
}

