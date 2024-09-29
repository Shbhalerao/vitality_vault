package com.fitnessapp.VitalityVault.controllers;

import com.fitnessapp.VitalityVault.domain.dto.FitnessCenterDto;
import com.fitnessapp.VitalityVault.domain.entities.FitnessCenterEntity;
import com.fitnessapp.VitalityVault.mappers.Mapper;
import com.fitnessapp.VitalityVault.services.FitnessCenterService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
public class FitnessCenterController {

    private FitnessCenterService fitnessCenterService;

    private Mapper<FitnessCenterEntity, FitnessCenterDto> fitnessCenterMapper;

    public FitnessCenterController(FitnessCenterService fitnessCenterService
            ,Mapper<FitnessCenterEntity, FitnessCenterDto> fitnessCenterMapper){
        this.fitnessCenterService = fitnessCenterService;
        this.fitnessCenterMapper = fitnessCenterMapper;
    }

    @PostMapping(path = "/fitness-centers")
    public ResponseEntity<FitnessCenterDto> createFitnessCenter(@RequestBody FitnessCenterDto fitnessCenterDto){
        FitnessCenterEntity fitnessCenterEntity = fitnessCenterMapper.mapFrom(fitnessCenterDto);
        FitnessCenterEntity savedFitnessCenterEntity = fitnessCenterService.createFitnessCenter(fitnessCenterEntity);
        return new ResponseEntity<>(fitnessCenterMapper.mapTo(savedFitnessCenterEntity), HttpStatus.CREATED);
    }

    @GetMapping(path = "/fitness-centers/{id}")
    public ResponseEntity<FitnessCenterDto> findOneById(@PathVariable("id") Long id){
        Optional<FitnessCenterEntity> fitnessCenter = fitnessCenterService.getFitnessCenterForId(id);
        return fitnessCenter
                .map(fitnessCenterEntity -> new ResponseEntity<>
                        (fitnessCenterMapper.mapTo(fitnessCenterEntity),HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping(path = "/fitness-centers")
    public ResponseEntity<List<FitnessCenterDto>> findAll(){
        List<FitnessCenterEntity> fitnessCenterEntityList = fitnessCenterService.findAll(false);
        List<FitnessCenterDto> fitnessCenterDtos = fitnessCenterEntityList.stream()
                .map(fitnessCenterMapper::mapTo)
                .toList();
        return new ResponseEntity<>(fitnessCenterDtos, HttpStatus.OK);
    }

    @PatchMapping(path = "/fitness-centers/{id}")
    public ResponseEntity<FitnessCenterDto> update(@PathVariable("id") Long id,
                                                   @RequestBody FitnessCenterDto fitnessCenterDto){
            if(!fitnessCenterService.isExists(id)){
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            FitnessCenterEntity updatedEntity =
                    fitnessCenterService.update(id,fitnessCenterMapper.mapFrom(fitnessCenterDto));

            return new ResponseEntity<>(fitnessCenterMapper.mapTo(updatedEntity),
                    HttpStatus.OK);

    }

    @DeleteMapping(path = "/fitness-centers/{id}")
    public ResponseEntity deactivateFitnessCenter(@PathVariable("id") Long id){
        fitnessCenterService.deactivateFitnessCenter(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @GetMapping(path = "/fitness-centers/deactivated")
    public ResponseEntity<List<FitnessCenterDto>> findAllDeactivatedCenters(){
        List<FitnessCenterEntity> fitnessCenterEntityList = fitnessCenterService.findAll(true);
        List<FitnessCenterDto> fitnessCenterDtos = fitnessCenterEntityList.stream()
                .map(fitnessCenterMapper::mapTo)
                .toList();
        return new ResponseEntity<>(fitnessCenterDtos, HttpStatus.OK);
    }



}
