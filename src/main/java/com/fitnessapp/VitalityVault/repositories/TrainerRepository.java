package com.fitnessapp.VitalityVault.repositories;

import com.fitnessapp.VitalityVault.domain.entities.TrainerEntity;
import com.fitnessapp.VitalityVault.services.TrainerService;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrainerRepository extends JpaRepository<TrainerEntity, Long> {
}
