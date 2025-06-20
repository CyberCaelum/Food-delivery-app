package com.sky.service;

import com.sky.dto.UserLoginDTO;
import com.sky.vo.UserLoginVO;

/**
 * @ClassName : UserService
 * @Description : C端用户service
 * @Author :  CyberCaelum
 * @Date: 2025-06-20 19:57
 */
public interface UserService {

    /**
     * @description: 微信登录
     * @author: CyberAstra
     * @date: 2025/6/20 at 21:19:49
     * @param: userLoginDTO
     * @return: com.sky.vo.UserLoginVO
     **/
    UserLoginVO login(UserLoginDTO userLoginDTO);
}
