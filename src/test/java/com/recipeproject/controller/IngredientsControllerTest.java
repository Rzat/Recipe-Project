package com.recipeproject.controller;

import com.recipeproject.commands.IngredientCommand;
import com.recipeproject.services.IngredientsService;
import com.recipeproject.services.RecipeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class IngredientsControllerTest {
    @Mock
    RecipeService recipeService;

    @Mock
    IngredientsService ingredientsService;

    MockMvc mockMvc;

    IngredientsController ingredientsController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        ingredientsController = new IngredientsController(ingredientsService, recipeService);
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
}