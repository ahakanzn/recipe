package com.example.recipe.presentation;

import com.example.recipe.exception.RecipeApiException;
import com.example.recipe.impl.RecipeServiceImpl;
import com.example.recipe.model.Ingredients;
import com.example.recipe.model.Recipe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(value = "/recipe")
public class RecipeController {
    @Autowired
    private final RecipeServiceImpl recipeService;

    public RecipeController(RecipeServiceImpl recipeService) {
        this.recipeService = recipeService;
    }

    @PostMapping(value = "/getRecipesByIngredients")
    public List<Recipe> getRecipesByIngredients(@RequestBody Ingredients ingredients) throws RecipeApiException, IOException {
        return recipeService.getRecipesByIngredients(ingredients);
    }

    @GetMapping(value = "/getDetailedRecipe/{id}")
    public Recipe getDetailedRecipe(@PathVariable String id) throws IOException {
        return recipeService.getDetailedRecipe(id);
    }

    @PostMapping(value = "/getDetailedRecipeListForIngredients")
    public List<Recipe> getDetailedRecipeListForIngredients(@RequestBody Ingredients ingredients) throws RecipeApiException {
        return recipeService.getDetailedRecipeListForIngredients(ingredients);
    }
}
