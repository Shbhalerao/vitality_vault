package com.fitnessapp.VitalityVault.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<String> handleResourceNotFoundException(ResourceNotFoundException e){
        log.error("Resource Not Found : {}", e.getMessage(), e);
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DuplicateContactNoException.class)
    public ResponseEntity<String> handleDuplicateContactNoException(DuplicateContactNoException e){
        log.warn("Duplicate Contact No Found : {}", e.getMessage(), e);
        return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(DuplicateEmailIdException.class)
    public ResponseEntity<String> handleDuplicateEmailIdException(DuplicateEmailIdException e){
        log.warn("Duplicate Email Id Found : {}", e.getMessage(), e);
        return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(IdGenerationException.class)
    public ResponseEntity<String> handleIdGenerationException(IdGenerationException e){
        log.error("ID generation failed : {}", e.getMessage(), e);
        return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGeneralException(Exception e){
        log.error("Unexpected error : {}", e.getMessage(), e);
        return new ResponseEntity<>("Something went wrong! "+ e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }



}
