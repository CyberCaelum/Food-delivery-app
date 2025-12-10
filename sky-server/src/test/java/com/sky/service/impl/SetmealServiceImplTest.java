package com.sky.service.impl;
import com.sky.dto.SetmealDTO;
import com.sky.entity.Setmeal;
import com.sky.entity.SetmealDish;
import com.sky.mapper.SetmealDishMapper;
import com.sky.mapper.SetmealMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class SetmealServiceImplTest {

    @Mock
    private SetmealMapper setmealMapper;

    @Mock
    private SetmealDishMapper setmealDishMapper;

    @InjectMocks
    private SetmealServiceImpl setmealService;

    private SetmealDTO setmealDTO;
    private Setmeal setmeal;

    @Before
    public void setUp() {
        setmealDTO = new SetmealDTO();
        setmeal = new Setmeal();
    }

    //
    @Test
    public void testUpdateSetmeal_WhenSetmealDishesIsNull() {
        // 准备测试数据
        setmealDTO.setId(1L);
        setmealDTO.setName("测试套餐");
        setmealDTO.setSetmealDishes(null);

        // 模拟Mapper行为
        doNothing().when(setmealMapper).updateSetmeal(any(Setmeal.class));
        doNothing().when(setmealDishMapper).deletBySetmealId(anyLong());

        // 执行测试
        setmealService.updateSetmeal(setmealDTO);

        // 验证
        verify(setmealMapper, times(1)).updateSetmeal(any(Setmeal.class));
        verify(setmealDishMapper, times(1)).deletBySetmealId(1L);
        verify(setmealDishMapper, never()).saveSetmealDish(any(SetmealDish.class));
    }

    @Test
    public void testUpdateSetmeal_WhenSetmealDishesIsEmpty() {
        // 准备测试数据
        setmealDTO.setId(1L);
        setmealDTO.setName("测试套餐");
        setmealDTO.setSetmealDishes(Collections.emptyList());

        // 模拟Mapper行为
        doNothing().when(setmealMapper).updateSetmeal(any(Setmeal.class));
        doNothing().when(setmealDishMapper).deletBySetmealId(anyLong());

        // 执行测试
        setmealService.updateSetmeal(setmealDTO);

        // 验证
        verify(setmealMapper, times(1)).updateSetmeal(any(Setmeal.class));
        verify(setmealDishMapper, times(1)).deletBySetmealId(1L);
        verify(setmealDishMapper, never()).saveSetmealDish(any(SetmealDish.class));
    }

    //
    @Test
    public void testUpdateSetmeal_WhenSetmealDishesIsNotEmpty() {
// 准备测试数据
        setmealDTO.setId(1L);
        setmealDTO.setName("测试套餐");

        SetmealDish dish1 = new SetmealDish();
        dish1.setDishId(101L);
        dish1.setCopies(2);

        SetmealDish dish2 = new SetmealDish();
        dish2.setDishId(102L);
        dish2.setCopies(3);

        List<SetmealDish> dishes = Arrays.asList(dish1, dish2);
        setmealDTO.setSetmealDishes(dishes);

        // 模拟Mapper行为
        doNothing().when(setmealMapper).updateSetmeal(any(Setmeal.class));
        doNothing().when(setmealDishMapper).deletBySetmealId(anyLong());
        doNothing().when(setmealDishMapper).saveSetmealDish(any(SetmealDish.class));

        // 创建ArgumentCaptor
        ArgumentCaptor<SetmealDish> dishCaptor = ArgumentCaptor.forClass(SetmealDish.class);

        // 执行测试
        setmealService.updateSetmeal(setmealDTO);

        // 验证
        verify(setmealMapper, times(1)).updateSetmeal(any(Setmeal.class));
        verify(setmealDishMapper, times(1)).deletBySetmealId(1L);

        // 验证saveSetmealDish被调用了2次，并捕获所有参数
        verify(setmealDishMapper, times(2)).saveSetmealDish(dishCaptor.capture());

        // 获取所有捕获的参数
        List<SetmealDish> capturedDishes = dishCaptor.getAllValues();

        // 验证参数
        assertEquals(2, capturedDishes.size());

        // 验证每个菜品都设置了正确的setmealId
        for (SetmealDish dish : capturedDishes) {
            assertEquals(1L, dish.getSetmealId().longValue());
        }

        // 可选：验证具体的菜品ID
        Set<Long> expectedDishIds = new HashSet<>(Arrays.asList(101L, 102L));
        Set<Long> actualDishIds = capturedDishes.stream()
                .map(SetmealDish::getDishId)
                .collect(Collectors.toSet());
        assertEquals(expectedDishIds, actualDishIds);
    }
}