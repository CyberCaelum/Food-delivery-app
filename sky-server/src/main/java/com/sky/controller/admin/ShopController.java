package com.sky.controller.admin;

import com.sky.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

/**
 * @ClassName : ShopController
 * @Description : 商家店铺相关接口
 * @Author :  CyberCaelum
 * @Date: 2025-06-15 16:17
 */

@Api(tags = "商家店铺相关接口")
@RestController("adminShopController")
@RequestMapping("/admin/shop")
@Slf4j
public class ShopController {

    private static final String KEY = "SHOP_STATUS";

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * @description: 设置店铺的营业状态
     * @author: CyberAstra
     * @date: 2025/6/15 at 16:31:30
     * @param: status
     * @return: com.sky.result.Result
     **/
    @PutMapping("/{status}")
    @ApiOperation("设置店铺营业状态")
    public Result setStatus(@PathVariable Integer status) {
        log.info("设置店铺的营业状态为：{}",status == 1 ? "营业中" : "打烊中");
        redisTemplate.opsForValue().set(KEY,status.toString());
        return Result.success();
    }



    /**
     * @description: 获取店铺的营业状态
     * @author: CyberAstra
     * @date: 2025/6/15 at 16:31:50
     * @return: com.sky.result.Result
     **/
    @GetMapping("/status")
    @ApiOperation("获取店铺营业状态")
    public Result getStatus() {

        Object s = redisTemplate.opsForValue().get(KEY);
        if (s == null || "".equals(s)) {
            //若reids为空，设置初始店铺状态为营业中
            log.info("redis中没有店铺状态，设置默认店铺状态为营业中");
            redisTemplate.opsForValue().set(KEY,"1");
        }
        String status = (String) redisTemplate.opsForValue().get(KEY);
        log.info("获取店铺的营业状态为：{}",Integer.valueOf(status) == 1 ? "营业中" : "打烊中");
        return Result.success(Integer.valueOf(status));
    }

}
