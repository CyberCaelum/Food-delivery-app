package com.sky.mapper;

import com.sky.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * @ClassName : UserMapper
 * @Description : C端用户Mapper层
 * @Author :  CyberCaelum
 * @Date: 2025-06-20 20:40
 */
@Mapper
public interface UserMapper {

    /**
     * @description: 通过openid查找用户
     * @author: CyberAstra
     * @date: 2025/6/20 at 20:43:46
     * @param: openid
     * @return: com.sky.entity.User
     **/
    @Select("select * from user where openid = #{openid}")
    User getByOpenid(String openid);

    /**
     * @description: 插入用户信息
     * @author: CyberAstra
     * @date: 2025/6/20 at 20:45:14
     * @param: user
     **/
    void insert(User user);

    /**
     * @description: 根据主键查找用户
     * @author: CyberAstra
     * @date: 2025/7/12 at 09:21:58
     * @param: userId
     * @return: com.sky.entity.User
     **/
    @Select(("select * from user where id = #{id}"))
    User getById(Long userId);
}
