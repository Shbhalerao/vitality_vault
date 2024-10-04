package com.fitnessapp.VitalityVault.mappers.Impl;

import com.fitnessapp.VitalityVault.domain.dto.TrainerDto;
import com.fitnessapp.VitalityVault.domain.entities.TrainerEntity;
import com.fitnessapp.VitalityVault.mappers.Mapper;
import org.modelmapper.ModelMapper;

public class TrainerMapperImpl implements Mapper<TrainerEntity, TrainerDto> {

    private ModelMapper modelMapper;

    public TrainerMapperImpl(ModelMapper modelMapper){
        this.modelMapper = modelMapper;
    }

    @Override
    public TrainerDto mapTo(TrainerEntity trainerEntity) {
        return null;
    }

    @Override
    public TrainerEntity mapFrom(TrainerDto trainerDto) {
        return null;
    }
}
