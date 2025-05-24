package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.annotation.AutoFill;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Setmeal;
import com.sky.enumeration.OperationType;
import com.sky.vo.SetmealVO;
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
}
