package com.recipeproject.services;

import com.recipeproject.converters.RecipeCommandToRecipe;
import com.recipeproject.converters.RecipeToRecipeCommand;
import com.recipeproject.domain.Recipe;
import com.recipeproject.repositories.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

@Slf4j
public class RecipeSericeImplTest {

    RecipeSericeImpl recipeSericeImpl;

    @Mock
    RecipeService recipeService;

    @Mock
    RecipeRepository recipeRepository;

    @Mock
    RecipeToRecipeCommand recipeToRecipeCommand;

    @Mock
    RecipeCommandToRecipe recipeCommandToRecipe;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);//setup the mock and thats tell mockito  give me a mock recipe
        // repository.
        recipeSericeImpl = new RecipeSericeImpl(recipeRepository, recipeCommandToRecipe, recipeToRecipeCommand);
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

    @Test
    public void getRecipeByIdtest() throws Exception {
        Recipe recipe1 = new Recipe();
        recipe1.setId(1L);
        Optional<Recipe> recipeOptional = Optional.of(recipe1);

        when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);

        Recipe find = recipeSericeImpl.findById(1L);
        assertNotNull("Null Recipe returned", find);
        verify(recipeRepository, times(1)).findById(anyLong());
        verify(recipeRepository, never()).findAll();
    }


    @Test
    public void deleteById() {
        //given
        Long deleteId = Long.valueOf(2L);

        //when
        recipeSericeImpl.deleteById(deleteId);
        //no when since method has void return type

        //then
        verify(recipeRepository, times(1)).deleteById(anyLong());

    }

}