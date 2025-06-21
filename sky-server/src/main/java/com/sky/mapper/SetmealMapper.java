package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.annotation.AutoFill;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Setmeal;
import com.sky.enumeration.OperationType;
import com.sky.vo.DishItemVO;
import com.sky.vo.SetmealVO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

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

    /**
     * @description: 套餐分页查询
     * @author: CyberAstra
     * @date: 2025/5/23 at 19:46:34
     * @param: setmealPageQueryDTO
     * @return: com.github.pagehelper.Page
     **/
    Page<SetmealVO> PageQuery(SetmealPageQueryDTO setmealPageQueryDTO);

    /**
     * @description: 修改套餐
     * @author: CyberAstra
     * @date: 2025/5/24 at 15:35:37
     * @param: setmeal
     **/
    @AutoFill(value = OperationType.UPDATE)
    void updateSetmeal(Setmeal setmeal);

    /**
     * @description: 新增套餐
     * @author: CyberAstra
     * @date: 2025/5/24 at 18:18:18
     * @param: setmeal
     **/
    @AutoFill(value = OperationType.INSERT)
    void saveSetmeal(Setmeal setmeal);

    /**
     * @description: 根据id查询套餐
     * @author: CyberAstra
     * @date: 2025/5/24 at 20:55:50
     * @param: id
     * @return: com.sky.entity.Setmeal
     **/
    @Select("select * from setmeal where id = #{id}")
    Setmeal getById(Long id);

    /**
     * @description: 套餐起售、停售
     * @author: CyberAstra
     * @date: 2025/5/25 at 14:30:50
     * @param: status
     * @param: id
     **/
    @AutoFill(value = OperationType.UPDATE)
    @Update("update setmeal set update_time = #{updateTime},update_user = #{updateUser},status = #{status} " +
            "where id = #{id}")
    void changeStatus(Setmeal setmeal);

    /**
     * @description: 批量删除套餐
     * @author: CyberAstra
     * @date: 2025/5/25 at 16:46:20
     * @param: ids
     **/
    void delete(List<Long> ids);

    /**
     * @description: 根据分类id查询套餐
     * @author: CyberAstra
     * @date: 2025/6/21 at 17:41:42
     * @param: categoryId
     * @return: java.util.List<com.sky.entity.Setmeal>
     **/
    @Select("select * from setmeal where category_id = #{categoryId}")
    List<Setmeal> getByCategoryId(int categoryId);

    /**
     * @description: 根据套餐id查询包含的菜品
     * @author: CyberAstra
     * @date: 2025/6/21 at 20:38:40
     * @param: id
     * @return: java.util.List<com.sky.vo.DishItemVO>
     **/
    @Select("select dish.name as name,dish.description as description,dish.image as image," +
            "setmeal_dish.copies as copies from setmeal_dish,dish where setmeal_id = #{id} and dish_id = dish.id")
    List<DishItemVO> getDishById(Integer id);
}
