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
public class FitnessCenterDto {

    Long id;

    String centerId; //Only to show in front end.

    String centerName;

    String locality;

    String addressLine1;

    String addressLine2;

    Long city; //Picklist

    Long pinCode;

    Integer state; //PickList

    String emailId;

    String contactNo;

    Date createdDate;

    Boolean deactivated;


    public void assignCenterId(Long id) {
        if (id != null) {
            this.centerId = "fc-" + id;
        }
    }

}
