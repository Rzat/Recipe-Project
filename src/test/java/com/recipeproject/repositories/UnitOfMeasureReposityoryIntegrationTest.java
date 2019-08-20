package com.recipeproject.repositories;

import com.recipeproject.domain.UnitOfMeasure;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UnitOfMeasureReposityoryIntegrationTest {

    @Autowired
    UnitOfMeasureReposityory unitOfMeasureReposityory;

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void findByDescription() {
        Optional<UnitOfMeasure> unitOfMeasure = unitOfMeasureReposityory.findByDescription("Teaspoon");
        assertEquals("Teaspoon", unitOfMeasure.get().getDescription());
    }
}