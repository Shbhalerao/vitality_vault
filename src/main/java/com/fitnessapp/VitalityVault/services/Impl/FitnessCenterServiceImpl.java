package com.fitnessapp.VitalityVault.services.Impl;

import com.fitnessapp.VitalityVault.domain.entities.FitnessCenter;
import com.fitnessapp.VitalityVault.exceptions.DuplicateContactNoException;
import com.fitnessapp.VitalityVault.exceptions.DuplicateEmailIdException;
import com.fitnessapp.VitalityVault.exceptions.ResourceNotFoundException;
import com.fitnessapp.VitalityVault.repositories.FitnessCenterRepository;
import com.fitnessapp.VitalityVault.services.FitnessCenterService;
import com.fitnessapp.VitalityVault.services.IdGeneratorService;
import jakarta.persistence.EntityManager;
import org.hibernate.Filter;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class FitnessCenterServiceImpl implements FitnessCenterService {

    private final FitnessCenterRepository fitnessCenterRepository;

    private final EntityManager entityManager;

    private final IdGeneratorService idGeneratorService;

    @Autowired
    public FitnessCenterServiceImpl(FitnessCenterRepository fitnessCenterRepository, EntityManager entityManager
                    ,IdGeneratorService idGeneratorService){
        this.fitnessCenterRepository = fitnessCenterRepository;
        this.entityManager = entityManager;
        this.idGeneratorService = idGeneratorService;
    }

    @Override
    public FitnessCenter createFitnessCenter(FitnessCenter fitnessCenter) {
        if(fitnessCenterRepository.existsByContactNo(fitnessCenter.getContactNo())){
            throw new DuplicateContactNoException("A Fitness Center already exists with contact no : "+ fitnessCenter.getContactNo());
        }
        if(fitnessCenterRepository.existsByContactNo(fitnessCenter.getEmailId())){
            throw new DuplicateEmailIdException("A Fitness Center already exists with email id : "+ fitnessCenter.getEmailId());
        }
        fitnessCenter.setCreatedDate(new Date());
        fitnessCenter.setCenterId(idGeneratorService.generateIdForFitnessCenter());
        return fitnessCenterRepository.save(fitnessCenter);
    }

    @Override
    public Optional<FitnessCenter> getFitnessCenterForId(Long id) {
      return Optional.ofNullable(fitnessCenterRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Fitness Center not found for id : " + id)));

    }


    @Override
    public List<FitnessCenter> findAll(boolean isDeactivated) {
        Session session = entityManager.unwrap(Session.class);
        Filter filter = session.enableFilter("deactivatedCenterFilter");
        filter.setParameter("isDeactivated", isDeactivated);
        List<FitnessCenter> fitnessCenterEntities =  fitnessCenterRepository.findAll();
        session.disableFilter("deactivatedCenterFilter");
        return fitnessCenterEntities;
    }

    @Override
    public boolean isExists(Long id) {
        return fitnessCenterRepository.existsById(id);
    }

    @Override
    public FitnessCenter update(Long id, FitnessCenter fitnessCenter) {
        fitnessCenter.setId(id);

        return fitnessCenterRepository.findById(id).map(
                existingFitnessCenter -> {
                    Optional.ofNullable(fitnessCenter.getCenterName()).ifPresent(existingFitnessCenter::setCenterName);
                    Optional.ofNullable(fitnessCenter.getCity()).ifPresent(existingFitnessCenter::setCity);
                    Optional.ofNullable(fitnessCenter.getAddressLine1()).ifPresent(existingFitnessCenter::setAddressLine1);
                    Optional.ofNullable(fitnessCenter.getAddressLine2()).ifPresent(existingFitnessCenter::setAddressLine2);
                    Optional.ofNullable(fitnessCenter.getLocality()).ifPresent(existingFitnessCenter::setLocality);
                    Optional.ofNullable(fitnessCenter.getPinCode()).ifPresent(existingFitnessCenter::setPinCode);
                    Optional.ofNullable(fitnessCenter.getState()).ifPresent(existingFitnessCenter::setState);
                    return fitnessCenterRepository.save(existingFitnessCenter);
                }
        ).orElseThrow(() -> new ResourceNotFoundException("Fitness Center not found for id : "+ id));
    }

    @Override
    public void deactivateFitnessCenter(Long id){
        fitnessCenterRepository.deleteById(id);

        //If Center is linked with trainers or clients then those trainers and clients need to be updated as well.
    }

    @Override
    public FitnessCenter updateContactNo(Long id, String contactNo){
        if(fitnessCenterRepository.existsByContactNo(contactNo)){
            throw new DuplicateContactNoException("Fitness Center already exists for contact no : "+contactNo);
        }

        return fitnessCenterRepository.findById(id).map(
                existingFitnessCenter -> {
                    Optional.ofNullable(contactNo).ifPresent(existingFitnessCenter::setContactNo);
                    return fitnessCenterRepository.save(existingFitnessCenter);
                }
        ).orElseThrow(() -> new ResourceNotFoundException("Fitness Center not found for id : "+ id));
    }

    @Override
    public FitnessCenter updateEmailId(Long id, String emailId){
        if(fitnessCenterRepository.existsByContactNo(emailId)){
            throw new DuplicateEmailIdException("Fitness Center already exists for Email Id : "+emailId);
        }

        return fitnessCenterRepository.findById(id).map(
                existingFitnessCenter -> {
                    Optional.ofNullable(emailId).ifPresent(existingFitnessCenter::setEmailId);
                    return fitnessCenterRepository.save(existingFitnessCenter);
                }
        ).orElseThrow(() -> new ResourceNotFoundException("Fitness Center not found for id : "+ id));
    }

   
}
