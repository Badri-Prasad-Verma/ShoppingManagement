package com.shoppingManagement.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.*;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDto {

    private Long customerId;

    @NotBlank(message = "First name cannot be blank")
    private String firstName;

    @NotBlank(message = "Last name cannot be blank")
    private String lastName;

    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "Mobile number cannot be blank")
    @Size(min = 10, max = 10, message = "Mobile number must be 10 digits")
    private String mobile;

    @NotBlank(message = "Password cannot be blank")
    @Size(min = 8, message = "Password must be between 8 and 20 characters")
    private String password;

    private LocalDateTime createdDate;

    private MultipartFile customerPhoto;

}
