package com.recipeproject.services;

import com.recipeproject.commands.IngredientCommand;


public interface IngredientsService {
    IngredientCommand findByRecipeIdAndIngredientId(Long id, Long id2);

    IngredientCommand saveIngredientCommand(IngredientCommand command);
}
