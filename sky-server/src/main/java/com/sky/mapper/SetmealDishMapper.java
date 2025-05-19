package com.sky.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @ClassName : SetmealDishMapper
 * @Description : 菜品和套餐关系
 * @Author :  CyberCaelum
 * @Date: 2025-05-19 20:45
 */
@Mapper
public interface SetmealDishMapper {

    /**
     * @description: 通过菜品id查找相关套餐
     * @author: CyberAstra
     * @date: 2025/5/19 at 20:48:53
     * @param: dishIds
     * @return: java.util.List<java.lang.Long>
     **/
    List<Long> getSetmealIdByDishIds(List<Long> dishIds);
}
