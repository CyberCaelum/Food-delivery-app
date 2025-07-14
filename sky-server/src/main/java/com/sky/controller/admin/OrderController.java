package com.sky.controller.admin;

import com.sky.dto.OrdersCancelDTO;
import com.sky.dto.OrdersConfirmDTO;
import com.sky.dto.OrdersPageQueryDTO;
import com.sky.dto.OrdersRejectionDTO;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.OrderService;
import com.sky.vo.OrderStatisticsVO;
import com.sky.vo.OrderVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @ClassName : OrderController
 * @Description : 商家端订单接口
 * @Author :  CyberCaelum
 * @Date: 2025-07-12 16:00
 */
@RestController
@Api(tags = "商家端订单接口")
@RequestMapping("/admin/order")
@Slf4j
public class OrderController {

    @Autowired
    private OrderService orderService;

    /**
     * @description: 订单搜索
     * @author: CyberAstra
     * @date: 2025/7/13 at 09:45:53
     * @param: ordersPageQueryDTO
     * @return: com.sky.result.Result
     **/
    @ApiOperation("订单搜索")
    @GetMapping("/conditionSearch")
    public Result conditionSearch(OrdersPageQueryDTO ordersPageQueryDTO){
        log.info("订单搜索：{}",ordersPageQueryDTO);
        PageResult pageResult = orderService.conditionSearch(ordersPageQueryDTO);
        return Result.success(pageResult);
    }

    /**
     * @description: 各个状态订单数量统计
     * @author: CyberAstra
     * @date: 2025/7/14 at 06:45:48
     * @return: com.sky.result.Result
     **/
    @ApiOperation("各个状态订单数量统计")
    @GetMapping("/statistics")
    public Result statistics(){
        OrderStatisticsVO orderStatisticsVO = orderService.statistics();
        return Result.success(orderStatisticsVO);
    }

    /**
     * @description: 查询订单详情
     * @author: CyberAstra
     * @date: 2025/7/14 at 07:03:50
     * @param: id
     * @return: com.sky.result.Result
     **/
    @ApiOperation("查询订单详情")
    @GetMapping("/details/{id}")
    public Result details(@PathVariable Long id){
        OrderVO orderVO = orderService.details(id);
        return Result.success(orderVO);
    }

    /**
     * @description: 接单
     * @author: CyberAstra
     * @date: 2025/7/14 at 07:10:30
     * @param: ordersConfirmDTO
     * @return: com.sky.result.Result
     **/
    @ApiOperation("接单")
    @PutMapping("/confirm")
    public Result confirm(@RequestBody OrdersConfirmDTO ordersConfirmDTO){
        orderService.confirm(ordersConfirmDTO);
        return Result.success();
    }

    /**
     * @description: 拒单
     * @author: CyberAstra
     * @date: 2025/7/14 at 07:12:39
     * @param: ordersRejectionDTO
     * @return: com.sky.result.Result
     **/
    @ApiOperation("拒单")
    @PutMapping("/rejection")
    public Result rejection(@RequestBody OrdersRejectionDTO ordersRejectionDTO) throws Exception {
        orderService.rejection(ordersRejectionDTO);
        return Result.success();
    }

    /**
     * @description: 商家取消订单
     * @author: CyberAstra
     * @date: 2025/7/14 at 08:57:02
     * @param: ordersCancelDTO
     * @return: com.sky.result.Result
     **/
    @ApiOperation("取消订单")
    @PutMapping("/cancel")
    public Result cancel(@RequestBody OrdersCancelDTO ordersCancelDTO){
        orderService.adminCancel(ordersCancelDTO);
        return Result.success();
    }

    /**
     * @description: 派送订单
     * @author: CyberAstra
     * @date: 2025/7/14 at 09:04:15
     * @param: id
     * @return: com.sky.result.Result
     **/
    @ApiOperation("派送订单")
    @PutMapping("/delivery/{id}")
    public Result delivery(@PathVariable Long id){
        orderService.delivery(id);
        return Result.success();
    }

    /**
     * @description: 完成订单
     * @author: CyberAstra
     * @date: 2025/7/14 at 09:08:58
     * @param: id
     * @return: com.sky.result.Result
     **/
    @ApiOperation("完成订单")
    @PutMapping("/complete/{id}")
    public Result complete(@PathVariable Long id){
        orderService.complete(id);
        return Result.success();
    }
}
