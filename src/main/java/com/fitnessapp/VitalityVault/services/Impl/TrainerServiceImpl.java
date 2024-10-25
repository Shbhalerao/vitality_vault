package com.fitnessapp.VitalityVault.services.Impl;

import com.fitnessapp.VitalityVault.domain.entities.Trainer;
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
    public Trainer createTrainer(Trainer trainer) {
        if(trainerRepository.existsByContactNo(trainer.getContactNo())){
            throw new DuplicateContactNoException("Trainer already exists for contact no : "+ trainer.getContactNo());
        }
        if(trainerRepository.existsByEmailId(trainer.getEmailId())){
            throw new DuplicateEmailIdException("Trainer already exists for Email Id : "+ trainer.getEmailId());
        }
            trainer.setCreatedDate(new Date());
            trainer.setTrainerId(idGeneratorService.generateIdForTrainer());
            return trainerRepository.save(trainer);
    }

    @Override
    public Optional<Trainer> getTrainerForId(Long id) {
        return Optional.empty();
    }

    @Override
    public List<Trainer> findAll(boolean isDeactivated) {
        Session session = entityManager.unwrap(Session.class);
        Filter filter = session.enableFilter("deactivatedTrainerFilter");
        filter.setParameter("isDeactivated", isDeactivated);
        List<Trainer> trainerEntities =  trainerRepository.findAll();
        session.disableFilter("deactivatedCenterFilter");
        return trainerEntities;
    }

    @Override
    public boolean isExists(Long id) {
        return trainerRepository.existsById(id);
    }

    @Override
    public Trainer update(Long id, Trainer trainer) {
        trainer.setId(id);

        return trainerRepository.findById(id).map(
                existingTrainer -> {
                    Optional.ofNullable(trainer.getTrainerName()).ifPresent(existingTrainer::setTrainerName);
                    Optional.ofNullable(trainer.getCity()).ifPresent(existingTrainer::setCity);
                    Optional.ofNullable(trainer.getAddress()).ifPresent(existingTrainer::setAddress);
                    Optional.ofNullable(trainer.getWorkingSince()).ifPresent(existingTrainer::setWorkingSince);
                    Optional.ofNullable(trainer.getCertifications()).ifPresent(existingTrainer::setCertifications);
                    Optional.ofNullable(trainer.getPinCode()).ifPresent(existingTrainer::setPinCode);
                    Optional.ofNullable(trainer.getState()).ifPresent(existingTrainer::setState);
                    Optional.ofNullable(trainer.getCountry()).ifPresent(existingTrainer::setCountry);
                    Optional.ofNullable(trainer.getFitnessCenter()).ifPresent(existingTrainer::setFitnessCenter);
                    return trainerRepository.save(existingTrainer);
                }
        ).orElseThrow(() -> new ResourceNotFoundException("Trainer not found for id : "+ id));
    }

    @Override
    public void deactivateTrainer(Long id) {
        trainerRepository.deleteById(id);
    }

    @Override
    public Trainer updateContactNo(Long id, String contactNo){
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
    public Trainer updateEmailId(Long id, String emailId){
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
