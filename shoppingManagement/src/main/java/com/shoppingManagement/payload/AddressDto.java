package com.shoppingManagement.payload;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressDto {

    private Long id;
    private Long customerId;

    @NotBlank(message = "Street number cannot be blank")
    private String streetNo;

    @NotBlank(message = "Building name cannot be blank")
    private String buildingName;

    @NotBlank(message = "Locality cannot be blank")
    private String locality;

    @NotBlank(message = "City cannot be blank")
    private String city;

    @NotBlank(message = "State cannot be blank")
    private String state;

    @Pattern(regexp = "^\\d{6}$", message = "Pin code must be 6 digits long")
    private String pinCode;


}

