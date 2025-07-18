package com.sky.service.impl;

import com.sky.entity.Orders;
import com.sky.mapper.OrderMapper;
import com.sky.mapper.UserMapper;
import com.sky.service.WorkspaceService;
import com.sky.vo.BusinessDataVO;
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
            orderCompletionRate = completedOrder/orderCompletionRate;
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


}
