package com.example.softunigamestore.exections;

public class ValidationException extends RuntimeException{
    public ValidationException(String reason){
        super(reason);
    }
}
