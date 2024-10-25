package com.fitnessapp.VitalityVault.controllers;

import com.fitnessapp.VitalityVault.domain.dto.ClientDto;
import com.fitnessapp.VitalityVault.domain.entities.Client;
import com.fitnessapp.VitalityVault.mappers.Mapper;
import com.fitnessapp.VitalityVault.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class ClientController {

    private final ClientService clientService;

    private final Mapper<Client, ClientDto> clientMapper;

    @Autowired
    public ClientController(ClientService clientService, Mapper<Client, ClientDto> clientMapper) {
        this.clientService = clientService;
        this.clientMapper = clientMapper;
    }

    @PostMapping(path = "/clients")
    public ResponseEntity<ClientDto> create(@RequestBody ClientDto ClientDto){
        Client client = clientMapper.mapFrom(ClientDto);
        Client savedClient = clientService.createClient(client);
        return new ResponseEntity<>(clientMapper.mapTo(savedClient), HttpStatus.CREATED);
    }

    @GetMapping(path = "/clients/{id}")
    public ResponseEntity<ClientDto> findOneById(@PathVariable("id") Long id){
        Optional<Client> trainer = clientService.getClientForId(id);
        return trainer
                .map(ClientEntity -> new ResponseEntity<>
                        (clientMapper.mapTo(ClientEntity),HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping(path = "/clients")
    public ResponseEntity<List<ClientDto>> findAll(){
        List<Client> clientEntities = clientService.findAll(false);
        List<ClientDto> clientDtoList = clientEntities.stream()
                .map(clientMapper::mapTo)
                .toList();
        return new ResponseEntity<>(clientDtoList, HttpStatus.OK);
    }

    @PatchMapping(path = "/clients/{id}")
    public ResponseEntity<ClientDto> update(@PathVariable("id") Long id,
                                             @RequestBody ClientDto ClientDto){
        if(clientService.isExists(id)){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Client updatedEntity =
                clientService.update(id,clientMapper.mapFrom(ClientDto));

        return new ResponseEntity<>(clientMapper.mapTo(updatedEntity),
                HttpStatus.OK);

    }

    @DeleteMapping(path = "/clients/{id}")
    public ResponseEntity deactivate(@PathVariable("id") Long id){
        clientService.deactivateClient(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @GetMapping(path = "/clients/{status}")
    public ResponseEntity<List<ClientDto>> findAllDeactivatedClients(@PathVariable("status") boolean status){
        List<Client> trainerEntities = clientService.findAll(status);
        List<ClientDto> ClientDtoList = trainerEntities.stream()
                .map(clientMapper::mapTo)
                .toList();
        return new ResponseEntity<>(ClientDtoList, HttpStatus.OK);
    }

    @PatchMapping(path = "/clients/contact/{id}/{contact_no}")
    public ResponseEntity<ClientDto> updateContact(@PathVariable("id") Long id,
                                                    @PathVariable("contact_no") String contactNo){
        if(clientService.isExists(id)){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Client updatedEntity =
                clientService.updateContactNo(id,contactNo);
        return new ResponseEntity<>(clientMapper.mapTo(updatedEntity),
                HttpStatus.OK);
    }

    @PatchMapping(path = "/clients/email/{id}/{email_id}")
    public ResponseEntity<ClientDto> updateEmail(@PathVariable("id") Long id,
                                                  @PathVariable("email_id") String emailId){
        if(clientService.isExists(id)){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Client updatedEntity =
                clientService.updateEmailId(id,emailId);
        return new ResponseEntity<>(clientMapper.mapTo(updatedEntity),
                HttpStatus.OK);
    }

}
