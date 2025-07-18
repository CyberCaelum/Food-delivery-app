package com.sky.service;

import com.sky.vo.BusinessDataVO;

/**
 * @ClassName : WorkspaceService
 * @Description : 工作台service
 * @Author :  CyberCaelum
 * @Date: 2025-07-18 16:11
 */
public interface WorkspaceService {

    /**
     * @description: 查询今日运营数据
     * @author: CyberAstra
     * @date: 2025/7/18 at 16:36:01
     * @return: com.sky.vo.BusinessDataVO
     **/
    BusinessDataVO businessData();
}
