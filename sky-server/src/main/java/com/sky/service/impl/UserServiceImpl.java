package com.sky.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.sky.constant.JwtClaimsConstant;
import com.sky.constant.MessageConstant;
import com.sky.dto.UserLoginDTO;
import com.sky.entity.User;
import com.sky.exception.LoginFailedException;
import com.sky.mapper.UserMapper;
import com.sky.properties.JwtProperties;
import com.sky.properties.WeChatProperties;
import com.sky.service.UserService;
import com.sky.utils.HttpClientUtil;
import com.sky.utils.JwtUtil;
import com.sky.utils.WeChatLoginUtill;
import com.sky.vo.UserLoginVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName : UserServiceImpl
 * @Description : C端用户service实现类
 * @Author :  CyberCaelum
 * @Date: 2025-06-20 19:59
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private WeChatProperties weChatProperties;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private JwtProperties jwtProperties;

    /**
     * @description: 微信登录
     * @author: CyberAstra
     * @date: 2025/6/20 at 21:18:35
     * @param: userLoginDTO
     * @return: com.sky.vo.UserLoginVO
     **/
    @Override
    public UserLoginVO login(UserLoginDTO userLoginDTO) {

        //调用微信登录工具包，获得唯一标识
        String openid = WeChatLoginUtill.getOpenid(weChatProperties.getAppid(),
                weChatProperties.getSecret(), userLoginDTO.getCode());

        //判断是否正确获取openid
        if (openid==null || openid.equals("")){
            throw new LoginFailedException(MessageConstant.LOGIN_FAILED);
        }
        User user = userMapper.getByOpenid(openid);

        //判断是否是新用户
        if (user==null){
            //存入数据库
            user = User.builder()
                        .openid(openid)
                        .createTime(LocalDateTime.now())
                        .build();
            userMapper.insert(user);
        }

        //调用JwtUtil创建JWT
        Map<String,Object> map = new HashMap<String,Object>();
        map.put(JwtClaimsConstant.USER_ID,user.getId());
        String jwt = JwtUtil.createJWT(jwtProperties.getUserSecretKey(), jwtProperties.getUserTtl(), map);
        UserLoginVO userLoginVO = UserLoginVO.builder()
                .id(user.getId())
                .openid(openid)
                .token(jwt)
                .build();
        return userLoginVO;
    }
}
