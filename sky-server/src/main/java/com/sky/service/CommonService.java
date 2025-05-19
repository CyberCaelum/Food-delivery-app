package com.sky.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @ClassName : CommonService
 * @Description : 通用接口实现抽象类
 * @Author :  CyberCaelum
 * @Date: 2025-05-18 21:06
 */
public interface CommonService {

    /**
     * @description: 文件上传
     * @author: CyberAstra
     * @date: 2025/5/18 at 21:15:42
     * @return: java.lang.String
     **/
    String upload(MultipartFile file);
}
