package com.sky.service;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.result.PageResult;

import java.util.List;

/**
 * @ClassName : DishService
 * @Description : 菜品操作抽象类
 * @Author :  CyberCaelum
 * @Date: 2025-05-19 14:48
 */
public interface DishService {

    /**
     * @description: 新增菜品和菜品的口味
     * @author: CyberAstra
     * @date: 2025/5/19 at 14:58:06
     * @param: dishDTO
     **/
    void saveWithFlavor(DishDTO dishDTO);

    /**
     * @description: 菜品分页查询
     * @author: CyberAstra
     * @date: 2025/5/19 at 16:30:05
     * @param: dishPageQueryDTO
     * @return: com.sky.result.PageResult
     **/
    PageResult pageQuery(DishPageQueryDTO dishPageQueryDTO);

    /**
     * @description: 批量删除菜品
     * @author: CyberAstra
     * @date: 2025/5/19 at 20:38:27
     * @param: ids
     **/
    void deleteDish(List<Long> ids);
}
