package com.fitnessapp.VitalityVault.mappers.Impl;

import com.fitnessapp.VitalityVault.domain.dto.FitnessCenterDto;
import com.fitnessapp.VitalityVault.domain.entities.FitnessCenter;
import com.fitnessapp.VitalityVault.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FitnessCenterMapperImpl implements Mapper<FitnessCenter, FitnessCenterDto> {

    private final ModelMapper modelMapper;

    @Autowired
    public FitnessCenterMapperImpl(ModelMapper modelMapper){
        this.modelMapper = modelMapper;
    }

    @Override
    public FitnessCenterDto mapTo(FitnessCenter fitnessCenter) {
        return modelMapper.map(fitnessCenter, FitnessCenterDto.class);
    }

    @Override
    public FitnessCenter mapFrom(FitnessCenterDto fitnessCenterDto) {
        return modelMapper.map(fitnessCenterDto, FitnessCenter.class);
    }
}
