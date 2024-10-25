package com.fitnessapp.VitalityVault.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
public class ClientDto {

    private Long id;

    private String clientId;

    private String firstName;

    private String lastName;

    private Date dateOfBirth;

    private Double height;

    private Double weight;

    private String address;

    private Long city;

    private Integer state;

    private Long pinCode;

    private String contactNo;

    private String emailId;

    private Integer fitnessGoal;

    private Date createdDate;

    private boolean deactivated = Boolean.FALSE;

    private TrainerDto trainerDto;

    private AddressDto addressDto;


}
