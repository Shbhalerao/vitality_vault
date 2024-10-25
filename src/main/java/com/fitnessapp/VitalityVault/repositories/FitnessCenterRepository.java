package com.fitnessapp.VitalityVault.repositories;

import com.fitnessapp.VitalityVault.domain.entities.FitnessCenter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FitnessCenterRepository extends JpaRepository<FitnessCenter, Long> {

    @Override
    @Query("SELECT f FROM FitnessCenterEntity f WHERE f.deactivated = false")
    List<FitnessCenter> findAll();

    List<FitnessCenter> findAllByDeactivated(Boolean deactivated);

   boolean existsByContactNo(String contactNo);

    boolean existsByEmailId(String emailId);
}
