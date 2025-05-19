package com.sky.service.impl;

import com.sky.constant.MessageConstant;
import com.sky.service.CommonService;
import com.sky.utils.AliOssUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.UUID;

/**
 * @ClassName : CommonServiceImpl
 * @Description : 通用接口实现类
 * @Author :  CyberCaelum
 * @Date: 2025-05-18 21:06
 */
@Service
@Slf4j
public class CommonServiceImpl implements CommonService {

    @Autowired
    private AliOssUtil aliOssUtil;
    /**
     * @description: 文件上传
     * @author: CyberAstra
     * @date: 2025/5/18 at 21:15:23
     * @return: java.lang.String
     **/
    @Override
    public String upload(MultipartFile file) {

        try {
            String originalFilename = file.getOriginalFilename();
            String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
            String objectName = UUID.randomUUID().toString() + extension;
            String filePath = aliOssUtil.upload(file.getBytes(), objectName);
            return filePath;
        } catch (IOException e) {
            log.error(e.getMessage());
        }
        return MessageConstant.UPLOAD_FAILED;
    }

}
