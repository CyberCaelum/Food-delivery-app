package com.sky.controller.admin;

import com.sky.result.Result;
import com.sky.service.WorkspaceService;
import com.sky.vo.BusinessDataVO;
import com.sky.vo.DishOverViewVO;
import com.sky.vo.OrderOverViewVO;
import com.sky.vo.SetmealOverViewVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

/**
 * @ClassName : WorkspaceController
 * @Description : 工作台接口
 * @Author :  CyberCaelum
 * @Date: 2025-07-18 16:07
 */
@Api(tags = "工作台")
@RestController
@RequestMapping("/admin/workspace")
@Slf4j
public class WorkspaceController {
    @Autowired
    private WorkspaceService workspaceService;

    /**
     * @description: 查询今日运营数据
     * @author: CyberAstra
     * @date: 2025/7/18 at 16:35:48
     * @return: com.sky.result.Result
     **/
    @ApiOperation("查询今日运营数据")
    @GetMapping("/businessData")
    public Result businessData(){
        BusinessDataVO businessDataVO = workspaceService.businessData(LocalDate.now());
        return Result.success(businessDataVO);
    }

    /**
     * @description: 查询套餐总览
     * @author: CyberAstra
     * @date: 2025/7/19 at 07:54:33
     * @return: com.sky.result.Result
     **/
    @ApiOperation("查询套餐总览")
    @GetMapping("/overviewSetmeals")
    public Result overviewSetmeals(){
        SetmealOverViewVO vo = workspaceService.overviewSetmeals();
        return Result.success(vo);
    }

    /**
     * @description: 查询菜品总览
     * @author: CyberAstra
     * @date: 2025/7/19 at 08:01:10
     * @return: com.sky.result.Result
     **/
    @ApiOperation("查询菜品总览")
    @GetMapping("/overviewDishes")
    public Result overviewDishes(){
        DishOverViewVO vo = workspaceService.overviewDishes();
        return Result.success(vo);
    }

    /**
     * @description: 查询订单管理数据
     * @author: CyberAstra
     * @date: 2025/7/19 at 08:14:26
     * @return: com.sky.result.Result
     **/
    @ApiOperation("查询订单管理数据")
    @GetMapping("/overviewOrders")
    public Result overviewOrders(){
        OrderOverViewVO vo = workspaceService.overviewOrders();
        return Result.success(vo);
    }
}
