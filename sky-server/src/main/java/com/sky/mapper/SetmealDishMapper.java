package com.sky.mapper;

import com.sky.entity.SetmealDish;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

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

    /**
     * @description: 修改套餐中的菜品
     * @author: CyberAstra
     * @date: 2025/5/24 at 15:53:15
     * @param: setmealDishes
     **/
    void updateSetmealDish(SetmealDish setmealDishes);

    /**
     * @description: 增加套餐中的菜品
     * @author: CyberAstra
     * @date: 2025/5/24 at 18:25:58
     * @param: setmealDish
     **/
    @Insert("insert into setmeal_dish (setmeal_id, dish_id, name, price, copies) VALUE " +
            "(#{setmealId},#{dishId},#{name},#{price},#{copies})")
    void saveSetmealDish(SetmealDish setmealDish);

    /**
     * @description: 根据套餐id查找菜品
     * @author: CyberAstra
     * @date: 2025/5/24 at 21:18:43
     * @param: setmealId
     * @return: java.util.List<com.sky.entity.SetmealDish>
     **/
    @Select("select * from setmeal_dish where setmeal_id = #{setmealId}")
    List<SetmealDish> getSetmealDishBySetmealId(Long setmealId);

    /**
     * @description: 通过套餐id删除套餐中的菜品
     * @author: CyberAstra
     * @date: 2025/5/25 at 10:44:31
     * @param: setmealId
     **/
    @Delete("delete from setmeal_dish where setmeal_id = #{setmealId}")
    void deletBySetmealId(Long setmealId);
}
