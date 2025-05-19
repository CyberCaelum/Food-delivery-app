package com.sky.controller.admin;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.DishService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
        dishService.deleteDish(ids);
        return Result.success();
    }
}
