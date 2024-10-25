package com.fitnessapp.VitalityVault.services;

import com.fitnessapp.VitalityVault.domain.entities.FitnessCenter;

import java.util.List;
import java.util.Optional;

public interface FitnessCenterService {

    FitnessCenter createFitnessCenter(FitnessCenter fitnessCenter);

    Optional<FitnessCenter> getFitnessCenterForId(Long id);

    List<FitnessCenter> findAll(boolean isDeactivated);

    boolean isExists(Long id);

    FitnessCenter update(Long id, FitnessCenter fitnessCenter);

    void deactivateFitnessCenter(Long id);

    FitnessCenter updateContactNo(Long id, String contactNo);

    FitnessCenter updateEmailId(Long id, String emailId);
}
