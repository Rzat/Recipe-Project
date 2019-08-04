package com.recipeproject.controller;

import com.recipeproject.domain.Recipe;
import com.recipeproject.services.RecipeService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class IndexControllerTest {
    IndexController indexController;

    @Mock
    Model model;
    @Mock
    RecipeService recipeService;


    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this); //setting up mock
        indexController = new IndexController(recipeService);
    }

    @Test
    public void getIndex() {
        //given
        Set<Recipe> recipes = new HashSet<>();
        recipes.add(new Recipe());
        /*
        Recipe recipe = new Recipe();
        recipe.setId(1L);
        recipes.add(recipe);*/

        when(recipeService.getRecipe()).thenReturn(recipes);
        ArgumentCaptor<Set<Recipe>> argumentCaptor = ArgumentCaptor.forClass(Set.class);

        //when
        String recipeSet = indexController.getIndex(model);

        //then
        assertEquals("index", recipeSet);
        verify(recipeService, times(1)).getRecipe();
        verify(model, times(1)).addAttribute(eq("recipes"), argumentCaptor.capture());
        Set<Recipe> setInController = argumentCaptor.getValue();
        assertEquals(1, setInController.size());
    }
}