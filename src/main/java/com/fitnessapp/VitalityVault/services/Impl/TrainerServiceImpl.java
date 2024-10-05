package com.fitnessapp.VitalityVault.services.Impl;

import com.fitnessapp.VitalityVault.domain.entities.TrainerEntity;
import com.fitnessapp.VitalityVault.repositories.TrainerRepository;
import com.fitnessapp.VitalityVault.services.IdGeneratorService;
import com.fitnessapp.VitalityVault.services.TrainerService;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class TrainerServiceImpl implements TrainerService {

    private final TrainerRepository trainerRepository;

    private final IdGeneratorService idGeneratorService;

    public TrainerServiceImpl(TrainerRepository trainerRepository, IdGeneratorService idGeneratorService){
        this.trainerRepository = trainerRepository;
        this.idGeneratorService = idGeneratorService;
    }

    @Override
    public TrainerEntity createTrainer(TrainerEntity trainerEntity) {
            trainerEntity.setCreatedDate(new Date());
            trainerEntity.setTrainerId(idGeneratorService.generateIdForTrainer());
            return trainerRepository.save(trainerEntity);
    }

    @Override
    public Optional<TrainerEntity> getTrainerForId(Long id) {
        return Optional.empty();
    }

    @Override
    public List<TrainerEntity> findAll(boolean isDeactivated) {
        return List.of();
    }

    @Override
    public boolean isExists(Long id) {
        return false;
    }

    @Override
    public TrainerEntity update(Long id, TrainerEntity trainerEntity) {
        return null;
    }

    @Override
    public void deactivateTrainer(Long id) {

    }
}
