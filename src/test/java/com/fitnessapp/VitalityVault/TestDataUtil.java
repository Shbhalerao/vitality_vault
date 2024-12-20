package com.fitnessapp.VitalityVault;

import com.fitnessapp.VitalityVault.domain.entities.FitnessCenter;

import java.util.Date;

public final class TestDataUtil {

    private TestDataUtil(){

    }

    public static FitnessCenter createFitnessCenterEntity_One(){
            return FitnessCenter.builder()
                    .centerName("Golds Gym")
                    .addressLine1("First Floor")
                    .addressLine2("Teen Hath Naka")
                    .city(1L)
                    .locality("Teen Hath Naka")
                    .deactivated(false)
                    .state(1)
                    .emailId("golds_gym@gmail.com")
                    .pinCode(400603L)
                    .contactNo("9999999999")
                    .createdDate(new Date())
                    .build();
    }

    public static FitnessCenter createFitnessCenterEntity_Two(){
        return FitnessCenter.builder()
                .centerName("Talwalkars Gym")
                .addressLine1("Ground Floor")
                .addressLine2("Panch Pakhadi")
                .city(1L)
                .locality("Panch Pakhadi")
                .deactivated(false)
                .state(1)
                .emailId("talwalkars_gym@gmail.com")
                .pinCode(400602L)
                .contactNo("8999999999")
                .createdDate(new Date())
                .build();
    }

}
