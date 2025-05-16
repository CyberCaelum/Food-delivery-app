package com.sky.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * @ClassName : SetmealMapper
 * @Description : 套餐Mapper
 * @Author :  CyberCaelum
 * @Date: 2025-05-16 20:26
 */
@Mapper
public interface SetmealMapper {

    /**
     * @description: 根据分类id查询套餐的数量
     * @author: CyberAstra
     * @date: 2025/5/16 at 20:29:25
     * @param: Id
     * @return: java.lang.Integer
     **/
    @Select("select count(id) from setmeal where category_id = #{categoryId}")
    Integer countByCategoryId(Long Id);
}
