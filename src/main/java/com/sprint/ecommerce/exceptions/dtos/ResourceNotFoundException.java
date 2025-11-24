package com.sprint.ecommerce.exceptions.dtos;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException{

    public ResourceNotFoundException(String resourceName, String fileName, Object fieldValue){
        super(String.format("%s não encontrado(a) com %s: '%s' ", resourceName, fileName, fieldValue));
    }
}
