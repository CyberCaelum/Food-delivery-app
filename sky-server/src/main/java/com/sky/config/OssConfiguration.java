package com.sky.config;

import com.sky.properties.AliOssProperties;
import com.sky.utils.AliOssUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName : OssConfiguration
 * @Description : 阿里云配置类
 * @Author :  CyberCaelum
 * @Date: 2025-05-18 20:48
 */
@Configuration
@Slf4j
public class OssConfiguration {

    @Autowired
    private AliOssProperties aliOssProperties;

    /**
     * @description: 创建阿里云文件上传工具类对象
     * @author: CyberAstra
     * @date: 2025/5/18 at 21:05:02
     * @return: com.sky.utils.AliOssUtil
     **/
    @Bean
    @ConditionalOnMissingBean
    public AliOssUtil aliOssUtil() {
        log.info("创建阿里云文件上传工具类对象");
        return new AliOssUtil(aliOssProperties.getEndpoint(),aliOssProperties.getAccessKeyId(),
                aliOssProperties.getAccessKeySecret(),aliOssProperties.getBucketName());
    }

}
