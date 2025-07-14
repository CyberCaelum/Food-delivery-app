package com.sky.service.impl;

import com.sky.context.BaseContext;
import com.sky.dto.ShoppingCartDTO;
import com.sky.entity.Dish;
import com.sky.entity.Setmeal;
import com.sky.entity.ShoppingCart;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetmealMapper;
import com.sky.mapper.ShoppingCartMapper;
import com.sky.service.ShoppingCartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @ClassName : ShoppingCartServiceImpl
 * @Description : C端购物车service
 * @Author :  CyberCaelum
 * @Date: 2025-07-09 15:28
 */
@Service
@Slf4j
public class ShoppingCartServiceImpl implements ShoppingCartService {

    @Autowired
    private ShoppingCartMapper shoppingCartMapper;

    @Autowired
    private DishMapper dishMapper;

    @Autowired
    private SetmealMapper setmealMapper;

    /**
     * @description: 添加购物车
     * @author: CyberAstra
     * @date: 2025/7/9 at 16:21:26
     * @param: shoppingCartDTO
     **/
    @Override
    public void addShoppingCart(ShoppingCartDTO shoppingCartDTO) {
        ShoppingCart shoppingCart = new ShoppingCart();
        BeanUtils.copyProperties(shoppingCartDTO, shoppingCart);
        Long userId = BaseContext.getCurrentId();
        shoppingCart.setUserId(userId);

        //查询是否存在
        List<ShoppingCart> list = shoppingCartMapper.list(shoppingCart);

        //不为空更新数量
        if (list != null && !list.isEmpty()) {
            ShoppingCart cart = list.get(0);
            cart.setNumber(cart.getNumber() + 1);
            shoppingCartMapper.updateNumber(cart);
            return;
        }
        //否则插入新的购物车
        if (shoppingCart.getDishId() != null){
            //插入的是菜品
            Dish dish = dishMapper.getById(shoppingCart.getDishId());
            shoppingCart.setName(dish.getName());
            shoppingCart.setAmount(dish.getPrice());
            shoppingCart.setImage(dish.getImage());
        }
        else {
            //插入的是套餐
            Setmeal setmeal = setmealMapper.getById(shoppingCart.getSetmealId());
            shoppingCart.setName(setmeal.getName());
            shoppingCart.setAmount(setmeal.getPrice());
            shoppingCart.setImage(setmeal.getImage());
        }
        shoppingCart.setNumber(1);
        shoppingCart.setCreateTime(LocalDateTime.now());
        shoppingCartMapper.add(shoppingCart);
    }

    /**
     * @description: 查看购物车
     * @author: CyberAstra
     * @date: 2025/7/9 at 16:27:07
     * @return: java.util.List<com.sky.entity.ShoppingCart>
     **/
    @Override
    public List<ShoppingCart> list() {
        Long userId = BaseContext.getCurrentId();
        ShoppingCart shoppingCart = ShoppingCart.builder()
                .userId(userId)
                .build();
        return shoppingCartMapper.list(shoppingCart);
    }

    /**
     * @description: 清空购物车
     * @author: CyberAstra
     * @date: 2025/7/9 at 16:46:02
     **/
    @Override
    public void clean() {
        Long userId = BaseContext.getCurrentId();
        shoppingCartMapper.deleteByUserId(userId);
    }

    /**
     * @description: 删除购物车中的一个商品
     * @author: CyberAstra
     * @date: 2025/7/10 at 06:48:18
     * @param: shoppingCartDTO
     **/
    @Override
    public void sub(ShoppingCartDTO shoppingCartDTO) {
        //先查找商品数量
        Long userId = BaseContext.getCurrentId();
        ShoppingCart shoppingCart = new ShoppingCart();
        BeanUtils.copyProperties(shoppingCartDTO, shoppingCart);
        shoppingCart.setUserId(userId);
        List<ShoppingCart> list = shoppingCartMapper.list(shoppingCart);
        //数量减一
        list.get(0).setNumber(list.get(0).getNumber() - 1);
        //若数量为零，删除商品
        if (list.get(0).getNumber() == 0){
            shoppingCartMapper.sub(shoppingCart);
            return;
        }
        //若数量不为零，更新数量
        shoppingCartMapper.updateNumber(list.get(0));
    }
}
