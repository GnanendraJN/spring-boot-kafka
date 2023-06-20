package org.learn.microservice.rest.exception;

import lombok.Data;
import lombok.val;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseStatus;

//@Data
//@ControllerAdvice
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException{

    private String resourceName;
    private String fieldName;
    private String fieldValue;

    public ResourceNotFoundException(String resourceName, String fieldName, String fieldValue){
        // If any fields are numeric then use single quotes
        //super(String.format("%s not found with %s : '%s'", resourceName, fieldName, fieldValue));
        //String message = String.format("%s not found with %s : %s", resourceName, fieldName, fieldValue);

        //System.out.println("Mesage is :  " + message);
        super(String.format("%s not found with %s : %s", resourceName, fieldName, fieldValue));

        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }
}
