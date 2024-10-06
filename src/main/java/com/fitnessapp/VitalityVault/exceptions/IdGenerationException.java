package com.fitnessapp.VitalityVault.exceptions;

import com.fitnessapp.VitalityVault.services.IdGeneratorService;

public class IdGenerationException extends RuntimeException{
    public IdGenerationException(String message){
        super(message);
    }
}
