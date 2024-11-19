package com.fitnessapp.VitalityVault.services.Impl;

import com.fitnessapp.VitalityVault.domain.dto.AddressDto;
import com.fitnessapp.VitalityVault.domain.entities.Address;
import com.fitnessapp.VitalityVault.mappers.Impl.AddressMapperImpl;
import com.fitnessapp.VitalityVault.mappers.Mapper;
import com.fitnessapp.VitalityVault.repositories.AddressRepository;
import com.fitnessapp.VitalityVault.repositories.FitnessCenterRepository;
import com.fitnessapp.VitalityVault.services.AddressService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class AddressServiceImpl implements AddressService {

    private static final Logger log = LoggerFactory.getLogger(AddressServiceImpl.class);
    private final AddressRepository addressRepository;

    private final Mapper<Address, AddressDto> addressMapper;

    @Autowired
    public AddressServiceImpl(AddressRepository addressRepository, Mapper<Address, AddressDto> addressMapper) {
        this.addressRepository = addressRepository;
        this.addressMapper = addressMapper;
    }

    @Override
    public Address save(AddressDto addressDto) {
        try{
            Address address = addressMapper.mapFrom(addressDto);
            return addressRepository.save(address);
        }catch(Exception e){
            log.error("ERROR : ", e);
            return null;
        }
    }
}
