package com.sky.service;

import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.vo.SetmealVO;

import java.util.List;

/**
 * @ClassName : SetmealService
 * @Description : 套餐Service
 * @Author :  CyberCaelum
 * @Date: 2025-05-23 19:36
 */
public interface SetmealService {
    /**
     * @description: 套餐分页查询
     * @author: CyberAstra
     * @date: 2025/5/23 at 20:00:19
     * @param: setmealPageQueryDTO
     * @return: com.sky.result.PageResult
     **/
    PageResult pageQuery(SetmealPageQueryDTO setmealPageQueryDTO);

    /**
     * @description: 修改套餐
     * @author: CyberAstra
     * @date: 2025/5/24 at 15:24:59
     * @param: setmealDTO
     **/
    void updateSetmeal(SetmealDTO setmealDTO);

    /**
     * @description: 新增套餐
     * @author: CyberAstra
     * @date: 2025/5/24 at 18:16:31
     * @param: setmealDTO
     **/
    void saveSetmeal(SetmealDTO setmealDTO);

    /**
     * @description: 根据id查询套餐
     * @author: CyberAstra
     * @date: 2025/5/24 at 20:54:20
     * @param: id
     * @return: com.sky.vo.SetmealVO
     **/
    SetmealVO getSetmealById(Long id);

    /**
     * @description: 套餐起售、停售
     * @author: CyberAstra
     * @date: 2025/5/25 at 14:29:17
     * @param: status
     * @param: id
     **/
    void changeStatus(Integer status, Long id);

    /**
     * @description: 批量删除套餐
     * @author: CyberAstra
     * @date: 2025/5/25 at 16:42:55
     * @param: ids
     **/
    void deleteSetmeal(List<Long> ids);
}
