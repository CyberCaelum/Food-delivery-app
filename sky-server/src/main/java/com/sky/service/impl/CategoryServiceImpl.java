package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.constant.StatusConstant;
import com.sky.context.BaseContext;
import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.exception.DeletionNotAllowedException;
import com.sky.mapper.CategoryMapper;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetmealMapper;
import com.sky.result.PageResult;
import com.sky.service.CategoryService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @ClassName : CategoryServiceImpl
 * @Description : 分类数据service
 * @Author :  CyberCaelum
 * @Date: 2025-05-16 16:24
 */
@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private DishMapper dishMapper;

    @Autowired
    private SetmealMapper setmealMapper;

    /**
     * @description: 分类分页查询
     * @author: CyberAstra
     * @date: 2025/5/16 at 17:00:23
     * @param: categoryPageQueryDTO
     * @return: com.sky.result.PageResult
     **/
    @Override
    public PageResult pageQuery(CategoryPageQueryDTO categoryPageQueryDTO) {
        PageHelper.startPage(categoryPageQueryDTO.getPage(),categoryPageQueryDTO.getPageSize());
        Page<Category> page = categoryMapper.pageQuery(categoryPageQueryDTO);

        return new PageResult(page.getTotal(),page.getResult());
    }

    /**
     * @description: 新增分类
     * @author: CyberAstra
     * @date: 2025/5/16 at 17:15:07
     * @param: categorydto
     **/
    @Override
    public void saveCategory(@RequestBody CategoryDTO categorydto) {
        //检查是否已存在
        if(categoryMapper.selectCategory(categorydto.getName()) != null){
            throw new DeletionNotAllowedException(MessageConstant.ACCOUNT_EXISTS);
        }
        Category category = new Category();
        //属性拷贝
        BeanUtils.copyProperties(categorydto,category);
        //设置创建和修改操作的时间和操作人
        category.setCreateTime(LocalDateTime.now());
        category.setUpdateTime(LocalDateTime.now());
        category.setCreateUser(BaseContext.getCurrentId());
        category.setUpdateUser(BaseContext.getCurrentId());
        //设置分类状态
        category.setStatus(StatusConstant.DISABLE);
        categoryMapper.saveCategory(category);
    }

    /**
     * @description: 修改分类
     * @author: CyberAstra
     * @date: 2025/5/16 at 19:26:34
     * @param: categorydto
     **/
    @Override
    public void updateCategory(CategoryDTO categorydto) {
        Category category = new Category();
        //属性拷贝
        BeanUtils.copyProperties(categorydto,category);
        //设置修改时间和操作人
        category.setUpdateTime(LocalDateTime.now());
        category.setUpdateUser(BaseContext.getCurrentId());
        categoryMapper.updateCategory(category);
    }

    /**
     * @description: 启用，禁用分类
     * @author: CyberAstra
     * @date: 2025/5/16 at 19:45:32
     * @param: status
     * @param: id
     **/
    @Override
    public void updateCategoryStatus(Integer status, Long id) {
        Category category = new Category();
        category.setId(id);
        category.setStatus(status);
        category.setUpdateTime(LocalDateTime.now());
        category.setUpdateUser(BaseContext.getCurrentId());
        categoryMapper.updateCategory(category);
    }

    /**
     * @description: 根据id删除分类
     * @author: CyberAstra
     * @date: 2025/5/16 at 19:56:46
     * @param: id
     **/
    @Override
    public void deleteCategory(Long id) {
        //查询当前分类是否关联了菜品，如果关联了就抛出业务异常
        Integer count = dishMapper.countByCategoryId(id);
        if(count > 0){
            //当前分类下有菜品，不能删除
            throw new DeletionNotAllowedException(MessageConstant.CATEGORY_BE_RELATED_BY_DISH);
        }

        //查询当前分类是否关联了套餐，如果关联了就抛出业务异常
        count = setmealMapper.countByCategoryId(id);
        if(count > 0){
            //当前分类下有菜品，不能删除
            throw new DeletionNotAllowedException(MessageConstant.CATEGORY_BE_RELATED_BY_SETMEAL);
        }

        //删除分类数据
        categoryMapper.deleteCategory(id);
    }

    /**
     * @description: 根据类型查询分类
     * @author: CyberAstra
     * @date: 2025/5/16 at 20:38:07
     * @param: type
     * @return: java.util.List<com.sky.entity.Category>
     **/
    @Override
    public List<Category> list(Integer type) {
        return categoryMapper.list(type);
    }
}
