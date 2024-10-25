package com.fitnessapp.VitalityVault.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddressDto {

    private Long addressId;

    private String street;

    private String city;

    private String state;

    private String country;

    private String pinCode;

    private String additionalInfo;

    private String entityType;

    private Long entityId;

}
