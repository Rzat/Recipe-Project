package com.recipeproject.bootstrapDataLoader;

import com.recipeproject.domain.*;
import com.recipeproject.repositories.CategoryRepository;
import com.recipeproject.repositories.RecipeRepository;
import com.recipeproject.repositories.UnitOfMeasureReposityory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Slf4j
//@RequiredArgsConstructor
@Component
public class RecipeBootstrap implements ApplicationListener<ContextRefreshedEvent> {


    private final CategoryRepository categoryRepository;
    private final UnitOfMeasureReposityory unitOfMeasureReposityory;
    private final RecipeRepository recipeRepository;

    public RecipeBootstrap(CategoryRepository categoryRepository, UnitOfMeasureReposityory unitOfMeasureReposityory, RecipeRepository recipeRepository) {
        this.categoryRepository = categoryRepository;
        this.unitOfMeasureReposityory = unitOfMeasureReposityory;
        this.recipeRepository = recipeRepository;
    }

    @Override
    @Transactional
    //create transaction around this method, so now everything is going to happen in the same transactionalcontext.
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        recipeRepository.saveAll(getRecipe());
    }


    private List<Recipe> getRecipe() {

        List<Recipe> recipes = new ArrayList<>(2);

        //getUOMs;
        UnitOfMeasure eachUomOptional = getUnitOfMeasure("Each");
        UnitOfMeasure tablespoonUomOptional = getUnitOfMeasure("Tablespoon");
        UnitOfMeasure teaspoonUomOptional = getUnitOfMeasure("Teaspoon");
        UnitOfMeasure cupUomOptional = getUnitOfMeasure("Cup");
        UnitOfMeasure pinchUomOptional = getUnitOfMeasure("Pinch");
        UnitOfMeasure ounceUomOptional = getUnitOfMeasure("Ounce");
        UnitOfMeasure dashUomOptional = getUnitOfMeasure("Dash");
        UnitOfMeasure pintUomOptional = getUnitOfMeasure("Pint");

        //get Catogries
        Category indianCategory = getCategory("NINDIAN");
        Category chineseCategory = getCategory("CHINESE");
        Category italianCategory = getCategory("ITALIAN");
        Category mexicanCategory = getCategory("MEXICAN");

        Recipe guacRecipe = new Recipe();
        guacRecipe.setDescription("Perfect  Guacamole");
        guacRecipe.setPrepTime(10);
        guacRecipe.setCookTime(0);
        guacRecipe.setDifficulty(Difficulty.EASY);
        guacRecipe.setDirections("1 Cut avocado, remove flesh: Cut the avocados in half. Remove seed. Score the inside of the avocado with a blunt knife and scoop out the flesh with a spoon" +
                "\n" +
                "2 Mash with a fork: Using a fork, roughly mash the avocado. (Don't overdo it! The guacamole should be a little chunky.)" +
                "\n" +
                "3 Add salt, lime juice, and the rest: Sprinkle with salt and lime (or lemon) juice. The acid in the lime juice will provide some balance to the richness of the avocado and will help delay the avocados from turning brown.\n" +
                "Add the chopped onion, cilantro, black pepper, and chiles. Chili peppers vary individually in their hotness. So, start with a half of one chili pepper and add to the guacamole to your desired degree of hotness.\n" +
                "Remember that much of this is done to taste because of the variability in the fresh ingredients. Start with this recipe and adjust to your taste.\n" +
                "4 Cover with plastic and chill to store: Place plastic wrap on the surface of the guacamole cover it and to prevent air reaching it. (The oxygen in the air causes oxidation which will turn the guacamole brown.) Refrigerate until ready to serve.\n" +
                "Chilling tomatoes hurts their flavor, so if you want to add chopped tomato to your guacamole, add it just before serving.\n");
        guacRecipe.setUrl("https://www.simplyrecipes.com/recipes/perfect_guacamole/");
        guacRecipe.setServings(4);
        guacRecipe.setSource("Simply Recipe");

        Notes guacNotes = new Notes();
        guacNotes.setRecipeNotes("For a very quick guacamole just take a 1/4 cup of salsa and mix it in with your mashed avocados.\n" +
                "Feel free to experiment! One classic Mexican guacamole has pomegranate seeds and chunks of peaches in it (a Diana Kennedy favorite). Try guacamole with added pineapple, mango, or strawberries.\n" +
                "The simplest version of guacamole is just mashed avocados with salt. Don't let the lack of availability of other ingredients stop you from making guacamole.\n" +
                "To extend a limited supply of avocados, add either sour cream or cottage cheese to your guacamole dip. Purists may be horrified, but so what? It tastes great.\n" +
                "\n" +
                "\n" +
                "Read more: http://www.simplyrecipes.com/recipes/perfect_guacamole/#ixzz4jvoun5ws");
        guacNotes.setRecipe(guacRecipe);
        guacRecipe.setNotes(guacNotes);
        guacRecipe.getIngredients().add(new Ingredients("ripe avacados", new BigDecimal(2), eachUomOptional, guacRecipe));
        guacRecipe.getIngredients().add(new Ingredients("Kosher salt", new BigDecimal(5), teaspoonUomOptional, guacRecipe));
        guacRecipe.getIngredients().add((new Ingredients("fresh lime juice or lemon juice", new BigDecimal(2), tablespoonUomOptional, guacRecipe)));
        guacRecipe.getIngredients().add(new Ingredients("minced red onion or thinly sliced green onion", new BigDecimal(2), tablespoonUomOptional, guacRecipe));
        guacRecipe.getIngredients().add(new Ingredients("serrano chiles, stems and seeds removed, minced", new BigDecimal(2), eachUomOptional, guacRecipe));
        guacRecipe.getIngredients().add(new Ingredients("cilantro (leaves and tender stems), finely chopped", new BigDecimal(2), tablespoonUomOptional, guacRecipe));
        guacRecipe.getIngredients().add(new Ingredients("freshly grated black pepper", new BigDecimal(1), dashUomOptional, guacRecipe));
        guacRecipe.getIngredients().add(new Ingredients("ripe tomato, seeds and pulp removed, chopped", new BigDecimal(.5), eachUomOptional, guacRecipe));

        guacRecipe.getCategories().add(mexicanCategory);
        guacRecipe.getCategories().add(italianCategory);

        recipes.add(guacRecipe);
        log.info("Recipe loaded on startup");
        return recipes;
    }

    private Category getCategory(String description) {
        return categoryRepository
                .findByDescription(description)
                .orElseThrow(() -> new RuntimeException("Category " + description + "not found"));
    }

    private UnitOfMeasure getUnitOfMeasure(String description) {

        return unitOfMeasureReposityory
                .findByDescription(description)
                .orElseThrow(() -> new RuntimeException("Unit of measure" + description + "not found"));
    }


}
