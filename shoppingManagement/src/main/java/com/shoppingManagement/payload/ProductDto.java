package com.shoppingManagement.payload;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {

    private Long productId;
    private Long sellerId;

    @NotBlank(message = "Product name cannot be blank")
    @Size(max = 255, message = "Product name cannot exceed 255 characters")
    private String productName;

    @NotNull(message = "Price cannot be null")
    @DecimalMin(value = "0.01", message = "Price must be greater than or equal to 0.01")
    @DecimalMax(value = "99999.99", message = "Price must be less than or equal to 99999.99")
    private Double price;

    @NotBlank(message = "Description cannot be blank")
    @Size(max = 1000, message = "Description cannot exceed 1000 characters")
    private String description;

    @NotBlank(message = "Manufacturer cannot be blank")
    @Size(max = 255, message = "Manufacturer name cannot exceed 255 characters")
    private String manufacturer;

    private MultipartFile productPhoto;

    @NotNull(message = "Quantity cannot be null")
    @Min(value = 0, message = "Quantity must be greater than or equal to 0")
    @Max(value = 99999, message = "Quantity must be less than or equal to 99999")
    private Integer quantity;

}

