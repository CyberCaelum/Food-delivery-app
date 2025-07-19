package com.sky.service;

import com.sky.vo.BusinessDataVO;
import com.sky.vo.DishOverViewVO;
import com.sky.vo.OrderOverViewVO;
import com.sky.vo.SetmealOverViewVO;

/**
 * @ClassName : WorkspaceService
 * @Description : 工作台service
 * @Author :  CyberCaelum
 * @Date: 2025-07-18 16:11
 */
public interface WorkspaceService {

    /**
     * @description: 查询今日运营数据
     * @author: CyberAstra
     * @date: 2025/7/18 at 16:36:01
     * @return: com.sky.vo.BusinessDataVO
     **/
    BusinessDataVO businessData();

    /**
     * @description: 查询套餐总览
     * @author: CyberAstra
     * @date: 2025/7/19 at 07:54:51
     * @return: com.sky.vo.SetmealOverViewVO
     **/
    SetmealOverViewVO overviewSetmeals();

    /**
     * @description: 查询菜品总览
     * @author: CyberAstra
     * @date: 2025/7/19 at 08:01:22
     * @return: com.sky.vo.DishOverViewVO
     **/
    DishOverViewVO overviewDishes();

    /**
     * @description: 查询订单管理数据
     * @author: CyberAstra
     * @date: 2025/7/19 at 08:14:37
     * @return: com.sky.vo.OrderOverViewVO
     **/
    OrderOverViewVO overviewOrders();
}
