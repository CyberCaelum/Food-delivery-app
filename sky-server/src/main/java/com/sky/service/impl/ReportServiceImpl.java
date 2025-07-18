package com.sky.service.impl;

import com.sky.entity.Orders;
import com.sky.mapper.OrderDetailMapper;
import com.sky.mapper.OrderMapper;
import com.sky.mapper.UserMapper;
import com.sky.service.ReportService;
import com.sky.vo.OrderReportVO;
import com.sky.vo.SalesTop10ReportVO;
import com.sky.vo.TurnoverReportVO;
import com.sky.vo.UserReportVO;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.TimeoutException;

/**
 * @ClassName : ReportServiceImpl
 * @Description : 数据统计service
 * @Author :  CyberCaelum
 * @Date: 2025-07-18 08:42
 */

@Service
public class ReportServiceImpl implements ReportService {


    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private OrderDetailMapper orderDetailMapper;

    private List<LocalDate> dateToString(LocalDate begin, LocalDate end) throws TimeoutException {
        List<LocalDate> dates = new ArrayList<>();
        dates.add(begin);

        if (begin.isAfter(end)){
            throw new TimeoutException();
        }

        while (!begin.equals(end)) {
            begin = begin.plusDays(1);
            dates.add(begin);
        }

        return dates;
    }

    /**
     * @description: 营业额统计
     * @author: CyberAstra
     * @date: 2025/7/18 at 09:55:33
     * @param: begin
     * @param: end
     * @return: com.sky.vo.TurnoverReportVO
     **/
    @Override
    public TurnoverReportVO turnoverStatistics(LocalDate begin, LocalDate end) throws TimeoutException {

        List<LocalDate> dates = dateToString(begin, end);
        String dateList = StringUtils.join(dates, ", ");

        List<Double> floats = new ArrayList<>();

        for (LocalDate date : dates) {
            Double f = orderMapper.getAmounts(date,Orders.COMPLETED);

            if (f == null) {
                f = (double)0;
            }

            floats.add(f);
        }

        String turnoverList = StringUtils.join(floats, ",");

        return TurnoverReportVO.builder().dateList(dateList).turnoverList(turnoverList).build();
    }

    /**
     * @description: 用户数据统计
     * @author: CyberAstra
     * @date: 2025/7/18 at 10:09:18
     * @param: begin
     * @param: end
     * @return: com.sky.vo.UserReportVO
     **/
    @Override
    public UserReportVO userStatistics(LocalDate begin, LocalDate end) throws TimeoutException {
        List<LocalDate> dates = dateToString(begin, end);
        String dateList = StringUtils.join(dates, ",");

        List<Integer> num = new ArrayList<>();
        List<Integer> total = new ArrayList<>();

        for (LocalDate date : dates) {
            Integer integer = userMapper.getNewAmount(date);

            if (integer == null) {
                integer = 0;
            }

            num.add(integer);
            Integer i = userMapper.getCount();
            total.add(i);
        }

        String newUserList = StringUtils.join(num, ",");
        String totalUserList = StringUtils.join(total, ",");

        return UserReportVO
                .builder()
                .dateList(dateList)
                .newUserList(newUserList)
                .totalUserList(totalUserList)
                .build();
    }

    /**
     * @description: 订单统计
     * @author: CyberAstra
     * @date: 2025/7/18 at 14:44:49
     * @param: begin
     * @param: end
     * @return: com.sky.vo.OrderReportVO
     **/
    @Override
    public OrderReportVO ordersStatistics(LocalDate begin, LocalDate end) throws TimeoutException {
        List<LocalDate> dates = dateToString(begin, end);
        String dateList = StringUtils.join(dates, ",");
        List<Integer> num = new ArrayList<>();//有效订单数
        List<Integer> total = new ArrayList<>();
        Integer validOrderCount =0;
        Integer totalOrderCount = 0;
        for (LocalDate date : dates) {
            //有效订单数
            Integer integer = orderMapper.getOrderCount(date,Orders.COMPLETED);
            if (integer == null) {
                integer = 0;
            }
            num.add(integer);
            validOrderCount += integer;
            //全部订单数
            Integer i = orderMapper.getOrderCount(date,null);
            if (i == null) {
                i = 0;
            }
            totalOrderCount += i;
            total.add(i);
        }
        String validOrderCountList = StringUtils.join(num, ",");
        String orderCountList = StringUtils.join(total, ",");
        //计算完成率
        Double orderCompletionRate = 0.0;
        if (totalOrderCount != 0){
            orderCompletionRate = (double)totalOrderCount / (double)validOrderCount;
        }

        return OrderReportVO
                .builder()
                .dateList(dateList)
                .orderCountList(orderCountList)
                .orderCompletionRate(orderCompletionRate)
                .totalOrderCount(totalOrderCount)
                .validOrderCountList(validOrderCountList)
                .validOrderCount(validOrderCount)
                .build();
    }

    /**
     * @description: 查询销量排名top10
     * @author: CyberAstra
     * @date: 2025/7/18 at 15:52:04
     * @param: begin
     * @param: end
     * @return: com.sky.vo.SalesTop10ReportVO
     **/
    @Override
    public SalesTop10ReportVO top10(LocalDate begin, LocalDate end) {
        List<Map<String, String>> list = orderDetailMapper.getTop10(begin,end);
        List<String> nameList = new ArrayList<>();
        List<Integer> numberList = new ArrayList<>();
        for (Map<String, String> map : list) {
            nameList.add(map.get("name"));
            numberList.add(Integer.valueOf(map.get("order_count")));
        }



        return SalesTop10ReportVO
                .builder()
                .nameList(StringUtils.join(nameList,","))
                .numberList(StringUtils.join(numberList,","))
                .build();
    }
}
