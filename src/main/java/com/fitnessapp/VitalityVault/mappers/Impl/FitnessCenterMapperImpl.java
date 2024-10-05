package com.fitnessapp.VitalityVault.mappers.Impl;

import com.fitnessapp.VitalityVault.domain.dto.FitnessCenterDto;
import com.fitnessapp.VitalityVault.domain.entities.FitnessCenterEntity;
import com.fitnessapp.VitalityVault.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FitnessCenterMapperImpl implements Mapper<FitnessCenterEntity, FitnessCenterDto> {

    private final ModelMapper modelMapper;

    @Autowired
    public FitnessCenterMapperImpl(ModelMapper modelMapper){
        this.modelMapper = modelMapper;
    }

    @Override
    public FitnessCenterDto mapTo(FitnessCenterEntity fitnessCenterEntity) {
        return modelMapper.map(fitnessCenterEntity, FitnessCenterDto.class);
    }

    @Override
    public FitnessCenterEntity mapFrom(FitnessCenterDto fitnessCenterDto) {
        return modelMapper.map(fitnessCenterDto, FitnessCenterEntity.class);
    }
}
