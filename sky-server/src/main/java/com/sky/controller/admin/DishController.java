package com.sky.controller.admin;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

/**
 * @ClassName : DishController
 * @Description : 菜品相关接口
 * @Author :  CyberCaelum
 * @Date: 2025-05-19 14:47
 */
@RestController
@RequestMapping("/admin/dish")
@Slf4j
@Api(tags = "菜品相关接口")
public class DishController {

    @Autowired
    private DishService dishService;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * @description: 清除缓存数据
     * @author: CyberAstra
     * @date: 2025/7/9 at 07:33:26
     * @param: patten
     **/
    private void cleanCache(String patten){
        Set key = redisTemplate.keys(patten);
        redisTemplate.delete(key);
    }

    /**
     * @description: 新增菜品
     * @author: CyberAstra
     * @date: 2025/5/19 at 14:52:44
     * @param: dishDTO
     * @return: com.sky.result.Result
     **/
    @ApiOperation("新增菜品")
    @PostMapping
    public Result save(@RequestBody DishDTO dishDTO){
        log.info("新增菜品:{}",dishDTO);
        cleanCache("dish_"+dishDTO.getCategoryId());
        dishService.saveWithFlavor(dishDTO);
        return Result.success();
    }

    /**
     * @description: 菜品分页查询
     * @author: CyberAstra
     * @date: 2025/5/19 at 16:28:12
     * @param: dishPageQueryDTO
     * @return: com.sky.result.Result
     **/
    @ApiOperation("菜品分页查询")
    @GetMapping("/page")
    public Result pageQuery(DishPageQueryDTO dishPageQueryDTO){
        log.info("菜品分页查询:{}",dishPageQueryDTO);
        PageResult pageResult = dishService.pageQuery(dishPageQueryDTO);
        return Result.success(pageResult);
    }

    /**
     * @description: 批量删除菜品
     * @author: CyberAstra
     * @date: 2025/5/19 at 20:36:05
     * @param: ids
     * @return: com.sky.result.Result
     **/
    @ApiOperation("批量删除菜品")
    @DeleteMapping
    public Result deleteDish(@RequestParam List<Long> ids){
        log.info("批量删除菜品:{}",ids);
        cleanCache("dish_*");
        dishService.deleteDish(ids);
        return Result.success();
    }

    /**
     * @description: 根据id查询菜品
     * @author: CyberAstra
     * @date: 2025/5/24 at 16:19:25
     * @param: id
     * @return: com.sky.result.Result
     **/
    @ApiOperation("根据id查询菜品")
    @GetMapping("/{id}")
    public Result getDishById(@PathVariable Long id){
        log.info("根据id查询菜品:{}",id);
        DishVO dishVO = dishService.getDishById(id);
        return Result.success(dishVO);
    }

    /**
     * @description: 根据分类id查询菜品
     * @author: CyberAstra
     * @date: 2025/5/24 at 16:21:17
     * @param: categoryId
     * @return: com.sky.result.Result
     **/
    @ApiOperation("根据分类id查询菜品")
    @GetMapping("/list")
    public Result getDishByCategoryId(Long categoryId){
        log.info("根据分类id查询菜品:{}",categoryId);
        List<Dish> dishes = dishService.getDishByCategoryId(categoryId);
        return Result.success(dishes);
    }

    /**
     * @description: 菜品起售、停售
     * @author: CyberAstra
     * @date: 2025/5/24 at 16:47:43
     * @param: status
     * @param: id
     * @return: com.sky.result.Result
     **/
    @ApiOperation("菜品起售、停售")
    @PostMapping("/status/{status}")
    public Result changeStatus(@PathVariable Integer status,Long id){
        log.info("菜品起售、停售");
        cleanCache("dish_*");
        dishService.changeStatus(status,id);
        return Result.success();
    }

    /**
     * @description: 修改菜品
     * @author: CyberAstra
     * @date: 2025/5/24 at 16:50:01
     * @param: dishDTO
     * @return: com.sky.result.Result
     **/
    @ApiOperation("修改菜品")
    @PutMapping
    public Result updateDish(@RequestBody DishDTO dishDTO){
        log.info("修改菜品");
        cleanCache("dish_*");
        dishService.updateDish(dishDTO);
        return Result.success();
    }
}
