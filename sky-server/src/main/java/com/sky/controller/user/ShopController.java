package com.sky.controller.user;

import com.sky.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName : ShopController
 * @Description : 用户店铺相关接口
 * @Author :  CyberCaelum
 * @Date: 2025-06-15 16:32
 */

@RestController("userShopController")
@RequestMapping("/user/shop")
@Api(tags = "用户店铺相关接口")
@Slf4j
public class ShopController {

    private static final String KEY = "SHOP_STATUS";

    @Autowired
    private RedisTemplate redisTemplate;

    @GetMapping("/status")
    @ApiOperation("获取店铺营业状态")
    public Result getStatus() {
        String status = (String) redisTemplate.opsForValue().get(KEY);
        log.info("店铺的营业状态为：{}",Integer.valueOf(status) == 1 ? "营业中" : "打烊中");
        return Result.success(Integer.valueOf(status));
    }
}
