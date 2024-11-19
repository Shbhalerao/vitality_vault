package com.fitnessapp.VitalityVault.services.Impl;

import com.fitnessapp.VitalityVault.domain.dto.TrainerDto;
import com.fitnessapp.VitalityVault.domain.entities.Address;
import com.fitnessapp.VitalityVault.domain.entities.Trainer;
import com.fitnessapp.VitalityVault.exceptions.DuplicateContactNoException;
import com.fitnessapp.VitalityVault.exceptions.DuplicateEmailIdException;
import com.fitnessapp.VitalityVault.exceptions.ResourceNotFoundException;
import com.fitnessapp.VitalityVault.mappers.Mapper;
import com.fitnessapp.VitalityVault.repositories.TrainerRepository;
import com.fitnessapp.VitalityVault.services.AddressService;
import com.fitnessapp.VitalityVault.services.IdGeneratorService;
import com.fitnessapp.VitalityVault.services.TrainerService;
import jakarta.persistence.EntityManager;
import org.hibernate.Filter;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public class TrainerServiceImpl implements TrainerService {

    private final TrainerRepository trainerRepository;

    private final IdGeneratorService idGeneratorService;

    private final EntityManager entityManager;

    private final AddressService addressService;

    private final Mapper<Trainer, TrainerDto> trainerMapper;

    @Autowired
    public TrainerServiceImpl(TrainerRepository trainerRepository, IdGeneratorService idGeneratorService, EntityManager entityManager, AddressService addressService, Mapper<Trainer, TrainerDto> trainerMapper){
        this.trainerRepository = trainerRepository;
        this.idGeneratorService = idGeneratorService;
        this.entityManager = entityManager;
        this.addressService = addressService;
        this.trainerMapper = trainerMapper;
    }

    @Override
    public TrainerDto createTrainer(TrainerDto trainerDto) {
        Trainer trainer = trainerMapper.mapFrom(trainerDto);
        if(trainerRepository.existsByContactNo(trainer.getContactNo())){
            throw new DuplicateContactNoException("Trainer already exists for contact no : "+ trainerDto.getContactNo());
        }
        if(trainerRepository.existsByEmailId(trainer.getEmailId())){
            throw new DuplicateEmailIdException("Trainer already exists for Email Id : "+ trainerDto.getEmailId());
        }
        Address savedAddress = addressService.save(trainerDto.getAddressDto());
        trainer.setAddressId(savedAddress.getAddressId());
        //From Dto to Entity
        trainer.setCreatedDate(new Date());
        trainer.setTrainerId(idGeneratorService.generateIdForTrainer());
        return trainerMapper.mapTo(trainerRepository.save(trainer));
    }

    @Override
    public Optional<TrainerDto> getTrainerForId(Long id) {
        Optional<Trainer> trainer = trainerRepository.findById(id);
        return trainer.map(trainerMapper::mapTo);
    }

    @Override
    public List<TrainerDto> findAll(boolean isDeactivated) {
        Session session = entityManager.unwrap(Session.class);
        Filter filter = session.enableFilter("deactivatedTrainerFilter");
        filter.setParameter("isDeactivated", isDeactivated);
        List<Trainer> trainerEntities =  trainerRepository.findAll();
        session.disableFilter("deactivatedCenterFilter");
        return trainerEntities.stream()
                .map(trainerMapper::mapTo)
                .toList();
    }

    @Override
    public boolean isExists(Long id) {
        return trainerRepository.existsById(id);
    }

    @Override
    public TrainerDto update(Long id, TrainerDto trainerDto) {
        Trainer trainer = trainerMapper.mapFrom(trainerDto);
        trainer.setId(id);
        Trainer updatedTrainer = trainerRepository.findById(id).map(
                existingTrainer -> {
                    Optional.ofNullable(trainer.getTrainerName()).ifPresent(existingTrainer::setTrainerName);
                    Optional.ofNullable(trainer.getWorkingSince()).ifPresent(existingTrainer::setWorkingSince);
                    Optional.ofNullable(trainer.getCertifications()).ifPresent(existingTrainer::setCertifications);
                    Optional.ofNullable(trainer.getFitnessCenter()).ifPresent(existingTrainer::setFitnessCenter);
                    return trainerRepository.save(existingTrainer);
                }
        ).orElseThrow(() -> new ResourceNotFoundException("Trainer not found for id : "+ id));

        return trainerMapper.mapTo(updatedTrainer);
    }

    @Override
    public void deactivateTrainer(Long id) {
        trainerRepository.deleteById(id);
    }

    @Override
    public TrainerDto updateContactNo(Long id, String contactNo){
        if(trainerRepository.existsByContactNo(contactNo)){
            throw new DuplicateContactNoException("Trainer already exists for contact no : "+contactNo);
        }

        Trainer trainer = trainerRepository.findById(id).map(
                existingTrainer -> {
                    Optional.ofNullable(contactNo).ifPresent(existingTrainer::setContactNo);
                    return trainerRepository.save(existingTrainer);
                }
        ).orElseThrow(() -> new ResourceNotFoundException("Trainer not found for id : "+ id));
        return trainerMapper.mapTo(trainer);
    }

    @Override
    public TrainerDto updateEmailId(Long id, String emailId){
        if(trainerRepository.existsByContactNo(emailId)){
            throw new DuplicateEmailIdException("Trainer already exists for Email Id : "+emailId);
        }

        Trainer trainer = trainerRepository.findById(id).map(
                existingTrainer -> {
                    Optional.ofNullable(emailId).ifPresent(existingTrainer::setEmailId);
                    return trainerRepository.save(existingTrainer);
                }
        ).orElseThrow(() -> new ResourceNotFoundException("Trainer not found for id : "+ id));
        return trainerMapper.mapTo(trainer);
    }
}
