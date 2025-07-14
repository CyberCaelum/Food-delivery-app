package com.sky.service;

import com.sky.dto.ShoppingCartDTO;
import com.sky.entity.ShoppingCart;

import java.util.List;

/**
 * @ClassName : ShoppingCartService
 * @Description : C端购物车service
 * @Author :  CyberCaelum
 * @Date: 2025-07-09 15:27
 */
public interface ShoppingCartService {

    /**
     * @description: 添加购物车
     * @author: CyberAstra
     * @date: 2025/7/9 at 16:21:13
     * @param: shoppingCartDTO
     **/
    void addShoppingCart(ShoppingCartDTO shoppingCartDTO);

    /**
     * @description: 查看购物车
     * @author: CyberAstra
     * @date: 2025/7/9 at 16:26:29
     * @return: java.util.List<com.sky.entity.ShoppingCart>
     **/
    List<ShoppingCart> list();

    /**
     * @description: 清空购物车
     * @author: CyberAstra
     * @date: 2025/7/9 at 16:45:18
     **/
    void clean();

    /**
     * @description: 删除购物车中的一个商品
     * @author: CyberAstra
     * @date: 2025/7/10 at 06:47:49
     * @param: shoppingCartDTO
     **/
    void sub(ShoppingCartDTO shoppingCartDTO);
}
