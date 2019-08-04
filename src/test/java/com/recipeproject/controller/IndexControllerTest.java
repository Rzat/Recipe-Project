package com.recipeproject.controller;

import com.recipeproject.services.RecipeService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

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
        String recipeSet = indexController.getIndex(model);
        assertEquals("index", recipeSet);
        verify(recipeService, times(1)).getRecipe();
        verify(model, times(1)).addAttribute(eq("recipes"), anySet());
    }
}