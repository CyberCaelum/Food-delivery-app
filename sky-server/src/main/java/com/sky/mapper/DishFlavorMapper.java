package com.sky.mapper;

import com.sky.entity.DishFlavor;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @ClassName : DishFlavorMapper
 * @Description : 菜品口味
 * @Author :  CyberCaelum
 * @Date: 2025-05-19 15:00
 */
@Mapper
public interface DishFlavorMapper {

    /**
     * @description: 保存菜品相关口味
     * @author: CyberAstra
     * @date: 2025/5/19 at 21:02:52
     * @param: dishFlavors
     **/
    void save(@Param("flavors") List<DishFlavor> dishFlavors);

    /**
     * @description: 删除菜品相关口味
     * @author: CyberAstra
     * @date: 2025/5/19 at 21:03:11
     * @param: id
     **/
    @Delete("delete from dish_flavor where dish_id = #{dishId}")
    void deleteByDishId(Long dishId);

    /**
     * @description: 查询菜品的口味
     * @author: CyberAstra
     * @date: 2025/5/24 at 16:11:34
     * @return: java.util.List<com.sky.entity.DishFlavor>
     **/
    @Select("select * from dish_flavor where dish_id = #{dishId}")
    List<DishFlavor> getFlavorByDishId(Long dishId);

    /**
     * @description: 修改菜品相关口味
     * @author: CyberAstra
     * @date: 2025/5/24 at 17:01:42
     * @param: id
     **/
    void update(DishFlavor flavor);
}
