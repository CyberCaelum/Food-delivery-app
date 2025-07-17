package com.sky.service;

import com.sky.dto.*;
import com.sky.result.PageResult;
import com.sky.vo.*;

/**
 * @ClassName : OrderService
 * @Description :订单service
 * @Author :  CyberCaelum
 * @Date: 2025-07-11 14:30
 */

public interface OrderService {

    /**
     * @description: 用户下单
     * @author: CyberAstra
     * @date: 2025/7/12 at 09:09:57
     * @param: ordersSubmitDTO
     * @return: com.sky.vo.OrderSubmitVO
     **/
    OrderSubmitVO submit(OrdersSubmitDTO ordersSubmitDTO) throws Exception;

    /**
     * 订单支付
     * @param ordersPaymentDTO
     * @return
     */
    OrderPaymentVO payment(OrdersPaymentDTO ordersPaymentDTO) throws Exception;

    /**
     * 支付成功，修改订单状态
     * @param outTradeNo
     */
    void paySuccess(String outTradeNo);

    /**
     * @description: 查看历史订单
     * @author: CyberAstra
     * @date: 2025/7/12 at 14:50:33
     * @param: page
     * @param: pageSize
     * @param: status
     * @return: com.sky.result.PageResult
     **/
    PageResult history(Integer page, Integer pageSize, Integer status);

    /**
     * @description: 取消订单
     * @author: CyberAstra
     * @date: 2025/7/12 at 15:12:29
     * @param: id
     **/
    void cancel(Long id);

    /**
     * @description: 查看订单详情
     * @author: CyberAstra
     * @date: 2025/7/12 at 15:21:00
     * @param: id
     * @return: com.sky.vo.OrderVO
     **/
    OrderVO details(Long id);

    /**
     * @description: 再来一单
     * @author: CyberAstra
     * @date: 2025/7/12 at 15:59:37
     * @param: id
     **/
    void repetition(Long id);

    /**
     * @description: 查看订单详情和订单信息
     * @author: CyberAstra
     * @date: 2025/7/15 at 15:50:54
     * @param: id
     * @return: com.sky.vo.OrderDetailVO
     **/
    OrderDetailVO detail(Long id);
    /**
     * @description: 订单搜索
     * @author: CyberAstra
     * @date: 2025/7/13 at 09:46:07
     * @param: ordersPageQueryDTO
     * @return: com.sky.result.PageResult
     **/
    PageResult conditionSearch(OrdersPageQueryDTO ordersPageQueryDTO);

    /**
     * @description: 各个状态订单数量统计
     * @author: CyberAstra
     * @date: 2025/7/14 at 06:45:42
     * @return: com.sky.vo.OrderStatisticsVO
     **/
    OrderStatisticsVO statistics();

    /**
     * @description: 接单
     * @author: CyberAstra
     * @date: 2025/7/14 at 07:10:47
     * @param: ordersConfirmDTO
     **/
    void confirm(OrdersConfirmDTO ordersConfirmDTO);

    /**
     * @description: 拒单
     * @author: CyberAstra
     * @date: 2025/7/14 at 07:12:48
     * @param: ordersRejectionDTO
     **/
    void rejection(OrdersRejectionDTO ordersRejectionDTO) throws Exception;

    /**
     * @description: 商家取消订单
     * @author: CyberAstra
     * @date: 2025/7/14 at 08:57:14
     * @param: ordersCancelDTO
     **/
    void adminCancel(OrdersCancelDTO ordersCancelDTO);

    /**
     * @description: 派送订单
     * @author: CyberAstra
     * @date: 2025/7/14 at 09:04:26
     * @param: id
     **/
    void delivery(Long id);

    /**
     * @description: 完成订单
     * @author: CyberAstra
     * @date: 2025/7/14 at 09:09:06
     * @param: id
     **/
    void complete(Long id);

    /**
     * @description: 用户催单
     * @author: CyberAstra
     * @date: 2025/7/16 at 16:07:32
     * @param: id
     **/
    void reminder(Long id);
}
