package com.fitnessapp.VitalityVault.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class FitnessCenterDto {

    @JsonProperty(required = false)
    private Long id;

    private String centerId;

    private String centerName;

    private String emailId;

    private String contactNo;

    private Date createdDate;

    private Boolean deactivated;

    private AddressDto addressDto;
}
