package com.fitnessapp.VitalityVault.services;

import com.fitnessapp.VitalityVault.domain.entities.Trainer;

import java.util.List;
import java.util.Optional;

public interface TrainerService {

    Trainer createTrainer(Trainer trainer);

    Optional<Trainer> getTrainerForId(Long id);

    List<Trainer> findAll(boolean isDeactivated);

    boolean isExists(Long id);

    Trainer update(Long id, Trainer trainer);

    void deactivateTrainer(Long id);

    Trainer updateContactNo(Long id, String contactNo);

    Trainer updateEmailId(Long id, String emailId);
}
