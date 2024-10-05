package com.fitnessapp.VitalityVault.services.Impl;

import com.fitnessapp.VitalityVault.constants.IdConstants;
import com.fitnessapp.VitalityVault.repositories.IdGeneratorRepository;
import com.fitnessapp.VitalityVault.services.IdGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;

/*
To improve the performance and reduce the database dependency
incorporate Redis to generate id.
1. Cache the id and then append it in the redis itself instead of querying in db everytime.
2. We are using simple approach to generate Id which is to increment max id by 1 and then
append a String in-front of it.
 */
public class IdGeneratorServiceImpl implements IdGeneratorService {

    private final IdGeneratorRepository idGeneratorRepository;

    @Autowired
    public IdGeneratorServiceImpl(IdGeneratorRepository idGeneratorRepository){
        this.idGeneratorRepository = idGeneratorRepository;
    }

    @Override
    public synchronized String generateIdForFitnessCenter() {
        Long id = idGeneratorRepository.findMaxIdForFitnessCenter() + 1;
        return IdConstants.FITNESS_CENTER_ID_PREFIX + String.format("%04d", id);
    }

    @Override
     public synchronized String generateIdForTrainer() {
        Long id = idGeneratorRepository.findMaxIdForTrainer() + 1;
        return IdConstants.TRAINER_ID_PREFIX + String.format("%06d", id);
    }
}
