package com.fitnessapp.VitalityVault.mappers.Impl;

import com.fitnessapp.VitalityVault.domain.dto.TrainerDto;
import com.fitnessapp.VitalityVault.domain.entities.Trainer;
import com.fitnessapp.VitalityVault.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

public class TrainerMapperImpl implements Mapper<Trainer, TrainerDto> {

    private final ModelMapper modelMapper;

    @Autowired
    public TrainerMapperImpl(ModelMapper modelMapper){
        this.modelMapper = modelMapper;
    }

    @Override
    public TrainerDto mapTo(Trainer trainer) {
        return modelMapper.map(trainer, TrainerDto.class);
    }

    @Override
    public Trainer mapFrom(TrainerDto trainerDto) {
        return modelMapper.map(trainerDto, Trainer.class);
    }
}
