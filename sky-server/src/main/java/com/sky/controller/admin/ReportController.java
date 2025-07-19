package com.sky.controller.admin;

import com.sky.result.Result;
import com.sky.service.ReportService;
import com.sky.vo.OrderReportVO;
import com.sky.vo.SalesTop10ReportVO;
import com.sky.vo.TurnoverReportVO;
import com.sky.vo.UserReportVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.concurrent.TimeoutException;

/**
 * @ClassName : ReportController
 * @Description : 数据统计相关接口
 * @Author :  CyberCaelum
 * @Date: 2025-07-18 08:28
 */
@Api(tags = "数据统计")
@RestController
@RequestMapping("/admin/report")
@Slf4j
public class ReportController {


    @Autowired
    private ReportService reportService;

    /**
     * @description: 营业额统计
     * @author: CyberAstra
     * @date: 2025/7/18 at 09:50:32
     * @param: begin
     * @param: end
     * @return: com.sky.result.Result
     **/
    @ApiOperation("营业额统计")
    @GetMapping("/turnoverStatistics")
    public Result turnoverStatistics (@DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate begin,
                                      @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end) throws TimeoutException {
        TurnoverReportVO vo = reportService.turnoverStatistics(begin,end);
        return Result.success(vo);
    }

    /**
     * @description: 用户数据统计
     * @author: CyberAstra
     * @date: 2025/7/18 at 10:08:56
     * @param: begin
     * @param: end
     * @return: com.sky.result.Result
     **/
    @ApiOperation("用户数据统计")
    @GetMapping("/userStatistics")
    public Result userStatistics(@DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate begin,
                                 @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end) throws TimeoutException {
        UserReportVO vo = reportService.userStatistics(begin,end);
        return Result.success(vo);
    }

    /**
     * @description: 订单统计
     * @author: CyberAstra
     * @date: 2025/7/18 at 14:44:31
     * @param: begin
     * @param: end
     * @return: com.sky.result.Result
     **/
    @ApiOperation("订单统计")
    @GetMapping("/ordersStatistics")
    public Result ordersStatistics(@DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate begin,
                                   @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end) throws TimeoutException {
        OrderReportVO vo = reportService.ordersStatistics(begin,end);
        return Result.success(vo);
    }

    /**
     * @description: 查询销量排名top10
     * @author: CyberAstra
     * @date: 2025/7/18 at 15:51:34
     * @param: begin
     * @param: end
     * @return: com.sky.result.Result
     **/
    @ApiOperation("查询销量排名top10")
    @GetMapping("/top10")
    public Result top10(@DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate begin,
                        @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end){
        SalesTop10ReportVO vo = reportService.top10(begin,end);
        log.info("信息:{}",vo);
        return Result.success(vo);
    }

    /**
     * @description: 导出Excel报表接口
     * @author: CyberAstra
     * @date: 2025/7/19 at 15:49:18
     * @param: response
     **/
    @ApiOperation("导出Excel报表接口")
    @GetMapping("/export")
    public void export(HttpServletResponse response){
        log.info("导出Excel报表");
        reportService.export(response);
    }
}
