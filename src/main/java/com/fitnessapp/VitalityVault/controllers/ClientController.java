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

    @Autowired
    public ClientController(ClientService clientService, Mapper<Client, ClientDto> clientMapper) {
        this.clientService = clientService;
    }

    @PostMapping(path = "/clients")
    public ResponseEntity<ClientDto> create(@RequestBody ClientDto clientDto){
        ClientDto savedClient = clientService.createClient(clientDto);
        return new ResponseEntity<>(savedClient, HttpStatus.CREATED);
    }

    @GetMapping(path = "/clients/{id}")
    public ResponseEntity<ClientDto> findOneById(@PathVariable("id") Long id){
        Optional<ClientDto> client = clientService.getClientForId(id);
        return client
                .map(clientDto -> new ResponseEntity<>
                        (clientDto,HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping(path = "/clients/{filter}")
    public ResponseEntity<List<ClientDto>> findAll(@PathVariable("filter")Boolean filter){
        List<ClientDto> clientDtoList = clientService.findAll(filter);
        return new ResponseEntity<>(clientDtoList, HttpStatus.OK);
    }

    @PatchMapping(path = "/clients/{id}")
    public ResponseEntity<ClientDto> update(@PathVariable("id") Long id,
                                             @RequestBody ClientDto ClientDto){
        if(clientService.isExists(id)){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        ClientDto updatedClient =
                clientService.update(id,ClientDto);
        return new ResponseEntity<>(updatedClient, HttpStatus.OK);

    }

    @DeleteMapping(path = "/clients/{id}")
    public ResponseEntity deactivate(@PathVariable("id") Long id){
        clientService.deactivateClient(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PatchMapping(path = "/clients/contact/{id}/{contact_no}")
    public ResponseEntity<ClientDto> updateContact(@PathVariable("id") Long id,
                                                    @PathVariable("contact_no") String contactNo){
        if(clientService.isExists(id)){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        ClientDto clientDto =
                clientService.updateContactNo(id,contactNo);
        return new ResponseEntity<>(clientDto,
                HttpStatus.OK);
    }

    @PatchMapping(path = "/clients/email/{id}/{email_id}")
    public ResponseEntity<ClientDto> updateEmail(@PathVariable("id") Long id,
                                                  @PathVariable("email_id") String emailId){
        if(clientService.isExists(id)){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        ClientDto clientDto =
                clientService.updateEmailId(id,emailId);
        return new ResponseEntity<>(clientDto,
                HttpStatus.OK);
    }

}
