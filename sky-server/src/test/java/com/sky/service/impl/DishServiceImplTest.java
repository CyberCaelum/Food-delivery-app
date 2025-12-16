package com.sky.service.impl;

import com.sky.mapper.DishFlavorMapper;
import com.sky.mapper.DishMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class DishServiceImplTest {

    @Mock
    private DishMapper dishMapper;

    @Mock
    private DishFlavorMapper dishFlavorMapper;

    @InjectMocks
    private DishServiceImpl dishService;

    @Test
    public void testSaveWithFlavor_FlavorIsNull(){

    }
    @Test
    public void testSaveWithFlavor_FlavorIsEmpty(){

    }
    @Test
    public void testSaveWithFlavor(){

    }
}