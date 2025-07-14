package com.sky.mapper;

import com.sky.entity.OrderDetail;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

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
}
