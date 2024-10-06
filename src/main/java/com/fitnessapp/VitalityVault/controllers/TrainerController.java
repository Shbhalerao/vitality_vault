package com.fitnessapp.VitalityVault.controllers;


import com.fitnessapp.VitalityVault.domain.dto.TrainerDto;
import com.fitnessapp.VitalityVault.domain.entities.TrainerEntity;
import com.fitnessapp.VitalityVault.mappers.Mapper;
import com.fitnessapp.VitalityVault.services.TrainerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class TrainerController {

    private final TrainerService trainerService;

    private final Mapper<TrainerEntity, TrainerDto> trainerMapper;

    @Autowired
    public TrainerController(TrainerService trainerService, Mapper<TrainerEntity, TrainerDto> trainerMapper) {
        this.trainerService = trainerService;
        this.trainerMapper = trainerMapper;
    }

    @PostMapping(path = "/trainers")
    public ResponseEntity<TrainerDto> createTrainer(@RequestBody TrainerDto trainerDto){
        TrainerEntity trainerEntity = trainerMapper.mapFrom(trainerDto);
        TrainerEntity savedTrainerEntity = trainerService.createTrainer(trainerEntity);
        return new ResponseEntity<>(trainerMapper.mapTo(savedTrainerEntity), HttpStatus.CREATED);
    }

    @GetMapping(path = "/trainers/{id}")
    public ResponseEntity<TrainerDto> findOneById(@PathVariable("id") Long id){
        Optional<TrainerEntity> trainer = trainerService.getTrainerForId(id);
        return trainer
                .map(trainerEntity -> new ResponseEntity<>
                        (trainerMapper.mapTo(trainerEntity),HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping(path = "/trainers")
    public ResponseEntity<List<TrainerDto>> findAll(){
        List<TrainerEntity> trainerEntities = trainerService.findAll(false);
        List<TrainerDto> trainerList = trainerEntities.stream()
                .map(trainerMapper::mapTo)
                .toList();
        return new ResponseEntity<>(trainerList, HttpStatus.OK);
    }

    @PatchMapping(path = "/trainers/{id}")
    public ResponseEntity<TrainerDto> update(@PathVariable("id") Long id,
                                                   @RequestBody TrainerDto trainerDto){
        if(!trainerService.isExists(id)){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        TrainerEntity updatedEntity =
                trainerService.update(id,trainerMapper.mapFrom(trainerDto));

        return new ResponseEntity<>(trainerMapper.mapTo(updatedEntity),
                HttpStatus.OK);

    }

    @DeleteMapping(path = "/trainers/{id}")
    public ResponseEntity deactivateFitnessCenter(@PathVariable("id") Long id){
        trainerService.deactivateTrainer(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @GetMapping(path = "/trainers/deactivated-trainers")
    public ResponseEntity<List<TrainerDto>> findAllDeactivatedCenters(){
        List<TrainerEntity> trainerEntities = trainerService.findAll(true);
        List<TrainerDto> trainerDtoList = trainerEntities.stream()
                .map(trainerMapper::mapTo)
                .toList();
        return new ResponseEntity<>(trainerDtoList, HttpStatus.OK);
    }

    @PatchMapping(path = "/trainers/update-contact/{id}/{contact_no}")
    public ResponseEntity<TrainerDto> updateContact(@PathVariable("id") Long id,
                                                     @PathVariable("contact_no") String contactNo){
        if(!trainerService.isExists(id)){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        TrainerEntity updatedEntity =
                trainerService.updateContactNo(id,contactNo);
        return new ResponseEntity<>(trainerMapper.mapTo(updatedEntity),
                HttpStatus.OK);
    }

    @PatchMapping(path = "/trainers/update-email/{id}/{email_id}")
    public ResponseEntity<TrainerDto> updateEmail(@PathVariable("id") Long id,
                                                    @PathVariable("email_id") String emailId){
        if(!trainerService.isExists(id)){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        TrainerEntity updatedEntity =
                trainerService.updateEmailId(id,emailId);
        return new ResponseEntity<>(trainerMapper.mapTo(updatedEntity),
                HttpStatus.OK);
    }



}
