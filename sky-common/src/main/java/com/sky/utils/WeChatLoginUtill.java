package com.sky.utils;

import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName : WeChatLoginUtill
 * @Description : 微信登录获得凭证工具类
 * @Author :  CyberCaelum
 * @Date: 2025-06-20 20:56
 */
public class WeChatLoginUtill {

    public static final String WECHAT_LOGIN_URL = "https://api.weixin.qq.com/sns/jscode2session";

    /**
     * @description: 获得
     * @author: CyberAstra
     * @date: 2025/6/20 at 21:00:59
     * @param: map
     * @return: java.lang.String
     **/
    public static String getOpenid(String appid, String secret, String code){
        Map<String, String> map = new HashMap<>();
        map.put("appid",appid);
        map.put("secret",secret);
        map.put("js_code",code);
        map.put("grant_type","authorization_code");
        String json = HttpClientUtil.doGet(WECHAT_LOGIN_URL, map);
        JSONObject jasonObject = JSONObject.parseObject(json);
        return jasonObject.getString("openid");
    }
}
