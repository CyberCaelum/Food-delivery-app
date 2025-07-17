package com.sky.task;

import com.github.pagehelper.Page;
import com.sky.dto.OrdersPageQueryDTO;
import com.sky.entity.Orders;
import com.sky.mapper.OrderMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * @ClassName : OrderTask
 * @Description : 定时任务
 * @Author :  CyberCaelum
 * @Date: 2025-07-16 08:43
 */
@Slf4j
@Component
public class OrderTask {

    @Autowired
    private OrderMapper orderMapper;

    /**
     * @description: 定时处理超时订单
     * @author: CyberAstra
     * @date: 2025/7/16 at 09:07:19
     **/
    @Scheduled(cron = "0 * * * * ? ")
    public void processTimeOutOrder(){
        log.info("定时处理超时的订单：{}", LocalDateTime.now());
        orderMapper.updateTimeOuntOrder(Orders.CANCELLED,"订单超时",LocalDateTime.now(),LocalDateTime.now().minusMinutes(15));
    }

    /**
     * @description: 定时处理派送中订单
     * @author: CyberAstra
     * @date: 2025/7/16 at 09:07:54
     * @param: null
     * @return: null
     **/
    public void processDeliveryOrder(){
        log.info("定时处理派送中订单:{}",LocalDateTime.now());
        OrdersPageQueryDTO ordersPageQueryDTO = new OrdersPageQueryDTO();
        ordersPageQueryDTO.setStatus(Orders.DELIVERY_IN_PROGRESS);
        Page<Orders> page = orderMapper.select(ordersPageQueryDTO);
        if (page != null && page.getTotal() != 0){
            for (Orders order : page){
                order.setStatus(Orders.COMPLETED);
                orderMapper.update(order);
            }
        }
    }
}
