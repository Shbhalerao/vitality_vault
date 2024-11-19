package com.fitnessapp.VitalityVault.services;

import com.fitnessapp.VitalityVault.domain.dto.ClientDto;
import com.fitnessapp.VitalityVault.domain.entities.Client;

import java.util.List;
import java.util.Optional;

public interface ClientService {

    ClientDto createClient(ClientDto client);

    Optional<ClientDto> getClientForId(Long id);

    List<ClientDto> findAll(boolean isDeactivated);

    ClientDto updateContactNo(Long id, String contactNo);

    ClientDto updateEmailId(Long id, String emailId);

    ClientDto update(Long id, ClientDto clientDto);

    void deactivateClient(Long id);

    boolean isExists(Long id);


}
