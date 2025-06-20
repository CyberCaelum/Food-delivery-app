package com.sky.controller.user;

import com.sky.dto.UserLoginDTO;
import com.sky.result.Result;
import com.sky.service.UserService;
import com.sky.vo.UserLoginVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName : UserController
 * @Description : C端用户相关接口
 * @Author :  CyberCaelum
 * @Date: 2025-06-20 19:53
 */
@RestController
@RequestMapping("/user/user")
@Slf4j
@Api(tags = "C端用户相关接口")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * @description: 微信登录
     * @author: CyberAstra
     * @date: 2025/6/20 at 19:56:22
     * @param: userLoginDTO
     * @return: com.sky.result.Result
     **/
    @PostMapping("/login")
    @ApiOperation("微信登录")
    public Result login(@RequestBody UserLoginDTO userLoginDTO){
        UserLoginVO userLoginVO =  userService.login(userLoginDTO);
        return Result.success(userLoginVO);
    }
}
