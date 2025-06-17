package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.annotation.AutoFill;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.enumeration.OperationType;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @ClassName : CategoryMapper.xml
 * @Description : 分类数据的Mapping
 * @Author :  CyberCaelum
 * @Date: 2025-05-16 16:21
 */

@Mapper
public interface CategoryMapper {

    /**
     * @description: 分页分类查询
     * @author: CyberAstra
     * @date: 2025/5/16 at 17:00:02
     * @param: categoryPageQueryDTO
     * @return: com.github.pagehelper.Page<com.sky.entity.Category>
     **/
    Page<Category> pageQuery(CategoryPageQueryDTO categoryPageQueryDTO);

    /**
     * @description: 新增分类
     * @author: CyberAstra
     * @date: 2025/5/16 at 17:14:04
     * @param: category
     **/
    @Insert("insert into category (type, name, sort, status) VALUE " +
            "(#{type},#{name},#{sort},#{status})")
    void saveCategory(Category category);

    /**
     * @description: 修改分类
     * @author: CyberAstra
     * @date: 2025/5/16 at 19:35:54
     * @param: category
     **/
    void updateCategory(Category category);

    /**
     * @description: 根据id删除分类
     * @author: CyberAstra
     * @date: 2025/5/16 at 19:59:33
     * @param: id
     **/
    @Delete("delete from category where id = #{id}")
    void deleteCategory(Long id);

    /**
     * @description: 通过分类名查找分类
     * @author: CyberAstra
     * @date: 2025/6/17 at 16:04:54
     * @param: name
     * @return: com.sky.entity.Category
     **/
    @Select("SELECT * from category where name = #{name}")
    Category selectCategory(String name);

    /**
     * @description: 通过类型查找分类
     * @author: CyberAstra
     * @date: 2025/6/17 at 16:05:51
     * @param: type
     * @return: java.util.List<com.sky.entity.Category>
     **/
    List<Category> list(Integer type);
}
