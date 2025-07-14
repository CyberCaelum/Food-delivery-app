package com.sky.controller.user;

import com.sky.dto.ShoppingCartDTO;
import com.sky.entity.ShoppingCart;
import com.sky.result.Result;
import com.sky.service.ShoppingCartService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ClassName : ShoppingCartController
 * @Description : c端购物车接口
 * @Author :  CyberCaelum
 * @Date: 2025-07-09 15:21
 */

@RestController
@RequestMapping("/user/shoppingCart")
@Slf4j
@Api(tags = "c端购物车接口")
public class ShoppingCartController {

    @Autowired
    private ShoppingCartService shoppingCartService;

    /**
     * @description: 添加购物车
     * @author: CyberAstra
     * @date: 2025/7/9 at 16:21:01
     * @param: shoppingCartDTO
     * @return: com.sky.result.Result
     **/
    @ApiOperation("添加购物车")
    @PostMapping("/add")
    public Result add(@RequestBody ShoppingCartDTO shoppingCartDTO){
        log.info("添加购物车:{}",shoppingCartDTO);
        shoppingCartService.addShoppingCart(shoppingCartDTO);
        return Result.success();
    }

    /**
     * @description: 查看购物车
     * @author: CyberAstra
     * @date: 2025/7/9 at 16:26:15
     * @return: com.sky.result.Result
     **/
    @ApiOperation("查看购物车")
    @GetMapping("/list")
    public Result list(){
        List<ShoppingCart> list = shoppingCartService.list();
        return Result.success(list);
    }

    /**
     * @description: 清空购物车
     * @author: CyberAstra
     * @date: 2025/7/9 at 16:44:39
     * @return: com.sky.result.Result
     **/
    @ApiOperation("清空购物车")
    @DeleteMapping("clean")
    public Result clean(){
        shoppingCartService.clean();
        return Result.success();
    }

    /**
     * @description: 删除购物车中的一个商品
     * @author: CyberAstra
     * @date: 2025/7/10 at 06:46:28
     * @return: com.sky.result.Result
     **/
    @ApiOperation("删除购物车中的一个商品")
    @PostMapping("/sub")
    public Result sub(@RequestBody ShoppingCartDTO shoppingCartDTO){
        log.info("删除的商品为:{}",shoppingCartDTO);
        shoppingCartService.sub(shoppingCartDTO);
        return Result.success();
    }

}
