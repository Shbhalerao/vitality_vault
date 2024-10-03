package com.fitnessapp.VitalityVault.services.Impl;

import com.fitnessapp.VitalityVault.domain.entities.FitnessCenterEntity;
import com.fitnessapp.VitalityVault.repositories.FitnessCenterRepository;
import com.fitnessapp.VitalityVault.services.FitnessCenterService;
import jakarta.persistence.EntityManager;
import org.hibernate.Filter;
import org.hibernate.Session;
import org.springframework.stereotype.Service;


import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class FitnessCenterServiceImpl implements FitnessCenterService {

    private FitnessCenterRepository fitnessCenterRepository;

    private EntityManager entityManager;

    public FitnessCenterServiceImpl(FitnessCenterRepository fitnessCenterRepository, EntityManager entityManager){
        this.fitnessCenterRepository = fitnessCenterRepository;
        this.entityManager = entityManager;
    }

    @Override
    public FitnessCenterEntity createFitnessCenter(FitnessCenterEntity fitnessCenterEntity) {
        fitnessCenterEntity.setCreatedDate(new Date());
        return fitnessCenterRepository.save(fitnessCenterEntity);
    }

    @Override
    public Optional<FitnessCenterEntity> getFitnessCenterForId(Long id) {
      return  fitnessCenterRepository.findById(id);

    }


    @Override
    public List<FitnessCenterEntity> findAll(boolean isDeactivated) {
        Session session = entityManager.unwrap(Session.class);
        Filter filter = session.enableFilter("deactivatedCenterFilter");
        filter.setParameter("isDeactivated", isDeactivated);
        List<FitnessCenterEntity> fitnessCenterEntities =  fitnessCenterRepository.findAll();
        session.disableFilter("deactivatedCenterFilter");
        return fitnessCenterEntities;
    }

    @Override
    public boolean isExists(Long id) {
        return fitnessCenterRepository.existsById(id);
    }

    @Override
    public FitnessCenterEntity update(Long id, FitnessCenterEntity fitnessCenterEntity) {
        fitnessCenterEntity.setId(id);

        return fitnessCenterRepository.findById(id).map(
                existingFitnessCenter -> {
                    Optional.ofNullable(fitnessCenterEntity.getCenterName()).ifPresent(existingFitnessCenter::setCenterName);
                    Optional.ofNullable(fitnessCenterEntity.getCity()).ifPresent(existingFitnessCenter::setCity);
                    Optional.ofNullable(fitnessCenterEntity.getAddressLine1()).ifPresent(existingFitnessCenter::setAddressLine1);
                    Optional.ofNullable(fitnessCenterEntity.getAddressLine2()).ifPresent(existingFitnessCenter::setAddressLine2);
                    Optional.ofNullable(fitnessCenterEntity.getContactNo()).ifPresent(existingFitnessCenter::setContactNo);
                    Optional.ofNullable(fitnessCenterEntity.getEmailId()).ifPresent(existingFitnessCenter::setEmailId);
                    Optional.ofNullable(fitnessCenterEntity.getLocality()).ifPresent(existingFitnessCenter::setLocality);
                    Optional.ofNullable(fitnessCenterEntity.getPinCode()).ifPresent(existingFitnessCenter::setPinCode);
                    Optional.ofNullable(fitnessCenterEntity.getState()).ifPresent(existingFitnessCenter::setState);
                    return fitnessCenterRepository.save(existingFitnessCenter);
                }
        ).orElseThrow(() -> new RuntimeException("Author does not exist"));
    }

    @Override
    public void deactivateFitnessCenter(Long id){
        fitnessCenterRepository.deleteById(id);
    }
}
