package com.fitnessapp.VitalityVault.services.Impl;

import com.fitnessapp.VitalityVault.domain.entities.Client;
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
    public Client createClient(Client client) {
        if(clientRepository.existsByContactNo(client.getContactNo())){
            throw new DuplicateContactNoException("Client already exists for contact no : "+ client.getContactNo());
        }
        if(clientRepository.existsByEmailId(client.getEmailId())){
            throw new DuplicateEmailIdException("Client already exists for Email Id : "+ client.getEmailId());
        }
        client.setCreatedDate(new Date());
        client.setClientId(idGeneratorService.generateIdForClient());
        return clientRepository.save(client);
    }

    @Override
    public Optional<Client> getClientForId(Long id) {
        return Optional.ofNullable(clientRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Client not found for id : " + id)));
    }


    @Override
    public List<Client> findAll(boolean isDeactivated) {
        Session session = entityManager.unwrap(Session.class);
        Filter filter = session.enableFilter("deactivatedClientFilter");
        filter.setParameter("isDeactivated", isDeactivated);
        List<Client> clientEntities =  clientRepository.findAll();
        session.disableFilter("deactivatedCenterFilter");
        return clientEntities;
    }



    @Override
    public Client updateContactNo(Long id, String contactNo){
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
    public Client updateEmailId(Long id, String emailId){
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
    public Client update(Long id, Client client) {
        client.setId(id);

        return clientRepository.findById(id).map(
                existingClient -> {
                    Optional.ofNullable(client.getFirstName()).ifPresent(existingClient::setFirstName);
                    Optional.ofNullable(client.getLastName()).ifPresent(existingClient::setLastName);
                    Optional.ofNullable(client.getAddress()).ifPresent(existingClient::setAddress);
                    Optional.ofNullable(client.getCity()).ifPresent(existingClient::setCity);
                    Optional.ofNullable(client.getFitnessGoal()).ifPresent(existingClient::setFitnessGoal);
                    Optional.ofNullable(client.getWeight()).ifPresent(existingClient::setWeight);
                    Optional.ofNullable(client.getHeight()).ifPresent(existingClient::setHeight);
                    Optional.ofNullable(client.getPinCode()).ifPresent(existingClient::setPinCode);
                    Optional.ofNullable(client.getState()).ifPresent(existingClient::setState);
                    Optional.ofNullable(client.getDateOfBirth()).ifPresent(existingClient::setDateOfBirth);
                    Optional.ofNullable(client.getTrainer()).ifPresent(existingClient::setTrainer);
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
