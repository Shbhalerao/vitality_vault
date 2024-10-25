package com.fitnessapp.VitalityVault.repositories;

import com.fitnessapp.VitalityVault.domain.entities.Trainer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrainerRepository extends JpaRepository<Trainer, Long> {

    boolean existsByContactNo(String contactNo);

    boolean existsByEmailId(String emailId);
}
