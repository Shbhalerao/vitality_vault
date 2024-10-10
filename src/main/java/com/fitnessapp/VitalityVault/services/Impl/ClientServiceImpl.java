package com.fitnessapp.VitalityVault.services.Impl;

import com.fitnessapp.VitalityVault.domain.entities.ClientEntity;
import com.fitnessapp.VitalityVault.exceptions.DuplicateContactNoException;
import com.fitnessapp.VitalityVault.exceptions.DuplicateEmailIdException;
import com.fitnessapp.VitalityVault.exceptions.ResourceNotFoundException;
import com.fitnessapp.VitalityVault.repositories.ClientRepository;
import com.fitnessapp.VitalityVault.services.ClientService;
import com.fitnessapp.VitalityVault.services.IdGeneratorService;
import jakarta.persistence.EntityManager;

import org.hibernate.Filter;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
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
    public List<ClientEntity> findAll(boolean isDeactivated) {
        Session session = entityManager.unwrap(Session.class);
        Filter filter = session.enableFilter("deactivatedClientFilter");
        filter.setParameter("isDeactivated", isDeactivated);
        List<ClientEntity> clientEntities =  clientRepository.findAll();
        session.disableFilter("deactivatedCenterFilter");
        return clientEntities;
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


    @Override
    public ClientEntity update(Long id, ClientEntity clientEntity) {
        clientEntity.setId(id);

        return clientRepository.findById(id).map(
                existingClient -> {
                    Optional.ofNullable(clientEntity.getFirstName()).ifPresent(existingClient::setFirstName);
                    Optional.ofNullable(clientEntity.getLastName()).ifPresent(existingClient::setLastName);
                    Optional.ofNullable(clientEntity.getAddress()).ifPresent(existingClient::setAddress);
                    Optional.ofNullable(clientEntity.getCity()).ifPresent(existingClient::setCity);
                    Optional.ofNullable(clientEntity.getFitnessGoal()).ifPresent(existingClient::setFitnessGoal);
                    Optional.ofNullable(clientEntity.getWeight()).ifPresent(existingClient::setWeight);
                    Optional.ofNullable(clientEntity.getHeight()).ifPresent(existingClient::setHeight);
                    Optional.ofNullable(clientEntity.getPinCode()).ifPresent(existingClient::setPinCode);
                    Optional.ofNullable(clientEntity.getState()).ifPresent(existingClient::setState);
                    Optional.ofNullable(clientEntity.getDateOfBirth()).ifPresent(existingClient::setDateOfBirth);
                    Optional.ofNullable(clientEntity.getTrainerEntity()).ifPresent(existingClient::setTrainerEntity);
                    return clientRepository.save(existingClient);
                }
        ).orElseThrow(() -> new ResourceNotFoundException("Client not found for id : "+ id));
    }

    @Override
    public void deactivateClient(Long id) {
        clientRepository.deleteById(id);
    }

    @Override
    public boolean isExists(Long id) {
        return clientRepository.existsById(id);
    }


}
