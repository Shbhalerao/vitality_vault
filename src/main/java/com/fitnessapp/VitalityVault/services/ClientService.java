package com.fitnessapp.VitalityVault.services;

import com.fitnessapp.VitalityVault.domain.entities.ClientEntity;

import java.util.List;
import java.util.Optional;

public interface ClientService {

    ClientEntity createClient(ClientEntity clientEntity);

    Optional<ClientEntity> getClientForId(Long id);

    List<ClientEntity> findAll(boolean isDeactivated);

    ClientEntity updateContactNo(Long id, String contactNo);

    ClientEntity updateEmailId(Long id, String emailId);

    ClientEntity update(Long id, ClientEntity clientEntity);

    void deactivateClient(Long id);

    boolean isExists(Long id);
}
