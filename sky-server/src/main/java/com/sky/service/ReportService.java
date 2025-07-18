package com.sky.service;

import com.sky.vo.OrderReportVO;
import com.sky.vo.SalesTop10ReportVO;
import com.sky.vo.TurnoverReportVO;
import com.sky.vo.UserReportVO;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.concurrent.TimeoutException;

/**
 * @ClassName : ReportService
 * @Description : 数据统计service
 * @Author :  CyberCaelum
 * @Date: 2025-07-18 08:38
 */
public interface ReportService {

    /**
     * @description: 营业额统计
     * @author: CyberAstra
     * @date: 2025/7/18 at 09:16:36
     * @param: begin
     * @param: end
     * @return: com.sky.vo.TurnoverReportVO
     **/
    TurnoverReportVO turnoverStatistics(LocalDate begin, LocalDate end) throws TimeoutException;

    /**
     * @description: 用户数据统计
     * @author: CyberAstra
     * @date: 2025/7/18 at 10:09:11
     * @param: begin
     * @param: end
     * @return: com.sky.vo.UserReportVO
     **/
    UserReportVO userStatistics(LocalDate begin, LocalDate end) throws TimeoutException;

    /**
     * @description: 订单统计
     * @author: CyberAstra
     * @date: 2025/7/18 at 14:44:41
     * @param: begin
     * @param: end
     * @return: com.sky.vo.OrderReportVO
     **/
    OrderReportVO ordersStatistics(LocalDate begin, LocalDate end) throws TimeoutException;

    /**
     * @description: 查询销量排名top10
     * @author: CyberAstra
     * @date: 2025/7/18 at 15:51:56
     * @param: begin
     * @param: end
     * @return: com.sky.vo.SalesTop10ReportVO
     **/
    SalesTop10ReportVO top10(LocalDate begin, LocalDate end);
}
