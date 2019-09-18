package com.recipeproject.converters;

import com.recipeproject.commands.IngredientCommand;
import com.recipeproject.domain.Ingredients;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class IngredientToIngredientCommand implements Converter<Ingredients, IngredientCommand> {
    private final UnitOfMeasureToUnitOfMeasureCommand uomConverter;

    public IngredientToIngredientCommand(UnitOfMeasureToUnitOfMeasureCommand uomConverter) {
        this.uomConverter = uomConverter;
    }

    @Synchronized
    @Nullable
    @Override
    public IngredientCommand convert(Ingredients ingredients) {
        if (ingredients == null)
            return null;

        IngredientCommand ing = new IngredientCommand();
        ing.setId(ingredients.getId());
        if (ingredients.getRecipe().getId() != null) {
            ing.setRecipeId(ingredients.getRecipe().getId());
        }
        ing.setDescription(ingredients.getDescription());
        ing.setAmount(ingredients.getAmount());
        ing.setUnitOfMeasure(uomConverter.convert(ingredients.getUnitOfMeasure()));

        return ing;
    }
}
