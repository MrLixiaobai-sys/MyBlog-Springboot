package com.lsy.Controller;

import com.lsy.domain.ResponseResult;
import com.lsy.domain.dto.UserDTO;
import com.lsy.domain.dto.UserLoginDTO;
import com.lsy.enums.BlogHttpCodeEnum;
import com.lsy.exception.SystemException;
import com.lsy.service.BlogLoginService;
import com.lsy.service.UserService;
import com.lsy.utils.AuthGetUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("/user")
public class AdminLoginController {

    @Autowired
    private BlogLoginService blogLoginService;


    //登录接口
    @PostMapping("/login")
    public ResponseResult login(@RequestBody UserLoginDTO userdto){

        //        登录前校验用户名是否为空
        if(Objects.isNull(userdto.getUserName())){
            throw new SystemException(BlogHttpCodeEnum.REQUIRE_USERNAME);
        }

        //        登录前校验用户名是否为空
        if(Objects.isNull(userdto.getPassword())){
            throw new SystemException(BlogHttpCodeEnum.REQUIRE_PASSWORD);
        }

        return blogLoginService.AdminLogin(userdto);

    }

    //退出登录接口
    @PostMapping("/logout")
    public ResponseResult logout(){
        return blogLoginService.adminLogout();
    }

}
