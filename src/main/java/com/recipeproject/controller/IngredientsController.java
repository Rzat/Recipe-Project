package com.recipeproject.controller;


import com.recipeproject.commands.IngredientCommand;
import com.recipeproject.commands.RecipeCommand;
import com.recipeproject.commands.UnitOfMeasureCommand;
import com.recipeproject.services.IngredientsService;
import com.recipeproject.services.RecipeService;
import com.recipeproject.services.UnitOfMeasureService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Slf4j
@Controller
public class IngredientsController {
    private final IngredientsService ingredientsService;
    private final RecipeService recipeService;
    private final UnitOfMeasureService unitOfMeasureService;

    public IngredientsController(IngredientsService ingredientsService, RecipeService recipeService, UnitOfMeasureService unitOfMeasureService) {
        this.ingredientsService = ingredientsService;
        this.recipeService = recipeService;
        this.unitOfMeasureService = unitOfMeasureService;
    }

    @GetMapping("/recipe/{recipeId}/ingredients/{id}/show")
    public String showIngredients(@PathVariable String recipeId, @PathVariable String id, Model model) {
        model.addAttribute("ingredient", ingredientsService.findByRecipeIdAndIngredientId(Long.valueOf(recipeId), Long.valueOf(id)));
        return "recipe/ingredients/showIng";
    }

    @GetMapping("/recipe/{id}/viewIngredients")
    public String viewIngredients(@PathVariable String id, Model model) {
        log.debug("View recipe id:" + id);
        model.addAttribute("recipe", recipeService.findCommandById(Long.valueOf(id)));
        return "recipe/ingredients/list";
    }

    @GetMapping("/recipe/{recipeId}/ingredients/{id}/update")
    public String updateRecipeIngredients(@PathVariable String recipeId, @PathVariable String id, Model model) {
        log.debug("recipeId" + recipeId + " IngredientId" + id);
        model.addAttribute("ingredient", ingredientsService.findByRecipeIdAndIngredientId(Long.valueOf(recipeId), Long.valueOf(id)));
        model.addAttribute("uomList", unitOfMeasureService.listAllUoms());
        return "recipe/ingredients/ingredientform";
    }


    @PostMapping("/recipe/{recipeId}/ingredient")
    public String saveOrUpdate(@ModelAttribute IngredientCommand ingredientCommand) {
        IngredientCommand command = ingredientsService.saveIngredientCommand(ingredientCommand);
        log.debug(" Recipe id  is: " + command.getRecipeId());
        log.debug("Ingredient  id  is: " + command.getId());
        return "redirect:/recipe/" + command.getRecipeId() + "/ingredients/" + command.getId() + "/show";
    }

    @GetMapping("/recipe/{recipeId}/ingredient/new")
    public String newRecipe(@PathVariable String recipeId, Model model) {
        RecipeCommand recipeCommand = recipeService.findCommandById(Long.valueOf(recipeId));

        IngredientCommand ingredientCommand = new IngredientCommand();
        ingredientCommand.setRecipeId(Long.valueOf(recipeId));
        model.addAttribute("ingredient", ingredientCommand);

        ingredientCommand.setUnitOfMeasure(new UnitOfMeasureCommand());
        model.addAttribute("uomList", unitOfMeasureService.listAllUoms());
        return "recipe/ingredients/ingredientform";
    }

    @GetMapping("/recipe/{recipeId}/ingredients/{id}/delete")
    public String deleteIngredient(@PathVariable String recipeId, @PathVariable String id) {
        log.debug("deleting ingreidient id: " + id);
        ingredientsService.deleteById(Long.valueOf(recipeId), Long.valueOf(id));
        return "redirect:/recipe/" + recipeId + "/viewIngredients";
    }
}
