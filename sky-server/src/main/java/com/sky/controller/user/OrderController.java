package com.sky.controller.user;

import com.github.pagehelper.Page;
import com.sky.dto.OrdersPageQueryDTO;
import com.sky.dto.OrdersPaymentDTO;
import com.sky.dto.OrdersSubmitDTO;
import com.sky.mapper.OrderMapper;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.OrderService;
import com.sky.vo.OrderPaymentVO;
import com.sky.vo.OrderSubmitVO;
import com.sky.vo.OrderVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @ClassName : OrderController
 * @Description : 用户端订单接口
 * @Author :  CyberCaelum
 * @Date: 2025-07-11 14:23
 */
@RestController("userOrderController")
@RequestMapping("/user/order")
@Api(tags = "用户端订单接口")
@Slf4j
public class OrderController {

    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderMapper orderMapper;

    /**
     * @description: 用户下单
     * @author: CyberAstra
     * @date: 2025/7/12 at 09:09:37
     * @param: ordersSubmitDTO
     * @return: com.sky.result.Result
     **/
    @ApiOperation("用户下单")
    @PostMapping("/submit")
    public Result submit(@RequestBody OrdersSubmitDTO ordersSubmitDTO){
        log.info("订单信息：{}",ordersSubmitDTO);
        OrderSubmitVO orderSubmitVO = orderService.submit(ordersSubmitDTO);
        return Result.success(orderSubmitVO);
    }


    /**
     * 订单支付
     *
     * @param ordersPaymentDTO
     * @return
     */
    @PutMapping("/payment")
    @ApiOperation("订单支付")
    public Result<OrderPaymentVO> payment(@RequestBody OrdersPaymentDTO ordersPaymentDTO) throws Exception {
        log.info("订单支付：{}", ordersPaymentDTO);
        //模拟支付

        OrderPaymentVO orderPaymentVO = orderService.payment(ordersPaymentDTO);
        log.info("生成预支付交易单：{}", orderPaymentVO);
        return Result.success(new OrderPaymentVO());
    }

    /**
     * @description: 查看历史订单
     * @author: CyberAstra
     * @date: 2025/7/12 at 14:18:41
     * @param: ordersPageQueryDTO
     * @return: com.sky.result.Result
     **/
    @ApiOperation("查看历史订单")
    @GetMapping("/historyOrders")
    public Result historyOrders(Integer page,Integer pageSize,Integer status) {
        PageResult pageResult = orderService.history(page,pageSize,status);
        return Result.success(pageResult);
    }

    /**
     * @description: 用户取消订单
     * @author: CyberAstra
     * @date: 2025/7/12 at 15:13:03
     * @param: id
     * @return: com.sky.result.Result
     **/
    @ApiOperation("取消订单")
    @PutMapping("/cancel/{id}")
    public Result cancel(@PathVariable Long id) {
        log.info("取消的订单：{}",id);
        orderService.cancel(id);
        return Result.success();

    }

    /**
     * @description: 查看订单详情
     * @author: CyberAstra
     * @date: 2025/7/12 at 15:20:43
     * @param: id
     * @return: com.sky.result.Result
     **/
    @ApiOperation("查看订单详情")
    @GetMapping("/orderDetail/{id}")
    public Result details(@PathVariable Long id) {
        OrderVO orderVO = orderService.details(id);
        return Result.success(orderVO);
    }

    /**
     * @description: 再来一单
     * @author: CyberAstra
     * @date: 2025/7/12 at 15:59:23
     * @param: id
     * @return: com.sky.result.Result
     **/
    @ApiOperation("再来一单")
    @PostMapping("repetition/{id}")
    public Result repetition(@PathVariable Long id){
        orderService.repetition(id);
        return Result.success();
    }

    @ApiOperation("用户催单")
    @GetMapping("/reminder/{id}")
    public Result reminder(@PathVariable Long id){

        return Result.success();
    }
}
