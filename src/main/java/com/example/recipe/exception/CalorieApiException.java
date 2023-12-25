package com.example.recipe.exception;


public class CalorieApiException extends RuntimeException{
    public CalorieApiException(String message){
        super(message);
    }

    public CalorieApiException(String message, Throwable throwable){
        super(message,throwable);
    }
}
