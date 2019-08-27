package com.recipeproject.services;

import com.recipeproject.domain.Recipe;

import java.util.Set;


public interface RecipeService  {

    Set<Recipe> getRecipe();
    Recipe findById(Long Id);

}
