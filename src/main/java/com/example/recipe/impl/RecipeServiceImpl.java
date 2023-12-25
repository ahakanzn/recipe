package com.example.recipe.impl;

import com.example.recipe.exception.RecipeApiException;
import com.example.recipe.model.Ingredients;
import com.example.recipe.model.Recipe;
import com.example.recipe.properties.RecipeProperties;
import com.example.recipe.service.RecipeService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class RecipeServiceImpl implements RecipeService {
    private final OkHttpClient client;
    private final String baseUrl;
    private final String rapidApiKey;
    private final ObjectMapper mapper = new ObjectMapper();

    public RecipeServiceImpl(RecipeProperties recipeProperties) {
        this.baseUrl = recipeProperties.getBaseUrl();
        this.rapidApiKey = recipeProperties.getRapidApiKey();
        this.client = new OkHttpClient();
    }

    @Override
    public List<Recipe> getRecipesByIngredients(Ingredients ingredients) throws IOException {
        try {
            MediaType mediaType = MediaType.parse("application/json");
            RequestBody body = RequestBody.create("{\n    \"ingredients\": \"" + ingredients.getIngredients() + "\"\n}", mediaType);
            Request request = new Request.Builder()
                    .url(baseUrl + "/search")
                    .post(body)
                    .addHeader("content-type", "application/json")
                    .addHeader("X-RapidAPI-Key", rapidApiKey)
                    .addHeader("X-RapidAPI-Host", "all-in-one-recipe-api.p.rapidapi.com")
                    .build();

            Response response = client.newCall(request).execute();
            JsonNode jsonResponse = mapper.readTree(response.body().string());
            JsonNode recipeData = jsonResponse.get("recipe").get("data");

            List<Recipe> recipes = new ArrayList<>();
            if (recipeData.isArray()) {
                for (JsonNode data : recipeData) {
                    Recipe recipe = new Recipe();
                    recipe.setName(data.get("name").asText());
                    recipe.setId(data.get("id").asText());
                    recipes.add(recipe);
                }
            }

            return recipes;
        } catch (IOException e) {
            String errorMessage = "An error occurred during API Call: " + e.getMessage();
            log.error(errorMessage);
            throw e;
        }
    }

    @Override
    public Recipe getDetailedRecipe(String id) throws IOException {
        Request request = new Request.Builder()
                .url(baseUrl + "/details/" + id)
                .get()
                .addHeader("X-RapidAPI-Key", rapidApiKey)
                .addHeader("X-RapidAPI-Host", "all-in-one-recipe-api.p.rapidapi.com")
                .build();

        try (Response response = client.newCall(request).execute()) {
            JsonNode jsonResponse = mapper.readTree(response.body().string());
            JsonNode recipeData = jsonResponse.get("recipe").get("data");
            Recipe recipe = mapper.convertValue(recipeData, Recipe.class);
            recipe.setId(id);
            return recipe;
        } catch (JsonProcessingException e) {
            String errorMessage = "Error occurred during parsing Json: " + e.getMessage();
            log.error(errorMessage);
            throw e;
        } catch (IOException e) {
            String errorMessage = "IO Error occurred: " + e.getMessage();
            log.error(errorMessage);
            throw e;
        }
    }

    public List<Recipe> getDetailedRecipeListForIngredients(Ingredients ingredients) throws RecipeApiException {
        try {
            List<Recipe> recipes = getRecipesByIngredients(ingredients);

            List<Recipe> detailedRecipes = new ArrayList<>();
            for (Recipe recipe : recipes) {
                try {
                    Recipe detailedRecipe = getDetailedRecipe(recipe.getId());
                    detailedRecipes.add(detailedRecipe);
                } catch (IOException | RuntimeException e) {
                    log.error("Error occurred while processing recipe: " + e.getMessage());
                    throw e;
                }
            }
            return detailedRecipes;
        } catch (IOException e) {
            log.error("IO Exception");
            throw new RecipeApiException("Error occurred while fetching recipes by ingredients: " + e.getMessage());
        }
    }

}

