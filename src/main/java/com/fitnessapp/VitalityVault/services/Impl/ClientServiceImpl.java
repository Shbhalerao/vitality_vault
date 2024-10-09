package com.fitnessapp.VitalityVault.services.Impl;

import com.fitnessapp.VitalityVault.domain.entities.ClientEntity;
import com.fitnessapp.VitalityVault.exceptions.DuplicateContactNoException;
import com.fitnessapp.VitalityVault.exceptions.DuplicateEmailIdException;
import com.fitnessapp.VitalityVault.exceptions.ResourceNotFoundException;
import com.fitnessapp.VitalityVault.repositories.ClientRepository;
import com.fitnessapp.VitalityVault.services.ClientService;
import com.fitnessapp.VitalityVault.services.IdGeneratorService;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.Optional;

public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;

    private final IdGeneratorService idGeneratorService;

    private final EntityManager entityManager;

    @Autowired
    public ClientServiceImpl(ClientRepository clientRepository, IdGeneratorService idGeneratorService, EntityManager entityManager) {
        this.clientRepository = clientRepository;
        this.idGeneratorService = idGeneratorService;
        this.entityManager = entityManager;
    }

    @Override
    public ClientEntity createClient(ClientEntity clientEntity) {
        if(clientRepository.existsByContactNo(clientEntity.getContactNo())){
            throw new DuplicateContactNoException("Client already exists for contact no : "+clientEntity.getContactNo());
        }
        if(clientRepository.existsByEmailId(clientEntity.getEmailId())){
            throw new DuplicateEmailIdException("Client already exists for Email Id : "+clientEntity.getEmailId());
        }
        clientEntity.setCreatedDate(new Date());
        clientEntity.setClientId(idGeneratorService.generateIdForClient());
        return clientRepository.save(clientEntity);
    }

    @Override
    public Optional<ClientEntity> getClientForId(Long id) {
        return Optional.ofNullable(clientRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Client not found for id : " + id)));
    }


    @Override
    public ClientEntity updateContactNo(Long id, String contactNo){
        if(clientRepository.existsByContactNo(contactNo)){
            throw new DuplicateContactNoException("Client already exists for contact no : "+contactNo);
        }

        return clientRepository.findById(id).map(
                existingClient -> {
                    Optional.ofNullable(contactNo).ifPresent(existingClient::setContactNo);
                    return clientRepository.save(existingClient);
                }
        ).orElseThrow(() -> new ResourceNotFoundException("Client not found for id : "+ id));
    }

    @Override
    public ClientEntity updateEmailId(Long id, String emailId){
        if(clientRepository.existsByContactNo(emailId)){
            throw new DuplicateEmailIdException("Client already exists for Email Id : "+emailId);
        }

        return clientRepository.findById(id).map(
                existingClient -> {
                    Optional.ofNullable(emailId).ifPresent(existingClient::setEmailId);
                    return clientRepository.save(existingClient);
                }
        ).orElseThrow(() -> new ResourceNotFoundException("Client not found for id : "+ id));
    }
}
