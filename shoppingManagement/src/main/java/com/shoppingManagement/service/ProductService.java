package com.shoppingManagement.service;

import com.shoppingManagement.payload.ProductDto;
import org.springframework.data.domain.Page;

import java.io.IOException;
import java.util.List;

public interface ProductService {
    Page<ProductDto> getAllProducts(int page, int size);

    List<ProductDto> searchProducts(String productName);

    List<ProductDto> getProductsByQuery(String productName,Double price);

    ProductDto getProductById(Long productId);

    ProductDto addProduct(Long sellerId,ProductDto productDto) throws IOException;

    void deleteProduct(Long productId);
}
