package com.recipeproject.services;

import com.recipeproject.commands.IngredientCommand;
import com.recipeproject.converters.IngredientToIngredientCommand;
import com.recipeproject.domain.Recipe;
import com.recipeproject.repositories.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class IngredientsServiceImpl implements IngredientsService {

    private final IngredientToIngredientCommand ingredientToIngredientCommand;
    private final RecipeRepository recipeRepository;

    public IngredientsServiceImpl(IngredientToIngredientCommand ingredientToIngredientCommand, RecipeRepository recipeRepository) {
        this.ingredientToIngredientCommand = ingredientToIngredientCommand;
        this.recipeRepository = recipeRepository;
    }

    @Override
    public IngredientCommand findByRecipeIdAndIngredientId(Long id, Long id2) {
        Optional<Recipe> recipeOptional = recipeRepository.findById(id);

        if (!recipeOptional.isPresent()) {
            log.error("recipe id not found: " + id);
        }

        Recipe recipe = recipeOptional.get();

        Optional<IngredientCommand> ingredientCommandOptional = recipe.getIngredients().stream()
                .filter(ingredients -> ingredients.getId().equals(id2))
                .map(ingredients -> ingredientToIngredientCommand.convert(ingredients)).findFirst();

        if (!ingredientCommandOptional.isPresent()) {
            log.error("ingredient id not found: " + id2);
        }
        return ingredientCommandOptional.get();
    }
}
