package com.fitnessapp.VitalityVault.services.Impl;

import com.fitnessapp.VitalityVault.domain.dto.FitnessCenterDto;
import com.fitnessapp.VitalityVault.domain.entities.Address;
import com.fitnessapp.VitalityVault.domain.entities.FitnessCenter;
import com.fitnessapp.VitalityVault.exceptions.DuplicateContactNoException;
import com.fitnessapp.VitalityVault.exceptions.DuplicateEmailIdException;
import com.fitnessapp.VitalityVault.exceptions.ResourceNotFoundException;
import com.fitnessapp.VitalityVault.mappers.Mapper;
import com.fitnessapp.VitalityVault.repositories.FitnessCenterRepository;
import com.fitnessapp.VitalityVault.services.AddressService;
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

    private final AddressService addressService;

    private final Mapper<FitnessCenter, FitnessCenterDto> fitnessCenterMapper;

    @Autowired
    public FitnessCenterServiceImpl(FitnessCenterRepository fitnessCenterRepository, EntityManager entityManager
                    , IdGeneratorService idGeneratorService, AddressService addressService, Mapper<FitnessCenter, FitnessCenterDto> fitnessCenterMapper){
        this.fitnessCenterRepository = fitnessCenterRepository;
        this.entityManager = entityManager;
        this.idGeneratorService = idGeneratorService;
        this.addressService = addressService;
        this.fitnessCenterMapper = fitnessCenterMapper;
    }

    @Override
    public FitnessCenterDto createFitnessCenter(FitnessCenterDto fitnessCenterDto) {
        FitnessCenter fitnessCenter = fitnessCenterMapper.mapFrom(fitnessCenterDto);
        if(fitnessCenterRepository.existsByContactNo(fitnessCenter.getContactNo())){
            throw new DuplicateContactNoException("A Fitness Center already exists with contact no : "+ fitnessCenter.getContactNo());
        }
        if(fitnessCenterRepository.existsByContactNo(fitnessCenter.getEmailId())){
            throw new DuplicateEmailIdException("A Fitness Center already exists with email id : "+ fitnessCenter.getEmailId());
        }
        Address savedAddress = addressService.save(fitnessCenterDto.getAddressDto());
        fitnessCenter.setAddressId(savedAddress.getAddressId());
        fitnessCenter.setCreatedDate(new Date());
        fitnessCenter.setCenterId(idGeneratorService.generateIdForFitnessCenter());
        return fitnessCenterMapper.mapTo(fitnessCenterRepository.save(fitnessCenter));
    }

    @Override
    public Optional<FitnessCenterDto> getFitnessCenterForId(Long id) {
      FitnessCenter fitnessCenter = fitnessCenterRepository
              .findById(id)
              .orElseThrow(() ->
                      new ResourceNotFoundException("Fitness Center not found for id : " + id));
      return Optional.ofNullable(fitnessCenterMapper.mapTo(fitnessCenter));

    }


    @Override
    public List<FitnessCenterDto> findAll(boolean isDeactivated) {
        Session session = entityManager.unwrap(Session.class);
        Filter filter = session.enableFilter("deactivatedCenterFilter");
        filter.setParameter("isDeactivated", isDeactivated);
        List<FitnessCenter> fitnessCenterEntities =  fitnessCenterRepository.findAll();
        session.disableFilter("deactivatedCenterFilter");
        return fitnessCenterEntities.stream()
                .map(fitnessCenterMapper::mapTo)
                .toList();
    }

    @Override
    public boolean isExists(Long id) {
        return fitnessCenterRepository.existsById(id);
    }

    @Override
    public FitnessCenterDto update(Long id, FitnessCenterDto fitnessCenterDto) {
        FitnessCenter fitnessCenter = fitnessCenterMapper.mapFrom(fitnessCenterDto);
        fitnessCenter.setId(id);
        FitnessCenter updatedCenter = fitnessCenterRepository.findById(id).map(
                existingFitnessCenter -> {
                    Optional.ofNullable(fitnessCenter.getCenterName()).ifPresent(existingFitnessCenter::setCenterName);
                    return fitnessCenterRepository.save(existingFitnessCenter);
                }
        ).orElseThrow(() -> new ResourceNotFoundException("Fitness Center not found for id : "+ id));
        return fitnessCenterMapper.mapTo(updatedCenter);
    }

    @Override
    public void deactivateFitnessCenter(Long id){
        fitnessCenterRepository.deleteById(id);

        //If Center is linked with trainers or clients then those trainers and clients need to be updated as well.
    }

    @Override
    public FitnessCenterDto updateContactNo(Long id, String contactNo){
        if(fitnessCenterRepository.existsByContactNo(contactNo)){
            throw new DuplicateContactNoException("Fitness Center already exists for contact no : "+contactNo);
        }

        FitnessCenter fitnessCenter = fitnessCenterRepository.findById(id).map(
                existingFitnessCenter -> {
                    Optional.ofNullable(contactNo).ifPresent(existingFitnessCenter::setContactNo);
                    return fitnessCenterRepository.save(existingFitnessCenter);
                }
        ).orElseThrow(() -> new ResourceNotFoundException("Fitness Center not found for id : "+ id));
        return fitnessCenterMapper.mapTo(fitnessCenter);
    }

    @Override
    public FitnessCenterDto updateEmailId(Long id, String emailId){
        if(fitnessCenterRepository.existsByContactNo(emailId)){
            throw new DuplicateEmailIdException("Fitness Center already exists for Email Id : "+emailId);
        }

        FitnessCenter fitnessCenter = fitnessCenterRepository.findById(id).map(
                existingFitnessCenter -> {
                    Optional.ofNullable(emailId).ifPresent(existingFitnessCenter::setEmailId);
                    return fitnessCenterRepository.save(existingFitnessCenter);
                }
        ).orElseThrow(() -> new ResourceNotFoundException("Fitness Center not found for id : "+ id));
        return fitnessCenterMapper.mapTo(fitnessCenter);
    }

   
}
