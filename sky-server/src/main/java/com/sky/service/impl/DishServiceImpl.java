package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.annotation.AutoFill;
import com.sky.constant.MessageConstant;
import com.sky.constant.StatusConstant;
import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.entity.DishFlavor;
import com.sky.enumeration.OperationType;
import com.sky.exception.DeletionNotAllowedException;
import com.sky.mapper.DishFlavorMapper;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetmealDishMapper;
import com.sky.result.PageResult;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @ClassName : DishServiceImpl
 * @Description : 菜品操作类
 * @Author :  CyberCaelum
 * @Date: 2025-05-19 14:49
 */
@Service
@Slf4j
public class DishServiceImpl implements DishService {

    @Autowired
    private DishFlavorMapper dishFlavorMapper;

    @Autowired
    private DishMapper dishMapper;

    @Autowired
    private SetmealDishMapper setmealDishMapper;

    /**
     * @description: 新增菜品和菜品的口味
     * @author: CyberAstra
     * @date: 2025/5/19 at 14:58:31
     * @param: dishDTO
     **/
    @Transactional
    @AutoFill(value = OperationType.INSERT)
    @Override
    public void saveWithFlavor(DishDTO dishDTO) {
        Dish dish = new Dish();
        BeanUtils.copyProperties(dishDTO, dish);
        dishMapper.save(dish);

        List<DishFlavor> flavors = dishDTO.getFlavors();
        if (flavors != null && !flavors.isEmpty()) {
            flavors.forEach(flavor -> {
                flavor.setDishId(dish.getId());
            });
            dishFlavorMapper.save(flavors);
        }
    }

    /**
     * @description: 菜品分页查询
     * @author: CyberAstra
     * @date: 2025/5/19 at 16:30:48
     * @param: dishPageQueryDTO
     * @return: com.sky.result.PageResult
     **/
    @Override
    public PageResult pageQuery(DishPageQueryDTO dishPageQueryDTO) {
        PageHelper.startPage(dishPageQueryDTO.getPage(),dishPageQueryDTO.getPageSize());
        Page<DishVO> page = dishMapper.pageQuery(dishPageQueryDTO);
        return new PageResult(page.getTotal(),page.getResult());
    }
    
    /**
     * @description: 批量删除菜品
     * @author: CyberAstra
     * @date: 2025/5/19 at 20:39:50
     * @param: ids
     **/
    @Override
    public void deleteDish(List<Long> ids) {
        for (Long id : ids) {
            Dish dish = dishMapper.getById(id);
            //判断当前菜品是否在售，在售抛出异常
            if (dish.getStatus() == StatusConstant.ENABLE){
                throw new DeletionNotAllowedException(MessageConstant.DISH_ON_SALE);
            }
        }
        //判断当前菜品是否关联了套餐，若关联抛出异常
        List<Long> setmealIds = setmealDishMapper.getSetmealIdByDishIds(ids);
        if (setmealIds != null && !setmealIds.isEmpty()) {
            throw new DeletionNotAllowedException(MessageConstant.DISH_BE_RELATED_BY_SETMEAL);
        }

        for (Long id : ids) {
            dishMapper.deleteById(id);
            dishFlavorMapper.deleteByDishId(id);
        }
    }

    /**
     * @description: 根据id查询菜品
     * @author: CyberAstra
     * @date: 2025/5/24 at 16:05:57
     * @param: id
     * @return: com.sky.vo.DishVO
     **/
    @Override
    public DishVO getDishById(Long id) {
        Dish dish = dishMapper.getById(id);
        DishVO dishVO = new DishVO();
        BeanUtils.copyProperties(dish, dishVO);
        List<DishFlavor> dishFlavors =  dishFlavorMapper.getFlavorByDishId(id);
        dishVO.setFlavors(dishFlavors);
        return dishVO;
    }

    /**
     * @description: 根据分类id查找菜品
     * @author: CyberAstra
     * @date: 2025/5/24 at 16:24:15
     * @param: categoryId
     * @return: java.util.List<com.sky.entity.Dish>
     **/
    @Override
    public List<Dish> getDishByCategoryId(Long categoryId) {
        List<Dish> dishes = dishMapper.getByCategoryId(categoryId);
        return dishes;
    }

    /**
     * @description: 菜品起售、停售
     * @author: CyberAstra
     * @date: 2025/5/24 at 16:46:31
     * @param: status
     * @param: id
     **/
    @Override
    public void changeStatus(Integer status, Long id) {
        Dish dish = dishMapper.getById(id);
        dish.setStatus(status);
        dishMapper.update(dish);
    }

    /**
     * @description: 修改菜品
     * @author: CyberAstra
     * @date: 2025/5/24 at 16:50:34
     * @param: dishDTO
     **/
    @Transactional
    @AutoFill(value = OperationType.UPDATE)
    @Override
    public void updateDish(DishDTO dishDTO) {
        Dish dish = new Dish();
        BeanUtils.copyProperties(dishDTO, dish);
        dishMapper.update(dish);
        Long dishId = dish.getId();
        List<DishFlavor> flavors = dishDTO.getFlavors();
        //TODO 感觉可以将口味表中这个菜的口味全部删除，之后重新存入
        //如果口味表中有菜品相关的口味，更新口味
        if (dishFlavorMapper.getFlavorByDishId(dishId) != null && !dishFlavorMapper.getFlavorByDishId(dishId).isEmpty()){
            //如果口味不为空，增加菜品的口味
            if (flavors != null && !flavors.isEmpty()) {
                flavors.forEach(flavor -> {
                    flavor.setDishId(dishId);
                    dishFlavorMapper.update(flavor);
                });
            }
            //如果口味为空，删除这个菜品相关的口味
            else {
                dishFlavorMapper.deleteByDishId(dishId);
            }
        }
        //如果口味表中没有相关的口味，增加菜品的口味
        else {
            if (flavors != null && !flavors.isEmpty()) {
                flavors.forEach(flavor -> {
                    flavor.setDishId(dishId);
                });
                dishFlavorMapper.save(flavors);
            }
        }
    }
}
