package com.sky.service.impl;

import com.sky.entity.Orders;
import com.sky.mapper.DishMapper;
import com.sky.mapper.OrderMapper;
import com.sky.mapper.SetmealMapper;
import com.sky.mapper.UserMapper;
import com.sky.service.WorkspaceService;
import com.sky.vo.BusinessDataVO;
import com.sky.vo.DishOverViewVO;
import com.sky.vo.OrderOverViewVO;
import com.sky.vo.SetmealOverViewVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

/**
 * @ClassName : WorkspaceServiceImpl
 * @Description : 工作台service
 * @Author :  CyberCaelum
 * @Date: 2025-07-18 16:11
 */
@Service
public class WorkspaceServiceImpl implements WorkspaceService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private SetmealMapper setmealMapper;

    @Autowired
    private DishMapper dishMapper;

    /**
     * @description: 查询今日运营数据
     * @author: CyberAstra
     * @date: 2025/7/18 at 16:36:10
     * @return: com.sky.vo.BusinessDataVO
     **/
    @Override
    public BusinessDataVO businessData() {
        //新用户
        Integer newAmount = userMapper.getNewAmount(LocalDate.now());
        //完成的订单数量
        Integer completedOrder = orderMapper.getOrderCount(LocalDate.now(), Orders.COMPLETED);
        //全部的订单数量
        Integer totalOrder = orderMapper.getOrderCount(LocalDate.now(), null);
        //完成率
        Double orderCompletionRate = 0.0;
        if (totalOrder > 0){
            orderCompletionRate = (double)completedOrder/totalOrder;
        }
        //获得营业额
        Double amounts = orderMapper.getAmounts(LocalDate.now(), Orders.COMPLETED);
        //完单人数
        Integer num = orderMapper.getOrderUser(LocalDate.now(), Orders.COMPLETED);
        //平均客价
        Double unitPrice = amounts/num;
        return BusinessDataVO
                .builder()
                .orderCompletionRate(orderCompletionRate)
                .newUsers(newAmount)
                .turnover(amounts)
                .unitPrice(unitPrice)
                .validOrderCount(completedOrder)
                .build();
    }

    /**
     * @description: 查询套餐总览
     * @author: CyberAstra
     * @date: 2025/7/19 at 07:55:21
     * @return: com.sky.vo.SetmealOverViewVO
     **/
    @Override
    public SetmealOverViewVO overviewSetmeals() {
        //已停售套餐的数量
        Integer discontinued = setmealMapper.getStatusCount(0);
        //已起售套餐数量
        Integer sold = setmealMapper.getStatusCount(1);
        return SetmealOverViewVO
                .builder()
                .discontinued(discontinued)
                .sold(sold)
                .build();
    }

    /**
     * @description: 查询菜品总览
     * @author: CyberAstra
     * @date: 2025/7/19 at 08:01:29
     * @return: com.sky.vo.DishOverViewVO
     **/
    @Override
    public DishOverViewVO overviewDishes() {
        //已停售菜品的数量
        Integer discontinued = dishMapper.getStatusCount(0);
        //已起售起售数量
        Integer sold = dishMapper.getStatusCount(1);
        return DishOverViewVO
                .builder()
                .discontinued(discontinued)
                .sold(sold)
                .build();
    }

    /**
     * @description: 查询订单管理数据
     * @author: CyberAstra
     * @date: 2025/7/19 at 08:14:45
     * @return: com.sky.vo.OrderOverViewVO
     **/
    @Override
    public OrderOverViewVO overviewOrders() {
        //全部订单
        Integer allOrders = orderMapper.getOrderCount(LocalDate.now(), null);
        //已取消数量
        Integer cancelledOrders = orderMapper.getOrderCount(LocalDate.now(), Orders.CANCELLED);
        //已完成数量
        Integer completedOrders = orderMapper.getOrderCount(LocalDate.now(), Orders.COMPLETED);
        //待派送数量
        Integer deliveredOrders = orderMapper.getOrderCount(LocalDate.now(), Orders.CONFIRMED);
        //待接单
        Integer waitingOrders = orderMapper.getOrderCount(LocalDate.now(),Orders.TO_BE_CONFIRMED);
        return OrderOverViewVO
                .builder()
                .allOrders(allOrders)
                .cancelledOrders(cancelledOrders)
                .completedOrders(completedOrders)
                .deliveredOrders(deliveredOrders)
                .waitingOrders(waitingOrders)
                .build();
    }
}
