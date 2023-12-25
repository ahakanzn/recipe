package com.example.recipe.exception;

public class RecipeApiException extends Exception {
    public RecipeApiException(String message) {
            super(message);
    }

    public RecipeApiException(String message, Throwable cause) {
        super(message, cause);
    }
}
