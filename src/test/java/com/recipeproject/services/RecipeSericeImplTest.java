package com.recipeproject.services;

import com.recipeproject.domain.Recipe;
import com.recipeproject.repositories.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@Slf4j
public class RecipeSericeImplTest {

    RecipeSericeImpl recipeSericeImpl;

    @Mock
    RecipeRepository recipeRepository;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);//setup the mock and thats tell mockito  give me a mock recipe
        // repository.
        recipeSericeImpl = new RecipeSericeImpl(recipeRepository);
    }

    @Test
    public void getRecipe() {
        Recipe recipe = new Recipe();
        HashSet recipeData = new HashSet();
        recipeData.add(recipe);

        when(recipeRepository.findAll()).thenReturn(recipeData);

        Set<Recipe> recipeSet = recipeSericeImpl.getRecipe();
        assertEquals(recipeSet.size(), 1);
        verify(recipeRepository, times(1)).findAll();
    }

}