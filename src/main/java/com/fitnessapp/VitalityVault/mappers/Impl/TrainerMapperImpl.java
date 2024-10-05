package com.fitnessapp.VitalityVault.mappers.Impl;

import com.fitnessapp.VitalityVault.domain.dto.TrainerDto;
import com.fitnessapp.VitalityVault.domain.entities.TrainerEntity;
import com.fitnessapp.VitalityVault.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

public class TrainerMapperImpl implements Mapper<TrainerEntity, TrainerDto> {

    private final ModelMapper modelMapper;

    @Autowired
    public TrainerMapperImpl(ModelMapper modelMapper){
        this.modelMapper = modelMapper;
    }

    @Override
    public TrainerDto mapTo(TrainerEntity trainerEntity) {
        return modelMapper.map(trainerEntity, TrainerDto.class);
    }

    @Override
    public TrainerEntity mapFrom(TrainerDto trainerDto) {
        return modelMapper.map(trainerDto, TrainerEntity.class);
    }
}
