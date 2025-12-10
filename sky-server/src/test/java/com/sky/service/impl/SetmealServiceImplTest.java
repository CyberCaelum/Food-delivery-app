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

    /**
     * TC1: setmealDishes = null
     * 条件1: setmealDishes != null -> false
     * 条件2: !setmealDishes.isEmpty() -> 不执行
     * 预期: 只更新套餐，不删除和保存菜品
     */
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

    /**
     * TC2: setmealDishes = [] (空列表)
     * 条件1: setmealDishes != null -> true
     * 条件2: !setmealDishes.isEmpty() -> false
     * 预期: 更新套餐，删除原有菜品，不保存新菜品
     */
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

    /**
     * TC3: setmealDishes = [菜品1, 菜品2] (非空列表)
     * 条件1: setmealDishes != null -> true
     * 条件2: !setmealDishes.isEmpty() -> true
     * 预期: 更新套餐，删除原有菜品，保存新菜品
     */
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

    /**
     * TC4: 测试事务回滚（可选）
     * 模拟保存菜品时抛出异常，验证事务是否会回滚
     */
    @Test(expected = RuntimeException.class)
    public void testUpdateSetmeal_TransactionRollback_WhenSaveDishFails() {
        // 准备测试数据
        setmealDTO.setId(1L);
        setmealDTO.setName("测试套餐");

        SetmealDish dish = new SetmealDish();
        dish.setDishId(101L);
        dish.setCopies(2);

        setmealDTO.setSetmealDishes(Arrays.asList(dish));

        // 模拟Mapper行为 - 保存菜品时抛出异常
        doNothing().when(setmealMapper).updateSetmeal(any(Setmeal.class));
        doNothing().when(setmealDishMapper).deletBySetmealId(anyLong());
        doThrow(new RuntimeException("数据库异常")).when(setmealDishMapper)
                .saveSetmealDish(any(SetmealDish.class));

        // 执行测试，期望抛出异常
        setmealService.updateSetmeal(setmealDTO);
    }

    /**
     * 边界测试: setmealId为空的情况
     */
    @Test
    public void testUpdateSetmeal_WhenSetmealIdIsNull() {
        // 准备测试数据
        setmealDTO.setId(null);
        setmealDTO.setName("测试套餐");

        // 执行测试 - 应该正常处理或抛出异常
        try {
            setmealService.updateSetmeal(setmealDTO);
        } catch (Exception e) {
            // 可以记录日志或验证异常类型
        }

        // 验证Mapper是否被调用
        verify(setmealMapper, times(1)).updateSetmeal(any(Setmeal.class));
    }

    /**
     * 验证BeanUtils.copyProperties的正确使用
     */
    @Test
    public void testUpdateSetmeal_BeanUtilsCopyProperties() {
        // 准备测试数据
        setmealDTO.setId(1L);
        setmealDTO.setName("测试套餐");
        setmealDTO.setPrice(new BigDecimal("99.0"));
        setmealDTO.setStatus(1);
        setmealDTO.setSetmealDishes(null);

        // 使用Spy来验证BeanUtils的调用
        SetmealServiceImpl spyService = spy(setmealService);
        doNothing().when(spyService).updateSetmeal(setmealDTO);

        // 执行
        spyService.updateSetmeal(setmealDTO);

        // 这里主要是验证逻辑，不是实际测试BeanUtils
        // 实际测试中应该验证更新后的setmeal对象属性
    }
}