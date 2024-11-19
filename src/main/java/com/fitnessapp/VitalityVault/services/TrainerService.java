package com.fitnessapp.VitalityVault.services;

import com.fitnessapp.VitalityVault.domain.dto.TrainerDto;
import com.fitnessapp.VitalityVault.domain.entities.Trainer;

import java.util.List;
import java.util.Optional;

public interface TrainerService {

    TrainerDto createTrainer(TrainerDto trainerDto);

    Optional<TrainerDto> getTrainerForId(Long id);

    List<TrainerDto> findAll(boolean isDeactivated);

    boolean isExists(Long id);

    TrainerDto update(Long id, TrainerDto trainerDto);

    void deactivateTrainer(Long id);

    TrainerDto updateContactNo(Long id, String contactNo);

    TrainerDto updateEmailId(Long id, String emailId);
}
