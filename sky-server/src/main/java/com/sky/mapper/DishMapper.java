package com.sky.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * @ClassName : DishMapper
 * @Description : 菜品mapper
 * @Author :  CyberCaelum
 * @Date: 2025-05-16 20:30
 */
@Mapper
public interface DishMapper {

    /**
     * @description: 根据分类id查询菜品数量
     * @author: CyberAstra
     * @date: 2025/5/16 at 20:32:27
     * @param: categoryId
     * @return: java.lang.Integer
     **/
    @Select("select count(id) from dish where category_id = #{categoryId}")
    Integer countByCategoryId(Long categoryId);
}
