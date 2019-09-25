package com.recipeproject.controller;

import com.recipeproject.commands.IngredientCommand;
import com.recipeproject.commands.RecipeCommand;
import com.recipeproject.services.IngredientsService;
import com.recipeproject.services.RecipeService;
import com.recipeproject.services.UnitOfMeasureService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.HashSet;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class IngredientsControllerTest {
    @Mock
    RecipeService recipeService;

    @Mock
    IngredientsService ingredientsService;

    @Mock
    UnitOfMeasureService unitOfMeasureService;

    MockMvc mockMvc;

    IngredientsController ingredientsController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        ingredientsController = new IngredientsController(ingredientsService, recipeService, unitOfMeasureService);
        mockMvc = MockMvcBuilders.standaloneSetup(ingredientsController).build();
    }

    @Test
    void showIngredients() throws Exception {
        IngredientCommand ingredient = new IngredientCommand();
        when(ingredientsService.findByRecipeIdAndIngredientId(anyLong(), anyLong())).thenReturn(ingredient);
        mockMvc.perform(get("/recipe/1/ingredients/2/show"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("ingredient"))
                .andExpect(view().name("recipe/ingredients/showIng"));

    }

    @Test
    void viewIngredient() throws Exception {
        RecipeCommand recipe = new RecipeCommand();

        when(recipeService.findCommandById(anyLong())).thenReturn(recipe);

        mockMvc.perform(get("/recipe/1/viewIngredients"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/ingredients/list"))
                .andExpect(model().attributeExists("recipe"));


        verify(recipeService, times(1)).findCommandById(anyLong());
    }

    @Test
    public void testNewIngredientForm() throws Exception {
        //given
        RecipeCommand recipeCommand = new RecipeCommand();
        recipeCommand.setId(1L);

        //when
        when(recipeService.findCommandById(anyLong())).thenReturn(recipeCommand);
        when(unitOfMeasureService.listAllUoms()).thenReturn(new HashSet<>());

        //then
        mockMvc.perform(get("/recipe/1/ingredient/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/ingredients/ingredientform"))
                .andExpect(model().attributeExists("ingredient"))
                .andExpect(model().attributeExists("uomList"));

    }
}