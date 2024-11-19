package com.fitnessapp.VitalityVault.services;

import com.fitnessapp.VitalityVault.domain.dto.FitnessCenterDto;
import com.fitnessapp.VitalityVault.domain.entities.FitnessCenter;

import java.util.List;
import java.util.Optional;

public interface FitnessCenterService {

    FitnessCenterDto createFitnessCenter(FitnessCenterDto fitnessCenter);

    Optional<FitnessCenterDto> getFitnessCenterForId(Long id);

    List<FitnessCenterDto> findAll(boolean isDeactivated);

    boolean isExists(Long id);

    FitnessCenterDto update(Long id, FitnessCenterDto fitnessCenterDto);

    void deactivateFitnessCenter(Long id);

    FitnessCenterDto updateContactNo(Long id, String contactNo);

    FitnessCenterDto updateEmailId(Long id, String emailId);
}
