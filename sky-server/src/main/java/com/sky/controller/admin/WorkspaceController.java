package com.sky.controller.admin;

import com.sky.result.Result;
import com.sky.service.WorkspaceService;
import com.sky.vo.BusinessDataVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        BusinessDataVO businessDataVO = workspaceService.businessData();
        return Result.success(businessDataVO);
    }
}
