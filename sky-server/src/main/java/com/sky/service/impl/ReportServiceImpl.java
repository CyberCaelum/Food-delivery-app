package com.sky.service.impl;

import com.sky.entity.Orders;
import com.sky.mapper.OrderDetailMapper;
import com.sky.mapper.OrderMapper;
import com.sky.mapper.UserMapper;
import com.sky.service.ReportService;
import com.sky.service.WorkspaceService;
import com.sky.vo.*;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
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

    @Autowired
    private WorkspaceService workspaceService;

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

    /**
     * @description: 导出Excel报表接口
     * @author: CyberAstra
     * @date: 2025/7/19 at 15:51:04
     * @param: response
     **/
    @Override
    public void export(HttpServletResponse response) {
        LocalDate dateBegin = LocalDate.now().minusDays(30);
        LocalDate dateEnd = LocalDate.now();
        LocalDate date = dateBegin;
        List<BusinessDataVO> dataVOs = new ArrayList<>();
        Integer newAmount = 0,completedOrder = 0;
        Double amounts = 0.0,unitPrice = 0.0,num = 0.0;
        while (date.isBefore(dateEnd)) {
            BusinessDataVO businessDataVO = workspaceService.businessData(date);
            dataVOs.add(businessDataVO);
            newAmount += businessDataVO.getNewUsers();//新用户数
            completedOrder += businessDataVO.getValidOrderCount();//完成的订单
            amounts += businessDataVO.getTurnover();//营业额
            unitPrice += businessDataVO.getUnitPrice();//30天平均客单价和
            num += businessDataVO.getOrderCompletionRate();//30订单完成率和
            date = date.plusDays(1);
        }
        //获得输入流
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("template/运营数据报表模板.xlsx");
        try {
            //基于模板创建一个新的excel文件
            XSSFWorkbook excel = new XSSFWorkbook(inputStream);
            //填充时间数据
            //根据索引获取页
            XSSFSheet sheet = excel.getSheetAt(0);
            //修改表格时间
            sheet.getRow(1).getCell(1).setCellValue(dateBegin + "-" + dateEnd.minusDays(1));
            //修改概览数据
            sheet.getRow(3).getCell(2).setCellValue(amounts);//营业额
            sheet.getRow(3).getCell(4).setCellValue(num/30);//订单完成率
            sheet.getRow(3).getCell(6).setCellValue(newAmount);//新增用户数
            sheet.getRow(4).getCell(2).setCellValue(completedOrder);//有效订单
            sheet.getRow(4).getCell(4).setCellValue(unitPrice/30);//平均客价
            //修改明细数据
            Integer i = 7;//明细数据第一行索引
            for (BusinessDataVO businessDataVO : dataVOs){
                XSSFRow row = sheet.getRow(i);
                row.getCell(1).setCellValue(dateBegin+"");//日期
                row.getCell(2).setCellValue(businessDataVO.getTurnover());//营业额
                row.getCell(3).setCellValue(businessDataVO.getValidOrderCount());//有效订单
                row.getCell(4).setCellValue(businessDataVO.getOrderCompletionRate());//订单完成率
                row.getCell(5).setCellValue(businessDataVO.getUnitPrice());//平均客价
                row.getCell(6).setCellValue(businessDataVO.getNewUsers());//新增用户数
                i++;
                dateBegin = dateBegin.plusDays(1);
            }
            //通过输出流将Excel文件下载到客户端浏览器
            ServletOutputStream out = response.getOutputStream();
            excel.write(out);

            //关闭资源
            out.close();
            excel.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
