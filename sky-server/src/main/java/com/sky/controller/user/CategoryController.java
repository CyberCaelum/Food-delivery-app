package com.sky.controller.user;

import com.sky.entity.Category;
import com.sky.result.Result;
import com.sky.service.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @ClassName : CategoryController
 * @Description : C端用户分类接口
 * @Author :  CyberCaelum
 * @Date: 2025-06-21 17:16
 */
@Slf4j
@Api(tags = "C端用户分类接口")
@RestController
@RequestMapping("/user/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    /**
     * @description: 根据类型查询分类
     * @author: CyberAstra
     * @date: 2025/6/21 at 17:26:45
     * @param: type
     * @return: com.sky.result.Result
     **/
    @ApiOperation("查询分类")
    @GetMapping("/list")
    public Result getCategory(Integer type) {
        log.info("查询分类type:{}",type);
        List<Category> list = categoryService.list(type);
        return Result.success(list);
    }
}
