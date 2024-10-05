package com.fitnessapp.VitalityVault.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IdGeneratorRepository extends JpaRepository<Object, Long> {

    @Query("SELECT MAX(id) FROM fitness_center_details")
    Long findMaxIdForFitnessCenter();

    @Query("SELECT MAX(id) FROM trainer_details")
    Long findMaxIdForTrainer();

}
