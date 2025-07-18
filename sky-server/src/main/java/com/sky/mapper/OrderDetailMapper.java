package com.sky.mapper;

import com.sky.entity.OrderDetail;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * @ClassName : OrderDetailMapper
 * @Description : 订单详情mapper
 * @Author :  CyberCaelum
 * @Date: 2025-07-11 14:39
 */

@Mapper
public interface OrderDetailMapper {

    /**
     * @description: 新增订单详情
     * @author: CyberAstra
     * @date: 2025/7/12 at 09:10:29
     * @param: orderDetails
     **/
    void insert(List<OrderDetail> orderDetails);

    /**
     * @description: 通过订单id查询订单详情
     * @author: CyberAstra
     * @date: 2025/7/12 at 14:51:42
     * @param: id
     * @return: java.util.List<com.sky.entity.OrderDetail>
     **/
    @Select("select * from order_detail where order_id = #{id}")
    List<OrderDetail> getByOrderId(Long id);

    /**
     * @description: 查询销量排名top10
     * @author: CyberAstra
     * @date: 2025/7/18 at 15:52:23
     * @param: begin
     * @param: end
     * @return: java.util.List<java.util.Map<java.lang.String,java.lang.String>>
     **/
    @Select("SELECT od.name, CONCAT(COUNT(*), '') AS order_count " +
            "FROM order_detail od " +
            "JOIN orders o ON od.order_id = o.id " +
            "WHERE DATE(o.order_time) BETWEEN #{begin} AND #{end} " +
            "GROUP BY od.name " +
            "ORDER BY order_count DESC LIMIT 10")
    List<Map<String, String>> getTop10(LocalDate begin, LocalDate end);
}
