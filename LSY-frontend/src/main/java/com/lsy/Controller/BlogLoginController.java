package com.lsy.Controller;

import com.lsy.annotation.SystempLog;
import com.lsy.domain.ResponseResult;
import com.lsy.domain.entity.User;
import com.lsy.enums.BlogHttpCodeEnum;
import com.lsy.exception.SystemException;
import com.lsy.service.BlogLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
public class BlogLoginController {

    @Autowired
    private BlogLoginService blogLoginService;

    //登录接口
    @PostMapping("/login")
    @SystempLog(businessName = "用户登录")
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
    public ResponseResult logout(){
        return blogLoginService.logout();
    }

}
