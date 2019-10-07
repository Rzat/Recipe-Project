package com.recipeproject.services;

import com.recipeproject.commands.IngredientCommand;
import com.recipeproject.converters.IngredientCommandToIngredient;
import com.recipeproject.converters.IngredientToIngredientCommand;
import com.recipeproject.domain.Ingredients;
import com.recipeproject.domain.Recipe;
import com.recipeproject.repositories.RecipeRepository;
import com.recipeproject.repositories.UnitOfMeasureReposityory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
public class IngredientsServiceImpl implements IngredientsService {

    private final IngredientToIngredientCommand ingredientToIngredientCommand;
    private final RecipeRepository recipeRepository;
    private final UnitOfMeasureReposityory unitOfMeasureReposityory;

    private final IngredientCommandToIngredient ingredientCommandToIngredient;

    public IngredientsServiceImpl(IngredientToIngredientCommand ingredientToIngredientCommand, IngredientCommandToIngredient ingredientCommandToIngredient, RecipeRepository recipeRepository, UnitOfMeasureReposityory unitOfMeasureReposityory) {
        this.ingredientToIngredientCommand = ingredientToIngredientCommand;
        this.ingredientCommandToIngredient = ingredientCommandToIngredient;
        this.recipeRepository = recipeRepository;
        this.unitOfMeasureReposityory = unitOfMeasureReposityory;
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

    @Override
    @Transactional
    public IngredientCommand saveIngredientCommand(IngredientCommand command) {
        Optional<Recipe> recipeOptional = recipeRepository.findById(command.getRecipeId());

        if (!recipeOptional.isPresent()) {

            //todo toss error if not found!
            log.error("Recipe not found for id: " + command.getRecipeId());
            return new IngredientCommand();
        } else {
            Recipe recipe = recipeOptional.get();

            Optional<Ingredients> ingredientOptional = recipe
                    .getIngredients()
                    .stream()
                    .filter(ingredient -> ingredient.getId().equals(command.getId()))
                    .findFirst();

            if (ingredientOptional.isPresent()) {
                Ingredients ingredientFound = ingredientOptional.get();
                ingredientFound.setDescription(command.getDescription());
                ingredientFound.setAmount(command.getAmount());
                ingredientFound.setUnitOfMeasure(unitOfMeasureReposityory
                        .findById(command.getUnitOfMeasure().getId())
                        .orElseThrow(() -> new RuntimeException("UOM NOT FOUND"))); //todo address this
            } else {
                //add new Ingredient
                Ingredients ingredients = ingredientCommandToIngredient.convert(command);
                ingredients.setRecipe(recipe);
                recipe.addIngredients(ingredients);
            }

            Recipe savedRecipe = recipeRepository.save(recipe);

            Optional<Ingredients> savedIngredientOptional = savedRecipe.getIngredients().stream()
                    .filter(recipeIngredients -> recipeIngredients.getId().equals(command.getId()))
                    .findFirst();

            if (!savedIngredientOptional.isPresent()) {
                savedIngredientOptional = savedRecipe.getIngredients().stream()
                        .filter(recipeIngredients -> recipeIngredients.getDescription().equals(command.getDescription()))
                        .filter(recipeIngredients -> recipeIngredients.getAmount().equals(command.getAmount()))
                        .filter(recipeIngredients -> recipeIngredients.getUnitOfMeasure().getId().equals(command.getUnitOfMeasure().getId()))
                        .findFirst();
            }

            //to do check for fail
            return ingredientToIngredientCommand.convert(savedIngredientOptional.get());
        }

    }

    @Override
    public void deleteById(Long recipeId, Long id) {
        Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);
        if (recipeOptional.isPresent()) {
            Recipe recipe = recipeOptional.get();
            log.debug("found recipe");
            Optional<Ingredients> ingredientsOptional = recipe
                    .getIngredients()
                    .stream()
                    .filter(ingredients -> ingredients.getId().equals(id))
                    .findFirst();

            if (ingredientsOptional.isPresent()) {
                log.debug("ingredient found");
                Ingredients ingredientsToDelete = ingredientsOptional.get();
                ingredientsToDelete.setRecipe(null);
                recipe.getIngredients().remove(ingredientsOptional.get());
                recipeRepository.save(recipe);
            } else {
                log.debug("Recipe Id not found:" + id);
            }

        }
    }
}
