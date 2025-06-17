package com.sky.mapper;

import com.alibaba.fastjson.JSONPatch;
import com.github.pagehelper.Page;
import com.sky.annotation.AutoFill;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.enumeration.OperationType;
import com.sky.vo.DishVO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

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

    /**
     * @description: 添加菜品
     * @author: CyberAstra
     * @date: 2025/5/19 at 15:42:00
     * @param: dish
     **/
    void save(Dish dish);

    /**
     * @description: 菜品分页查询
     * @author: CyberAstra
     * @date: 2025/5/19 at 16:42:07
     * @param: dishPageQueryDTO
     * @return: com.github.pagehelper.Page
     **/
    Page<DishVO> pageQuery(DishPageQueryDTO dishPageQueryDTO);

    /**
     * @description: 通过id查找菜品
     * @author: CyberAstra
     * @date: 2025/5/19 at 20:42:23
     * @param: id
     * @return: com.sky.entity.Dish
     **/
    @Select("select * from dish where id = #{id}")
    Dish getById(Long id);

    /**
     * @description: 通过id删除菜品
     * @author: CyberAstra
     * @date: 2025/5/19 at 21:01:38
     * @param: id
     **/
    @Delete("delete from dish where id = #{id}")
    void deleteById(Long id);

    /**
     * @description: 根据分类id查找菜品
     * @author: CyberAstra
     * @date: 2025/5/24 at 16:25:47
     * @param: categoryId
     * @return: java.util.List<com.sky.entity.Dish>
     */
    @Select("select * from dish where category_id = #{categoryId}")
    List<Dish> getByCategoryId(Long categoryId);

    /**
     * @description: 修改菜品
     * @author: CyberAstra
     * @date: 2025/5/24 at 16:53:25
     * @param: dish
     **/
    void update(Dish dish);

}
