package com.sky.controller.admin;

import com.github.pagehelper.Page;
import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Setmeal;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.SetmealService;
import com.sky.vo.SetmealVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ClassName : SetmealController
 * @Description : 套餐接口
 * @Author :  CyberCaelum
 * @Date: 2025-05-23 19:27
 */
@Api(tags = "套餐接口")
@RestController
@RequestMapping("/api/setmeal")
@Slf4j
public class SetmealController {

    @Autowired
    private SetmealService setmealService;

    /**
     * @description: 套餐分页查询
     * @author: CyberAstra
     * @date: 2025/5/24 at 15:20:54
     * @param: setmealPageQueryDTO
     * @return: com.sky.result.Result
     **/
    @ApiOperation("套餐分页查询")
    @GetMapping("/page")
    public Result pageQuery(SetmealPageQueryDTO setmealPageQueryDTO){
        log.info("套餐分页查询:{}",setmealPageQueryDTO);
        PageResult pageResult  = setmealService.pageQuery(setmealPageQueryDTO);
        return Result.success(pageResult);
    }

    /**
     * @description: 修改套餐
     * @author: CyberAstra
     * @date: 2025/5/24 at 15:23:12
     * @return: com.sky.result.Result
     **/
    @ApiOperation("修改套餐")
    @PutMapping()
    public Result updateSetmeal(@RequestBody SetmealDTO setmealDTO){
        log.info("修改套餐:{}",setmealDTO);
        setmealService.updateSetmeal(setmealDTO);
        return Result.success();
    }

    /**
     * @description: 新增套餐
     * @author: CyberAstra
     * @date: 2025/5/24 at 18:15:14
     * @param: setmealDTO
     * @return: com.sky.result.Result
     **/
    @ApiOperation("新增套餐")
    @PostMapping
    public Result saveSetmeal(@RequestBody SetmealDTO setmealDTO){
        log.info("新增套餐:{}",setmealDTO);
        setmealService.saveSetmeal(setmealDTO);
        return Result.success();
    }

    /**
     * @description: 根据id查询套餐
     * @author: CyberAstra
     * @date: 2025/5/24 at 20:52:53
     * @param: id
     * @return: com.sky.result.Result
     **/
    @ApiOperation("根据id查询套餐")
    @GetMapping("/{id}")
    public Result getSetmealById(@PathVariable Long id){
        log.info("根据id查询套餐:{}",id);
        SetmealVO setmealVO = setmealService.getSetmealById(id);
        return Result.success(setmealVO);
    }

    /**
     * @description: 套餐起售、停售
     * @author: CyberAstra
     * @date: 2025/5/25 at 14:27:07
     * @param: status
     * @return: com.sky.result.Result
     **/
    @ApiOperation("套餐起售、停售")
    @PostMapping("/status/{status}")
    public Result changeStatus(@PathVariable Integer status,Long id){
        log.info("套餐起售、停售:status{},id{}",status,id);
        setmealService.changeStatus(status,id);
        return Result.success();
    }

    /**
     * @description: 批量删除套餐
     * @author: CyberAstra
     * @date: 2025/5/25 at 16:40:50
     * @param: ids
     * @return: com.sky.result.Result
     **/
    @ApiOperation("批量删除套餐")
    @DeleteMapping
    public Result deleteSetmeal(@RequestParam List<Long> ids){
        log.info("批量删除套餐");
        setmealService.deleteSetmeal(ids);
        return Result.success();
    }
}
