package com.example.recipe.service;

import com.example.recipe.exception.RecipeApiException;
import com.example.recipe.model.Ingredients;
import com.example.recipe.model.Recipe;

import java.io.IOException;
import java.util.List;

public interface RecipeService {
    List<Recipe> getRecipesByIngredients(Ingredients ingredients) throws RecipeApiException, IOException;
    Recipe getDetailedRecipe(String id) throws IOException;
}
