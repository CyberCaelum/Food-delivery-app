package com.sky.service;

import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.result.PageResult;

import java.util.List;

/**
 * @ClassName : CategoryService
 * @Description : 分类数据
 * @Author :  CyberCaelum
 * @Date: 2025-05-16 16:25
 */
public interface CategoryService {

    /**
     * @description: 分页分类查询
     * @author: CyberAstra
     * @date: 2025/5/16 at 17:00:42
     * @param: categoryPageQueryDTO
     * @return: com.sky.result.PageResult
     **/
    PageResult pageQuery(CategoryPageQueryDTO categoryPageQueryDTO);

    /**
     * @description: 新增分类
     * @author: CyberAstra
     * @date: 2025/5/16 at 17:14:37
     * @param: categorydto
     **/
    void saveCategory(CategoryDTO categorydto);

    /**
     * @description: 修改分类
     * @author: CyberAstra
     * @date: 2025/5/16 at 19:26:11
     * @param: categorydto
     **/
    void updateCategory(CategoryDTO categorydto);

    /**
     * @description: 启用，禁用分类
     * @author: CyberAstra
     * @date: 2025/5/16 at 19:45:09
     * @param: status
     * @param: id
     **/
    void updateCategoryStatus(Integer status, Long id);

    /**
     * @description: 根据id删除分类
     * @author: CyberAstra
     * @date: 2025/5/16 at 19:56:18
     * @param: id
     **/
    void deleteCategory(Long id);

    /**
     * @description: 根据类型查询分类
     * @author: CyberAstra
     * @date: 2025/5/16 at 20:37:44
     * @param: type
     * @return: java.util.List<com.sky.entity.Category>
     **/
    List<Category> list(Integer type);
}
