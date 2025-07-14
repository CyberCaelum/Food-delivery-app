package com.sky.mapper;

import com.sky.entity.ShoppingCart;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @ClassName : ShoppingCartMapper
 * @Description : 购物车Mapper
 * @Author :  CyberCaelum
 * @Date: 2025-07-09 15:30
 */

@Mapper
public interface ShoppingCartMapper {

    /**
     * @description: 查询购物车
     * @author: CyberAstra
     * @date: 2025/7/9 at 16:21:45
     * @param: shoppingCart
     * @return: java.util.List<com.sky.entity.ShoppingCart>
     **/
    List<ShoppingCart> list(ShoppingCart shoppingCart);

    /**
     * @description: 更新购物车中商品的数量
     * @author: CyberAstra
     * @date: 2025/7/9 at 16:21:58
     * @param: cart
     **/
    @Update("update shopping_cart set number = #{number} where id = #{id}")
    void updateNumber(ShoppingCart cart);

    /**
     * @description: 插入购物车数据
     * @author: CyberAstra
     * @date: 2025/7/9 at 16:22:32
     * @param: shoppingCart
     **/
    @Insert("insert into shopping_cart (name, image, user_id, dish_id, setmeal_id, dish_flavor, number, amount, create_time) " +
            "VALUE (#{name},#{image},#{userId},#{dishId},#{setmealId},#{dishFlavor},#{number},#{amount},#{createTime})")
    void add(ShoppingCart shoppingCart);

    /**
     * @description: 清空购物车
     * @author: CyberAstra
     * @date: 2025/7/9 at 16:47:36
     * @param: userId
     **/
    @Delete("delete from shopping_cart where user_id = #{userId}")
    void deleteByUserId(Long userId);

    /**
     * @description: 删除购物车中的一个商品
     * @author: CyberAstra
     * @date: 2025/7/10 at 06:51:46
     * @param: shoppingCartDTO
     **/
    void sub(ShoppingCart shoppingCart);

    /**
     * @description: 批量插入购物车
     * @author: CyberAstra
     * @date: 2025/7/12 at 15:58:41
     * @param: carts
     **/
    void addList(List<ShoppingCart> carts);
}
