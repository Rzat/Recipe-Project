package com.recipeproject.services;

import com.recipeproject.converters.IngredientCommandToIngredient;
import com.recipeproject.converters.IngredientToIngredientCommand;
import com.recipeproject.converters.UnitOfMeasureCommandToUnitOfMeasure;
import com.recipeproject.converters.UnitOfMeasureToUnitOfMeasureCommand;
import com.recipeproject.domain.Ingredients;
import com.recipeproject.domain.Recipe;
import com.recipeproject.repositories.RecipeRepository;
import com.recipeproject.repositories.UnitOfMeasureReposityory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class IngredientsServiceImplTest {
    private final IngredientToIngredientCommand ingredientToIngredientCommand;
    private final IngredientCommandToIngredient ingredientCommandToIngredient;

    @Mock
    RecipeRepository recipeRepository;
    @Mock
    UnitOfMeasureReposityory unitOfMeasureReposityory;

    IngredientsService ingredientsService;

   public IngredientsServiceImplTest() {
        this.ingredientToIngredientCommand = new IngredientToIngredientCommand(new UnitOfMeasureToUnitOfMeasureCommand());
        this.ingredientCommandToIngredient = new IngredientCommandToIngredient(new UnitOfMeasureCommandToUnitOfMeasure());
    }

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        ingredientsService = new IngredientsServiceImpl(ingredientToIngredientCommand, ingredientCommandToIngredient, recipeRepository, unitOfMeasureReposityory);
    }

    @Test
    void deleteById() throws Exception {
        //given
        Recipe recipe = new Recipe();
        Ingredients ingredients = new Ingredients();
        ingredients.setId(3L);
        recipe.addIngredients(ingredients);
        ingredients.setRecipe(recipe);
        Optional<Recipe> recipeOptional = Optional.of(recipe);

        when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);

        //when
        ingredientsService.deleteById(1L, 3L);

        //then
        verify(recipeRepository, times(1)).findById(anyLong());
        verify(recipeRepository, times(1)).save(any(Recipe.class));

    }
}