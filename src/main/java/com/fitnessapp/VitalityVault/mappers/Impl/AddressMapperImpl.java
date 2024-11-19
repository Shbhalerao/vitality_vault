package com.fitnessapp.VitalityVault.mappers.Impl;

import com.fitnessapp.VitalityVault.domain.dto.AddressDto;
import com.fitnessapp.VitalityVault.domain.dto.ClientDto;
import com.fitnessapp.VitalityVault.domain.entities.Address;
import com.fitnessapp.VitalityVault.domain.entities.Client;
import com.fitnessapp.VitalityVault.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

public class AddressMapperImpl implements Mapper<Address, AddressDto> {
    private final ModelMapper modelMapper;



    @Autowired
    public AddressMapperImpl(ModelMapper modelMapper, Mapper<Address, AddressDto> addressDtoMapper) {
        this.modelMapper = modelMapper;
    }


    @Override
    public AddressDto mapTo(Address address) {
        return modelMapper.map(address, AddressDto.class);
    }

    @Override
    public Address mapFrom(AddressDto addressDto) {
        return modelMapper.map(addressDto, Address.class);
    }
}
