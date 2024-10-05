package com.fitnessapp.VitalityVault.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class TrainerDto {

    private Long id;

    String trainerId;

    String centerId;

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


    public void assignTrainerId(Long id) {
        if (id != null) {
            this.trainerId = "tr-" + String.format("%04d", id);
        }
    }

}
