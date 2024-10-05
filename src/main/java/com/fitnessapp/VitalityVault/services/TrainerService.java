package com.fitnessapp.VitalityVault.services;

import com.fitnessapp.VitalityVault.domain.entities.FitnessCenterEntity;
import com.fitnessapp.VitalityVault.domain.entities.TrainerEntity;

import java.util.List;
import java.util.Optional;

public interface TrainerService {

    TrainerEntity createTrainer(TrainerEntity trainerEntity);

    Optional<TrainerEntity> getTrainerForId(Long id);

    List<TrainerEntity> findAll(boolean isDeactivated);

    boolean isExists(Long id);

    TrainerEntity update(Long id,TrainerEntity trainerEntity);

    void deactivateTrainer(Long id);
}
