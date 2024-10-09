package com.fitnessapp.VitalityVault.services;

import com.fitnessapp.VitalityVault.domain.entities.ClientEntity;

import java.util.Optional;

public interface ClientService {

    ClientEntity createClient(ClientEntity clientEntity);

    Optional<ClientEntity> getClientForId(Long id);

    ClientEntity updateContactNo(Long id, String contactNo);

    ClientEntity updateEmailId(Long id, String emailId);
}
