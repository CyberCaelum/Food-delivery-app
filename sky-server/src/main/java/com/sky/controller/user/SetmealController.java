package com.sky.controller.user;

import com.sky.entity.Setmeal;
import com.sky.result.Result;
import com.sky.service.SetmealService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @ClassName : SetmealController
 * @Description : C端用户套餐相关接口
 * @Author :  CyberCaelum
 * @Date: 2025-06-21 17:29
 */
@Slf4j
@Api(tags = "C端用户套餐接口")
@RestController
@RequestMapping("/user/setmeal")
public class SetmealController {

    @Autowired
    private SetmealService setmealService;

    /**
     * @description: 根据费雷id查询套餐
     * @author: CyberAstra
     * @date: 2025/6/21 at 17:42:47
     * @param: categoryId
     * @return: com.sky.result.Result
     **/
    @GetMapping("/list")
    @ApiOperation("根据分类id查询套餐")
    public Result getByCategoryId(int categoryId) {
        List<Setmeal> list = setmealService.getByCategoryId(categoryId);
        return Result.success(list);
    }
}
