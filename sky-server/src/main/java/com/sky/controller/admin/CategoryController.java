package com.sky.controller.admin;

import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ClassName : CategoryController
 * @Description : 分类相关接口
 * @Author :  CyberCaelum
 * @Date: 2025-05-16 16:07
 */

@RestController
@RequestMapping("/api/category")
@Slf4j
@Api(tags = "分类相关接口")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    /**
     * @description: 分类分页查询
     * @author: CyberAstra
     * @date: 2025/5/16 at 16:59:43
     * @param: categoryPageQueryDTO
     * @return: com.sky.result.Result
     **/
    @ApiOperation("分类分页查询")
    @GetMapping("/page")
    public Result pageQuery(CategoryPageQueryDTO categoryPageQueryDTO){
        log.info("分页查询categoryPageQueryDTO:{}",categoryPageQueryDTO);
        PageResult pageResult = categoryService.pageQuery(categoryPageQueryDTO);
        return Result.success(pageResult);
    }

    /**
     * @description: 新增分类
     * @author: CyberAstra
     * @date: 2025/5/16 at 17:15:35
     * @param: categorydto
     * @return: com.sky.result.Result
     **/
    @ApiOperation("新增分类")
    @PostMapping
    public Result saveCategory(@RequestBody CategoryDTO categorydto){
        log.info("新增分类categorydto:{}",categorydto);
        categoryService.saveCategory(categorydto);
        return Result.success();
    }

    /**
     * @description: 修改分类
     * @author: CyberAstra
     * @date: 2025/5/16 at 19:24:29
     * @param: categorydto
     * @return: com.sky.result.Result
     **/
    @ApiOperation("修改分类")
    @PutMapping
    public Result updateCategory(@RequestBody CategoryDTO categorydto){
        log.info("修改分类categorydto:{}",categorydto);
        categoryService.updateCategory(categorydto);
        return Result.success();
    }

    /**
     * @description: 启用，禁用分类
     * @author: CyberAstra
     * @date: 2025/5/16 at 19:43:17
     * @param: status
     * @param: id
     * @return: com.sky.result.Result
     **/
    @ApiOperation("启用，禁用分类")
    @PostMapping("/status/{status}")
    public Result updateCategoryStatus(@PathVariable Integer status,Long id){
        log.info("启用，禁用分类:status:{},id:{}",status,id);
        categoryService.updateCategoryStatus(status,id);
        return Result.success();
    }

    /**
     * @description: 根据id删除分类
     * @author: CyberAstra
     * @date: 2025/5/16 at 19:54:13
     * @param: id
     * @return: com.sky.result.Result
     **/
    @ApiOperation("根据id删除分类")
    @DeleteMapping
    public Result deleteCategory(Long id){
        log.info("根据id删除分类：id:{}",id);
        categoryService.deleteCategory(id);
        return Result.success();
    }

    @ApiOperation("根据类型查询分类")
    @GetMapping("/list")
    public Result<List<Category>> list(Integer type){
        List<Category> list = categoryService.list(type);
        return Result.success(list);
    }
}
