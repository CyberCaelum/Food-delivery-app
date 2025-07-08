package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Setmeal;
import com.sky.entity.SetmealDish;
import com.sky.mapper.SetmealDishMapper;
import com.sky.mapper.SetmealMapper;
import com.sky.result.PageResult;
import com.sky.service.SetmealService;
import com.sky.vo.SetmealVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @ClassName : SetmealServiceImpl
 * @Description : 套餐service
 * @Author :  CyberCaelum
 * @Date: 2025-05-23 19:35
 */
@Service
public class SetmealServiceImpl implements SetmealService {

    @Autowired
    private SetmealMapper setmealMapper;

    @Autowired
    private SetmealDishMapper setmealDishMapper;

    /**
     * @description: 套餐分页查询
     * @author: CyberAstra
     * @date: 2025/5/23 at 19:41:14
     * @param: setmealPageQueryDTO
     * @return: com.sky.result.PageResult
     **/
    @Override
    public PageResult pageQuery(SetmealPageQueryDTO setmealPageQueryDTO) {
        PageHelper.startPage(setmealPageQueryDTO.getPage(),setmealPageQueryDTO.getPageSize());
        Page<SetmealVO> page = setmealMapper.PageQuery(setmealPageQueryDTO);
        return new PageResult(page.getTotal(),page.getResult());
    }

    /**
     * @description: 修改套餐
     * @author: CyberAstra
     * @date: 2025/5/24 at 15:25:24
     * @param: setmealDTO
     **/
    @Transactional
    @Override
    public void updateSetmeal(SetmealDTO setmealDTO) {
        Setmeal setmeal = new Setmeal();
        BeanUtils.copyProperties(setmealDTO,setmeal);
        setmealMapper.updateSetmeal(setmeal);
        List<SetmealDish> setmealDishes = setmealDTO.getSetmealDishes();
        Long setmealId = setmealDTO.getId();
        //将套餐中的菜品全部删除
        setmealDishMapper.deletBySetmealId(setmealId);
        //如果套餐中有菜品，将菜品全部存储
        if (setmealDishes != null && !setmealDishes.isEmpty()) {
            setmealDishes.forEach(setmealDish -> {
                setmealDish.setSetmealId(setmealId);
                setmealDishMapper.saveSetmealDish(setmealDish);
            });
        }
    }

    /**
     * @description: 新增套餐
     * @author: CyberAstra
     * @date: 2025/5/24 at 18:16:48
     * @param: setmealDTO
     **/
    @Transactional
    @Override
    public void saveSetmeal(SetmealDTO setmealDTO) {
        Setmeal setmeal = new Setmeal();
        BeanUtils.copyProperties(setmealDTO,setmeal);
        setmealMapper.saveSetmeal(setmeal);
        List<SetmealDish> setmealDishes = setmealDTO.getSetmealDishes();
        if (setmealDishes != null && !setmealDishes.isEmpty()) {
            setmealDishes.forEach(setmealDish -> {
                setmealDish.setSetmealId(setmeal.getId());
                setmealDishMapper.saveSetmealDish(setmealDish);
            });
        }
    }

    /**
     * @description: 根据id查询套餐
     * @author: CyberAstra
     * @date: 2025/5/24 at 20:54:38
     * @param: id
     * @return: com.sky.vo.SetmealVO
     **/
    @Override
    public SetmealVO getSetmealById(Long id) {
        Setmeal setmeal = setmealMapper.getById(id);
        List<SetmealDish> setmealDishes = setmealDishMapper.getSetmealDishBySetmealId(id);
        SetmealVO setmealVO = new SetmealVO();
        BeanUtils.copyProperties(setmeal,setmealVO);
        setmealVO.setSetmealDishes(setmealDishes);
        return setmealVO;
    }

    /**
     * @description: 套餐起售、停售
     * @author: CyberAstra
     * @date: 2025/5/25 at 14:29:31
     * @param: status
     * @param: id
     **/
    @Override
    public void changeStatus(Integer status, Long id) {
        Setmeal setmeal = Setmeal.builder()
                        .id(id)
                        .status(status)
                        .build();
        setmealMapper.changeStatus(setmeal);
    }

    /**
     * @description: 批量删除套餐
     * @author: CyberAstra
     * @date: 2025/5/25 at 16:43:08
     * @param: ids
     **/
    @Transactional
    @Override
    public void deleteSetmeal(List<Long> ids) {
        //删除套餐表中的信息
        setmealMapper.delete(ids);
        //删除套餐菜品表中的信息
        ids.forEach(id->{
            setmealDishMapper.deletBySetmealId(id);
        });
    }
}
