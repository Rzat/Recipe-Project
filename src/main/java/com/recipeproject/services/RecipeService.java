package com.recipeproject.services;

import com.recipeproject.commands.RecipeCommand;
import com.recipeproject.domain.Recipe;

import java.util.Set;


public interface RecipeService {

    Set<Recipe> getRecipe();

    Recipe findById(Long Id);

    RecipeCommand saveRecipeCommand(RecipeCommand command);

    RecipeCommand findCommandById(Long id);

    void deleteById(Long id);
}
