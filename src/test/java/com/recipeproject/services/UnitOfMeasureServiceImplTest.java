package com.recipeproject.services;

import com.recipeproject.commands.UnitOfMeasureCommand;
import com.recipeproject.converters.UnitOfMeasureToUnitOfMeasureCommand;
import com.recipeproject.domain.UnitOfMeasure;
import com.recipeproject.repositories.UnitOfMeasureReposityory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class UnitOfMeasureServiceImplTest {

    UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureToUnitOfMeasureCommand = new UnitOfMeasureToUnitOfMeasureCommand();
    UnitOfMeasureService unitOfMeasureService;

    @Mock
    UnitOfMeasureReposityory unitOfMeasureReposityory;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        unitOfMeasureService = new UnitOfMeasureServiceImpl(unitOfMeasureReposityory, unitOfMeasureToUnitOfMeasureCommand);
    }

    @Test
    void listAllUoms() throws Exception {
        //given
        Set<UnitOfMeasure> setUom = new HashSet<>();
        UnitOfMeasure uom = new UnitOfMeasure();
        uom.setId(1L);
        setUom.add(uom);

        UnitOfMeasure uom2 = new UnitOfMeasure();
        uom2.setId(2L);
        setUom.add(uom2);

        when(unitOfMeasureReposityory.findAll()).thenReturn(setUom);

        //when
        Set<UnitOfMeasureCommand> unitOfMeasureCommands = unitOfMeasureService.listAllUoms();

        //then
        assertEquals(2, unitOfMeasureCommands.size());
        verify(unitOfMeasureReposityory, times(1)).findAll();
    }
}