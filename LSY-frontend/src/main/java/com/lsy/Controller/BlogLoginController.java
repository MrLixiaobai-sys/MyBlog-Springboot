package com.lsy.Controller;

import com.lsy.annotation.SystempLog;
import com.lsy.domain.ResponseResult;
import com.lsy.domain.entity.User;
import com.lsy.enums.BlogHttpCodeEnum;
import com.lsy.exception.SystemException;
import com.lsy.service.BlogLoginService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
@Api(tags = "登录", description = "登录和退出登录接口")
public class BlogLoginController {

    @Autowired
    private BlogLoginService blogLoginService;

    //登录接口
    @PostMapping("/login")
    @SystempLog(businessName = "用户登录")
    @ApiOperation(value = "登录接口", notes = "用户登录")
    @ApiImplicitParam(name = "user", value = "用户信息", required = true, dataType = "User")
    public ResponseResult login(@RequestBody User user) {

//        登录前校验用户名是否为空
        if(Objects.isNull(user.getUserName())){
            throw new SystemException(BlogHttpCodeEnum.REQUIRE_USERNAME);
        }

        //        登录前校验用户名是否为空
        if(Objects.isNull(user.getPassword())){
            throw new SystemException(BlogHttpCodeEnum.REQUIRE_PASSWORD);
        }


        return blogLoginService.login(user);
    }

    //退出登录接口
    @PostMapping("/logout")
    @SystempLog(businessName = "用户退出登录")
    @ApiOperation(value = "退出登录", notes = "用户退出登录")
    public ResponseResult logout(){
        return blogLoginService.logout();
    }

}
