package com.fitnessapp.VitalityVault.mappers.Impl;

import com.fitnessapp.VitalityVault.domain.dto.ClientDto;
import com.fitnessapp.VitalityVault.domain.entities.Client;
import com.fitnessapp.VitalityVault.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

public class ClientMapperImpl implements Mapper<Client, ClientDto> {

    private final ModelMapper modelMapper;

    @Autowired
    public ClientMapperImpl(ModelMapper modelMapper){
        this.modelMapper = modelMapper;
    }


    @Override
    public ClientDto mapTo(Client client) {
        return modelMapper.map(client, ClientDto.class);
    }

    @Override
    public Client mapFrom(ClientDto clientDto) {
        return modelMapper.map(clientDto, Client.class);
    }
}
