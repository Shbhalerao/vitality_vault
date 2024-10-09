package com.fitnessapp.VitalityVault.mappers.Impl;

import com.fitnessapp.VitalityVault.domain.dto.ClientDto;
import com.fitnessapp.VitalityVault.domain.entities.ClientEntity;
import com.fitnessapp.VitalityVault.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

public class ClientMapperImpl implements Mapper<ClientEntity, ClientDto> {

    private final ModelMapper modelMapper;

    @Autowired
    public ClientMapperImpl(ModelMapper modelMapper){
        this.modelMapper = modelMapper;
    }


    @Override
    public ClientDto mapTo(ClientEntity clientEntity) {
        return modelMapper.map(clientEntity, ClientDto.class);
    }

    @Override
    public ClientEntity mapFrom(ClientDto clientDto) {
        return modelMapper.map(clientDto, ClientEntity.class);
    }
}
