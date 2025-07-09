package com.sky.controller.user;

import com.sky.result.Result;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
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
@RestController("UserDishController")
@RequestMapping("/user/dish")
@Api(tags = "C端菜品浏览接口")
public class DishController {

    @Autowired
    private DishService dishService;

    @Autowired
    private RedisTemplate redisTemplate;

    @GetMapping("/list")
    @ApiOperation("根据分类id查询菜品")
    public Result getById(Long categoryId){
        //构造key
        String key = "dish_"+categoryId;
        //在redis中查询
        List<DishVO> list = (List<DishVO>) redisTemplate.opsForValue().get(key);
        //不为空直接返回
        if(list != null && !list.isEmpty()){
            return Result.success(list);
        }
        //为空查询数据库
        list = dishService.getDishByCategory(categoryId);
        //之后放入redis
        redisTemplate.opsForValue().set(key,list);
        return Result.success(list);
    }
}
