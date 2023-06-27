package com.shruteekatech.electronicstore.exceptions;

public class ResourceNotFoundException extends RuntimeException{
    public ResourceNotFoundException(String e){
        super(e);
    }
    public ResourceNotFoundException(){
        super("Resource Not Found");
    }
}
