package com.fitnessapp.VitalityVault.repositories;

import com.fitnessapp.VitalityVault.domain.entities.FitnessCenterEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FitnessCenterRepository extends JpaRepository<FitnessCenterEntity, Long> {

    @Override
    @Query("SELECT f FROM FitnessCenterEntity f WHERE f.deactivated = false")
    List<FitnessCenterEntity> findAll();

    List<FitnessCenterEntity> findAllByDeactivated(Boolean deactivated);
}
