package com.fitnessapp.VitalityVault.services.Impl;

import com.fitnessapp.VitalityVault.domain.entities.TrainerEntity;
import com.fitnessapp.VitalityVault.exceptions.DuplicateContactNoException;
import com.fitnessapp.VitalityVault.exceptions.DuplicateEmailIdException;
import com.fitnessapp.VitalityVault.exceptions.ResourceNotFoundException;
import com.fitnessapp.VitalityVault.repositories.TrainerRepository;
import com.fitnessapp.VitalityVault.services.IdGeneratorService;
import com.fitnessapp.VitalityVault.services.TrainerService;
import jakarta.persistence.EntityManager;
import org.hibernate.Filter;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class TrainerServiceImpl implements TrainerService {

    private final TrainerRepository trainerRepository;

    private final IdGeneratorService idGeneratorService;

    private final EntityManager entityManager;

    @Autowired
    public TrainerServiceImpl(TrainerRepository trainerRepository, IdGeneratorService idGeneratorService, EntityManager entityManager){
        this.trainerRepository = trainerRepository;
        this.idGeneratorService = idGeneratorService;
        this.entityManager = entityManager;
    }

    @Override
    public TrainerEntity createTrainer(TrainerEntity trainerEntity) {
        if(trainerRepository.existsByContactNo(trainerEntity.getContactNo())){
            throw new DuplicateContactNoException("Trainer already exists for contact no : "+trainerEntity.getContactNo());
        }
        if(trainerRepository.existsByEmailId(trainerEntity.getEmailId())){
            throw new DuplicateEmailIdException("Trainer already exists for Email Id : "+trainerEntity.getEmailId());
        }
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
        Session session = entityManager.unwrap(Session.class);
        Filter filter = session.enableFilter("deactivatedCenterFilter");
        filter.setParameter("isDeactivated", isDeactivated);
        List<TrainerEntity> trainerEntities =  trainerRepository.findAll();
        session.disableFilter("deactivatedCenterFilter");
        return trainerEntities;
    }

    @Override
    public boolean isExists(Long id) {
        return trainerRepository.existsById(id);
    }

    @Override
    public TrainerEntity update(Long id, TrainerEntity trainerEntity) {
        trainerEntity.setId(id);

        return trainerRepository.findById(id).map(
                existingTrainer -> {
                    Optional.ofNullable(trainerEntity.getTrainerName()).ifPresent(existingTrainer::setTrainerName);
                    Optional.ofNullable(trainerEntity.getCity()).ifPresent(existingTrainer::setCity);
                    Optional.ofNullable(trainerEntity.getAddress()).ifPresent(existingTrainer::setAddress);
                    Optional.ofNullable(trainerEntity.getWorkingSince()).ifPresent(existingTrainer::setWorkingSince);
                    Optional.ofNullable(trainerEntity.getCertifications()).ifPresent(existingTrainer::setCertifications);
                    Optional.ofNullable(trainerEntity.getPinCode()).ifPresent(existingTrainer::setPinCode);
                    Optional.ofNullable(trainerEntity.getState()).ifPresent(existingTrainer::setState);
                    Optional.ofNullable(trainerEntity.getCountry()).ifPresent(existingTrainer::setCountry);
                    Optional.ofNullable(trainerEntity.getFitnessCenterEntity()).ifPresent(existingTrainer::setFitnessCenterEntity);
                    return trainerRepository.save(existingTrainer);
                }
        ).orElseThrow(() -> new ResourceNotFoundException("Trainer not found for id : "+ id));
    }

    @Override
    public void deactivateTrainer(Long id) {
        trainerRepository.deleteById(id);

        //If trainer is linked with clients then those clients need to be updated as well.
    }

    @Override
    public TrainerEntity updateContactNo(Long id, String contactNo){
        if(trainerRepository.existsByContactNo(contactNo)){
            throw new DuplicateContactNoException("Trainer already exists for contact no : "+contactNo);
        }

        return trainerRepository.findById(id).map(
                existingTrainer -> {
                    Optional.ofNullable(contactNo).ifPresent(existingTrainer::setContactNo);
                    return trainerRepository.save(existingTrainer);
                }
        ).orElseThrow(() -> new ResourceNotFoundException("Trainer not found for id : "+ id));
    }

    @Override
    public TrainerEntity updateEmailId(Long id, String emailId){
        if(trainerRepository.existsByContactNo(emailId)){
            throw new DuplicateEmailIdException("Trainer already exists for Email Id : "+emailId);
        }

        return trainerRepository.findById(id).map(
                existingTrainer -> {
                    Optional.ofNullable(emailId).ifPresent(existingTrainer::setEmailId);
                    return trainerRepository.save(existingTrainer);
                }
        ).orElseThrow(() -> new ResourceNotFoundException("Trainer not found for id : "+ id));
    }

}
