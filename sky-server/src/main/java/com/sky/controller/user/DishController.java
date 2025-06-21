package com.sky.controller.user;

import com.sky.result.Result;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @ClassName : DishController
 * @Description : C端菜品浏览接口
 * @Author :  CyberCaelum
 * @Date: 2025-06-21 20:49
 */
@Slf4j
@RestController
@RequestMapping("/user/dish")
@Api(tags = "C端菜品浏览接口")
public class DishController {

    @Autowired
    private DishService dishService;

    @GetMapping("/list")
    @ApiOperation("根据分类id查询菜品")
    public Result getById(Long categoryId){
        List<DishVO> list = dishService.getDishByCategory(categoryId);
        return Result.success(list);
    }
}
