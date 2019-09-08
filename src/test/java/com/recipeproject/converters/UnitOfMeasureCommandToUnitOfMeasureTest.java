package com.recipeproject.converters;

import com.recipeproject.commands.UnitOfMeasureCommand;
import com.recipeproject.domain.UnitOfMeasure;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UnitOfMeasureCommandToUnitOfMeasureTest {

    private static final Long id = new Long(1);
    private static final String description = "description";

    UnitOfMeasureCommandToUnitOfMeasure converter;

    @BeforeEach
    void setUp() {
        converter = new UnitOfMeasureCommandToUnitOfMeasure();
    }

    @Test
    public void testNullPointer() throws Exception {
        assertNull(converter.convert(null));
    }

    @Test
    public void testEmptyObject() throws Exception {
        assertNotNull(converter.convert(new UnitOfMeasureCommand()));
    }

    @Test
    void convert() {
        //given
        UnitOfMeasureCommand unitOfMeasure = new UnitOfMeasureCommand();
        unitOfMeasure.setId(id);
        unitOfMeasure.setDescription(description);

        //when
        UnitOfMeasure uom = converter.convert(unitOfMeasure);

        //then
        assertNotNull(uom);
        assertEquals(id, uom.getId());
        assertEquals(description, uom.getDescription());

    }
}