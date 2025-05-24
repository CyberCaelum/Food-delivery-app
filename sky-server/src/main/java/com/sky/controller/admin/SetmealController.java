package com.sky.controller.admin;

import com.github.pagehelper.Page;
import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Setmeal;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.SetmealService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @ClassName : SetmealController
 * @Description : 套餐接口
 * @Author :  CyberCaelum
 * @Date: 2025-05-23 19:27
 */
@Api(tags = "套餐接口")
@RestController
@RequestMapping("/admin/setmeal")
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
}
