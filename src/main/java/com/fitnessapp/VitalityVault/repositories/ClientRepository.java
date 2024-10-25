package com.fitnessapp.VitalityVault.repositories;

import com.fitnessapp.VitalityVault.domain.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {

    boolean existsByContactNo(String contactNo);

    boolean existsByEmailId(String emailId);
}
