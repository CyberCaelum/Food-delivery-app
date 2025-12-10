package com.sky.service.impl;

import com.sky.exception.DeletionNotAllowedException;
import com.sky.mapper.CategoryMapper;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetmealMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CategoryServiceImplTest {
    @Mock
    private DishMapper dishMapper;

    @Mock
    private SetmealMapper setmealMapper;

    @Mock
    private CategoryMapper categoryMapper;

    @InjectMocks
    private CategoryServiceImpl categoryService;

    //分类下有菜品
    @Test(expected = DeletionNotAllowedException.class)
    public void testDeleteCategory_WithRelatedDishes() {
        //分类id
        Long categoryId = 1L;
        // 模拟：分类关联了菜品
        when(dishMapper.countByCategoryId(categoryId)).thenReturn(3);  // > 0
        categoryService.deleteCategory(categoryId);
    }

    //分类下有套餐
    @Test(expected = DeletionNotAllowedException.class)
    public void testDeleteCategory_WithRelatedSetmeals() {
        //分类id
        Long categoryId = 1L;
        // 模拟：分类关联了套餐
        when(setmealMapper.countByCategoryId(categoryId)).thenReturn(3);
        categoryService.deleteCategory(categoryId);
    }

    @Test
    public void testDeleteCategory_NoRelations() {
        //分类id
        Long categoryId = 1L;
        when(dishMapper.countByCategoryId(categoryId)).thenReturn(0);
        when(setmealMapper.countByCategoryId(categoryId)).thenReturn(0);
        doNothing().when(categoryMapper).deleteCategory(categoryId);
        categoryService.deleteCategory(categoryId);
        // 验证：deleteCategory方法确实被调用了
        verify(categoryMapper).deleteCategory(categoryId);
    }
}