package com.example.recipe.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Recipe {
    private String id;
    @JsonProperty("Name")
    private String name;
    @JsonProperty("Description")
    private String description;
    @JsonProperty("Time")
    private List<String> time;
    @JsonProperty("Ingredients")
    private List<String> ingredients;
    @JsonProperty("Directions")
    private List<String> directions;
    @JsonProperty("Nutritions")
    private List<String> nutritions;
    @JsonProperty("Rating")
    private String rating;
    @JsonProperty("Category")
    private String category;
    @JsonProperty("Cuisine")
    private String cuisine;
}
