package com.fitnessapp.VitalityVault.services;

import com.fitnessapp.VitalityVault.domain.entities.FitnessCenterEntity;

import java.util.List;
import java.util.Optional;

public interface FitnessCenterService {

    FitnessCenterEntity createFitnessCenter(FitnessCenterEntity fitnessCenterEntity);

    Optional<FitnessCenterEntity> getFitnessCenterForId(Long id);

    List<FitnessCenterEntity> findAll(boolean isDeactivated);

    boolean isExists(Long id);

    FitnessCenterEntity update(Long id,FitnessCenterEntity fitnessCenterEntity);

    void deactivateFitnessCenter(Long id);

    FitnessCenterEntity updateContactNo(Long id, String contactNo);

    FitnessCenterEntity updateEmailId(Long id, String emailId);
}
