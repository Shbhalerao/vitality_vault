package com.fitnessapp.VitalityVault.controllers;


import com.fitnessapp.VitalityVault.domain.dto.TrainerDto;
import com.fitnessapp.VitalityVault.domain.entities.Trainer;
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

    @Autowired
    public TrainerController(TrainerService trainerService, Mapper<Trainer, TrainerDto> trainerMapper) {
        this.trainerService = trainerService;
    }

    @PostMapping(path = "/trainers")
    public ResponseEntity<TrainerDto> createTrainer(@RequestBody TrainerDto trainerDto){
        TrainerDto savedTrainer = trainerService.createTrainer(trainerDto);
        return new ResponseEntity<>(savedTrainer, HttpStatus.CREATED);
    }

    @GetMapping(path = "/trainers/{id}")
    public ResponseEntity<TrainerDto> findOneById(@PathVariable("id") Long id){
        Optional<TrainerDto> trainer = trainerService.getTrainerForId(id);
        return trainer
                .map(trainerDto -> new ResponseEntity<>
                        (trainerDto,HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping(path = "/trainers/{filter}")
    public ResponseEntity<List<TrainerDto>> findAll(@PathVariable("filter") Boolean filter){
        List<TrainerDto> trainerDtoList = trainerService.findAll(filter);
        return new ResponseEntity<>(trainerDtoList, HttpStatus.OK);
    }

    @PatchMapping(path = "/trainers/{id}")
    public ResponseEntity<TrainerDto> update(@PathVariable("id") Long id,
                                                   @RequestBody TrainerDto trainerDto){
        if(!trainerService.isExists(id)){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        TrainerDto updated =
                trainerService.update(id,trainerDto);

        return new ResponseEntity<>(updated,
                HttpStatus.OK);

    }

    @DeleteMapping(path = "/trainers/{id}")
    public ResponseEntity deactivateFitnessCenter(@PathVariable("id") Long id){
        trainerService.deactivateTrainer(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }


    @PatchMapping(path = "/trainers/contact/{id}/{contact_no}")
    public ResponseEntity<TrainerDto> updateContact(@PathVariable("id") Long id,
                                                     @PathVariable("contact_no") String contactNo){
        if(!trainerService.isExists(id)){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        TrainerDto updatedTrainer =
                trainerService.updateContactNo(id,contactNo);
        return new ResponseEntity<>(updatedTrainer,
                HttpStatus.OK);
    }

    @PatchMapping(path = "/trainers/email/{id}/{email_id}")
    public ResponseEntity<TrainerDto> updateEmail(@PathVariable("id") Long id,
                                                    @PathVariable("email_id") String emailId){
        if(!trainerService.isExists(id)){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        TrainerDto updatedTrainer = trainerService.updateEmailId(id,emailId);
        return new ResponseEntity<>(updatedTrainer,
                HttpStatus.OK);
    }



}
