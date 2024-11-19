package com.fitnessapp.VitalityVault.controllers;

import com.fitnessapp.VitalityVault.domain.dto.FitnessCenterDto;
import com.fitnessapp.VitalityVault.domain.entities.FitnessCenter;
import com.fitnessapp.VitalityVault.mappers.Mapper;
import com.fitnessapp.VitalityVault.services.FitnessCenterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
public class FitnessCenterController {

    private final FitnessCenterService fitnessCenterService;

    @Autowired
    public FitnessCenterController(FitnessCenterService fitnessCenterService
            ,Mapper<FitnessCenter, FitnessCenterDto> fitnessCenterMapper){
        this.fitnessCenterService = fitnessCenterService;
    }

    @PostMapping(path = "/fitness-centers")
    public ResponseEntity<FitnessCenterDto> createFitnessCenter(@RequestBody FitnessCenterDto fitnessCenterDto){
        FitnessCenterDto savedFitnessCenter = fitnessCenterService.createFitnessCenter(fitnessCenterDto);
        return new ResponseEntity<>(savedFitnessCenter, HttpStatus.CREATED);
    }

    @GetMapping(path = "/fitness-centers/{id}")
    public ResponseEntity<FitnessCenterDto> findOneById(@PathVariable("id") Long id){
        Optional<FitnessCenterDto> fitnessCenter = fitnessCenterService.getFitnessCenterForId(id);
        return fitnessCenter
                .map(fitnessCenterDto -> new ResponseEntity<>
                        (fitnessCenterDto,HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping(path = "/fitness-centers/{filter}")
    public ResponseEntity<List<FitnessCenterDto>> findAll(@PathVariable("filter") Boolean filter){
        List<FitnessCenterDto> fitnessCenterList = fitnessCenterService.findAll(filter);
        return new ResponseEntity<>(fitnessCenterList, HttpStatus.OK);
    }

    @PatchMapping(path = "/fitness-centers/{id}")
    public ResponseEntity<FitnessCenterDto> update(@PathVariable("id") Long id,
                                                   @RequestBody FitnessCenterDto fitnessCenterDto){
            if(!fitnessCenterService.isExists(id)){
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            FitnessCenterDto updatedCenter =
                    fitnessCenterService.update(id,fitnessCenterDto);
            return new ResponseEntity<>(updatedCenter,
                    HttpStatus.OK);

    }

    @DeleteMapping(path = "/fitness-centers/{id}")
    public ResponseEntity deactivateFitnessCenter(@PathVariable("id") Long id){
        fitnessCenterService.deactivateFitnessCenter(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }


    @PatchMapping(path = "/fitness-centers/contact/{id}/{contact_no}")
    public ResponseEntity<FitnessCenterDto> updateContact(@PathVariable("id") Long id,
                                                    @PathVariable("contact_no") String contactNo){
        if(!fitnessCenterService.isExists(id)){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        FitnessCenterDto updatedCenter =
                fitnessCenterService.updateContactNo(id,contactNo);
        return new ResponseEntity<>(updatedCenter,
                HttpStatus.OK);
    }

    @PatchMapping(path = "/fitness-centers/email/{id}/{email_id}")
    public ResponseEntity<FitnessCenterDto> updateEmail(@PathVariable("id") Long id,
                                                  @PathVariable("email_id") String emailId){
        if(!fitnessCenterService.isExists(id)){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        FitnessCenterDto updatedCenter =
                fitnessCenterService.updateContactNo(id,emailId);
        return new ResponseEntity<>(updatedCenter,
                HttpStatus.OK);
    }



}
