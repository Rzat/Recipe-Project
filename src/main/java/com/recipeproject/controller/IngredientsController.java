package com.recipeproject.controller;


import com.recipeproject.services.IngredientsService;
import com.recipeproject.services.RecipeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class IngredientsController {
    private final IngredientsService ingredientsService;
    private final RecipeService recipeService;

    public IngredientsController(IngredientsService ingredientsService, RecipeService recipeService) {
        this.ingredientsService = ingredientsService;
        this.recipeService = recipeService;
    }

    @GetMapping("/recipe/{recipeId}/ingredients/{id}/show")
    public String showIngredients(@PathVariable String recipeId, @PathVariable String id, Model model) {
        model.addAttribute("ingredient", ingredientsService.findByRecipeIdAndIngredientId(Long.valueOf(recipeId), Long.valueOf(id)));
        return "recipe/ingredients/showIng";
    }
}
