package com.fitnessapp.VitalityVault.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class TrainerDto {

    private Long id;

    private String trainerId;

    private String trainerName;

    private Date workingSince;

    private List<Long> certifications;

    private Long city;

    private Long pinCode;

    private Integer state;

    private Long country;

    private String address;

    private String contactNo;

    private String emailId;

    private Date createdDate;

    private Boolean deactivated =  Boolean.FALSE;

    private FitnessCenterDto fitnessCenterDto;


}
