package com.fitnessapp.VitalityVault.services;

import com.fitnessapp.VitalityVault.domain.entities.Client;

import java.util.List;
import java.util.Optional;

public interface ClientService {

    Client createClient(Client client);

    Optional<Client> getClientForId(Long id);

    List<Client> findAll(boolean isDeactivated);

    Client updateContactNo(Long id, String contactNo);

    Client updateEmailId(Long id, String emailId);

    Client update(Long id, Client client);

    void deactivateClient(Long id);

    boolean isExists(Long id);


}
