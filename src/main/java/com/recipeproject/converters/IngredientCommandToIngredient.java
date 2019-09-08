package com.recipeproject.converters;

import com.recipeproject.commands.IngredientCommand;
import com.recipeproject.domain.Ingredients;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class IngredientCommandToIngredient implements Converter<IngredientCommand, Ingredients> {

    private final UnitOfMeasureCommandToUnitOfMeasure uomConverter;

    public IngredientCommandToIngredient(UnitOfMeasureCommandToUnitOfMeasure uomConverter) {
        this.uomConverter = uomConverter;
    }


    @Nullable
    @Override
    public Ingredients convert(IngredientCommand ingredientCommand) {
        if (ingredientCommand == null)
            return null;

        final Ingredients ingredients = new Ingredients();
        ingredients.setId(ingredientCommand.getId());
        ingredients.setDescription(ingredientCommand.getDescription());
        ingredients.setAmount(ingredientCommand.getAmount());
        ingredients.setUnitOfMeasure(uomConverter.convert(ingredientCommand.getUnitOfMeasure()));
        return ingredients;
    }
}
