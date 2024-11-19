package com.fitnessapp.VitalityVault.services.Impl;

import com.fitnessapp.VitalityVault.domain.dto.ClientDto;
import com.fitnessapp.VitalityVault.domain.entities.Address;
import com.fitnessapp.VitalityVault.domain.entities.Client;
import com.fitnessapp.VitalityVault.exceptions.DuplicateContactNoException;
import com.fitnessapp.VitalityVault.exceptions.DuplicateEmailIdException;
import com.fitnessapp.VitalityVault.exceptions.ResourceNotFoundException;
import com.fitnessapp.VitalityVault.mappers.Mapper;
import com.fitnessapp.VitalityVault.repositories.ClientRepository;
import com.fitnessapp.VitalityVault.services.AddressService;
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

    private final AddressService addressService;

    private final EntityManager entityManager;

    private final Mapper<Client, ClientDto> clientMapper;

    @Autowired
    public ClientServiceImpl(ClientRepository clientRepository, IdGeneratorService idGeneratorService, AddressService addressService, EntityManager entityManager, Mapper<Client, ClientDto> clientMapper) {
        this.clientRepository = clientRepository;
        this.idGeneratorService = idGeneratorService;
        this.addressService = addressService;
        this.entityManager = entityManager;
        this.clientMapper = clientMapper;
    }

    @Override
    public ClientDto createClient(ClientDto clientDto) {
        Client client = clientMapper.mapFrom(clientDto);
        if(clientRepository.existsByContactNo(client.getContactNo())){
            throw new DuplicateContactNoException("Client already exists for contact no : "+ client.getContactNo());
        }
        if(clientRepository.existsByEmailId(client.getEmailId())){
            throw new DuplicateEmailIdException("Client already exists for Email Id : "+ client.getEmailId());
        }
        Address savedAddress = addressService.save(clientDto.getAddressDto());
        client.setAddressId(savedAddress.getAddressId());
        client.setCreatedDate(new Date());
        client.setClientId(idGeneratorService.generateIdForClient());
        Client savedClient =  clientRepository.save(client);
        return clientMapper.mapTo(savedClient);
    }

    @Override
    public Optional<ClientDto> getClientForId(Long id) {
        Client client =  clientRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException
                                ("Client not found for id : " + id));
        return Optional.ofNullable(clientMapper.mapTo(client));
    }


    @Override
    public List<ClientDto> findAll(boolean isDeactivated) {
        Session session = entityManager.unwrap(Session.class);
        Filter filter = session.enableFilter("deactivatedClientFilter");
        filter.setParameter("isDeactivated", isDeactivated);
        List<Client> clientEntities =  clientRepository.findAll();
        session.disableFilter("deactivatedCenterFilter");
        List<ClientDto> clientDtoList = clientEntities.stream()
                .map(clientMapper::mapTo)
                .toList();
        return clientDtoList;
    }



    @Override
    public ClientDto updateContactNo(Long id, String contactNo){
        if(clientRepository.existsByContactNo(contactNo)){
            throw new DuplicateContactNoException("Client already exists for contact no : "+contactNo);
        }

        Client client = clientRepository.findById(id).map(
                existingClient -> {
                    Optional.ofNullable(contactNo).ifPresent(existingClient::setContactNo);
                    return clientRepository.save(existingClient);
                }
        ).orElseThrow(() -> new ResourceNotFoundException("Client not found for id : "+ id));
        return clientMapper.mapTo(client);
    }

    @Override
    public ClientDto updateEmailId(Long id, String emailId){
        if(clientRepository.existsByContactNo(emailId)){
            throw new DuplicateEmailIdException("Client already exists for Email Id : "+emailId);
        }

        Client client =  clientRepository.findById(id).map(
                existingClient -> {
                    Optional.ofNullable(emailId).ifPresent(existingClient::setEmailId);
                    return clientRepository.save(existingClient);
                }
        ).orElseThrow(() -> new ResourceNotFoundException("Client not found for id : "+ id));
        return clientMapper.mapTo(client);
    }


    @Override
    public ClientDto update(Long id, ClientDto clientDto) {
        Client client = clientMapper.mapFrom(clientDto);
        client.setId(id);
        Client savedClient = clientRepository.findById(id).map(
                existingClient -> {
                    Optional.ofNullable(client.getFirstName()).ifPresent(existingClient::setFirstName);
                    Optional.ofNullable(client.getLastName()).ifPresent(existingClient::setLastName);
                    Optional.ofNullable(client.getFitnessGoal()).ifPresent(existingClient::setFitnessGoal);
                    Optional.ofNullable(client.getWeight()).ifPresent(existingClient::setWeight);
                    Optional.ofNullable(client.getHeight()).ifPresent(existingClient::setHeight);
                    Optional.ofNullable(client.getDateOfBirth()).ifPresent(existingClient::setDateOfBirth);
                    Optional.ofNullable(client.getTrainer()).ifPresent(existingClient::setTrainer);
                    return clientRepository.save(existingClient);
                }
        ).orElseThrow(() -> new ResourceNotFoundException("Client not found for id : "+ id));
        return clientMapper.mapTo(savedClient);
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
