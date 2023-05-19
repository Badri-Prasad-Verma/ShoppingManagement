package com.shoppingManagement.controller;

import com.shoppingManagement.payload.ProductDto;
import com.shoppingManagement.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/finds")
    public ResponseEntity<Page<ProductDto>> getAllProducts(@RequestParam(defaultValue = "0",required = false) int page,
                                                           @RequestParam(defaultValue = "10",required = false) int size) {
        Page<ProductDto> products = productService.getAllProducts(page, size);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable("id") Long productId) {
        ProductDto product = productService.getProductById(productId);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @PostMapping("/save/seller/{sellerId}")
    public ResponseEntity<ProductDto> addProduct(@ModelAttribute    @PathVariable Long sellerId,
                                                 @RequestParam("productName") String productName,
                                                 @RequestParam("description") String description,
                                                 @RequestParam("manufacturer") String manufacturer,
                                                 @RequestParam int quantity,
                                                 @RequestParam Double price,
                                                 @RequestParam("productPhoto") MultipartFile productPhoto) throws IOException {
        ProductDto productDto=new ProductDto();
        productDto.setProductName(productName);
        productDto.setDescription(description);
        productDto.setManufacturer(manufacturer);
        productDto.setPrice(price);
        productDto.setQuantity(quantity);
        productDto.setProductPhoto(productPhoto);

        ProductDto savedProduct = productService.addProduct(sellerId,productDto);
        return new ResponseEntity<>(savedProduct, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable("id") Long productId) {
        productService.deleteProduct(productId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/finding")
    public ResponseEntity<List<ProductDto>> searchProducts(@RequestParam(value = "productName",required = false) String productName) {
        List<ProductDto> products = productService.searchProducts(productName);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("/getByQuery")
    public ResponseEntity<List<ProductDto>> searchProductsByQuery(@RequestParam(value = "productName",required = false) String productName,
                                                                  @RequestParam(required = false) Double price  ) {
        List<ProductDto> products = productService.getProductsByQuery(productName, Double.valueOf(String.valueOf(price)));
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

}

