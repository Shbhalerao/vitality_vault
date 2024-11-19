package com.fitnessapp.VitalityVault.services;

import com.fitnessapp.VitalityVault.domain.dto.AddressDto;
import com.fitnessapp.VitalityVault.domain.entities.Address;

public interface AddressService {
    Address save(AddressDto addressDto);
}
