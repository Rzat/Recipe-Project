package com.recipeproject.converters;

import com.recipeproject.commands.RecipeCommand;
import com.recipeproject.domain.Recipe;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class RecipeCommandToRecipe implements Converter<RecipeCommand, Recipe> {

    private final CategoryCommandToCategory categoryCommand;
    private final NotesCommandToNotes notesCommand;
    private final IngredientCommandToIngredient ingredientCommand;

    public RecipeCommandToRecipe(CategoryCommandToCategory categoryCommand, NotesCommandToNotes notesCommand, IngredientCommandToIngredient ingredientCommand) {
        this.categoryCommand = categoryCommand;
        this.notesCommand = notesCommand;
        this.ingredientCommand = ingredientCommand;
    }


    @Synchronized
    @Nullable
    @Override
    public Recipe convert(RecipeCommand recipeCommand) {
        if (recipeCommand == null)
            return null;

        final Recipe recipe = new Recipe();
        recipe.setId(recipeCommand.getId());
        recipe.setDescription(recipeCommand.getDescription());
        recipe.setPrepTime(recipeCommand.getPrepTime());
        recipe.setCookTime(recipeCommand.getCookTime());
        recipe.setServings(recipeCommand.getServings());
        recipe.setSource(recipeCommand.getSource());
        recipe.setUrl(recipeCommand.getUrl());
        recipe.setDirections(recipeCommand.getDirection());
        recipe.setNotes(notesCommand.convert(recipeCommand.getNotesCommand()));

        if (recipeCommand.getCategoryCommands() != null && recipeCommand.getCategoryCommands().size() > 0) {
            recipeCommand.getCategoryCommands()
                    .forEach(category -> recipe.getCategories().add(categoryCommand.convert(category)));
        }
        if (recipeCommand.getIngredients() != null && recipeCommand.getIngredients().size() > 0) {
            recipeCommand.getIngredients()
                    .forEach(ingredient -> recipe.getIngredients().add(ingredientCommand.convert(ingredient)));
        }
        return recipe;
    }
}
