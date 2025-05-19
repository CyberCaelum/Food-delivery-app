package com.sky.controller.admin;

import com.sky.result.Result;
import com.sky.service.CommonService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @ClassName : CommonController
 * @Description : 通用接口
 * @Author :  CyberCaelum
 * @Date: 2025-05-18 21:01
 */
@RestController
@RequestMapping("/admin/common")
@Slf4j
@Api(tags = "通用接口")
public class CommonController {
    @Autowired
    private CommonService commonService;

    /**
     * @description: 文件上传
     * @author: CyberAstra
     * @date: 2025/5/18 at 21:04:41
     * @param: file
     * @return: com.sky.result.Result
     **/
    @ApiOperation("文件上传")
    @PostMapping("/upload")
    public Result upload(MultipartFile file) {
        log.info("文件上传");
        String string = commonService.upload(file);
        return Result.success(string);
    }
}
