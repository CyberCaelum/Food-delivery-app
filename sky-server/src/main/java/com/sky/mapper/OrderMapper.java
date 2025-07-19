package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.dto.OrdersPageQueryDTO;
import com.sky.entity.Orders;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @ClassName : OrderMapper
 * @Description : 订单Mapper
 * @Author :  CyberCaelum
 * @Date: 2025-07-11 14:38
 */

@Mapper
public interface OrderMapper {

    /**
     * @description: 新增订单
     * @author: CyberAstra
     * @date: 2025/7/12 at 09:10:49
     * @param: orders
     **/
    void insert(Orders orders);

    /**
     * 根据订单号查询订单
     * @param orderNumber
     */
    @Select("select * from orders where number = #{orderNumber}")
    Orders getByNumber(String orderNumber);

    /**
     * 修改订单信息
     * @param orders
     */
    void update(Orders orders);

    /**
     * @description: 更新订单支付状态
     * @author: CyberAstra
     * @date: 2025/7/12 at 09:48:57
     * @param: orderStatus
     * @param: orderPaidStatus
     * @param: check_out_time
     * @param: orderNumber
     **/
    @Update("update orders set status = #{orderStatus},pay_status = #{orderPaidStatus} ,checkout_time = #{check_out_time} " +
            "where number = #{orderNumber}")
    void updateStatus(Integer orderStatus, Integer orderPaidStatus, LocalDateTime check_out_time, String orderNumber);

    /**
     * @description: 查看历史订单
     * @author: CyberAstra
     * @date: 2025/7/12 at 14:51:18
     * @param: currentId
     * @param: status
     * @return: com.github.pagehelper.Page<com.sky.entity.Orders>
     **/
    Page<Orders> history(Long currentId, Integer status);

    /**
     * @description: 根据id查找订单
     * @author: CyberAstra
     * @date: 2025/7/13 at 09:46:48
     * @param: id
     * @return: com.sky.entity.Orders
     **/
    @Select("select * from orders where id = #{id}")
    Orders getById(Long id);

    /**
     * @description: 订单搜索
     * @author: CyberAstra
     * @date: 2025/7/13 at 09:46:28
     * @param: ordersPageQueryDTO
     * @return: com.github.pagehelper.Page<com.sky.entity.Orders>
     **/
    Page<Orders> select(OrdersPageQueryDTO ordersPageQueryDTO);

    /**
     * @description: 查询各个状态的订单的数量
     * @author: CyberAstra
     * @date: 2025/7/14 at 06:50:40
     * @param: status
     * @return: java.lang.Integer
     **/
    @Select("select count(*) from orders where status = #{status}")
    Integer statistics(Integer status);

    /**
     * @description: 更新超时订单信息
     * @author: CyberAstra
     * @date: 2025/7/16 at 09:05:39
     * @param: status
     * @param: reason
     * @param: localDateTime
     * @param: time
     **/
    @Update("update orders set status = #{status}," +
            "cancel_reason = #{reason},cancel_time = #{time} where order_time < #{localDateTime}")
    void updateTimeOuntOrder(Integer status,String reason,LocalDateTime localDateTime,LocalDateTime time);

    /**
     * @description: 获取营业额
     * @author: CyberAstra
     * @date: 2025/7/18 at 09:16:51
     * @param: dates
     * @param: status
     * @return: java.util.List<java.lang.Float>
     **/
    @Select("select coalesce(sum(amount), 0) from orders where date(order_time) = #{date} and status = #{status}")
    Double getAmounts(LocalDate date, Integer status);

    /**
     * @description: 获得指定状态的订单数量
     * @author: CyberAstra
     * @date: 2025/7/18 at 14:45:03
     * @param: date
     * @param: status
     * @return: java.lang.Integer
     **/
    Integer getOrderCount(LocalDate date, Integer status);

    /**
     * @description: 完单人数
     * @author: CyberAstra
     * @date: 2025/7/18 at 16:36:35
     * @param: now
     * @param: completed
     * @return: java.lang.Integer
     **/
    @Select("select coalesce(count(DISTINCT user_id), 0) from orders where date(order_time) = #{date} and status = #{status}")
    Integer getOrderUser(LocalDate date, Integer status);
}
